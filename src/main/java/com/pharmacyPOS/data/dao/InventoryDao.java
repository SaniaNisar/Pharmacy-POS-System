package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao {

    private final DatabaseConnection databaseConnection;

    public InventoryDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Inventory getInventoryByProductId(int productId) {
        String sql = "SELECT * FROM inventory WHERE product_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Inventory(
                            rs.getInt("inventory_id"),
                            productId,
                            rs.getInt("quantity"),
                            rs.getDate("expiry_date"),
                            rs.getInt("low_stock_threshold")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        try (Connection conn = databaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                inventoryList.add(new Inventory(
                        rs.getInt("inventory_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDate("expiry_date"),
                        rs.getInt("low_stock_threshold")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    public void createInventoryItem(Inventory inventory) {
        String sql = "INSERT INTO inventory (product_id, quantity, expiry_date) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, inventory.getProductId());
            pstmt.setInt(2, inventory.getQuantity());
            pstmt.setDate(3, inventory.getExpiryDate());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        inventory.setInventoryId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateInventoryItem(Inventory inventory) {
        String sql = "UPDATE inventory SET quantity = ? WHERE inventory_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, inventory.getQuantity());
            pstmt.setInt(2, inventory.getInventoryId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInventoryItem(int inventoryId) {
        String sql = "DELETE FROM inventory WHERE inventory_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, inventoryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

