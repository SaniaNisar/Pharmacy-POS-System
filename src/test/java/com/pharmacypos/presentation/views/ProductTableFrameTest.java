package com.pharmacyPOS.presentation.views;

import org.junit.Before;
import org.junit.Test;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductTableFrameTest {

    private ProductTableFrame productTableFrame;
    private List<String> testProducts;
    private DefaultTableModel productTableModel;

    @Before
    public void setUp() {
        // Create a list of test products
        testProducts = Arrays.asList("Product 1", "Product 2", "Product 3");
        // Initialize ProductTableFrame with the test products
        productTableFrame = new ProductTableFrame(testProducts);
        // Access the JTable model to verify table contents
        productTableModel = (DefaultTableModel) productTableFrame
                .getProductTable() // This method needs to be added to ProductTableFrame to get the table
                .getModel();
    }

    @Test
    public void testProductTablePopulation() {
        // Verify the table has the same number of rows as the size of testProducts
        assertEquals(testProducts.size(), productTableModel.getRowCount());

        // Verify each row contains the correct product name
        for (int i = 0; i < testProducts.size(); i++) {
            assertEquals(testProducts.get(i), productTableModel.getValueAt(i, 0));
        }
    }
}
