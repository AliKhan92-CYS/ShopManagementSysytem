package com.shop.test;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import java.util.List;

public class TestProduct {

    public static void main(String[] args) {

        // ================= CREATE =================
        Product p1 = new Product("Keyboard", 50.0, 10, "Electronics");
        ProductDAO.addProduct(p1);

        Product p2 = new Product("Apple", 2.5, 100, "Grocery");
        ProductDAO.addProduct(p2);

        // ================= READ =================
        System.out.println("\n📦 All Products:");
        List<Product> list = ProductDAO.getAllProducts();

        for (Product p : list) {
            System.out.println(
                    p.getId() + " | " +
                            p.getName() + " | " +
                            p.getPrice() + " | " +
                            p.getQuantity() + " | " +
                            p.getCategory()
            );
        }

        // ================= UPDATE =================
        // (Make sure ID exists in your DB)
        Product updated = new Product(1, "Gaming Keyboard", 80.0, 5, "Electronics");
        ProductDAO.updateProduct(updated);

        // ================= DELETE =================
        // (Be careful: this will remove data)
        ProductDAO.deleteProduct(2);

        // ================= CATEGORY STOCK =================
        ProductDAO.getStockByCategory("Electronics");
    }
}