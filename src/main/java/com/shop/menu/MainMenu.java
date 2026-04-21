package com.shop.menu;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== SHOP MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Check Stock by Category");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> searchProduct();
                case 6 -> checkStock();
                case 7 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void addProduct() {
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = sc.nextDouble();

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();

        ProductDAO.addProduct(new Product(name, price, qty, cat));
    }

    static void viewProducts() {
        List<Product> list = ProductDAO.getAllProducts();
        for (Product p : list) {
            System.out.println(p.getId() + " " + p.getName() + " " + p.getPrice());
        }
    }

    static void updateProduct() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("New Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = sc.nextDouble();

        System.out.print("Qty: ");
        int qty = sc.nextInt();

        sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();

        ProductDAO.updateProduct(new Product(id, name, price, qty, cat));
    }

    static void deleteProduct() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        ProductDAO.deleteProduct(id);
    }

    static void searchProduct() {
        sc.nextLine();
        System.out.print("Search: ");
        String name = sc.nextLine();

        List<Product> list = ProductDAO.searchProductByName(name);
        for (Product p : list) {
            System.out.println(p.getName());
        }
    }

    static void checkStock() {
        sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();

        ProductDAO.getStockByCategory(cat);
    }
}