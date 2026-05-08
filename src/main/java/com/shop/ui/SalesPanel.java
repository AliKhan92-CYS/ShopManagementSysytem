package com.shop.ui;

import com.shop.dao.CustomerDAO;
import com.shop.dao.ProductDAO;
import com.shop.dao.SaleDAO;
import com.shop.model.Customer;
import com.shop.model.Product;
import com.shop.model.Sale;
import com.shop.model.SaleItem;
import com.shop.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SalesPanel extends JPanel {

    private final User user; // logged-in user for role checks

    private JComboBox<Customer> customerBox;
    private JComboBox<Product> productBox;

    private JTextField qtyField;

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;

    private double total = 0;

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final SaleDAO saleDAO = new SaleDAO();

    private final List<SaleItem> saleItems = new ArrayList<>();

    public SalesPanel(User user) {
        this.user = user;

        setLayout(new BorderLayout(10,10));

        // ================= TOP =================
        JPanel top = new JPanel(new GridLayout(2,1));

        JPanel customerPanel = new JPanel();
        customerBox = new JComboBox<>();
        loadCustomers();
        customerPanel.add(new JLabel("Customer:"));
        customerPanel.add(customerBox);
        top.add(customerPanel);

        JPanel productPanel = new JPanel();
        productBox = new JComboBox<>();
        loadProducts();
        qtyField = new JTextField(5);
        JButton addBtn = new JButton("Add Item");

        productPanel.add(new JLabel("Product:"));
        productPanel.add(productBox);
        productPanel.add(new JLabel("Qty:"));
        productPanel.add(qtyField);
        productPanel.add(addBtn);
        top.add(productPanel);

        add(top, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{"Product","Qty","Price","Subtotal"},
                0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= BOTTOM =================
        JPanel bottom = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total: 0");
        JButton billBtn = new JButton("Generate Bill");
        bottom.add(totalLabel, BorderLayout.WEST);
        bottom.add(billBtn, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        // ================= ROLE CONTROL =================
        if(!user.isAdmin()){
            addBtn.setEnabled(false);
            billBtn.setEnabled(false);
            productBox.setEnabled(false);
            qtyField.setEnabled(false);
        }

        // ================= ACTIONS =================
        addBtn.addActionListener(e -> addItem());
        billBtn.addActionListener(e -> generateBill());
    }

    // ================= LOAD CUSTOMERS =================
    private void loadCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        customerBox.removeAllItems();
        for (Customer c : customers) {
            customerBox.addItem(c);
        }
    }

    // ================= LOAD PRODUCTS =================
    private void loadProducts() {
        List<Product> products = productDAO.getAllProducts();
        productBox.removeAllItems();
        for (Product p : products) {
            productBox.addItem(p);
        }
    }

    // ================= ADD ITEM =================
    private void addItem() {
        try {
            Product p = (Product) productBox.getSelectedItem();
            int qty = Integer.parseInt(qtyField.getText());

            if(qty <= 0){
                JOptionPane.showMessageDialog(this,"Invalid quantity");
                return;
            }
            if(qty > p.getQuantity()){
                JOptionPane.showMessageDialog(this,"Not enough stock");
                return;
            }

            double subtotal = qty * p.getPrice();
            total += subtotal;
            totalLabel.setText("Total: " + total);

            model.addRow(new Object[]{p.getName(), qty, p.getPrice(), subtotal});

            saleItems.add(new SaleItem(p.getId(), qty, p.getPrice()));

        } catch (Exception e){
            JOptionPane.showMessageDialog(this,"Invalid quantity");
        }
    }

    // ================= GENERATE BILL =================
    private void generateBill() {
        if(saleItems.isEmpty()){
            JOptionPane.showMessageDialog(this,"Add items first");
            return;
        }

        Customer c = (Customer) customerBox.getSelectedItem();
        Sale sale = new Sale(c.getId(), total);

        boolean success = saleDAO.createSale(sale, saleItems);

        if(success){
            JOptionPane.showMessageDialog(this,"Bill Generated Successfully");
            model.setRowCount(0);
            saleItems.clear();
            total = 0;
            totalLabel.setText("Total: 0");
        } else {
            JOptionPane.showMessageDialog(this,"Failed to generate bill");
        }
    }
}