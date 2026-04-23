package com.shop.service;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import java.util.List;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();

    public boolean addProduct(Product p) {

        if (p.getName() == null || p.getName().isEmpty()) return false;
        if (p.getPrice() < 0) return false;
        if (p.getQuantity() < 0) return false;

        return dao.addProduct(p);
    }

    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    public boolean updateProduct(Product p) {
        return dao.updateProduct(p);
    }

    public boolean deleteProduct(int id) {
        return dao.deleteProduct(id);
    }

    public List<Product> searchByName(String name) {
        return dao.searchByName(name);
    }

    public List<Product> searchByCategory(String category) {
        return dao.searchByCategory(category);
    }

    public int getStockByCategory(String category) {

        int total = 0;

        for (Product p : dao.searchByCategory(category)) {
            total += p.getQuantity();
        }

        return total;
    }
}