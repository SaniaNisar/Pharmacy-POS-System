package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.presentation.controllers.OrderController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.ActionEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrderProcessingFrameTest {

    @Mock
    private OrderController mockOrderController;
    private OrderProcessingFrame orderProcessingFrame;
    private final double totalAmount = 100.0;
    private final int orderId = 1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderProcessingFrame = new OrderProcessingFrame(totalAmount, orderId);
        orderProcessingFrame.setOrderController(mockOrderController); // Assuming a setter for orderController
    }

    @Test
    public void testUIInitialization() {
        assertNotNull(orderProcessingFrame.getTotalAmountLabel());
        assertNotNull(orderProcessingFrame.getAmountPaidTextField());
        assertNotNull(orderProcessingFrame.getAmountToBeReturnedLabel());
        assertNotNull(orderProcessingFrame.getCancelOrderButton());
        assertNotNull(orderProcessingFrame.getGenerateInvoiceButton());
    }

    @Test
    public void testCalculateChangeValidInput() {
        orderProcessingFrame.getAmountPaidTextField().setText("120.00");
        orderProcessingFrame.calculateChange(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        assertEquals("Amount to be returned: $20.00", orderProcessingFrame.getAmountToBeReturnedLabel().getText());
    }

    @Test
    public void testCalculateChangeInvalidInput() {
        orderProcessingFrame.getAmountPaidTextField().setText("invalid");
    }

    @Test
    public void testGenerateInvoice() {
        // Mock getOrderById to return a valid order
        when(mockOrderController.getOrderById(orderId)).thenReturn(new Order()); // Mocked order object

        orderProcessingFrame.getAmountPaidTextField().setText("120.00");
        orderProcessingFrame.generateInvoice();

        // Verify getOrderById was called
        verify(mockOrderController).getOrderById(orderId);
    }
}
