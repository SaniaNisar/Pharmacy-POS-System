package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.SalesDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.data.entities.OrderDetail;
import com.pharmacyPOS.data.entities.Sale;
import com.pharmacyPOS.data.entities.SaleItem;
import com.pharmacyPOS.service.InvoiceGeneration;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class POSReceipt extends JFrame {
    private Order order;
    private double paidAmount;
    private double total;
    private SalesDao salesDao;
    private JTextArea receiptArea;

    public POSReceipt(Order order, double total, double paidAmount) {
        this.order = order;
        this.total = total;
        this.paidAmount = paidAmount;
        this.salesDao = new SalesDao(new DatabaseConnection()); // Initialize with actual DatabaseConnection

        setupUI();
        saveSaleToDatabase(); // Save the sale to the database
        double change = paidAmount - total;
        InvoiceGeneration invoiceGeneration = new InvoiceGeneration(order, total, paidAmount, change);
        invoiceGeneration.generateInvoice();
    }

    private void setupUI() {
        setTitle("Pharmacy POS Receipt");
        setSize(300, 600);
        setLayout(new BorderLayout());

         receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Format and append receipt details
        StringBuilder receiptText = new StringBuilder();
        receiptText.append("Pharmacy POS Receipt\n");
        receiptText.append("----------------------------\n");
        receiptText.append("Order ID: ").append(order.getOrderId()).append("\n");
        receiptText.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
        receiptText.append("Sales Assistant\n\n");
        receiptText.append("Items:\n");

        // Append order details

        if (order.getOrderDetails() != null) {
            for (OrderDetail item : order.getOrderDetails()) {
                receiptText.append("Product id: ").append(item.getProductId()).append("\n Product Quantity: ").append(item.getQuantity())
                        .append(" \n Price per Unit: ").append(item.getUnitPrice()).append("\n");
            }
        }
        else {
            receiptText.append("No order details available.\n");
        }

        double change = paidAmount - total;
        receiptText.append("\nTotal Amount: ").append(String.format("%.2f", total)).append("\n");
        receiptText.append("Paid Amount: ").append(String.format("%.2f", paidAmount)).append("\n");
        receiptText.append("Change: ").append(String.format("%.2f", change)).append("\n");

        receiptArea.setText(receiptText.toString());

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveSaleToDatabase() {
        Sale sale = new Sale();
        sale.setSaleDate(new java.sql.Date(order.getTimestamp().getTime()));
        sale.setTotalCost(total);

        // Add SaleItems
        for (OrderDetail item : order.getOrderDetails()) {
            sale.addItem(new SaleItem(item.getProductId(), item.getQuantity(), item.getUnitPrice()));
        }

        try {
            salesDao.createSale(sale);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving sale to database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JTextArea getFrame() {
        return receiptArea;
    }
}
