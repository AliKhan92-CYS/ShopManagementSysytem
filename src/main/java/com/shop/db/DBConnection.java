package com.shop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/shop.db";
                conn = DriverManager.getConnection(url);

                // 🔥 Initialize DB tables automatically
                initializeDatabase(conn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    // ================= DATABASE INITIALIZER =================
    private static void initializeDatabase(Connection conn) {

        try (Statement stmt = conn.createStatement()) {

            // USERS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE,
                    password TEXT,
                    role TEXT
                )
            """);

            // PRODUCTS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS products (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    price REAL,
                    quantity INTEGER,
                    category TEXT
                )
            """);

            // CUSTOMERS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    phone TEXT,
                    email TEXT
                )
            """);

            // SALES
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS sales (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_id INTEGER,
                    total REAL,
                    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // SALE ITEMS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS sale_items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sale_id INTEGER,
                    product_id INTEGER,
                    quantity INTEGER,
                    price REAL
                )
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}