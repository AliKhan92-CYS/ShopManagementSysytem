package com.shop;

import com.shop.db.DBConnection;
import com.shop.ui.LoginFrame;

public class App {
    public static void main(String[] args) {

        //  Ensure database and all tables exist before app starts
        DBConnection.getConnection();

        // Open login screen
        new LoginFrame();
    }
}