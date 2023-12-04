package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.presentation.controllers.InventoryController;
import com.pharmacyPOS.service.InventoryService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryChartFrame extends JFrame {
    private InventoryController inventoryController;
    private JTextArea reportTextArea;

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
        // Create a panel to hold both the chart and report data
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the chart panel
        JFreeChart barChart = createChart();
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 300));

        // Create a text area for the report data
        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        JScrollPane reportScrollPane = new JScrollPane(reportTextArea);
        reportScrollPane.setPreferredSize(new Dimension(800, 300));

        // Add the chart panel and report text area to the main panel
        mainPanel.add(chartPanel, BorderLayout.NORTH);
        mainPanel.add(reportScrollPane, BorderLayout.SOUTH);

        // Set the main panel as the content pane of the frame
        setContentPane(mainPanel);
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

    // Update the report text area with the inventory report
    public void updateReportTextArea(List<Inventory> inventoryList) {
        StringBuilder reportText = new StringBuilder();
        for (Inventory inventory : inventoryList) {
            String productName = inventoryController.getProductNameById(inventory.getProductId());
            reportText.append(String.format("Product Name: %s, Quantity: %d%n", productName, inventory.getQuantity()));
        }
        reportTextArea.setText(reportText.toString());
    }

    public static void main(String[] args)
    {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        InventoryDao inventoryDao = new InventoryDao(dbConnection);
        InventoryService inventoryService = new InventoryService(inventoryDao);
        InventoryController inventoryController1 = new InventoryController(inventoryService);
        SwingUtilities.invokeLater(() -> {
            InventoryChartFrame frame = new InventoryChartFrame(inventoryController1);

            // Fetch inventory data and update the report text area
            List<Inventory> inventoryList = inventoryController1.getInventoryList();
            frame.updateReportTextArea(inventoryList);

            frame.setVisible(true);
        });
    }
}
