package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.SalesDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.service.SalesReportGeneration;
import com.pharmacyPOS.data.entities.SalesReport;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class SalesReportsFrame extends JFrame {

    private SalesReportGeneration salesReportGeneration;
    private JTextArea reportArea;
    private JDatePickerImpl datePicker;  // Declared as class member


    public SalesReportsFrame(SalesDao salesDao) {
        // Initialize SalesReportGeneration with SalesDao
        this.salesReportGeneration = new SalesReportGeneration(salesDao);

        // Set frame properties
        setTitle("Sales Report Generator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 128));

        JTabbedPane tabbedPane = new JTabbedPane();

        // Create panels for each tab
        JPanel dailyPanel = createDailyPanel();
        JPanel weeklyPanel = createWeeklyPanel();
        JPanel monthlyPanel = createMonthlyPanel();

        // Add panels to the tabbed pane with corresponding titles
        tabbedPane.addTab("Daily Report", dailyPanel);
        tabbedPane.addTab("Weekly Report", weeklyPanel);
        tabbedPane.addTab("Monthly Report", monthlyPanel);

        // Add tabbed pane to the frame
        add(tabbedPane);

        // Set up frame visibility
        setVisible(true);
    }

    private JPanel createDailyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Select Date:");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);  // Initialize here
        JButton button = new JButton("Generate Report");
        button.addActionListener(e -> generateDailyReport());

        JPanel topPanel = new JPanel();
        topPanel.add(label);
        topPanel.add(datePicker);
        topPanel.add(button);

        panel.add(topPanel, BorderLayout.NORTH);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        return panel;
    }


    private JPanel createWeeklyPanel() {
        // Similar structure to createDailyPanel() but with week selection
        JPanel panel = new JPanel();
        // ... rest of the implementation
        return panel;
    }

    private JPanel createMonthlyPanel() {
        // Similar structure to createDailyPanel() but with month and year selection
        JPanel panel = new JPanel();
        // ... rest of the implementation
        return panel;
    }

    private void generateDailyReport() {
        try {
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date", "Date Selection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Format the date to YYYY-MM-DD
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(selectedDate);

            // Convert formatted date to SQL Date
            Date sqlDate = Date.valueOf(formattedDate);

            List<SalesReport> reports = salesReportGeneration.getDailySalesReport(sqlDate);

            System.out.println("Reports fetched: " + reports); // Debug print

            displayReports(reports);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Report Generation Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Print the stack trace for debugging
        }
    }



    private void displayReports(List<SalesReport> reports) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (SalesReport report : reports)
        {
            sb.append("Date: ").append(dateFormat.format(report.getSaleDate())).append("\n");
            sb.append("Product ID: ").append(report.getProductId()).append("\n");
            sb.append("Product Name: ").append(report.getProductName()).append("\n");
            sb.append("Total Quantity Sold: ").append(report.getTotalQuantitySold()).append("\n");
            sb.append("Total Sales Amount: $").append(String.format("%.2f", report.getTotalSalesAmount())).append("\n\n");
        }

        reportArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        // Create and run the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();
            SalesDao salesDao = new SalesDao(new DatabaseConnection()); // Example, provide actual connection
            new SalesReportsFrame(salesDao);
        });
    }
}
