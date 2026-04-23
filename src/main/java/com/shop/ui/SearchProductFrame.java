package com.shop.ui;

import com.shop.model.Product;
import com.shop.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchProductFrame extends JFrame {

    private JTextField searchField;
    private JTable table;
    private DefaultTableModel model;

    private final ProductService service = new ProductService();

    public SearchProductFrame() {

        setTitle("Search Product");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

        topPanel.add(new JLabel("Enter Name / Category: "));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Price", "Qty", "Category"}, 0
        );

        table = new JTable(model);

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

        // Try search by name
        List<Product> list = service.searchByName(keyword);

        // fallback to category search
        if (list.isEmpty()) {
            list = service.searchByCategory(keyword);
        }

        // clear table
        model.setRowCount(0);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No product found");
            return;
        }

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