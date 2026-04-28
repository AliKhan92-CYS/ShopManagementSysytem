package com.shop.menu;

import com.shop.model.Product;
import com.shop.service.ProductService;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    static Scanner sc = new Scanner(System.in);
    static ProductService service = new ProductService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== SHOP MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Enter valid number!");
                continue;
            }

            switch (choice) {

                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> searchMenu();
                case 6 -> {
                    System.out.println("👋 Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ================= ADD =================
    static void addProduct() {

        try {
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Quantity: ");
            int qty = Integer.parseInt(sc.nextLine());

            System.out.print("Category: ");
            String cat = sc.nextLine();

            boolean success = service.addProduct(
                    new Product(name, price, qty, cat)
            );

            System.out.println(success ? "✔ Product Added" : "❌ Invalid input");

        } catch (Exception e) {
            System.out.println("❌ Invalid input format!");
        }
    }

    // ================= VIEW =================
    static void viewProducts() {

        List<Product> list = service.getAllProducts();

        if (list.isEmpty()) {
            System.out.println("No products found");
            return;
        }

        for (Product p : list) {
            System.out.println(p.getId() + " | " +
                    p.getName() + " | " +
                    p.getPrice() + " | " +
                    p.getQuantity() + " | " +
                    p.getCategory());
        }
    }

    // ================= UPDATE =================
    static void updateProduct() {

        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("New Name: ");
            String name = sc.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Qty: ");
            int qty = Integer.parseInt(sc.nextLine());

            System.out.print("Category: ");
            String cat = sc.nextLine();

            boolean success = service.updateProduct(
                    new Product(id, name, price, qty, cat)
            );

            System.out.println(success ? "✔ Updated" : "❌ Failed");

        } catch (Exception e) {
            System.out.println("❌ Invalid input format!");
        }
    }

    // ================= DELETE =================
    static void deleteProduct() {

        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            boolean success = service.deleteProduct(id);

            System.out.println(success ? "✔ Deleted" : "❌ Failed");

        } catch (Exception e) {
            System.out.println("❌ Invalid ID!");
        }
    }

    // ================= SEARCH MENU =================
    static void searchMenu() {

        while (true) {

            System.out.println("\n===== SEARCH MENU =====");
            System.out.println("1. Search by Name");
            System.out.println("2. Search by Category");
            System.out.println("3. Back");

            System.out.print("Enter choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Invalid input");
                continue;
            }

            switch (ch) {
                case 1 -> searchByName();
                case 2 -> searchByCategory();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ================= SEARCH NAME =================
    static void searchByName() {

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        List<Product> list = service.searchProductByName(name);

        if (list.isEmpty()) {
            System.out.println("❌ No product found");
            return;
        }

        for (Product p : list) {
            System.out.println(p.getId() + " | " + p.getName());
        }
    }

    // ================= SEARCH CATEGORY =================
    static void searchByCategory() {

        System.out.print("Enter category: ");
        String cat = sc.nextLine();

        List<Product> list = service.searchProductByCategory(cat);

        if (list.isEmpty()) {
            System.out.println("❌ No products found");
            return;
        }

        for (Product p : list) {
            System.out.println(p.getId() + " | " +
                    p.getName() + " | " +
                    p.getCategory());
        }
    }
}