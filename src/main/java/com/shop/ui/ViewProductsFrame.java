package com.shop.ui;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewProductsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewProductsFrame() {

        setTitle("All Products");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TABLE SETUP =====
        String[] columns = {"ID", "Name", "Price", "Qty", "Category"};

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table read-only
            }
        };

        table = new JTable(model);

        loadProducts();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel panel = new JPanel(new FlowLayout());

        JButton refreshBtn = new JButton("Refresh");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        panel.add(refreshBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);

        add(panel, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        refreshBtn.addActionListener(e -> loadProducts());
        editBtn.addActionListener(e -> editProduct());
        deleteBtn.addActionListener(e -> deleteProduct());

        setVisible(true);
    }

    // ===== LOAD DATA =====
    private void loadProducts() {
        model.setRowCount(0);

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
    }

    // ===== DELETE =====
    private void deleteProduct() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            ProductDAO.deleteProduct(id);
            JOptionPane.showMessageDialog(this, "Deleted successfully");
            loadProducts();
        }
    }

    // ===== EDIT =====
    private void editProduct() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String name = model.getValueAt(row, 1).toString();
        String price = model.getValueAt(row, 2).toString();
        String qty = model.getValueAt(row, 3).toString();
        String category = model.getValueAt(row, 4).toString();

        // input dialogs
        String newName = JOptionPane.showInputDialog(this, "Name:", name);
        String newPrice = JOptionPane.showInputDialog(this, "Price:", price);
        String newQty = JOptionPane.showInputDialog(this, "Quantity:", qty);
        String newCategory = JOptionPane.showInputDialog(this, "Category:", category);

        // validation
        if (newName == null || newPrice == null || newQty == null || newCategory == null ||
                newName.trim().isEmpty() || newPrice.trim().isEmpty() ||
                newQty.trim().isEmpty() || newCategory.trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Invalid input!");
            return;
        }

        try {
            double p = Double.parseDouble(newPrice);
            int q = Integer.parseInt(newQty);

            if (p < 0 || q < 0) {
                JOptionPane.showMessageDialog(this, "Values must be positive!");
                return;
            }

            ProductDAO.updateProduct(new Product(id, newName.trim(), p, q, newCategory.trim()));

            JOptionPane.showMessageDialog(this, "Updated successfully");
            loadProducts();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
        }
    }
}