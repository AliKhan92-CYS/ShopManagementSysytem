package com.shop.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection conn;

    public static Connection getConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/shop.db";
                conn = DriverManager.getConnection(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}