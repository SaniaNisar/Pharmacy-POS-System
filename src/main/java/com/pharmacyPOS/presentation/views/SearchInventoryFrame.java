package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.CartDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.entities.SaleItem;
import com.pharmacyPOS.presentation.controllers.CartController;
import com.pharmacyPOS.presentation.controllers.ProductController;
import com.pharmacyPOS.service.CartService;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class SearchInventoryFrame extends JFrame {
    private ProductController productController;
    private JTextField searchTextField;
    private JButton searchButton;
    private JPanel resultPanel;
    private DatabaseConnection conn;
    private JButton addToCartButton;
    private JTable searchResultsTable;
    private DefaultTableModel searchResultsTableModel;
    private CartController cartController;
    private Cart currentCart;
    private int userId;

    public SearchInventoryFrame(int userId) {
        this.userId = userId; // Set the userId for the current user session

        conn = new DatabaseConnection();
        conn.connect();
        this.productController = new ProductController(new ProductService(new ProductDao(conn)));
        this.cartController = new CartController(new CartService(new CartDao(conn)));

        initializeCart();

        addToCartButton = new JButton("Add to Cart");
        searchResultsTableModel = new DefaultTableModel();
        searchResultsTable = new JTable(searchResultsTableModel);

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(new JScrollPane(searchResultsTable), BorderLayout.CENTER);
        resultsPanel.add(addToCartButton, BorderLayout.SOUTH);

        add(resultsPanel, BorderLayout.CENTER);

        addToCartButton.addActionListener(this::onAddToCartClicked);

        setTitle("Search Inventory");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();

        setVisible(true);
    }

    private void setupUI() {
        searchTextField = new JTextField(20);
        searchButton = new JButton("Search");
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Enter Product Name:"));
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Initialize table columns
        searchResultsTableModel.setColumnIdentifiers(new String[] {"Product ID", "Name", "Description", "Price"});

        searchButton.addActionListener(this::performSearch);
    }

    private void performSearch(ActionEvent event) {
        String query = searchTextField.getText().trim();
        searchResultsTableModel.setRowCount(0); // Clear existing data

        if (!query.isEmpty()) {
            List<Product> products = productController.findProductsByName(query);
            if (products.isEmpty()) {
                searchResultsTableModel.addRow(new Object[]{"No products found.", "", "", ""});
            } else {
                for (Product product : products) {
                    searchResultsTableModel.addRow(new Object[]{product.getProductId(), product.getName(), product.getDescription(), product.getPrice()});
                }
            }
        }
    }

    /*private void onAddToCartClicked(ActionEvent e) {
        int selectedRow = searchResultsTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = searchResultsTableModel.getValueAt(selectedRow, 0).toString();
            String productName = searchResultsTableModel.getValueAt(selectedRow, 1).toString();
            double price = Double.parseDouble(searchResultsTableModel.getValueAt(selectedRow, 3).toString());

            // Assuming a SaleItem constructor with a default quantity of 1
            SaleItem newItem = new SaleItem(Integer.parseInt(productId), 1, price);
            currentCart.addItem(newItem);
            try {
                cartController.addItemToCart(currentCart.getCartId(), newItem);
                JOptionPane.showMessageDialog(this, "Added to cart: " + productName);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding to cart: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
     */

    private void initializeCart() {
        try {
            currentCart = cartController.getCurrentCart(userId);
            if (currentCart == null)
            {
                currentCart = new Cart(userId);
                int cartId = cartController.createCart(currentCart);
                currentCart.setCartId(cartId);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error initializing cart: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAddToCartClicked(ActionEvent e) {
        int selectedRow = searchResultsTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = searchResultsTableModel.getValueAt(selectedRow, 0).toString();
            String productName = searchResultsTableModel.getValueAt(selectedRow, 1).toString();
            double price = Double.parseDouble(searchResultsTableModel.getValueAt(selectedRow, 3).toString());

            // Assuming a SaleItem constructor with a default quantity of 1
            SaleItem newItem = new SaleItem(Integer.parseInt(productId), 1, price);

            try {
                // Add the item to the currentCart object
                currentCart.addItem(newItem);
                // Persist the new item in the cart to the database
                cartController.addItemToCart(currentCart.getCartId(), newItem);
                JOptionPane.showMessageDialog(this, "Added to cart: " + productName);
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(this, "Error adding to cart: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                sqlException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SearchInventoryFrame(2);
    }
}
