package com.shop.dao;

import com.shop.db.DBConnection;
import com.shop.model.Sale;
import com.shop.model.SaleItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    // ================= CREATE SALE =================
    public boolean createSale(Sale sale, List<SaleItem> items) {

        String saleSQL =
                "INSERT INTO sales(customer_id, total) VALUES (?, ?)";

        String itemSQL =
                "INSERT INTO sale_items(sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        String stockSQL =
                "UPDATE products SET quantity = quantity - ? WHERE id=?";

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // ================= INSERT SALE =================
            PreparedStatement saleStmt =
                    conn.prepareStatement(saleSQL, Statement.RETURN_GENERATED_KEYS);

            saleStmt.setInt(1, sale.getCustomerId());
            saleStmt.setDouble(2, sale.getTotal());

            saleStmt.executeUpdate();

            ResultSet rs = saleStmt.getGeneratedKeys();

            int saleId = 0;

            if (rs.next()) {
                saleId = rs.getInt(1);
            }

            // ================= INSERT ITEMS =================
            PreparedStatement itemStmt = conn.prepareStatement(itemSQL);
            PreparedStatement stockStmt = conn.prepareStatement(stockSQL);

            for (SaleItem item : items) {

                // sale_items
                itemStmt.setInt(1, saleId);
                itemStmt.setInt(2, item.getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getPrice());
                itemStmt.addBatch();

                // stock update
                stockStmt.setInt(1, item.getQuantity());
                stockStmt.setInt(2, item.getProductId());
                stockStmt.addBatch();
            }

            itemStmt.executeBatch();
            stockStmt.executeBatch();

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();

            // 🔥 IMPORTANT FIX: rollback
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    // ================= GET SALES =================
    public List<Sale> getAllSales() {

        List<Sale> list = new ArrayList<>();

        String sql = "SELECT * FROM sales ORDER BY sale_date DESC";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                Sale sale = new Sale(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("total"),
                        rs.getTimestamp("sale_date")
                );

                // attach sale items
                sale.setSaleItems(getSaleItemsBySaleId(sale.getId()));

                list.add(sale);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= GET SALE ITEMS =================
    public List<SaleItem> getSaleItemsBySaleId(int saleId) {

        List<SaleItem> items = new ArrayList<>();
        String sql = "SELECT * FROM sale_items WHERE sale_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, saleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                items.add(new SaleItem(
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    // ================= SALES BY DATE (FOR DASHBOARD) =================
    public List<Object[]> getSalesByDate() {

        List<Object[]> list = new ArrayList<>();

        String sql =
                "SELECT DATE(sale_date) as sdate, SUM(total) as total " +
                        "FROM sales GROUP BY DATE(sale_date) ORDER BY sdate";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getString("sdate"),
                        rs.getDouble("total")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}