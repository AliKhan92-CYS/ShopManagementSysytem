package com.shop.ui;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ViewProductsFrame extends JFrame {

    public ViewProductsFrame() {

        setTitle("All Products");
        setSize(500, 400);
        setLocationRelativeTo(null);

        String[] columns = {"ID", "Name", "Price", "Qty", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        List<Product> list = ProductDAO.getAllProducts();

        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory()
            });
        }

        add(new JScrollPane(table));

        setVisible(true);
    }
}
