package com.pharmacyPOS.service;
import com.pharmacyPOS.data.dao.ProductDao;
import java.util.List;
import com.pharmacyPOS.data.entities.Product;


public class ProductService
{
    private ProductDao productDao;  // Assume you have a ProductDAO class

    public ProductService(ProductDao productDAO) {
        this.productDao = productDAO;
    }

    // Service method to get all products
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public void deleteProduct(int productId)
    {
        productDao.deleteProduct(productId);
    }

    public void updateProduct(Product product)
    {
        productDao.updateProduct(product);
    }

    public Product getProductById(int productId)
    {
        return (productDao.getProductById(productId));
    }

    public Product getProductByName(String name)
    {
        return (productDao.getProductByName(name));
    }

    public void createProduct(Product product)
    {
        productDao.createProduct(product);
    }

    public List<Product> getProductsByCategory(String categoryName)
    {
        return (productDao.getProductsByCategory(categoryName));
    }
}
