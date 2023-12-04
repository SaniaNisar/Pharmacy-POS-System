package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.CategoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Category;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.CategoryService;
import com.pharmacyPOS.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductCategoryFrameTest {

    @Mock
    private DatabaseConnection mockDatabaseConnection;
    @Mock
    private CategoryService mockCategoryService;
    @Mock
    private ProductService mockProductService;
    private ProductCategoryFrame productCategoryFrame;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        verify(mockDatabaseConnection.connect());

        // Mock the category and product services
        when(mockCategoryService.getAllCategories()).thenReturn(Arrays.asList(
                new Category(1, "Category1","Cat1 desc"),
                new Category(2, "Category2","Cat2 desc")
        ));

        when(mockProductService.getProductsByCategory("Category1")).thenReturn(Arrays.asList(
                new Product("Product1", "Description1", 100.0, 1,new Date("2024-02-23")),
                new Product( "Product2", "Description2", 200.0, 1,new Date("2025-12-26"))
        ));

        productCategoryFrame = new ProductCategoryFrame(mockDatabaseConnection);
    }

    @Test
    public void testFrameInitialization() {
        assertNotNull(productCategoryFrame.getFrame());
        assertNotNull(productCategoryFrame.getProductTable());
        assertNotNull(productCategoryFrame.getCategoryButtonGroup());
    }

    @Test
    public void testCategorySelectionAction() {
        // Simulate selecting a category
        JRadioButton radioButton = findRadioButton(productCategoryFrame.getCategoryButtonGroup(), "Category1");
        assertNotNull(radioButton);
        radioButton.doClick();

        // Verify that the table is updated with products of the selected category
        JTable table =  productCategoryFrame.getProductTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals(2, model.getRowCount());
        assertEquals("Product1", model.getValueAt(0, 0));
        assertEquals("Product2", model.getValueAt(1, 0));
    }

    private JRadioButton findRadioButton(ButtonGroup group, String buttonText) {
        for (AbstractButton button : Collections.list(group.getElements())) {
            if (button.getText().equals(buttonText)) {
                return (JRadioButton) button;
            }
        }
        return null;
    }
        @Test
    public void testCategoryRadioButtonSelection() {
        ProductCategoryFrame frame = new ProductCategoryFrame(mockDatabaseConnection);
        ButtonGroup categoryButtonGroup = frame.getCategoryButtonGroup();

        // Find the specific radio button for a category
        Enumeration<AbstractButton> buttons = categoryButtonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if ("Category1".equals(button.getText())) {
                button.doClick(); // Simulate click
                break;
            }
        }

        // Now check if the product table is updated correctly
        JTable productTable = frame.getProductTable();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        assertEquals("Expected number of rows after selection", 2, model.getRowCount());
    }
}
