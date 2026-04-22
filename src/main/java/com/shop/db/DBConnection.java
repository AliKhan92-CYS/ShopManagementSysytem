package com.shop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:shop.db";

    public static Connection getConnection() {

        try {
            Connection conn = DriverManager.getConnection(URL);

            // Create table automatically if not exists
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "price REAL," +
                    "quantity INTEGER," +
                    "category TEXT" +
                    ")";

            stmt.execute(sql);

            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}