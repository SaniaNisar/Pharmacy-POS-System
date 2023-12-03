package com.pharmacyPOS.services;

import com.pharmacyPOS.data.dao.OrderDetailDao;
import com.pharmacyPOS.data.entities.OrderDetail;

import java.util.List;

public class OrderDetailService {

    private final OrderDetailDao orderDetailDao;

    public OrderDetailService(OrderDetailDao orderDetailDao) {
        this.orderDetailDao = orderDetailDao;
    }

    // Additional methods can be added based on business logic or requirements

    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.updateOrderDetail(orderDetail);
    }

    public void deleteOrderDetail(int orderDetailId) {
        orderDetailDao.deleteOrderDetail(orderDetailId);
    }

    public List<OrderDetail> getAllOrderDetails() {
        // You can add additional logic or validation if needed
        return orderDetailDao.getAllOrderDetails();
    }
}
