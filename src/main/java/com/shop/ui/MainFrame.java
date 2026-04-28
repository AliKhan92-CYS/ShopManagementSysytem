package com.shop.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.shop.model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private final User user;

    public MainFrame(User user) {

        this.user = user;

        FlatLightLaf.setup();

        setTitle("Shop System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel title = new JLabel("Shop Management System");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel userInfo = new JLabel("User: " + user.getUsername() + " (" + user.getRole() + ")");
        userInfo.setForeground(Color.GRAY);

        header.add(title, BorderLayout.WEST);
        header.add(userInfo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ================= SIDEBAR =================
        SidebarPanel sidebar = new SidebarPanel(user, this::switchPanel);
        add(sidebar, BorderLayout.WEST);

        // ================= CONTENT =================
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // ================= DEFAULT =================
        switchPanel("Dashboard");

        setVisible(true);
    }

    // ================= PANEL SWITCH =================
    private void switchPanel(String name) {

        contentPanel.removeAll();

        switch (name) {

            case "Dashboard" -> contentPanel.add(new DashboardPanel(user));

            case "Products" -> contentPanel.add(new ProductPanel(user));

            case "Manage Users" -> new ManageUsersFrame(user);

            default -> contentPanel.add(
                    new JLabel("Coming Soon...", SwingConstants.CENTER)
            );
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}