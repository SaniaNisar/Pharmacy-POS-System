package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.service.ProductService;

import java.sql.Date;

public class Inventory {
    private int inventoryId;
    private int productId;
    private int quantity;
    //private double price; // If price is specific to inventory or varies with batch, etc.
    private ProductService productService;
    private InventoryDao inventoryDao;
    // Default constructor
    public Inventory() {
    }

    // Parameterized constructor
    public Inventory(int inventoryId, int productId, int quantity, Date expiryDate) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
        //this.price = price;
        this.ExpiryDate=expiryDate;
    }
    private Date ExpiryDate;

    public void setExpiryDate(Date expiryDate) {
        ExpiryDate = expiryDate;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    // Getters and setters
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // toString method for debugging
    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
    private int lowStockThreshold; // Assuming this variable exists

    /**
     * Gets the low stock threshold for this inventory item.
     *
     * @return The low stock threshold.
     */
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    // Optionally, if you need to set this value:
    public void setLowStockThreshold(int threshold) {
        this.lowStockThreshold = threshold;
    }

    public Product getProductNameForInventoryItem() {
        return productService.getProductById(productId);
    }

    // Optionally, implement equals() and hashCode() methods if necessary.
}
