package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Product;

import java.sql.Date;

public class ReplenishmentService {
    private final InventoryDao inventoryDao;
    private final ProductService productService;

    public ReplenishmentService(InventoryDao inventoryDao, ProductService productService) {
        this.inventoryDao = inventoryDao;
        this.productService = productService;
    }

    // Method to replenish inventory on the arrival of new stock
    public void replenishInventory(int productId, int quantity, Date expiryDate, int lowStockThreshold) {
        // Check if there is an existing inventory record for the product
        Inventory existingInventory = inventoryDao.getInventoryByProductId(productId);

        if (existingInventory != null) {
            // Update the quantity for the existing inventory record
            existingInventory.setQuantity(existingInventory.getQuantity() + quantity);
            inventoryDao.updateInventoryItem(existingInventory);
        } else {
            // Create a new inventory record if none exists
            Inventory newInventory = new Inventory(0, productId, quantity, expiryDate, lowStockThreshold);
            inventoryDao.createInventoryItem(newInventory);
        }
    }

    // Optional: Method to reduce inventory when items are sold
    public void sellInventory(int productId, int quantity) {
        Inventory existingInventory = inventoryDao.getInventoryByProductId(productId);

        if (existingInventory != null) {
            // Check if there's enough inventory to fulfill the sale
            if (existingInventory.getQuantity() >= quantity) {
                // Update the quantity for the existing inventory record
                existingInventory.setQuantity(existingInventory.getQuantity() - quantity);
                inventoryDao.updateInventoryItem(existingInventory);
            } else {
                // Handle insufficient inventory (throw an exception, log, etc.)
                System.out.println("Insufficient inventory for product ID: " + productId);
            }
        } else {
            // Handle missing inventory (throw an exception, log, etc.)
            System.out.println("Inventory not found for product ID: " + productId);
        }
    }

    // Optional: Method to get product name for an inventory item
    public String getProductNameForInventoryItem(int productId) {
        Product product = productService.getProductById(productId);
        return (product != null) ? product.getName() : "Product not found";
    }
}
