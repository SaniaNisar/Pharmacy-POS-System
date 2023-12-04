package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.service.ProductService;
import com.pharmacyPOS.service.InventoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddProductFrame extends JFrame {
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField priceTextField;
    private JTextField categoryIdTextField;
    private JTextField expirationDateTextField;
    private JTextField quantityTextField;
    private JTextField lowStockThresholdTextField; // New field for low stock threshold


    public AddProductFrame(DefaultTableModel tableModel, ProductService productService, InventoryService inventoryService) {
        setTitle("Add Product");
        setSize(500, 400); // Adjust size as needed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BoxLayout for vertical stacking
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Initialize all text fields
        nameTextField = new JTextField(20);
        descriptionTextField = new JTextField(20);
        priceTextField = new JTextField(20);
        categoryIdTextField = new JTextField(20);
        expirationDateTextField = new JTextField(20);
        quantityTextField = new JTextField(20);
        lowStockThresholdTextField = new JTextField(20);

        // Add label-text field pairs to the panel
        panel.add(createRow("Name:", nameTextField));
        panel.add(createRow("Description:", descriptionTextField));
        panel.add(createRow("Price:", priceTextField));
        panel.add(createRow("Category ID:", categoryIdTextField));
        panel.add(createRow("Expiration Date:", expirationDateTextField));
        panel.add(createRow("Quantity:", quantityTextField));
        panel.add(createRow("Low Stock Threshold:", lowStockThresholdTextField));

        // Add button with its own FlowLayout panel
        JButton addButton = new JButton("Add");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        panel.add(buttonPanel);

        // Add action listener to the "Add" button
        addButton.addActionListener(e -> {
            try {
                // Create a new Product object with the input values
                Product newProduct = new Product(
                        nameTextField.getText(),
                        descriptionTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(categoryIdTextField.getText()),
                        java.sql.Date.valueOf(expirationDateTextField.getText())
                );

                // Call the createProduct method to add the new product to the database
                productService.createProduct(newProduct);

                // Assuming the productDao returns the generated product_id,
                // we can use it to create a new Inventory record.
                int quantity = Integer.parseInt(quantityTextField.getText());
                int lowStockThreshold = Integer.parseInt(lowStockThresholdTextField.getText());
                Inventory newInventory = new Inventory(
                        0, // Assuming inventoryId is auto-generated
                        newProduct.getProductId(),
                        Integer.parseInt(quantityTextField.getText()),
                        java.sql.Date.valueOf(expirationDateTextField.getText()),
                        lowStockThreshold // New field value
                );

                // Call the createInventoryItem method to add the new inventory record to the database
                inventoryService.createInventoryItem(newInventory);

                // Add the new product to the table model
                tableModel.addRow(new Object[]{
                        newProduct.getProductId(),
                        newProduct.getName(),
                        newProduct.getDescription(),
                        newProduct.getPrice(),
                        newProduct.getCategoryId(),
                        newProduct.getExpirationDate(),
                        quantity
                });

                // Clear the input fields
                nameTextField.setText("");
                descriptionTextField.setText("");
                priceTextField.setText("");
                categoryIdTextField.setText("");
                expirationDateTextField.setText("");
                quantityTextField.setText("");

                // Close the frame
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error adding product: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the panel to the frame
        add(panel);
        setVisible(true);
    }

    private JPanel createRow(String labelText, JTextField textField) {
        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel(labelText));
        row.add(textField);
        return row;
    }
    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JTextField getCategoryIdTextField() {
        return categoryIdTextField;
    }

    public JTextField getExpirationDateTextField() {
        return expirationDateTextField;
    }

    public JTextField getQuantityTextField() {
        return quantityTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
