package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.dao.CategoryDao;
import com.pharmacyPOS.service.CategoryService;
import com.pharmacyPOS.data.entities.Category;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategoryFrame {
    private final JFrame frame;
    private final Map<String, List<Product>> categories;
    private JTable productTable;
    private ButtonGroup categoryButtonGroup;

    DatabaseConnection databaseConnection;

    public ProductCategoryFrame(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        frame = new JFrame("Product Categories");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Initialize categories and products
        categories = new HashMap<>();

        // Fetch categories from the database and populate the HashMap
        CategoryService categoryService = new CategoryService(new CategoryDao(databaseConnection));
        List<Category> categoryList = categoryService.getAllCategories();

        for (Category category : categoryList) {
            categories.put(category.getName(), new ArrayList<>());
        }

        // Create a panel to display components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a label for category selection
        JLabel categoryLabel = new JLabel("Select your Category:");
        panel.add(categoryLabel, BorderLayout.NORTH);
        panel.setBackground(new Color(173, 216, 230));
        // Create a radio button group for category selection
        categoryButtonGroup = new ButtonGroup();
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(categories.size(), 1));

        for (String category : categories.keySet()) {
            JRadioButton categoryRadioButton = new JRadioButton(category);
            //categoryRadioButton.setForeground(Color.BLUE);
            categoryRadioButton.setBackground(new Color(173, 216, 230)); // Light blue background
            categoryRadioButton.addActionListener(new CategoryRadioButtonListener(category));
            categoryButtonGroup.add(categoryRadioButton);
            radioPanel.add(categoryRadioButton);
        }

        // Create a table model for product table
        DefaultTableModel productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Product Name");
        productTableModel.addColumn("Description");
        productTableModel.addColumn("Price");

        // Create the product table
        productTable = new JTable(productTableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Add components to the panel
        panel.add(radioPanel, BorderLayout.WEST);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public ButtonGroup getCategoryButtonGroup() {
        return categoryButtonGroup;
    }

    private class CategoryRadioButtonListener implements ActionListener {
        private final String category;

        public CategoryRadioButtonListener(String category) {
            this.category = category;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // When a radio button is selected, fetch and display products for that category
            List<Product> products = categories.get(category);

            // Fetch products from the database using ProductDao
            ProductService productService = new ProductService(new ProductDao(databaseConnection));
            List<Product> productList = productService.getProductsByCategory(category);

            // Clear the existing products for the category and add the new products
            products.clear();
            products.addAll(productList);

            // Update the product table with the new products
            DefaultTableModel productTableModel = (DefaultTableModel) productTable.getModel();
            productTableModel.setRowCount(0); // Clear existing rows
            for (Product product : products) {
                productTableModel.addRow(new Object[]{product.getName(), product.getDescription(), product.getPrice()});
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection c = new DatabaseConnection();
            c.connect();
            new ProductCategoryFrame(c);
        });
    }
}
