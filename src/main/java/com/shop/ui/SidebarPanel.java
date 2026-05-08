package com.shop.ui;

import com.shop.model.User;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {

    public interface NavigationListener {
        void onNavigate(String destination);
    }

    public SidebarPanel(User user, NavigationListener listener) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(180, 0));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        // NAV BUTTONS
        add(Box.createVerticalStrut(20));

        // Existing buttons
        add(createButton("Dashboard", listener));
        add(createButton("Products", listener));

        if (user.isAdmin()) {
            add(createButton("Manage Users", listener));
        }

        // ================= NEW MODULE BUTTONS =================
        add(createButton("Customers", listener));
        add(createButton("Sales", listener));
        add(createButton("Reports", listener));

        add(Box.createVerticalGlue());
    }

    private JButton createButton(String text, NavigationListener listener) {

        JButton btn = new JButton(text);

        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);

        btn.setFont(new Font("Arial", Font.PLAIN, 14));

        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        //  Hover effect (light gray)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 240, 240));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });

        btn.addActionListener(e -> listener.onNavigate(text));

        return btn;
    }
}