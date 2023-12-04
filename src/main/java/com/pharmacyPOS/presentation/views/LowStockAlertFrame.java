package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LowStockAlertFrame extends JFrame {
    private InventoryService inventoryService;
    private JPanel mainPanel;
    private JButton refreshButton;

    public LowStockAlertFrame(InventoryService inventoryService)
    {
        this.inventoryService = inventoryService;

        setTitle("Low Stock Alerts");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        refreshButton = new JButton("Refresh Low Stock Alerts");
        refreshButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLowStockDisplay();
            }
        });

        add(refreshButton, BorderLayout.NORTH);
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        // Initial update
        updateLowStockDisplay();

        setVisible(true);
    }

    private void updateLowStockDisplay() {
        mainPanel.removeAll();

        List<Product> lowStockProducts = inventoryService.checkAndAlertLowStock();

        if (lowStockProducts.isEmpty())
        {
            mainPanel.add(new JLabel("No low stock items found."));
        }
        else
        {
            for (Product product : lowStockProducts)
            {
                JPanel productPanel = new JPanel();
                productPanel.add(new JLabel(product.getName() + " (ID: " + product.getProductId() + ") - Low Stock"));
                JButton reorderButton = new JButton("Reorder");
                reorderButton.addActionListener(e -> initiateReorder(product));
                productPanel.add(reorderButton);
                mainPanel.add(productPanel);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void initiateReorder(Product product) {
        JOptionPane.showMessageDialog(this, "Reorder initiated for: " + product.getName());
    }

    public static void main(String[] args) {
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();
        ProductService productService = new ProductService(new ProductDao(conn)); // Replace with actual implementation
        InventoryService inventoryService = new InventoryService(new InventoryDao(conn)); // Replace with actual implementation

        new LowStockAlertFrame(inventoryService);
    }
}
