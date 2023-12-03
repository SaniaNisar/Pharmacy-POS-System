package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.presentation.controllers.InventoryController;
import com.pharmacyPOS.presentation.controllers.ProductController;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateInventoryFrame extends JFrame {

    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField lowStockThresholdField;
    private JTextField expiryDateField;
    private JButton updateButton;

    private Inventory inventory;
    private InventoryController inventoryController;
    private Product product;

    public UpdateInventoryFrame(Inventory inventory, InventoryDao inventoryDao, Product product) {
        this.inventory = inventory;
        this.product = product;
        this.inventoryController = new InventoryController(new InventoryService(inventoryDao));
        setTitle("Update Product");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        productNameField = new JTextField(product.getName()); // Initialize productNameField with the product name

        quantityField = new JTextField(String.valueOf(inventory.getQuantity())); // Initialize quantityField with the quantity
        lowStockThresholdField = new JTextField(String.valueOf(inventory.getLowStockThreshold())); // Initialize lowStockThresholdField with the low stock threshold
        expiryDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(inventory.getExpiryDate())); // Initialize expiryDateField with the expiry date
        updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateButtonClicked();
            }
        });

        initializeComponents();
        setupLayout();

        setVisible(true);
    }

    private void initializeComponents() {
        productNameField = new JTextField();
        quantityField = new JTextField();
        lowStockThresholdField = new JTextField();
        expiryDateField = new JTextField();
        updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUpdateButtonClicked();
            }
        });
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Product Name:"));
        panel.add(productNameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Low Stock Threshold:"));
        panel.add(lowStockThresholdField);
        panel.add(new JLabel("Expiry Date (yyyy-MM-dd):"));
        panel.add(expiryDateField);
        panel.add(new JLabel(""));
        panel.add(updateButton);

        add(panel, BorderLayout.CENTER);
    }

    private void onUpdateButtonClicked() {
        // Retrieve values from text fields
        String productName = productNameField.getText();
        String quantityStr = quantityField.getText();
        String lowStockThresholdStr = lowStockThresholdField.getText();
        String expiryDateStr = expiryDateField.getText();

        try {
            // Parse input values
            int quantity = Integer.parseInt(quantityStr);
            int lowStockThreshold = Integer.parseInt(lowStockThresholdStr);

            // Parse the date string
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date expiryDate = dateFormat.parse(expiryDateStr);

            // Update the inventory item
            inventory.setQuantity(quantity);
            inventory.setLowStockThreshold(lowStockThreshold);
            inventory.setExpiryDate(new java.sql.Date(expiryDate.getTime()));

            // Update the product name
            product.setName(productName);

            // Save the updated inventory item and product to the database
            inventoryController.updateInventoryItemWithProductInfo(inventory, product);

            JOptionPane.showMessageDialog(this, "Inventory item updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Close the update frame after saving
            dispose();

        } catch (NumberFormatException | ParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Add this to log the exception
        }
    }
}

