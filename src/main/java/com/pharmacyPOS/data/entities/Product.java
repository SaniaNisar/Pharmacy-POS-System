package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.dao.ProductDao;

import java.util.Date;

public class Product {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int categoryId;
    private Date expirationDate;
    private ProductDao productDao;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor for all fields
    public Product(int productId, String name, String description, double price, int categoryId, Date expirationDate) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.expirationDate = expirationDate;
    }

    public Product(String name, String description, double price, int categoryId, Date expirationDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", expirationDate=" + expirationDate +
                '}';
    }
    public Product getProduct(int productId)
    {
        return productDao.getProductById(productId);
    }


    // Implement equals and hashCode if necessary.
}
