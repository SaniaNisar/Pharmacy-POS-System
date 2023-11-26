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

    public AddProductFrame(DefaultTableModel tableModel, ProductService productService, InventoryService inventoryService) {
        setTitle("Add Product");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionTextField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        priceTextField = new JTextField();

        JLabel categoryIdLabel = new JLabel("Category ID:");
        categoryIdTextField = new JTextField();

        JLabel expirationDateLabel = new JLabel("Expiration Date:");
        expirationDateTextField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityTextField = new JTextField();

        JButton addButton = new JButton("Add");

        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(descriptionLabel);
        panel.add(descriptionTextField);
        panel.add(priceLabel);
        panel.add(priceTextField);
        panel.add(categoryIdLabel);
        panel.add(categoryIdTextField);
        panel.add(expirationDateLabel);
        panel.add(expirationDateTextField);
        panel.add(quantityLabel);
        panel.add(quantityTextField);
        panel.add(new JLabel()); // Placeholder for layout adjustment
        panel.add(addButton);

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
                Inventory newInventory = new Inventory(
                        0, // Inventory ID will be auto-generated
                        newProduct.getProductId(),
                        quantity,
                        java.sql.Date.valueOf(expirationDateTextField.getText())
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
}
