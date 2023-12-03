package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.OrderDetail;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao {

    private final DatabaseConnection databaseConnection;

    public OrderDetailDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    // ... Other methods ...

    public void updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE order_details SET order_id = ?, product_id = ?, quantity = ?, unit_price = ? WHERE order_detail_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderDetail.getOrderId());
            pstmt.setInt(2, orderDetail.getProductId());
            pstmt.setInt(3, orderDetail.getQuantity());
            pstmt.setDouble(4, orderDetail.getUnitPrice());
            pstmt.setInt(5, orderDetail.getOrderDetailId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderDetail(int orderDetailId) {
        String sql = "DELETE FROM order_details WHERE order_detail_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderDetailId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderDetail extractOrderDetail(ResultSet rs) throws SQLException {
        return new OrderDetail(
                rs.getInt("order_detail_id"),
                rs.getInt("order_id"),
                rs.getInt("product_id"),
                rs.getInt("quantity"),
                rs.getDouble("unit_price")
        );
    }

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM order_details";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                OrderDetail orderDetail = extractOrderDetail(rs);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
}
