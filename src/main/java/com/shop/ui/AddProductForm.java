package com.shop.ui;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import javax.swing.*;
import java.awt.*;

public class AddProductForm extends JFrame {

    public AddProductForm() {

        setTitle("Add Product");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JTextField name = new JTextField();
        JTextField price = new JTextField();
        JTextField qty = new JTextField();
        JTextField category = new JTextField();

        JButton save = new JButton("Save");

        add(new JLabel("Name"));
        add(name);

        add(new JLabel("Price"));
        add(price);

        add(new JLabel("Quantity"));
        add(qty);

        add(new JLabel("Category"));
        add(category);

        add(save);

        save.addActionListener(e -> {

            Product p = new Product(
                    name.getText(),
                    Double.parseDouble(price.getText()),
                    Integer.parseInt(qty.getText()),
                    category.getText()
            );

            ProductDAO.addProduct(p);

            JOptionPane.showMessageDialog(this, "Product Added!");
            dispose();
        });

        setVisible(true);
    }
}
