package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.presentation.controllers.ProductController;
import com.pharmacyPOS.presentation.controllers.ReplenishmentController;
import com.pharmacyPOS.service.ProductService;
import com.pharmacyPOS.service.ReplenishmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryManagementPage extends JFrame {
    private ReplenishmentController replenishmentController;
    private InventoryDao inventoryDao;
    private ProductService productService;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    public InventoryManagementPage(DatabaseConnection conn) {

        this.inventoryDao = new InventoryDao(conn);
        this.productService = new ProductService(new ProductDao(conn));
        this.replenishmentController = new ReplenishmentController(new ReplenishmentService(inventoryDao,productService));// Assuming ProductDao constructor accepts DatabaseConnection

        setupUI();
        loadInventoryItems();

        setTitle("Inventory Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Inventory ID", "Product ID", "Product Name", "Quantity", "Expiry Date", "Low Stock Threshold"});
        inventoryTable = new JTable(tableModel);

        JButton addButton = new JButton("Add New Stock");
        addButton.addActionListener(this::onAddStockClicked);

        JButton updateButton = new JButton("Update Selected Stock");
        updateButton.addActionListener(this::onUpdateStockClicked);

        JButton deleteButton = new JButton("Delete Selected Stock");
        deleteButton.addActionListener(this::onDeleteStockClicked);

        JButton refreshButton = new JButton("Refresh Inventory");
        refreshButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadInventoryItems();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadInventoryItems() {
        tableModel.setRowCount(0);
        List<Inventory> inventoryList = inventoryDao.getAllInventory();
        for (Inventory inventory : inventoryList) {
            Product product = productService.getProductById(inventory.getProductId());
            String productName = (product != null) ? product.getName() : "Unknown Product";

            tableModel.addRow(new Object[]{
                    inventory.getInventoryId(),
                    inventory.getProductId(),
                    productName,
                    inventory.getQuantity(),
                    inventory.getExpiryDate(),
                    inventory.getLowStockThreshold()
            });
        }
    }

    private void onAddStockClicked(ActionEvent e) {
        new AddInventoryFrame(replenishmentController);
    }

    private void onUpdateStockClicked(ActionEvent e) {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            int inventoryId = (int) tableModel.getValueAt(selectedRow, 0);
            Inventory inventory = inventoryDao.getInventoryById(inventoryId);

            // Retrieve the associated product
            Product product = productService.getProductById(inventory.getProductId());

            if (inventory != null && product != null) {
                UpdateInventoryFrame updateFrame = new UpdateInventoryFrame(inventory, inventoryDao, product);
                updateFrame.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an inventory item to update", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void onDeleteStockClicked(ActionEvent e) {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            int inventoryId = (int) tableModel.getValueAt(selectedRow, 0);
            inventoryDao.deleteInventoryItem(inventoryId);
            loadInventoryItems();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an inventory item to delete", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        InventoryDao inventoryDao = new InventoryDao(dbConnection);
        ProductService productService = new ProductService(new ProductDao(dbConnection));
        ReplenishmentService replenishmentService = new ReplenishmentService(inventoryDao, productService);
        new InventoryManagementPage(dbConnection);
    }

    // ... existing main method ...
}
