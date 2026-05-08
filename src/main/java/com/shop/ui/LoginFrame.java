package com.shop.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.shop.dao.UserDAO;
import com.shop.model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO dao = new UserDAO();

    public LoginFrame() {

        FlatLightLaf.setup();

        setTitle("Login");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        add(mainPanel);

        // LEFT (AVATAR IMAGE)
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(new Color(245, 247, 250));

        ImageIcon icon = new ImageIcon(
                getClass().getResource("/images/avataar.jpg")
        );

        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel avatar = new JLabel(new ImageIcon(img));

        left.add(avatar);
        mainPanel.add(left);

        //  RIGHT (FORM)
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        // Rounded Borders
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );

        usernameField.setBorder(roundedBorder);
        passwordField.setBorder(roundedBorder);

        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JButton loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //  Button hover effect
        loginBtn.setBackground(new Color(60, 130, 246));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(40, 110, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginBtn.setBackground(new Color(60, 130, 246));
            }
        });

        //  Show Password
        JCheckBox showPassword = new JCheckBox("Show Password");
        char defaultEcho = passwordField.getEchoChar();

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar(defaultEcho);
            }
        });

        right.add(title);
        right.add(Box.createRigidArea(new Dimension(0, 20)));

        right.add(new JLabel("Username"));
        right.add(usernameField);
        right.add(Box.createRigidArea(new Dimension(0, 10)));

        right.add(new JLabel("Password"));
        right.add(passwordField);
        right.add(showPassword);

        right.add(Box.createRigidArea(new Dimension(0, 20)));
        right.add(loginBtn);

        mainPanel.add(right);

        // ACTION
        loginBtn.addActionListener(e -> login());
        passwordField.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields");
            return;
        }

        User user = dao.login(username, password);

        if (user != null) {

            JOptionPane.showMessageDialog(this, "Login Successful ✔");

            new MainFrame(user);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password ❌");
        }
    }
}