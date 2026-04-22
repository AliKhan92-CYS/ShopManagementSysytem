package com.shop.ui;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import javax.swing.*;
import java.awt.*;

public class AddProductForm extends JFrame {

    private JTextField nameField, priceField, qtyField, categoryField;

    public AddProductForm() {

        setTitle("Add Product");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Fields
        nameField = new JTextField();
        priceField = new JTextField();
        qtyField = new JTextField();
        categoryField = new JTextField();

        JButton addBtn = new JButton("Add Product");

        // Layout
        add(new JLabel("Name:"));
        add(nameField);

        add(new JLabel("Price:"));
        add(priceField);

        add(new JLabel("Quantity:"));
        add(qtyField);

        add(new JLabel("Category:"));
        add(categoryField);

        add(new JLabel());
        add(addBtn);

        // Action
        addBtn.addActionListener(e -> addProduct());

        setVisible(true);
    }

    private void addProduct() {

        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String qtyText = qtyField.getText().trim();
        String category = categoryField.getText().trim();

        // ✅ VALIDATION
        if (name.isEmpty() || priceText.isEmpty() || qtyText.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        double price;
        int qty;

        try {
            price = Double.parseDouble(priceText);
            qty = Integer.parseInt(qtyText);

            if (price < 0 || qty < 0) {
                JOptionPane.showMessageDialog(this, "Price and Quantity must be positive!");
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
            return;
        }

        // Save
        ProductDAO.addProduct(new Product(name, price, qty, category));

        JOptionPane.showMessageDialog(this, "✔ Product Added Successfully");

        // Clear fields
        nameField.setText("");
        priceField.setText("");
        qtyField.setText("");
        categoryField.setText("");
    }
}