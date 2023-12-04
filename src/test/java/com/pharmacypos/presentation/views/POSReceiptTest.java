package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.SalesDao;
import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.data.entities.OrderDetail;
import com.pharmacyPOS.data.entities.Sale;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class POSReceiptTest {

    private POSReceipt posReceipt;
    private Order testOrder;

    @Mock
    private SalesDao mockSalesDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testOrder = createTestOrder();
        posReceipt = new POSReceipt(testOrder, 100.0, 120.0);
    }

    @Test
    public void testUIInitialization() {
        JTextArea frame = posReceipt.getFrame();
        assertNotNull(frame);

        JScrollPane scrollPane = findComponentOfType(frame, JScrollPane.class);
        assertNotNull(scrollPane);

        JTextArea receiptArea = findComponentOfType(frame, JTextArea.class);
        assertNotNull(receiptArea);
    }

    @Test
    public void testReceiptContent() {
        JTextArea receiptArea = findComponentOfType(posReceipt.getFrame(), JTextArea.class);
        String receiptText = receiptArea.getText();

        assertTrue(receiptText.contains("Order ID: " + testOrder.getOrderId()));
        assertTrue(receiptText.contains("Total Amount: " + String.format("%.2f", 100.0)));
        assertTrue(receiptText.contains("Paid Amount: " + String.format("%.2f", 120.0)));
        assertTrue(receiptText.contains("Change: " + String.format("%.2f", 20.0)));
        // Additional checks for product details
    }

    @Test
    public void testSaveSaleToDatabase() throws SQLException {
        // Ideally, you should mock SalesDao and verify that createSale is called
        // with the correct Sale object. This requires SalesDao to be injectable.
        verify(mockSalesDao, times(1)).createSale(any(Sale.class));
    }

    private <T extends Component> T findComponentOfType(Container container, Class<T> componentClass) {
        for (Component comp : container.getComponents()) {
            if (componentClass.isInstance(comp)) {
                return componentClass.cast(comp);
            } else if (comp instanceof Container) {
                T result = findComponentOfType((Container) comp, componentClass);
                if (result != null) return result;
            }
        }
        return null;
    }

    private Order createTestOrder() {
        Order testOrder = new Order();
        testOrder.setOrderId(123); // Example order ID
        testOrder.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis())); // Current time as order timestamp

        // Create order details (assuming the OrderDetail class has a constructor and setters for these fields)
        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId(1); // Example product ID
        detail1.setQuantity(2); // Quantity of the product
        detail1.setUnitPrice(50.0); // Price per unit

        OrderDetail detail2 = new OrderDetail();
        detail2.setProductId(2);
        detail2.setQuantity(1);
        detail2.setUnitPrice(30.0);

        // Add these details to the order
        testOrder.setOrderDetails(Arrays.asList(detail1, detail2).toArray(new OrderDetail[0]));

        return testOrder;
    }
}
