package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.service.ProductService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.List;

public class InventoryReportGenerator {
    private DatabaseConnection dbConnection;
    private InventoryDao inventoryDao;
    private ProductService productService;

    public InventoryReportGenerator(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.inventoryDao = new InventoryDao(dbConnection);
        this.productService = new ProductService(new ProductDao(dbConnection));
    }

    public void generateAndDisplayReports() {
        List<Inventory> inventoryList = inventoryDao.getAllInventory();
        for (Inventory inventory : inventoryList) {
            Product product = productService.getProductById(inventory.getProductId());
            String productName = (product != null) ? product.getName() : "Unknown Product";
            double price = (product != null) ? product.getPrice() : 0.0;

            InventoryReport report = new InventoryReport(
                    inventory.getProductId(),
                    productName,
                    inventory.getQuantity(),
                    price
            );

            report.display();
        }
    }

    public void generateAndWriteReportToFile() {
        String fileName = createFileNameWithCurrentDate();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            List<Inventory> inventoryList = inventoryDao.getAllInventory();
            for (Inventory inventory : inventoryList) {
                Product product = productService.getProductById(inventory.getProductId());
                String productName = (product != null) ? product.getName() : "Unknown Product";
                double price = (product != null) ? product.getPrice() : 0.0;

                String reportLine = String.format(
                        "Product ID: %d, Name: %s, Quantity: %d, Price: %.2f",
                        inventory.getProductId(), productName, inventory.getQuantity(), price);
                writer.write(reportLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createFileNameWithCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return "InventoryReport - " + currentDate + ".txt";
    }

    public static void main(String[] args) {
            /*DatabaseConnection dbConnection = new DatabaseConnection();
            dbConnection.connect();
            InventoryReportGenerator reportGenerator = new InventoryReportGenerator(dbConnection);
            reportGenerator.generateAndDisplayReports();

             */

        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        InventoryReportGenerator reportGenerator = new InventoryReportGenerator(dbConnection);
        reportGenerator.generateAndWriteReportToFile();
        }

}
