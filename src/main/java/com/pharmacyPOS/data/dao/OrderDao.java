package com.pharmacyPOS.data.dao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.data.entities.OrderDetail;
import com.pharmacyPOS.presentation.controllers.CartController;
import com.pharmacyPOS.service.CartService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private final DatabaseConnection databaseConnection;

    public OrderDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /*public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("order_id"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("timestamp")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     */

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Order order = null;
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("timestamp")
                );
                List<OrderDetail> detailsList = getOrderDetails(orderId);
                OrderDetail[] detailsArray = new OrderDetail[detailsList.size()];
                detailsArray = detailsList.toArray(detailsArray); // Convert list to array
                order.setOrderDetails(detailsArray); // Set order details as an array
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    private List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String detailSql = "SELECT * FROM order_details WHERE order_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(detailSql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderDetail detail = new OrderDetail(
                        // Assuming constructor or setters for OrderDetail fields
                        rs.getInt("product_id"),
                        rs.getInt("quantity_ordered"),
                        rs.getDouble("price_at_time_of_sale")
                );
                orderDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }


    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = databaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, timestamp) VALUES (?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setTimestamp(2, new Timestamp(order.getTimestamp().getTime()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setOrderId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET user_id = ?, timestamp = ? WHERE order_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setTimestamp(2, new Timestamp(order.getTimestamp().getTime()));
            pstmt.setInt(3, order.getOrderId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrderDetail(OrderDetail detail) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity_ordered, price_at_time_of_sale) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, detail.getOrderId());
            pstmt.setInt(2, detail.getProductId());
            pstmt.setInt(3, detail.getQuantity());
            pstmt.setDouble(4, detail.getUnitPrice());
            pstmt.executeUpdate();
        }
    }


    public void saveOrder(Order order) throws SQLException {
        // Create the order and get its generated ID
        createOrder(order);

        // For each order detail, save it using the generated order ID
        for (OrderDetail detail : order.getOrderDetails()) {
            detail.setOrderId(order.getOrderId());
            saveOrderDetail(detail);
        }
    }
   /* public void saveOrder(Order order) throws SQLException {
        // Create the order and get its generated ID
        createOrder(order);

        // For each order detail, save it using the generated order ID
        for (OrderDetail detail : order.getOrderDetails()) {
            detail.setOrderId(order.getOrderId());
            saveOrderDetail(detail);

            // Decrement inventory if needed
            CartController cartController = new CartController(new CartService(new CartDao(databaseConnection)));
            cartController.decrementInventory(detail.getProductId(), detail.getQuantity());
        }
    }

    */

}
