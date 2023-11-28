package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerDashboard extends JFrame {

    private DatabaseConnection conn;

    public ManagerDashboard(DatabaseConnection c) {
        this.conn = c;
        setTitle("Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Add menu "Inventory"
       /* JMenu inventoryMenu = new JMenu("Inventory");
        JMenuItem manageInventoryItem = new JMenuItem("Manage Inventory");
        manageInventoryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to open inventory management
            }
        });
        inventoryMenu.add(manageInventoryItem);
        */

        JMenu inventoryMenu = new JMenu("Inventory");

        // Menu item "Manage Inventory"
        JMenuItem manageInventoryItem = new JMenuItem("Manage Inventory");
        manageInventoryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new InventoryManagementPage(conn);
            }
        });
        inventoryMenu.add(manageInventoryItem);

        JMenu logoutMenu = new JMenu("Logout");
        JMenuItem logout = new JMenuItem("Logout Manager");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                //setDefaultCloseOperation(EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(null,"You have been logged out successfully!");

                new LoginGUI(conn);
                //setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
        logoutMenu.add(logout);


        // Menu item "View Alerts"
        JMenuItem viewAlertsItem = new JMenuItem("View Alerts");
        viewAlertsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LowStockAlertFrame(new InventoryService(new InventoryDao(conn)));
            }
        });
        inventoryMenu.add(viewAlertsItem);

        // Menu item "Track Expired Products"
        JMenuItem trackExpiredProductsItem = new JMenuItem("Track Expired Products");
        trackExpiredProductsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrackExpiredProductsFrame frame = new TrackExpiredProductsFrame(conn);
                frame.setVisible(true);
            }
        });
        inventoryMenu.add(trackExpiredProductsItem);


        // Add menu "Catalog"
        JMenu productCatalog = new JMenu("Catalog");
        JMenuItem editProducts = new JMenuItem("Edit Products");
        editProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ProductManagementFrame productManagementFrame = new ProductManagementFrame(new ProductService(new ProductDao(conn)));
            }
        });
        productCatalog.add(editProducts);

        JMenuItem organizeProducts = new JMenuItem("Organize Products");
        organizeProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductCategoryFrame(conn);
            }
        });
        productCatalog.add(organizeProducts);

        JMenuItem manageCategories = new JMenuItem("Manage Categories");
        manageCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageCategoriesPage(conn);
            }
        });
        productCatalog.add(manageCategories);


        // Add menu "Reports"
        JMenu reportsMenu = new JMenu("Reports");
        JMenuItem salesReportItem = new JMenuItem("Sales Report");
        salesReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to generate sales report
            }
        });
        JMenuItem inventoryReportItem = new JMenuItem("Inventory Report");
        inventoryReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to generate inventory report
            }
        });
        reportsMenu.add(salesReportItem);
        reportsMenu.add(inventoryReportItem);

        // Add menus to the menu bar
        menuBar.add(inventoryMenu);
        menuBar.add(productCatalog);
        menuBar.add(reportsMenu);
        menuBar.add(logoutMenu);

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("Welcome, Manager", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Create a main panel
        JButton inventory = new JButton("Manage Inventory");
        JButton reports = new JButton("Generate Reports");
        JButton catalog = new JButton("Manage Catalog");
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Example layout

        catalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProductPage productPage = new MainProductPage(conn);
                productPage.setVisible(true);
            }
        });

        inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainInventoryFrame mainInventoryFrame = new MainInventoryFrame(conn);
                mainInventoryFrame.setVisible(true);
            }
        });

        reports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainReportsPage(conn);
            }
        });

        mainPanel.add(inventory);
        mainPanel.add(catalog);
        mainPanel.add(reports);
        add(mainPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection conn = new DatabaseConnection();
            conn.connect();
            ManagerDashboard managerDashboard = new ManagerDashboard(conn);
            managerDashboard.setVisible(true);
        });
    }
}
