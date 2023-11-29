package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.service.ProductService;

import java.util.List;

public class InventoryReport extends Report {
    private int productId;
    private String productName; // Optional, could be included for readability
    private int currentQuantity;
    private double price; // Current price, if you want to track the value of inventory
    private Inventory inventory;

    // Constructor with all fields
    public InventoryReport(int productId, String productName, int currentQuantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.currentQuantity = currentQuantity;
        this.price = price;
    }

    public InventoryReport() {
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "InventoryReport{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", currentQuantity=" + currentQuantity +
                ", price=" + price +
                '}';
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }


    public void setQuantity(int quantity) {
        this.currentQuantity = quantity;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.inventory.setLowStockThreshold(lowStockThreshold);
    }

}

