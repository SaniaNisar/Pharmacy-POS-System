package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.data.entities.OrderDetail;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.presentation.controllers.CartController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class POSReceipt extends JFrame {
    private Order order; // Assuming Order is a class that holds order details
    private double paidAmount;
    private double total;

    private String salesAssistantName;
    private OrderDetail orderDetails;

    private CartController cartController;

    public POSReceipt(Order order, double total, double paidAmount, String salesAssistantName) {
        this.order = order;
        this.total = total;
        this.paidAmount = paidAmount;
        this.salesAssistantName = salesAssistantName;
        setupUI();
    }

    private void setupUI() {
        setTitle("Pharmacy POS Receipt");
        setSize(300, 600);
        setLayout(new BorderLayout());

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Format and append receipt details
        StringBuilder receiptText = new StringBuilder();
        receiptText.append("Pharmacy POS Receipt\n");
        receiptText.append("----------------------------\n");
        receiptText.append("Order ID: ").append(order.getOrderId()).append("\n");
        receiptText.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
        receiptText.append("Sales Assistant: ").append(salesAssistantName).append("\n\n");
        receiptText.append("Items:\n");

        // Assuming OrderDetail is a class that holds item details
        List<OrderDetail> items = List.of(order.getOrderDetails());
        for (OrderDetail item : items) {
            receiptText.append(item.getProductId()).append(" x").append(item.getQuantity())
                    .append(" @ ").append(item.getUnitPrice()).append("\n");
        }


        int userId = 2;
//        double total = cartController.getCartTotal(userId); // Assuming getTotalAmount() calculates the total
        double change = paidAmount - total;

        receiptText.append("\nTotal Amount: ").append(String.format("%.2f", total)).append("\n");
        receiptText.append("Paid Amount: ").append(String.format("%.2f", paidAmount)).append("\n");
        receiptText.append("Change: ").append(String.format("%.2f", change)).append("\n");

        receiptArea.setText(receiptText.toString());

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        InvoiceDetails();
        setVisible(true);
    }
    public void InvoiceDetails() {
        String fileName = createFileNameWithCurrentDate();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            StringBuilder receiptText = new StringBuilder();
            receiptText.append("Pharmacy POS Receipt\n");
            receiptText.append("----------------------------\n");
            receiptText.append("Order ID: ").append(order.getOrderId()).append("\n");
            receiptText.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
            receiptText.append("Sales Assistant: ").append(salesAssistantName).append("\n\n");
            receiptText.append("Items:\n");

            // Assuming OrderDetail is a class that holds item details
            List<OrderDetail> items = List.of(order.getOrderDetails());
            for (OrderDetail item : items) {
                receiptText.append(item.getProductId()).append(" x").append(item.getQuantity())
                        .append(" @ ").append(item.getUnitPrice()).append("\n");
            }


            int userId = 2;
//            double total = cartController.getCartTotal(userId); // Assuming getTotalAmount() calculates the total
            double change = paidAmount - total;

            receiptText.append("\nTotal Amount: ").append(String.format("%.2f", total)).append("\n");
            receiptText.append("Paid Amount: ").append(String.format("%.2f", paidAmount)).append("\n");
            receiptText.append("Change: ").append(String.format("%.2f", change)).append("\n");
            writer.write(String.valueOf(receiptText));
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String createFileNameWithCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return "Invoice Slip - " + currentDate + ".txt";
    }

    

}
