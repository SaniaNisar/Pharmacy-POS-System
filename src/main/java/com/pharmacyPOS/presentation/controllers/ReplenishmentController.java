package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.service.ProductService;
import com.pharmacyPOS.service.ReplenishmentService;

import java.sql.Date;

public class ReplenishmentController {
    private final ReplenishmentService replenishmentService;

    public ReplenishmentController(ReplenishmentService replenishmentService) {
        this.replenishmentService = replenishmentService;
    }

    public void replenishInventory(int productId, int quantity, Date expiryDate, int lowStockThreshold) {
        replenishmentService.replenishInventory(productId, quantity, expiryDate, lowStockThreshold);
        System.out.println("Inventory replenished for product ID: " + productId);
    }

    // Method to handle selling inventory when items are sold
    public void sellInventory(int productId, int quantity) {
        replenishmentService.sellInventory(productId, quantity);
        System.out.println("Inventory sold for product ID: " + productId);
    }

    public String getProductNameForInventoryItem(int productId) {
        return replenishmentService.getProductNameForInventoryItem(productId);
    }

}

