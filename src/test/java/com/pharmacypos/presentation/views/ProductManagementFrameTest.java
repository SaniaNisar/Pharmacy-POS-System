package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProductManagementFrameTest {

    private ProductService mockProductService;
    private ProductManagementFrame productManagementFrame;
    private DefaultTableModel tableModel;

    @Before
    public void setUp() {
        // Create a mock ProductService
        mockProductService = mock(ProductService.class);

        // Prepare some mock products
        List<Product> mockProducts = Arrays.asList(
                new Product(1, "Product 1", "Desc 1", 100.0, 1, new Date("2021-01-01")),
                new Product(2, "Product 2", "Desc 2", 200.0, 2, new Date("2022-01-01"))
        );

        // Configure ProductService to return the mock products
        when(mockProductService.getAllProducts()).thenReturn(mockProducts);

        // Initialize ProductManagementFrame with the mock ProductService
        productManagementFrame = new ProductManagementFrame(mockProductService);

        // Access the table model
        tableModel = (DefaultTableModel) productManagementFrame.getProductTable().getModel();
    }

    @Test
    public void testFrameInitialization() {
        // Verify that the frame is not null
        assertNotNull(productManagementFrame.getFrame());
    }

    @Test
    public void testProductTableUpdate() {
        // Verify that the ProductService.getAllProducts() is called
        verify(mockProductService).getAllProducts();

        // Verify the table model is updated with the correct number of rows
        assertEquals(2, tableModel.getRowCount());

        // Verify the first row contains the correct data
        assertEquals("Product 1", tableModel.getValueAt(0, 1));
        assertEquals("Desc 1", tableModel.getValueAt(0, 2));
        assertEquals(100.0, tableModel.getValueAt(0, 3));
        assertEquals(1, tableModel.getValueAt(0, 4));
        assertEquals("2021-01-01", tableModel.getValueAt(0, 5));
    }

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Test
    public void testAddButtonAction() {
        // Find the add button
        JButton addButton = productManagementFrame.getAddButton(); // You'll need to add a getter for this
        assertNotNull(addButton);

        // Simulate clicking the add button
        addButton.doClick();

        assertTrue(addButton.getActionListeners().length > 0);
    }

    @Test
    public void testDeleteButtonAction() {
        // Assuming there are products in the table, select the first one
        JTable productTable = new JTable();
        productTable.setRowSelectionInterval(0, 0);

        // Find the delete button
        JButton deleteButton = productManagementFrame.getDeleteButton(); // You'll need to add a getter for this
        assertNotNull(deleteButton);

        // Simulate clicking the delete button
        deleteButton.doClick();

        // Capture the argument and verify that the correct product ID is being deleted
        verify(mockProductService).deleteProduct(productCaptor.getValue().getProductId());
        assertEquals(1, productCaptor.getValue().getProductId());
    }

    @Test
    public void testEditButtonAction() {
        // Assuming there are products in the table, select the first one
        JTable productTable = new JTable();
        productTable.setRowSelectionInterval(0, 0);

        // Find the edit button
        JButton editButton = productManagementFrame.getEditButton(); // You'll need to add a getter for this
        assertNotNull(editButton);

        // Simulate clicking the edit button
        editButton.doClick();


        assertTrue(editButton.getActionListeners().length > 0);
    }

    @Test
    public void testRefreshButtonAction() {
        // Find the refresh button
        JButton refreshButton = productManagementFrame.getRefreshButton(); // You'll need to add a getter for this
        assertNotNull(refreshButton);

        // Simulate clicking the refresh button
        refreshButton.doClick();

        // Verify that the table model is refreshed
        verify(mockProductService, atLeast(2)).getAllProducts();
    }
}

