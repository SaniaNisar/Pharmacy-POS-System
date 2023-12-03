package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderDetailTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        int orderDetailId = 1;
        int orderId = 101;
        int productId = 201;
        int quantity = 5;
        double unitPrice = 10.99;
        OrderDetail orderDetail = new OrderDetail();

        // Act
        orderDetail.setOrderDetailId(orderDetailId);
        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(productId);
        orderDetail.setQuantity(quantity);
        orderDetail.setUnitPrice(unitPrice);

        // Assert
        assertEquals(orderDetailId, orderDetail.getOrderDetailId());
        assertEquals(orderId, orderDetail.getOrderId());
        assertEquals(productId, orderDetail.getProductId());
        assertEquals(quantity, orderDetail.getQuantity());
        assertEquals(unitPrice, orderDetail.getUnitPrice());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        int orderDetailId = 1;
        int orderId = 101;
        int productId = 201;
        int quantity = 5;
        double unitPrice = 10.99;
        OrderDetail orderDetail = new OrderDetail(orderDetailId, orderId, productId, quantity, unitPrice);

        // Act
        String result = orderDetail.toString();

        // Assert
        assertTrue(result.contains("orderDetailId=" + orderDetailId));
        assertTrue(result.contains("orderId=" + orderId));
        assertTrue(result.contains("productId=" + productId));
        assertTrue(result.contains("quantity=" + quantity));
        assertTrue(result.contains("unitPrice=" + unitPrice));
    }
}

