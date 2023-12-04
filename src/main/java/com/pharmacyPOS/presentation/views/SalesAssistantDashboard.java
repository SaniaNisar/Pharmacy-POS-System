package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesAssistantDashboard extends JFrame {

    private DatabaseConnection conn;
    private int userId;

    public SalesAssistantDashboard(DatabaseConnection c, int id) {
        this.userId=id;
        this.conn = c;
        setTitle("Sales Assistant Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Add menu "Transactions"
        JMenu transactionsMenu = new JMenu("Transactions");

        // Menu item "Search Inventory"
        JMenuItem searchInventoryItem = new JMenuItem("Search Inventory");
        searchInventoryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchInventoryFrame(id);
            }
        });
        // transactionsMenu.add(searchInventoryItem);

        // Menu item "Manage Cart"
        JMenuItem manageCartItem = new JMenuItem("Manage Cart");
        manageCartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageCartFrame(id);
            }
        });
        transactionsMenu.add(manageCartItem);

        JMenu cartMenu = new JMenu("Cart");
        JMenuItem viewCart = new JMenuItem("View Cart");
        viewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageCartFrame(userId);
            }
        });
        cartMenu.add(viewCart);

        // Add menu "Logout"
        JMenu logoutMenu = new JMenu("Logout");
        JMenuItem logoutItem = new JMenuItem("Logout Sales Assistant");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //setDefaultCloseOperation(EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(null,"You have been logged out successfully!");
                new LoginGUI(conn);
            }
        });
        logoutMenu.add(logoutItem);

        JMenu searchMenu = new JMenu("Search");
        JMenuItem searchItem = new JMenuItem("Search Inventory");
        searchItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchInventoryFrame(id);
            }
        });

        searchMenu.add(searchItem);
        // Add menus to the menu bar
        menuBar.add(transactionsMenu);
        menuBar.add(searchMenu);
        menuBar.add(logoutMenu);
        menuBar.add(cartMenu);


        // Create a welcome label
        JLabel welcomeLabel = new JLabel("Welcome, Sales Assistant", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Create a main panel
        JButton searchInventoryButton = new JButton("Search Inventory");
        JButton manageCartButton = new JButton("Manage Cart");
       // JButton invoicePaymentButton = new JButton("Invoice and Payment");
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Example layout

        searchInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchInventoryFrame(id);
            }
        });

        manageCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageCartFrame(userId);
            }
        });

        mainPanel.add(searchInventoryButton);
        mainPanel.add(manageCartButton);
        //mainPanel.add(invoicePaymentButton);
        add(mainPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();
            SalesAssistantDashboard salesAssistantDashboard = new SalesAssistantDashboard(conn,2);
            salesAssistantDashboard.setVisible(true);
        });
    }
}
