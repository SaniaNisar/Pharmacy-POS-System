package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TrackExpProdsFrameTest {

    private TrackExpiredProductsFrame trackExpiredProductsFrame;
    private ProductService mockProductService;
    private InventoryService mockInventoryService;
    private DatabaseConnection mockConnection;

    @Before
    public void setUp() {
        mockConnection = Mockito.mock(DatabaseConnection.class);
        mockProductService = Mockito.mock(ProductService.class);
        mockInventoryService = Mockito.mock(InventoryService.class);

        // Setup mock responses
//        when(mockConnection.connect()).thenReturn(true);
        when(mockProductService.getAllProducts()).thenReturn(createTestProducts());

        trackExpiredProductsFrame = new TrackExpiredProductsFrame(mockConnection);
    }

    private List<Product> createTestProducts() {
        // Create a list of test products
        Product product1 = new Product();
        // Set properties for product1, including an expiration date that is past

        Product product2 = new Product();
        // Set properties for product2, including an expiration date that is future

        return Arrays.asList(product1, product2);
    }

    @Test
    public void testFrameInitialization() {
        assertNotNull("Frame should be initialized", trackExpiredProductsFrame);
    }

    @Test
    public void testTableLoading() {
        JTable table = findProductsTable(trackExpiredProductsFrame);
        assertNotNull("Table should be initialized", table);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals("Table should contain rows for each product", 2, model.getRowCount());
    }

    private JTable findProductsTable(JFrame frame) {
        for (Component comp : frame.getContentPane().getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                if (scrollPane.getViewport().getView() instanceof JTable) {
                    return (JTable) scrollPane.getViewport().getView();
                }
            }
        }
        return null;
    }
    @Test
    public void testRemoveButtonRendering() {
        JTable table = findProductsTable(trackExpiredProductsFrame);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String actionText = (String) model.getValueAt(i, 6); // Assuming column 6 is the "Action" column
            Date expirationDate = (Date) model.getValueAt(i, 4); // Assuming column 4 is the "Expiration Date" column
            if (new Date(System.currentTimeMillis()).after(expirationDate)) {
                assertEquals("Remove", actionText);
            } else {
                assertEquals("", actionText);
            }
        }
    }

}
