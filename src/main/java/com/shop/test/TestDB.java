package com.shop.test;

import com.shop.db.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("🎉 Database Working Perfectly!");
        } else {
            System.out.println("⚠️ Database Connection Failed");
        }
    }
}