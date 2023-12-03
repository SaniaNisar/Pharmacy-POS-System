package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import javax.swing.*;
import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UpdateProductFrameTest {

    private UpdateProductFrame updateProductFrame;
    private Product testProduct;
    private ProductService mockProductService;

    @Before
    public void setUp() {
        testProduct = new Product();
        testProduct.setName("TestProduct");
        testProduct.setDescription("TestDescription");
        testProduct.setPrice(100.0);
        testProduct.setCategoryId(1);
        testProduct.setExpirationDate(Date.valueOf("2023-12-31"));

        // Create a mock ProductService
        mockProductService = Mockito.mock(ProductService.class);

        updateProductFrame = new UpdateProductFrame(testProduct, mockProductService);
    }

    @Test
    public void testComponentInitialization() {
        assertEquals("TestProduct", ((JTextField) updateProductFrame.getContentPane().getComponent(1)).getText());
        assertEquals("TestDescription", ((JTextField) updateProductFrame.getContentPane().getComponent(3)).getText());
        assertEquals("100.0", ((JTextField) updateProductFrame.getContentPane().getComponent(5)).getText());
        assertEquals("1", ((JTextField) updateProductFrame.getContentPane().getComponent(7)).getText());
        assertEquals("2023-12-31", ((JTextField) updateProductFrame.getContentPane().getComponent(9)).getText());
    }

    @Test
    public void testUpdateButtonAction() {
        // Simulate user input
        ((JTextField) updateProductFrame.getContentPane().getComponent(1)).setText("UpdatedName");
        ((JTextField) updateProductFrame.getContentPane().getComponent(3)).setText("UpdatedDescription");
        ((JTextField) updateProductFrame.getContentPane().getComponent(5)).setText("200.0");
        ((JTextField) updateProductFrame.getContentPane().getComponent(7)).setText("2");
        ((JTextField) updateProductFrame.getContentPane().getComponent(9)).setText("2024-01-01");

        // Simulate button click
        JButton updateButton = (JButton) updateProductFrame.getContentPane().getComponent(11);
        updateButton.doClick();

        // Verify that ProductService.updateProduct was called once with the updated product
        verify(mockProductService, times(1)).updateProduct(any(Product.class));
    }
}

