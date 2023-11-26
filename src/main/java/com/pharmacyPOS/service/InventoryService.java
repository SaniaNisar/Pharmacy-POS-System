package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.InventoryReport;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final InventoryDao inventoryDao;

    public InventoryService(DatabaseConnection databaseConnection) {
        this.inventoryDao = new InventoryDao(databaseConnection);
    }

    public Inventory getInventoryByProductId(int productId) {
        return inventoryDao.getInventoryByProductId(productId);
    }

    public List<Inventory> getAllInventory() {
        return inventoryDao.getAllInventory();
    }

    public void createInventoryItem(Inventory inventory) {
        inventoryDao.createInventoryItem(inventory);
    }

    public void updateInventoryItem(Inventory inventory) {
        inventoryDao.updateInventoryItem(inventory);
    }

    public void deleteInventoryItem(int inventoryId) {
        inventoryDao.deleteInventoryItem(inventoryId);
    }

    /**
     * Checks if the stock level of an inventory item is below its low stock threshold.
     *
     * @param item The inventory item to check.
     * @return true if the stock is low, false otherwise.
     */
    public boolean isStockLow(Inventory item) {
        if (item == null) {
            throw new IllegalArgumentException("Inventory item cannot be null");
        }

        int currentStock = item.getQuantity();
        int lowStockThreshold = item.getLowStockThreshold();

        return currentStock <= lowStockThreshold;
    }

        /**
         * Generates a report of the current inventory.
         *
         * @return A list of inventory report data.
         */
        public List<InventoryReport> generateInventoryReport () {
            List<Inventory> inventoryList = inventoryDao.getAllInventory();
            List<InventoryReport> reportList = new ArrayList<>();

            for (Inventory item : inventoryList) {
                InventoryReport report = new InventoryReport();
                // Assuming InventoryReport has methods like setProductId, setProductName, setQuantity, etc.
                report.setProductId(item.getProductId());
                report.setProductName(String.valueOf(item.getProductNameForInventoryItem()));
                report.setQuantity(item.getQuantity());
                report.setLowStockThreshold(item.getLowStockThreshold());
                // Add other necessary fields and calculations

                reportList.add(report);
            }

            return reportList;
        }
    }

