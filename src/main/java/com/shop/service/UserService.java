package com.shop.service;

import com.shop.dao.UserDAO;
import com.shop.model.User;

public class UserService {

    private final UserDAO dao = new UserDAO();

    public User login(String username, String password) {

        if (username == null || username.isEmpty()) return null;
        if (password == null || password.isEmpty()) return null;

        return dao.login(username, password);
    }
    public boolean addUser(User u) {

        if (u.getUsername() == null || u.getUsername().isEmpty()) return false;
        if (u.getPassword() == null || u.getPassword().isEmpty()) return false;

        return dao.addUser(u);
    }
}