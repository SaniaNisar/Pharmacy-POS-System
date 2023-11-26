package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.entities.Product;
import java.util.List;

public class ProductController {

    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product getProductById(int productId) {
        try {
            return productDao.getProductById(productId);
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> getAllProducts() {
        try {
            return productDao.getAllProducts();
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return null;
        }
    }

    public boolean addProduct(Product product) {
        try {
            productDao.createProduct(product);
            return true;
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        try {
            productDao.updateProduct(product);
            return true;
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        try {
            productDao.deleteProduct(productId);
            return true;
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Searches for products by name.
     *
     * @param name The name or partial name of the product.
     * @return A list of products matching the search criteria.
     */
    public List<Product> searchProductsByName(String name) {
        try {
            return productDao.findProductsByName(name);
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the price of a product.
     *
     * @param productId The ID of the product.
     * @param newPrice  The new price to set.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateProductPrice(int productId, double newPrice) {
        try {
            Product product = productDao.getProductById(productId);
            if (product != null) {
                product.setPrice(newPrice);
                productDao.updateProduct(product);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves products by category.
     *
     * @param categoryId The ID of the category.
     * @return A list of products in the specified category.
     */
    public List<Product> getProductsByCategory(int categoryId) {
        try {
            return productDao.findProductsByCategory(categoryId);
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return null;
        }
    }

}