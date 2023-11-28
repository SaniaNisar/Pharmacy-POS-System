package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.OrderDao;
import com.pharmacyPOS.data.entities.Order;

import java.util.List;

public class OrderService {

    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public void createOrder(Order order) {
        orderDao.createOrder(order);
    }

    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    public void deleteOrder(int orderId) {
        orderDao.deleteOrder(orderId);
    }
}
