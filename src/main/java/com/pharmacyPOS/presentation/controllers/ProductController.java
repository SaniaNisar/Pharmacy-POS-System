package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.ProductService;

import java.util.List;

public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    public List<Product> findProductsByName(String query)
    {
        return (productService.findProductsByName(query));
    }

    public void deleteProduct(int productId) {
        productService.deleteProduct(productId);
    }

    public void updateProduct(Product product) {
        productService.updateProduct(product);
    }

    public Product getProductById(int productId) {
        return productService.getProductById(productId);
    }

    public Product getProductByName(String name) {
        return productService.getProductByName(name);
    }

    public void createProduct(Product product) {
        productService.createProduct(product);
    }

    public List<Product> getProductsByCategory(String categoryName) {
        return productService.getProductsByCategory(categoryName);
    }

    // Additional methods for other operations can be added here...
}
