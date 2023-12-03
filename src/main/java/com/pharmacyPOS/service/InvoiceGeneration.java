package com.pharmacyPOS.service;

import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.data.entities.OrderDetail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceGeneration {
    private Order order;
    private double total;
    private double paidAmount;
    private double change;

    public InvoiceGeneration(Order order, double total, double paidAmount, double change) {
        this.order = order;
        this.total = total;
        this.paidAmount = paidAmount;
        this.change = change;
    }

    public void generateInvoice() {
        String fileName = createFileNameWithCurrentDate();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            StringBuilder invoiceText = new StringBuilder();
            invoiceText.append("Pharmacy POS Receipt\n");
            invoiceText.append("----------------------------\n");
            invoiceText.append("Order ID: ").append(order.getOrderId()).append("\n");
            invoiceText.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
            invoiceText.append("Sales Assistant\n\n");
            invoiceText.append("Items:\n");

            if (order.getOrderDetails() != null) {
                for (OrderDetail item : order.getOrderDetails()) {
                    invoiceText.append("Product id: ").append(item.getProductId())
                            .append(", Quantity: ").append(item.getQuantity())
                            .append(", Price: $").append(String.format("%.2f", item.getUnitPrice()))
                            .append("\n");
                }
            } else {
                invoiceText.append("No order details available.\n");
            }

            invoiceText.append("\nTotal Amount: $").append(String.format("%.2f", total)).append("\n");
            invoiceText.append("Paid Amount: $").append(String.format("%.2f", paidAmount)).append("\n");
            invoiceText.append("Change: $").append(String.format("%.2f", change)).append("\n");

            writer.write(invoiceText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createFileNameWithCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return "Invoice_" + sdf.format(new Date()) + ".txt";
    }
}
