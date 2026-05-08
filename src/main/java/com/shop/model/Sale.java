package com.shop.model;

import java.util.Date;
import java.util.List;

public class Sale {

    private int id;
    private int customerId;
    private double total;
    private Date saleDate;
    private List<SaleItem> saleItems; // Added for billing/reporting

    // Constructor with all fields
    public Sale(int id, int customerId, double total, Date saleDate) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.saleDate = saleDate;
    }

    // Constructor for creating a new sale
    public Sale(int customerId, double total) {
        this.customerId = customerId;
        this.total = total;
    }

    // ===== GETTERS & SETTERS =====
    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getTotal() {
        return total;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}