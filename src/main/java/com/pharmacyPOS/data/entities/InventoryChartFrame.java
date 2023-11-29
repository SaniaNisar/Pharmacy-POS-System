package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.presentation.controllers.InventoryController;
import com.pharmacyPOS.service.InventoryService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

public class InventoryChartFrame extends JFrame {
    private InventoryController inventoryController;

    public InventoryChartFrame(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
        initUI();
        setTitle("Inventory Report Chart");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    private void initUI() {
        JFreeChart barChart = createChart();
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fetch inventory data from database and populate dataset
        List<Inventory> inventoryList = inventoryController.getInventoryList();
        for (Inventory inventory : inventoryList) {
            // Assuming you have a method in InventoryDao to get product names
            String productName = inventoryController.getProductNameById(inventory.getProductId());
            dataset.addValue(inventory.getQuantity(), "Products", productName);
        }

        return ChartFactory.createBarChart(
                "Inventory Report", // Chart title
                "Product", // Domain axis label
                "Quantity", // Range axis label
                dataset, // Data
                org.jfree.chart.plot.PlotOrientation.VERTICAL, // Orientation
                true, // Include legend
                true, // Tooltips
                false // URLs
        );
    }

    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        InventoryDao inventoryDao = new InventoryDao(dbConnection);
        InventoryService inventoryService = new InventoryService(inventoryDao);
        InventoryController inventoryController1 = new InventoryController(inventoryService);
        SwingUtilities.invokeLater(() -> {
            InventoryChartFrame frame = new InventoryChartFrame(inventoryController1);
           // frame.pack();
            //frame.setTitle("Inventory Report Chart");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
