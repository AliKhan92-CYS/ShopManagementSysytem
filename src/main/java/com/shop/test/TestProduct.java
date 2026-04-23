package com.shop.test;

import com.shop.model.Product;
import com.shop.service.ProductService;

import java.util.List;

public class TestProduct {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        // ================= CREATE =================
        Product p1 = new Product("Keyboard", 50.0, 10, "Electronics");
        service.addProduct(p1);

        Product p2 = new Product("Apple", 2.5, 100, "Grocery");
        service.addProduct(p2);

        // ================= READ =================
        System.out.println("\n📦 All Products:");

        List<Product> list = service.getAllProducts();

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
        Product updated = new Product(1, "Gaming Keyboard", 80.0, 5, "Electronics");
        service.updateProduct(updated);

        // ================= DELETE =================
        service.deleteProduct(2);

        // ================= STOCK CHECK =================
        int stock = service.getStockByCategory("Electronics");
        System.out.println("\nStock in Electronics: " + stock);
    }
}