package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.InventoryReport;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final InventoryDao inventoryDao;
    private ProductService productService;

    public InventoryService(InventoryDao inventoryDao, ProductService productService) {
        this.inventoryDao = inventoryDao;
        this.productService = productService;
    }

    public InventoryService(InventoryDao inventoryDao)
    {
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        this.inventoryDao = inventoryDao;
        this.productService = new ProductService(new ProductDao(c));
    }

    public Inventory getInventoryByProductId(int productId)
    {
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

    public boolean isStockLow(Inventory item) {
        if (item == null) {
            throw new IllegalArgumentException("Inventory item cannot be null");
        }
        return item.getQuantity() <= item.getLowStockThreshold();
    }

    public Inventory getInventoryById(int inventoryId) {
        return inventoryDao.getInventoryById(inventoryId);
    }

    public List<Product> checkAndAlertLowStock() {
        List<Inventory> allInventory = getAllInventory();
        List<Product> lowStockProducts = new ArrayList<>();

        for (Inventory inventory : allInventory) {
            //System.out.println(inventory.getQuantity() + " > " + inventory.getLowStockThreshold());
            if (isStockLow(inventory)) {
                Product product = productService.getProductById(inventory.getProductId());
                lowStockProducts.add(product);
                //System.out.println("Low Stock Alert: " + product.getName() + " (ID: " + product.getProductId() + ") - Quantity: " + inventory.getQuantity());
            }
        }

        return lowStockProducts;
    }

    public List<InventoryReport> generateInventoryReport() {
        List<Inventory> inventoryList = getAllInventory();
        List<InventoryReport> reportList = new ArrayList<>();

        for (Inventory item : inventoryList)
        {
            InventoryReport report = new InventoryReport();
            Product product = productService.getProductById(item.getProductId());
            report.setProductId(item.getProductId());
            report.setProductName(product.getName());
            report.setQuantity(item.getQuantity());
            report.setLowStockThreshold(item.getLowStockThreshold());
            // Add other necessary fields and calculations
            reportList.add(report);
        }

        return reportList;
    }

    public String getProductNameById(int productId)
    {
        return (inventoryDao.getProductNameById(productId));
    }

    public void replenishInventory()
    {
        inventoryDao.replenishInventory();
    }
    public void updateInventoryItemWithProductInfo(Inventory inventory, Product product) {
        inventoryDao.updateInventoryItemWithProductInfo(inventory,product);
    }

    public int getQuantityByProductId(int productId) {
        try {
            // Call the inventoryService to retrieve the quantity by product ID
            return inventoryDao.getQuantityByProductId(productId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void decrementQuantityByProductId(int productId) throws SQLException {
        inventoryDao.decrementQuantityByProductId(productId);
    }

    public int getCurrentInventoryQuantity(int productId)
    {
        return (inventoryDao.getCurrentInventoryQuantity(productId));
    }

}
