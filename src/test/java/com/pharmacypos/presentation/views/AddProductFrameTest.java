package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.presentation.views.AddProductFrame;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.table.DefaultTableModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AddProductFrameTest {

    @Mock
    private ProductService mockProductService;
    @Mock
    private InventoryService mockInventoryService;
    private AddProductFrame addProductFrame;
    private DefaultTableModel mockTableModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockTableModel = new DefaultTableModel();
        addProductFrame = new AddProductFrame(mockTableModel, mockProductService, mockInventoryService);
    }

    @Test
    public void testUIInitialization() {
        assertNotNull(addProductFrame.getNameTextField());
        assertNotNull(addProductFrame.getDescriptionTextField());
        assertNotNull(addProductFrame.getPriceTextField());
        assertNotNull(addProductFrame.getCategoryIdTextField());
        assertNotNull(addProductFrame.getExpirationDateTextField());
        assertNotNull(addProductFrame.getQuantityTextField());
        assertNotNull(addProductFrame.getAddButton());
    }

    @Test
    public void testAddProductValidInput() {
        setValidInputFields();

        // Simulate clicking the add button
        addProductFrame.getAddButton().doClick();

        // Verify interaction with ProductService and InventoryService
        verify(mockProductService).createProduct(any(Product.class));
        verify(mockInventoryService).createInventoryItem(any(Inventory.class));

        // Verify the table model is updated
        assertEquals(1, mockTableModel.getRowCount());
    }

    @Test
    public void testAddProductInvalidInput() {
        setInvalidInputFields();

        // Simulate clicking the add button
        addProductFrame.getAddButton().doClick();

        // Verify that no interaction with services occurs due to invalid input
        verify(mockProductService, never()).createProduct(any(Product.class));
        verify(mockInventoryService, never()).createInventoryItem(any(Inventory.class));

        // Verify no rows are added to the table model
        assertEquals(0, mockTableModel.getRowCount());
    }

    private void setValidInputFields() {
        addProductFrame.getNameTextField().setText("Product Name");
        addProductFrame.getDescriptionTextField().setText("Description");
        addProductFrame.getPriceTextField().setText("10.0");
        addProductFrame.getCategoryIdTextField().setText("1");
        addProductFrame.getExpirationDateTextField().setText("2023-12-31");
        addProductFrame.getQuantityTextField().setText("100");
    }

    private void setInvalidInputFields() {
        addProductFrame.getNameTextField().setText("");
        addProductFrame.getDescriptionTextField().setText("");
        addProductFrame.getPriceTextField().setText("invalid");
        addProductFrame.getCategoryIdTextField().setText("invalid");
        addProductFrame.getExpirationDateTextField().setText("invalid");
        addProductFrame.getQuantityTextField().setText("invalid");
    }
}
