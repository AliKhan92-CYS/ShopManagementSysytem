package com.shop.service;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;

import java.util.List;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    public boolean addProduct(Product p) {

        if (p.getName().isEmpty() || p.getPrice() < 0 || p.getQuantity() < 0) {
            return false;
        }

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

    // SEARCH
    public List<Product> searchProductByName(String name) {
        return dao.searchProductByName(name);
    }

    public List<Product> searchProductByCategory(String category) {
        return dao.searchProductByCategory(category);
    }
}