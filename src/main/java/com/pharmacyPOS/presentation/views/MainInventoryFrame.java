package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInventoryFrame extends JFrame {

    private DatabaseConnection conn;

    public MainInventoryFrame(DatabaseConnection c) {
        this.conn = c;

        setTitle("Inventory Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue

        // Create buttons
        JButton trackExpiredOrdersButton = createButton("Track Expired Orders");
        trackExpiredOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrackExpiredProductsFrame trackExpiredProductsFrame = new TrackExpiredProductsFrame(conn);
                trackExpiredProductsFrame.setVisible(true);
            }
        });

        JButton viewAlertsButton = createButton("View Alerts");
        viewAlertsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to handle View Alerts
            }
        });

        JButton manageInventoryButton = createButton("Manage Inventory");
        manageInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to open Manage Inventory
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, with 10-pixel gaps

        buttonPanel.add(trackExpiredOrdersButton);
        buttonPanel.add(viewAlertsButton);
        buttonPanel.add(manageInventoryButton);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(0, 0, 139)); // Dark blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50)); // Adjust button size as needed

        return button;
    }
}
