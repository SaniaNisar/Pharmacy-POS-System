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
import com.pharmacyPOS.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SearchInventoryFrameTest {
    @Mock private DatabaseConnection mockConnection;
    @Mock private ProductDao mockProductDao;
    @Mock private ProductService mockProductService;
    @Mock private ProductController mockProductController;
    @Mock private CartDao mockCartDao;
    @Mock private CartService mockCartService;
    @Mock private CartController mockCartController;

    private SearchInventoryFrame searchInventoryFrame;
    private final int testUserId = 1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        when(mockConnection.connect()).thenReturn(true);

        // Assume these controllers have been refactored to be injectable
        mockProductService = new ProductService(mockProductDao);
        mockProductController = new ProductController(mockProductService);
        mockCartService = new CartService(mockCartDao);
        mockCartController = new CartController(mockCartService);

        // Setup the frame with mocked dependencies
        searchInventoryFrame = new SearchInventoryFrame(testUserId);
    }

    @Test
    public void testSearchFunctionality() {
        // Mock the product search results
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1, "TestProduct", "Description", 10.00, 5, new Date("2024-02-12")));
        when(mockProductController.findProductsByName(anyString())).thenReturn(mockProducts);

        // Call the method to perform search directly (this method may need to be made package-private or public for testing)
        searchInventoryFrame.performSearch("Test");

        // Access the table model directly or use a getter if one is provided
        DefaultTableModel model = (DefaultTableModel) searchInventoryFrame.getSearchResultsTable().getModel();

        // Verify the table now contains the search results
        assertEquals(1, model.getRowCount());
        assertEquals("TestProduct", model.getValueAt(0, 1));

        // Verify that the search was actually performed using the controller
        verify(mockProductController, times(1)).findProductsByName("Test");
    }

    @Test
    public void testAddToCartFunctionality() throws SQLException {
        // Assume a product is already found and the table is populated
        List<Product> mockProducts = Arrays.asList(
                new Product(1, "TestProduct", "Description", 10.00, 5, new Date("2024-02-12")) // Today's date as the expiration date
        );
        when(mockProductController.findProductsByName(anyString())).thenReturn(mockProducts);
        searchInventoryFrame.performSearch(new ActionEvent(searchInventoryFrame.getSearchButton(), ActionEvent.ACTION_PERFORMED, null));

        // Simulate the user selecting the first row in the table
        searchInventoryFrame.getSearchResultsTable().setRowSelectionInterval(0, 0);

        // Call the method to add to cart directly (this method may need to be made package-private or public for testing)
        searchInventoryFrame.onAddToCartClicked(new ActionEvent(searchInventoryFrame.getAddToCartButton(), ActionEvent.ACTION_PERFORMED, null));

        // Capture the argument to verify that the correct product ID is being added to the cart
        ArgumentCaptor<SaleItem> saleItemCaptor = ArgumentCaptor.forClass(SaleItem.class);
        verify(mockCartController, times(1)).addItemToCart(eq(searchInventoryFrame.getCurrentCart().getCartId()), saleItemCaptor.capture());

        SaleItem capturedSaleItem = saleItemCaptor.getValue();
        assertEquals(1, capturedSaleItem.getProductId());
        assertEquals(1, capturedSaleItem.getQuantity()); // assuming the quantity is set to 1 by default
    }
}
