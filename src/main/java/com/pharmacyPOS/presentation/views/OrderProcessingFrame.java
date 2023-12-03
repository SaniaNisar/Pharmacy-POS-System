package com.pharmacyPOS.presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderProcessingFrame extends JFrame {
    private JLabel totalAmountLabel;
    private JTextField amountPaidTextField;
    private JLabel amountToBeReturnedLabel;
    private JButton cancelOrderButton;
    private JButton generateInvoiceButton;
    private double totalAmount;

    public OrderProcessingFrame(double totalAmount) {
        this.totalAmount = totalAmount;
        initComponents();
    }

    private void initComponents() {
        setTitle("Order Processing");
        setLayout(new GridLayout(0, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize components
        totalAmountLabel = new JLabel("Total Amount: $" + String.format("%.2f", totalAmount));
        amountPaidTextField = new JTextField(10);
        amountToBeReturnedLabel = new JLabel("Amount to be returned: $0.00");
        cancelOrderButton = new JButton("Cancel Order");
        generateInvoiceButton = new JButton("Generate Invoice");

        // Set component sizes
        cancelOrderButton.setPreferredSize(new Dimension(150, 30));
        generateInvoiceButton.setPreferredSize(new Dimension(150, 30));

        // Add components
        add(new JLabel("Total Amount:"));
        add(totalAmountLabel);
        add(new JLabel("Amount Paid:"));
        add(amountPaidTextField);
        add(new JLabel("Amount to be Returned:"));
        add(amountToBeReturnedLabel);
        add(cancelOrderButton);
        add(generateInvoiceButton);

        // Action Listeners
        generateInvoiceButton.addActionListener(this::calculateChange);
        cancelOrderButton.addActionListener(e -> dispose()); // Close the frame

        //pack();
        setSize(400,400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calculateChange(ActionEvent e) {
        try {
            double amountPaid = Double.parseDouble(amountPaidTextField.getText());
            if (amountPaid < totalAmount) {
                JOptionPane.showMessageDialog(this, "Error: Amount paid is less than total amount", "Payment Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amountToReturn = amountPaid - totalAmount;
            amountToBeReturnedLabel.setText("Amount to be returned: $" + String.format("%.2f", amountToReturn));
            JOptionPane.showMessageDialog(this, "Invoice generated successfully!", "Invoice", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        new OrderProcessingFrame(100.00); // Example total amount
    }
}
