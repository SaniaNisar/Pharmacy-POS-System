package com.pharmacyPOS.presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.ProductService;

public class UpdateProductFrame extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField categoryIdField;
    private JTextField expirationDateField;

    private JButton updateButton;

    private Product product; // The product being updated

    public UpdateProductFrame(Product product, ProductService productService) {
        this.product = product;

        setTitle("Update Product");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and configure the input fields
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        priceField = new JTextField(20);
        categoryIdField = new JTextField(20);
        expirationDateField = new JTextField(20);

        // Set the initial values from the existing product
        nameField.setText(product.getName());
        descriptionField.setText(product.getDescription());
        priceField.setText(String.valueOf(product.getPrice()));
        categoryIdField.setText(String.valueOf(product.getCategoryId()));
        expirationDateField.setText(product.getExpirationDate().toString());

        // Create the update button
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the update button action here
                // Get the updated values from the input fields
                String updatedName = nameField.getText();
                String updatedDescription = descriptionField.getText();
                double updatedPrice = Double.parseDouble(priceField.getText());
                int updatedCategoryId = Integer.parseInt(categoryIdField.getText());
                // Parse the expiration date from the input field (you may need to validate it)
                java.sql.Date updatedExpirationDate = java.sql.Date.valueOf(expirationDateField.getText());

                // Update the product object with the new values
                product.setName(updatedName);
                product.setDescription(updatedDescription);
                product.setPrice(updatedPrice);
                product.setCategoryId(updatedCategoryId);
                product.setExpirationDate(updatedExpirationDate);

                // Call the updateProduct method to update the product in the database
                productService.updateProduct(product);

                dispose();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Category ID:"));
        inputPanel.add(categoryIdField);
        inputPanel.add(new JLabel("Expiration Date (YYYY-MM-DD):"));
        inputPanel.add(expirationDateField);
        inputPanel.add(new JLabel("")); // Empty label for spacing
        inputPanel.add(updateButton);

        // Add the input panel to the frame
        add(inputPanel);

        setVisible(true);
    }
}

