package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class OrderTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        int orderId = 1;
        int userId = 101;
        Date timestamp = new Date();
        Order order = new Order();

        // Act
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setTimestamp(timestamp);

        // Assert
        assertEquals(orderId, order.getOrderId());
        assertEquals(userId, order.getUserId());
        assertEquals(timestamp, order.getTimestamp());
    }

    @Test
    void getOrderDetails_ShouldReturnOrderDetailsArray() {
        // Arrange
        Order order = new Order();
        OrderDetail[] orderDetails = {
                new OrderDetail(1, 1, 5),
                new OrderDetail(2, 2, 3)
        };
        order.setOrderDetails(orderDetails);

        // Act
        OrderDetail[] retrievedOrderDetails = order.getOrderDetails();

        // Assert
        assertArrayEquals(orderDetails, retrievedOrderDetails);
    }

    @Test
    void setOrderDetails_ShouldSetOrderDetailsArray() {
        // Arrange
        Order order = new Order();
        OrderDetail[] orderDetails = {
                new OrderDetail(1, 1, 5),
                new OrderDetail(2, 2, 3)
        };

        // Act
        order.setOrderDetails(orderDetails);
        OrderDetail[] retrievedOrderDetails = order.getOrderDetails();

        // Assert
        assertArrayEquals(orderDetails, retrievedOrderDetails);
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        int orderId = 1;
        int userId = 101;
        Date timestamp = new Date();
        Order order = new Order(orderId, userId, timestamp);

        // Act
        String result = order.toString();

        // Assert
        assertTrue(result.contains("orderId=" + orderId));
        assertTrue(result.contains("userId=" + userId));
        assertTrue(result.contains("timestamp=" + timestamp));
    }
}
