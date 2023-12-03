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

    public SalesReportsFrame(SalesDao salesDao) {
        this.salesReportGeneration = new SalesReportGeneration(salesDao);

        setTitle("Sales Report Generator");
        setSize(600, 400);
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
        reportAreaDaily = new JTextArea();
        reportAreaDaily.setEditable(false);
        panel.add(new JScrollPane(reportAreaDaily), BorderLayout.CENTER);

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
        reportAreaWeekly = new JTextArea();
        reportAreaWeekly.setEditable(false);
        panel.add(new JScrollPane(reportAreaWeekly), BorderLayout.CENTER);

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
        monthComboBox = new JComboBox<>(new String[] {
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
        reportAreaMonthly = new JTextArea();
        reportAreaMonthly.setEditable(false);
        panel.add(new JScrollPane(reportAreaMonthly), BorderLayout.CENTER);

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
        displayReports(reports, reportAreaDaily);
    }

    private void generateWeeklyReport() {
        java.util.Date selectedDate = (java.util.Date) datePickerWeekly.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a start date", "Date Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date sqlDate = new Date(selectedDate.getTime());
        List<SalesReport> reports = salesReportGeneration.getWeeklySalesReport(sqlDate);
        displayReports(reports, reportAreaWeekly);
    }

    private void generateMonthlyReport() {
        int year = (int) yearComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex() + 1;
        List<SalesReport> reports = salesReportGeneration.getMonthlySalesReport(year, month);
        displayReports(reports, reportAreaMonthly);
    }

    private void displayReports(List<SalesReport> reports, JTextArea reportArea) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (SalesReport report : reports) {
            sb.append("Date: ").append(dateFormat.format(report.getSaleDate())).append("\n");
            sb.append("Product ID: ").append(report.getProductId()).append("\n");
            sb.append("Product Name: ").append(report.getProductName()).append("\n");
            sb.append("Total Quantity Sold: ").append(report.getTotalQuantitySold()).append("\n");
            sb.append("Total Sales Amount: $").append(String.format("%.2f", report.getTotalSalesAmount())).append("\n\n");
        }

        reportArea.setText(sb.toString());
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
