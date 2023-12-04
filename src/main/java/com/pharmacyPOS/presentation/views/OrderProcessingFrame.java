package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.OrderDao;
import com.pharmacyPOS.data.dao.OrderDetailDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.data.entities.OrderDetail;
import com.pharmacyPOS.presentation.controllers.InventoryController;
import com.pharmacyPOS.presentation.controllers.OrderController;
//import com.pharmacyPOS.presentation.views.POSReceipt;
import com.pharmacyPOS.presentation.controllers.OrderDetailController;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.OrderDetailService;
import com.pharmacyPOS.service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderProcessingFrame extends JFrame {
    private JLabel totalAmountLabel;
    private JTextField amountPaidTextField;
    private JLabel amountToBeReturnedLabel;
    private JButton cancelOrderButton;
    private JButton generateInvoiceButton;
    private double totalAmount;
    private OrderController orderController;
    private DatabaseConnection connection;
    private int orderId;
    private InventoryController inventoryController;

    private OrderDetailController orderDetailController;
    public OrderProcessingFrame(double totalAmount, int orderID) {
        this.totalAmount = totalAmount;
        connection = new DatabaseConnection();
        connection.connect();
        //orderController = new OrderController(new OrderService(new OrderDao(connection)));
        this.orderId = orderID;
        this.inventoryController = new InventoryController(new InventoryService(new InventoryDao(connection)));
        this.orderController=new OrderController(new OrderService(new OrderDao(connection)));
        this.orderDetailController = new OrderDetailController(new OrderDetailService(new OrderDetailDao(connection)));
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
        generateInvoiceButton.addActionListener(e -> generateInvoice());
        cancelOrderButton.addActionListener(e -> {
            List<OrderDetail> orderDetails = orderDetailController.getOrderDetails(orderId);

            for (OrderDetail detail : orderDetails)
            {
                int currentQuantity = inventoryController.getCurrentInventoryQuantity(detail.getProductId());

                inventoryController.updateInventoryQuantity(detail.getProductId(), detail.getQuantity()+currentQuantity);
                orderDetailController.deleteOrderDetail(detail.getOrderDetailId());
            }

            // Delete the order itself
            orderController.deleteOrder(orderId);

            // Close the frame
            dispose();
        });

        setSize(400, 400);
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
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateInvoice() {
        try {
            double amountPaid = Double.parseDouble(amountPaidTextField.getText());
            if (amountPaid >= totalAmount) {

                Order order = orderController.getOrderById(orderId); // Placeholder for actual order object

                // Create the POSReceipt frame
                new POSReceipt(order, totalAmount, amountPaid);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Amount paid is less than the total amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        new OrderProcessingFrame(48.00,4); // Example total amount
    }
}
