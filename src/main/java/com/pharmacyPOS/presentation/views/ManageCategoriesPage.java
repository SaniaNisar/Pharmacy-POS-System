package com.pharmacyPOS.presentation.views;
import com.pharmacyPOS.data.dao.CategoryDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Category;
import com.pharmacyPOS.presentation.controllers.CategoryController;
import com.pharmacyPOS.service.CategoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ManageCategoriesPage extends JFrame
{
    private CategoryController categoryController;
    private JTable categoriesTable;
    private DefaultTableModel tableModel;

    public ManageCategoriesPage(DatabaseConnection conn)
    {
        this.categoryController = new CategoryController(new CategoryService(new CategoryDao(conn)));

        setupUI();
        loadCategories();

        setTitle("Manage Categories");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        tableModel = new DefaultTableModel(new Object[]{"Category ID", "Name", "Description"}, 0);
        categoriesTable = new JTable(tableModel);

        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(this::onAddCategoryClicked);

        JButton removeButton = new JButton("Remove Selected Category");
        removeButton.addActionListener(this::onRemoveCategoryClicked);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(new JScrollPane(categoriesTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCategories() {
        tableModel.setRowCount(0);
        List<Category> categories = categoryController.getAllCategories();
        for (Category category : categories) {
            tableModel.addRow(new Object[]{category.getCategoryId(), category.getName(), category.getDescription()});
        }
    }

    private void onAddCategoryClicked(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Enter category name:");
        String description = JOptionPane.showInputDialog(this, "Enter category description:");

        if (name != null && description != null) {
            Category newCategory = new Category(0, name, description);
            categoryController.createCategory(newCategory);
            loadCategories();
        }
    }

    private void onRemoveCategoryClicked(ActionEvent e) {
        int selectedRow = categoriesTable.getSelectedRow();
        if (selectedRow >= 0) {
            int categoryId = (int) tableModel.getValueAt(selectedRow, 0);
            categoryController.deleteCategory(categoryId);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category to remove", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args)
    {
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();
        CategoryDao categoryDao = new CategoryDao(dbConnection);
        new ManageCategoriesPage(dbConnection);
    }
}

