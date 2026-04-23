package com.shop.ui;

import com.shop.model.Product;
import com.shop.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class AddProductForm extends JFrame {

    private JTextField nameField, priceField, qtyField, categoryField;
    private final ProductService service = new ProductService();

    public AddProductForm() {

        setTitle("Add Product");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();
        priceField = new JTextField();
        qtyField = new JTextField();
        categoryField = new JTextField();

        JButton addBtn = new JButton("Add Product");

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

        addBtn.addActionListener(e -> addProduct());

        setVisible(true);
    }

    private void addProduct() {

        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String qtyText = qtyField.getText().trim();
        String category = categoryField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty() || qtyText.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int qty = Integer.parseInt(qtyText);

            Product p = new Product(name, price, qty, category);

            boolean success = service.addProduct(p);

            if (success) {
                JOptionPane.showMessageDialog(this, "✔ Product Added");
                nameField.setText("");
                priceField.setText("");
                qtyField.setText("");
                categoryField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid input");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
        }
    }
}