package com.shop.ui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        // Apply FlatLaf theme
        FlatLightLaf.setup();

        setTitle("Shop Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton addBtn = new JButton("Add Product");
        JButton viewBtn = new JButton("View Products");
        JButton searchBtn = new JButton("Search Product");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        panel.add(addBtn);
        panel.add(viewBtn);
        panel.add(searchBtn);

        add(panel);
        // ================= FIXED ACTIONS =================

        addBtn.addActionListener(e -> {
            new AddProductForm().setVisible(true);
        });

        viewBtn.addActionListener(e -> {
            new ViewProductsFrame().setVisible(true);
        });

        searchBtn.addActionListener(e -> {
            new SearchProductFrame().setVisible(true); // ❗ FIXED missing action
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}