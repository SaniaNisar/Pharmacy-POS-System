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

    void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JFreeChart barChart = createChart();
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 300));

        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        JScrollPane reportScrollPane = new JScrollPane(reportTextArea);
        reportScrollPane.setPreferredSize(new Dimension(800, 300));

        mainPanel.add(chartPanel, BorderLayout.NORTH);
        mainPanel.add(reportScrollPane, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Inventory> inventoryList = inventoryController.getInventoryList();
        for (Inventory inventory : inventoryList) {
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

            List<Inventory> inventoryList = inventoryController1.getInventoryList();
            frame.updateReportTextArea(inventoryList);

            frame.setVisible(true);
        });
    }
}
