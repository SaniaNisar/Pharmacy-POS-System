package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.service.OrderService;

import java.util.List;

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Order getOrderById(int orderId) {
        return orderService.getOrderById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public void createOrder(Order order) {
        orderService.createOrder(order);
    }

    public void updateOrder(Order order) {
        orderService.updateOrder(order);
    }

    public void deleteOrder(int orderId) {
        orderService.deleteOrder(orderId);
    }
}
