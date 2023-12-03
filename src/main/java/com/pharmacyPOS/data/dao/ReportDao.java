package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.SalesReport;
import com.pharmacyPOS.data.entities.InventoryReport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {

    //private final DatabaseConnection databaseConnection;

    //public ReportDao(DatabaseConnection databaseConnection) {
      //  this.databaseConnection = databaseConnection;
    //}

    /*public List<SalesReport> generateSalesReport(Date startDate, Date endDate) {
        List<SalesReport> salesReports = new ArrayList<>();
        String sql = "SELECT product_id, SUM(quantity) as total_quantity, SUM(quantity * unit_price) as total_sales " +
                "FROM order_details od " +
                "JOIN orders o ON od.order_id = o.order_id " +
                "WHERE o.timestamp BETWEEN ? AND ? " +
                "GROUP BY product_id";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    salesReports.add(new SalesReport(
                            rs.getInt("product_id"),
                            rs.getInt("total_quantity"),
                            rs.getDouble("total_sales")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReports;
    }
     */

   /* public List<InventoryReport> generateInventoryReport() {
        List<InventoryReport> inventoryReports = new ArrayList<>();
        String sql = "SELECT product_id, quantity " +
                "FROM inventory";
        try (Connection conn = databaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                inventoryReports.add(new InventoryReport(
                        rs.getInt("product_id"),
                        // Assuming there is a column 'name' in the 'inventory' table to fetch product name
                        // If not, you'll need to join with the 'products' table or remove the productName field
                        rs.getString("name"), // Placeholder for the actual product name column
                        rs.getInt("quantity"),
                        rs.getDouble("price") // Placeholder for the actual price column
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryReports;
    }

    // Additional report methods can be implemented here...

    */
}
