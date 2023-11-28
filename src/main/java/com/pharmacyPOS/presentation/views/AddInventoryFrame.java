package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.presentation.controllers.ReplenishmentController;
import com.pharmacyPOS.service.ProductService;
import com.pharmacyPOS.service.ReplenishmentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddInventoryFrame extends JFrame {
    //private ReplenishmentService replenishmentService;
    private ReplenishmentController replenishmentController;

    private JTextField productIdField;
    private JTextField quantityField;
    private JTextField expiryDateField;
    private JTextField lowStockThresholdField;
    private JButton addButton;

    public AddInventoryFrame(ReplenishmentController replenishmentController)
    {
        this.replenishmentController = replenishmentController;

        setupUI();

        setTitle("Add Inventory Item");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Product ID:"));
        productIdField = new JTextField(20);
        add(productIdField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField(20);
        add(quantityField);

        add(new JLabel("Expiry Date (yyyy-mm-dd):"));
        expiryDateField = new JTextField(20);
        add(expiryDateField);

        add(new JLabel("Low Stock Threshold:"));
        lowStockThresholdField = new JTextField(20);
        add(lowStockThresholdField);

        addButton = new JButton("Add Inventory");
        addButton.addActionListener(this::onAddButtonClicked);
        add(addButton);
    }

    private void onAddButtonClicked(ActionEvent e) {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Date expiryDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(expiryDateField.getText()).getTime());
            int lowStockThreshold = Integer.parseInt(lowStockThresholdField.getText());

            replenishmentController.replenishInventory(productId, quantity, expiryDate, lowStockThreshold);
            JOptionPane.showMessageDialog(this, "Inventory item added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check your data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for demonstration purposes
    public static void main(String[] args) {
        // Replace with actual initialization of ReplenishmentService
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();
        ReplenishmentService replenishmentService = new ReplenishmentService(new InventoryDao(conn),new ProductService(new ProductDao(conn)));
        new AddInventoryFrame(new ReplenishmentController(replenishmentService));
    }
}
