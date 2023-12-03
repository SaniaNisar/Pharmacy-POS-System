package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.SalesDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.service.SalesReportGeneration;
import com.pharmacyPOS.data.entities.SalesReport;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SalesReportsFrame extends JFrame {
    private SalesReportGeneration salesReportGeneration;
    private JTextArea reportAreaDaily;
    private JTextArea reportAreaWeekly;
    private JTextArea reportAreaMonthly;
    private JDatePickerImpl datePickerDaily;
    private JDatePickerImpl datePickerWeekly;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> monthComboBox;
    private JPanel chartPanelDaily;
    private JPanel chartPanelWeekly;
    private JPanel chartPanelMonthly;

    public SalesReportsFrame(SalesDao salesDao) {
        this.salesReportGeneration = new SalesReportGeneration(salesDao);

        setTitle("Sales Report Generator");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 0, 128));

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel dailyPanel = createDailyPanel();
        JPanel weeklyPanel = createWeeklyPanel();
        JPanel monthlyPanel = createMonthlyPanel();

        tabbedPane.addTab("Daily Report", dailyPanel);
        tabbedPane.addTab("Weekly Report", weeklyPanel);
        tabbedPane.addTab("Monthly Report", monthlyPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createDailyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select Date:");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePickerDaily = new JDatePickerImpl(datePanel);
        JButton button = new JButton("Generate Report");
        button.addActionListener(e -> generateDailyReport());

        JPanel topPanel = new JPanel();
        topPanel.add(label);
        topPanel.add(datePickerDaily);
        topPanel.add(button);

        panel.add(topPanel, BorderLayout.NORTH);

        chartPanelDaily = new JPanel(new BorderLayout());
        reportAreaDaily = new JTextArea();
        reportAreaDaily.setEditable(false);

        // Combine the report area and chart in one panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(new JScrollPane(reportAreaDaily), BorderLayout.CENTER);
        combinedPanel.add(chartPanelDaily, BorderLayout.SOUTH); // Chart will be added here

        panel.add(combinedPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createWeeklyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select Start Date of Week:");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePickerWeekly = new JDatePickerImpl(datePanel);
        JButton button = new JButton("Generate Report");
        button.addActionListener(e -> generateWeeklyReport());

        JPanel topPanel = new JPanel();
        topPanel.add(label);
        topPanel.add(datePickerWeekly);
        topPanel.add(button);

        panel.add(topPanel, BorderLayout.NORTH);

        chartPanelWeekly = new JPanel(new BorderLayout());
        reportAreaWeekly = new JTextArea();
        reportAreaWeekly.setEditable(false);

        // Combine the report area and chart in one panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(new JScrollPane(reportAreaWeekly), BorderLayout.CENTER);
        combinedPanel.add(chartPanelWeekly, BorderLayout.SOUTH); // Chart will be added here

        panel.add(combinedPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMonthlyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel labelYear = new JLabel("Select Year:");
        yearComboBox = new JComboBox<>();
        for (int year = 2000; year <= Calendar.getInstance().get(Calendar.YEAR); year++) {
            yearComboBox.addItem(year);
        }

        JLabel labelMonth = new JLabel("Select Month:");
        monthComboBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        });

        JButton button = new JButton("Generate Report");
        button.addActionListener(e -> generateMonthlyReport());

        JPanel topPanel = new JPanel();
        topPanel.add(labelYear);
        topPanel.add(yearComboBox);
        topPanel.add(labelMonth);
        topPanel.add(monthComboBox);
        topPanel.add(button);

        panel.add(topPanel, BorderLayout.NORTH);

        chartPanelMonthly = new JPanel(new BorderLayout());
        reportAreaMonthly = new JTextArea();
        reportAreaMonthly.setEditable(false);

        // Combine the report area and chart in one panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(new JScrollPane(reportAreaMonthly), BorderLayout.CENTER);
        combinedPanel.add(chartPanelMonthly, BorderLayout.SOUTH); // Chart will be added here

        panel.add(combinedPanel, BorderLayout.CENTER);
        return panel;
    }

    private void generateDailyReport() {
        java.util.Date selectedDate = (java.util.Date) datePickerDaily.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date", "Date Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date sqlDate = new Date(selectedDate.getTime());
        List<SalesReport> reports = salesReportGeneration.getDailySalesReport(sqlDate);
        displayReports(reports, reportAreaDaily, chartPanelDaily);
    }

    private void generateWeeklyReport() {
        java.util.Date selectedDate = (java.util.Date) datePickerWeekly.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a start date", "Date Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date sqlDate = new Date(selectedDate.getTime());
        List<SalesReport> reports = salesReportGeneration.getWeeklySalesReport(sqlDate);
        displayReports(reports, reportAreaWeekly, chartPanelWeekly);
    }

    private void generateMonthlyReport() {
        int year = (int) yearComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex() + 1;
        List<SalesReport> reports = salesReportGeneration.getMonthlySalesReport(year, month);
        displayReports(reports, reportAreaMonthly, chartPanelMonthly);
    }

    private void displayReports(List<SalesReport> reports, JTextArea reportArea, JPanel chartPanel) {
        // Update text area
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Dataset for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (SalesReport report : reports) {
            // Update text information
            sb.append("Date: ").append(dateFormat.format(report.getSaleDate())).append("\n");
            sb.append("Product ID: ").append(report.getProductId()).append("\n");
            sb.append("Product Name: ").append(report.getProductName()).append("\n");
            sb.append("Total Quantity Sold: ").append(report.getTotalQuantitySold()).append("\n");
            sb.append("Total Sales Amount: $").append(String.format("%.2f", report.getTotalSalesAmount())).append("\n\n");

            // Add data to the dataset for chart
            dataset.addValue(report.getTotalSalesAmount(), "Sales", report.getProductName());
        }

        reportArea.setText(sb.toString());

        // Create chart based on the dataset
        JFreeChart barChart = ChartFactory.createBarChart(
                "Sales Chart",
                "Product",
                "Sales Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Create a ChartPanel for the chart
        ChartPanel newChartPanel = new ChartPanel(barChart);
        newChartPanel.setPreferredSize(new Dimension(600, 300)); // Set preferred size for the chart panel

        // Clear the previous chart and add the new one
        chartPanel.removeAll();
        chartPanel.add(newChartPanel);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();
            SalesDao salesDao = new SalesDao(conn); // Example, provide actual connection
            new SalesReportsFrame(salesDao);
        });
    }
}
