package com.shop.ui;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchProductFrame extends JFrame {

    private JTextField searchField;
    private JTable table;
    private DefaultTableModel model;

    public SearchProductFrame() {

        setTitle("Search Product");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel();

        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

        topPanel.add(new JLabel("Enter Name / Category: "));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Qty");
        model.addColumn("Category");

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= ACTION =================
        searchBtn.addActionListener(e -> searchProducts());

        setVisible(true);
    }

    // ================= SEARCH LOGIC =================
    private void searchProducts() {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search text");
            return;
        }

        // Try name search first
        List<Product> list = ProductDAO.searchProductByName(keyword);

        // If empty, try category search
        if (list.isEmpty()) {
            list = ProductDAO.searchProductByCategory(keyword);
        }

        // Clear table
        model.setRowCount(0);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No product found");
            return;
        }

        // Fill table
        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory()
            });
        }
    }
}