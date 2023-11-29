package com.pharmacyPOS.data.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private Date timestamp;
    private OrderDetail[] orderDetails; // Array of OrderDetail objects

    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(int orderId, int userId, Date timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.timestamp = timestamp;
    }


    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                '}';
    }

    public OrderDetail[] getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetail[] orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Implement equals and hashCode if necessary.
}
