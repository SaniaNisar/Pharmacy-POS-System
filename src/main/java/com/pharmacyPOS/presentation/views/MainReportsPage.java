package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainReportsPage extends JFrame {

    private DatabaseConnection conn;

    public MainReportsPage(DatabaseConnection c) {
        this.conn = c;

        // Set the frame properties
        setTitle("Reports");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Set the background color to green
        getContentPane().setBackground(new Color(0, 128, 0)); // Green

        // Create buttons
        JButton salesReportButton = createButton("Sales Report");
        salesReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to generate and display sales report
            }
        });

        JButton inventoryReportButton = createButton("Inventory Report");
        inventoryReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to generate and display inventory report
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column, with 10-pixel gaps

        // Add buttons to the panel
        buttonPanel.add(salesReportButton);
        buttonPanel.add(inventoryReportButton);

        // Set layout manager for the main frame
        setLayout(new BorderLayout());

        // Add the button panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);

    }

    private JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBackground(new Color(0, 0, 139));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the focus border
        button.setPreferredSize(new Dimension(200, 40)); // Adjust button size as needed

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();
            MainReportsPage reportsPage = new MainReportsPage(conn);
            reportsPage.setVisible(true);
        });
    }
}
