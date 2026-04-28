package com.shop.ui;

import com.shop.model.Product;
import com.shop.model.User;
import com.shop.service.ProductService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardPanel extends JPanel {

    private final ProductService service = new ProductService();
    private final User user;

    private JPanel centerPanel;

    public DashboardPanel(User user) {

        this.user = user;

        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, " + user.getUsername());
        welcome.setFont(new Font("Arial", Font.BOLD, 20));

        JButton refreshBtn = new JButton("Refresh");

        header.add(welcome, BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ================= CENTER =================
        centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setOpaque(false);

        add(centerPanel, BorderLayout.CENTER);

        // ================= CARDS =================
        JPanel cards = new JPanel(new GridLayout(1, 2, 15, 15));
        cards.setOpaque(false);

        cards.add(createCard("Total Products", getTotalProducts()));
        cards.add(createCard("Total Quantity", getTotalQuantity()));

        add(cards, BorderLayout.SOUTH);

        // Load content
        loadDashboard();

        refreshBtn.addActionListener(e -> refreshDashboard());
    }

    // ================= LOAD =================
    private void loadDashboard() {

        centerPanel.removeAll();

        // LEFT → actual chart
        centerPanel.add(createCategoryChart());

        // RIGHT → placeholder for future features
        centerPanel.add(createPlaceholderPanel());

        refreshUI();
    }

    private void refreshDashboard() {
        loadDashboard();
    }

    // ================= CATEGORY CHART =================
    private ChartPanel createCategoryChart() {

        List<Product> list = service.getAllProducts();

        Map<String, Integer> categoryMap = new HashMap<>();

        for (Product p : list) {
            categoryMap.put(
                    p.getCategory(),
                    categoryMap.getOrDefault(p.getCategory(), 0) + p.getQuantity()
            );
        }

        DefaultPieDataset dataset = new DefaultPieDataset();

        for (String key : categoryMap.keySet()) {
            dataset.setValue(key, categoryMap.get(key));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Products by Category",
                dataset,
                true,
                true,
                false
        );

        return new ChartPanel(chart);
    }

    // ================= PLACEHOLDER =================
    private JPanel createPlaceholderPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createTitledBorder("Future Analytics"));

        JLabel label = new JLabel("Charts coming soon...");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.GRAY);

        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    // ================= CARD =================
    private JPanel createCard(String title, int value) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 247, 250));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // ================= CALCULATIONS =================
    private int getTotalProducts() {
        return service.getAllProducts().size();
    }

    private int getTotalQuantity() {
        int total = 0;
        for (Product p : service.getAllProducts()) {
            total += p.getQuantity();
        }
        return total;
    }

    private void refreshUI() {
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}