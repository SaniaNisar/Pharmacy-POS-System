package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.OrderDetail;
import com.pharmacyPOS.services.OrderDetailService;

import java.util.List;

public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    // Additional methods can be added based on business logic or requirements

    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailService.updateOrderDetail(orderDetail);
        // Add any additional logic or response handling if needed
    }

    public void deleteOrderDetail(int orderDetailId) {
        orderDetailService.deleteOrderDetail(orderDetailId);
        // Add any additional logic or response handling if needed
    }

}
