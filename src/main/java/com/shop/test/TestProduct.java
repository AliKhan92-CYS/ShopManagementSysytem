package com.shop.test;

import com.shop.model.Product;
import com.shop.service.ProductService;

import java.util.List;

public class TestProduct {

    public static void main(String[] args) {

        ProductService service = new ProductService();

        //CREATE
        Product p1 = new Product("Keyboard", 50.0, 10, "Electronics");
        Product p2 = new Product("Apple", 2.5, 100, "Grocery");

        service.addProduct(p1);
        service.addProduct(p2);

        // READ
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

        // UPDATE
        System.out.println("\n🔄 Updating product with ID = 1");

        Product updated = new Product(1, "Gaming Keyboard", 80.0, 5, "Electronics");
        boolean updatedResult = service.updateProduct(updated);

        System.out.println(updatedResult ? "✔ Update Success" : "❌ Update Failed");

        // DELETE
        System.out.println("\n🗑 Deleting product with ID = 2");

        boolean deleteResult = service.deleteProduct(2);

        System.out.println(deleteResult ? "✔ Delete Success" : "❌ Delete Failed");

        // SEARCH
        System.out.println("\n🔍 Search by Name: 'Keyboard'");

        List<Product> searchList = service.searchProductByName("Keyboard");

        for (Product p : searchList) {
            System.out.println(p.getId() + " | " + p.getName());
        }

        System.out.println("\n🔍 Search by Category: 'Electronics'");

        List<Product> categoryList = service.searchProductByCategory("Electronics");

        for (Product p : categoryList) {
            System.out.println(p.getId() + " | " + p.getName() + " | " + p.getCategory());
        }
    }
}