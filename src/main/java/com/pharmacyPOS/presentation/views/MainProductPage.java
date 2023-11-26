package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainProductPage extends JFrame {

    private DatabaseConnection conn = new DatabaseConnection();

    public MainProductPage(DatabaseConnection c) {
        conn=c;
        // Set the frame properties
        setTitle("Catalog Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Set the background color to blue
        getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue

        // Create buttons
        JButton editButton = createButton("Product Management");
        ActionListener edit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductManagementFrame productManagementFrame = new ProductManagementFrame(new ProductService(new ProductDao(conn)));
            }
        };
        editButton.addActionListener(edit);

        JButton organizeButton = createButton("Organize Products");
        organizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new ProductCategoryFrame(conn);
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column, with 10-pixel gaps

        // Add buttons to the panel
        buttonPanel.add(editButton);
        buttonPanel.add(organizeButton);

        // Set layout manager for the main frame
        setLayout(new BorderLayout());

        // Add the button panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(0, 0, 139)); // Dark blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the focus border
        button.setPreferredSize(new Dimension(200, 40)); // Adjust button size as needed

        return button;
    }

    /*public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();
            MainProductPage productPage = new MainProductPage(conn);
            productPage.setVisible(true);
        });
    }
     */
}

