//package com.pharmacyPOS.data.entities;
//
//import com.pharmacyPOS.data.dao.SalesDao;
//import com.pharmacyPOS.data.database.DatabaseConnection;
//import com.pharmacyPOS.presentation.controllers.SalesController;
//import com.pharmacyPOS.service.SalesService;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.util.List;
//
//public class SalesChartFrame extends JFrame {
//    private SalesController salesController;
//
//    public SalesChartFrame(SalesController salesController) {
//        this.salesController = salesController;
//        initUI();
//        setTitle("Sales Report Chart");
//        setVisible(true);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        pack();
//    }
//
//    private void initUI() {
//        JFreeChart barChart = createChart();
//        ChartPanel chartPanel = new ChartPanel(barChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
//        setContentPane(chartPanel);
//    }
//
//    private JFreeChart createChart() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        // Fetch inventory data from database and populate dataset
//        List<Inventory> inventoryList = salesController.getSaleList();
//        for (Inventory inventory : inventoryList) {
//            // Assuming you have a method in InventoryDao to get product names
//            String productName = salesController.getProductNameById(inventory.getProductId());
//            dataset.addValue(inventory.getQuantity(), "Products", productName);
//        }
//
//        return ChartFactory.createBarChart(
//                "Sales Report", // Chart title
//                "Product", // Domain axis label
//                "Quantity", // Range axis label
//                dataset, // Data
//                org.jfree.chart.plot.PlotOrientation.VERTICAL, // Orientation
//                true, // Include legend
//                true, // Tooltips
//                false // URLs
//        );
//    }
//
//    public static void main(String[] args) {
//        DatabaseConnection dbConnection = new DatabaseConnection();
//        dbConnection.connect();
//        SalesDao saleDao = new SalesDao(dbConnection);
//        SalesService saleService = new SalesService(saleDao);
//        SalesController salesController1 = new SalesController(saleService);
//        SwingUtilities.invokeLater(() -> {
//            SalesChartFrame frame = new SalesChartFrame(salesController1);
//            // frame.pack();
//            //frame.setTitle("Inventory Report Chart");
//            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            //frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
//}
