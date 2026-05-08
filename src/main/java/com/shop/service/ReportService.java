package com.shop.service;

import com.shop.dao.SaleDAO;
import com.shop.dao.ProductDAO;
import com.shop.model.Product;
import com.shop.model.Sale;
import com.shop.model.SaleItem;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    private final SaleDAO saleDAO = new SaleDAO();
    private final ProductDAO productDAO = new ProductDAO();

    // Total sales per product
    public Map<String, Integer> getProductSalesQuantity() {

        List<Product> products = productDAO.getAllProducts();
        Map<String, Integer> map = new LinkedHashMap<>();

        for (Product p : products) {
            map.put(p.getName(), 0);
        }

        List<Sale> sales = saleDAO.getAllSales();

        // Aggregate quantities from sale_items
        for (Sale s : sales) {
            s.getSaleItems().forEach(item -> {
                String name = productDAO.getProductById(item.getProductId()).getName();
                map.put(name, map.getOrDefault(name, 0) + item.getQuantity());
            });
        }

        return map;
    }

    // Total sales per category
    public Map<String, Integer> getCategorySalesQuantity() {
        List<Product> products = productDAO.getAllProducts();
        Map<String, Integer> map = new LinkedHashMap<>();

        for (Product p : products) {
            map.put(p.getCategory(), 0);
        }

        List<Sale> sales = saleDAO.getAllSales();

        for (Sale s : sales) {
            s.getSaleItems().forEach(item -> {
                Product p = productDAO.getProductById(item.getProductId());
                map.put(p.getCategory(), map.getOrDefault(p.getCategory(), 0) + item.getQuantity());
            });
        }

        return map;
    }

    // Total revenue per day (date vs total)
    public Map<String, Double> getDailyRevenue() {

        List<Sale> sales = saleDAO.getAllSales();

        Map<String, Double> map = new TreeMap<>();

        for (Sale s : sales) {
            String day = s.getSaleDate().toString().substring(0,10); // yyyy-mm-dd
            map.put(day, map.getOrDefault(day, 0.0) + s.getTotal());
        }

        return map;
    }
}
