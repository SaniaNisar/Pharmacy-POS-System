package com.pharmacyPOS.presentation.views;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;
import java.util.List;

public class ProductManagementFrame {
    private final ProductService productService;
    private JFrame frame;
    private JTable productTable;
    private DefaultTableModel tableModel;
    JButton addButton ;
    JButton deleteButton;
    JButton editButton ;
    JButton refreshButton ;

    public ProductManagementFrame(ProductService productService) {
        this.productService = productService;

        // Create the main frame
        frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // Create a table model and add columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Price");
        tableModel.addColumn("Category ID");
        tableModel.addColumn("Expiration Date");

        productTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create buttons for add, delete, and edit
         addButton = new JButton("Add");
         deleteButton = new JButton("Delete");
         editButton = new JButton("Edit");
         refreshButton = new JButton("Refresh");

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(refreshButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddProductFrame with the table model
                DatabaseConnection conn = new DatabaseConnection();
                conn.connect();
                new AddProductFrame(tableModel, productService, new InventoryService(new InventoryDao(conn)));
                updateProductTable();
            }
        });


        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int productId = (int) productTable.getValueAt(selectedRow, 0);
                    productService.deleteProduct(productId);
                    updateProductTable();
                }
            }
        });

        editButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the edit button action here
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int productId = (int) productTable.getValueAt(selectedRow, 0);
                    // Fetch the product details by productId
                    Product product = productService.getProductById(productId);

                    if (product != null) {
                        // Open the UpdateProductFrame with the selected product
                        new UpdateProductFrame(product, productService);
                    }
                }
            }
        });

        refreshButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable();
            }
        });


        // Initialize the product table with data
        updateProductTable();

        // Set frame visibility
        frame.setVisible(true);
    }

    private void updateProductTable()
    {
        // Fetch all products from the ProductService and update the table
        List<Product> products = productService.getAllProducts();
        tableModel.setRowCount(0); // Clear existing data
        for (Product product : products)
        {
            tableModel.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategoryId(),
                    product.getExpirationDate()
            });
        }
    }

    public static void main(String[] args) {
        // Initialize the ProductService and create the GUI
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();
        ProductDao productDao = new ProductDao(conn);
        ProductService productService = new ProductService(productDao);
        new ProductManagementFrame(productService);
    }

    public JTable getProductTable() {
        return productTable;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}

