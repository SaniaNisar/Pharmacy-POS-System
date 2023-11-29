package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.entities.Sale;
import com.pharmacyPOS.data.entities.SaleItem;
import com.pharmacyPOS.data.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDao {

    private DatabaseConnection databaseConnection;

    public SalesDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void createSale(Sale sale) throws SQLException {
        String insertSaleSQL = "INSERT INTO sales (sale_date, total_cost) VALUES (?, ?)";
        String insertSaleItemSQL = "INSERT INTO sale_items (sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement insertSaleStmt = null;
        PreparedStatement insertSaleItemStmt = null;

        try {
            connection = databaseConnection.connect();
            // Start a transaction
            connection.setAutoCommit(false);

            // Insert the sale
            insertSaleStmt = connection.prepareStatement(insertSaleSQL, Statement.RETURN_GENERATED_KEYS);
            insertSaleStmt.setDate(1, new Date(sale.getSaleDate().getTime()));
            insertSaleStmt.setDouble(2, sale.getTotalCost());
            int affectedRows = insertSaleStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating sale failed, no rows affected.");
            }

            // Retrieve the generated key for the sale
            try (ResultSet generatedKeys = insertSaleStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sale.setSaleId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating sale failed, no ID obtained.");
                }
            }

            // Insert the sale items
            insertSaleItemStmt = connection.prepareStatement(insertSaleItemSQL);

            for (SaleItem item : sale.getItems()) {
                insertSaleItemStmt.setInt(1, sale.getSaleId());
                insertSaleItemStmt.setInt(2, item.getProductId());
                insertSaleItemStmt.setInt(3, item.getQuantity());
                insertSaleItemStmt.setDouble(4, item.getPrice());
                insertSaleItemStmt.addBatch();
            }

            insertSaleItemStmt.executeBatch();
            connection.commit(); // Commit the transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    e.addSuppressed(ex);
                }
            }
            throw e; // Rethrow the exception
        } finally {
            // Clean up JDBC objects
            if (insertSaleItemStmt != null) {
                try {
                    insertSaleItemStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (insertSaleStmt != null) {
                try {
                    insertSaleStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Sale> getSalesByDateRange(Date startDate, Date endDate) throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales WHERE sale_date BETWEEN ? AND ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale();
                // Assuming Sale has a constructor that accepts ID, Date, and totalCost
                sale.setSaleId(rs.getInt("sale_id"));
                sale.setSaleDate(rs.getDate("sale_date"));
                sale.setTotalCost(rs.getDouble("total_cost"));
                // Add sale items and other details if necessary
                sales.add(sale);
            }
        }
        return sales;
    }
    public Date getSaleDateFromDatabase(int saleId) {
        Date saleDate = null;
        String sql = "{ ? = call getSaleDate(?) }"; // Assuming you have a stored procedure named getSaleDate

        try (Connection conn = databaseConnection.connect();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.registerOutParameter(1, Types.DATE);
            cstmt.setInt(2, saleId);
            cstmt.execute();
            saleDate = cstmt.getDate(1);
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return saleDate;
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        try {
            Statement statement = databaseConnection.connect().createStatement();
            String sql = "SELECT * FROM sales"; // Replace with your actual sales table name
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int saleId = resultSet.getInt("sale_id");
                Date saleDate = resultSet.getDate("sale_date");

                List<SaleItem> saleItems = getSaleItems(saleId); // Method to fetch sale items for this sale
                double totalCost = calculateTotalCost(saleItems); // Calculate total cost based on the items

                Sale sale = new Sale();
                sale.setSaleId(saleId);
                sale.setItems(saleItems);
                sale.setTotalCost(totalCost);
                sale.setSaleDate(saleDate);

                sales.add(sale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sales;
    }

    private double calculateTotalCost(List<SaleItem> items) {
        double total = 0.0;
        for (SaleItem item : items) {
            total += item.getPrice() * item.getQuantity(); // Assuming SaleItem has getPrice and getQuantity methods
        }
        return total;
    }
    private List<SaleItem> getSaleItems(int saleId) {
        List<SaleItem> saleItems = new ArrayList<>();
        String query = "SELECT * FROM sale_items WHERE sale_id = ?";
        try (PreparedStatement ps = databaseConnection.connect().prepareStatement(query)) {
            ps.setInt(1, saleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SaleItem item = new SaleItem();
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                // Add other fields as necessary
                saleItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleItems;
    }

    public List<Product> getAllInventory() {
        List<Product> inventory = new ArrayList<>();
        String query = "SELECT * FROM products"; // Replace 'products' with your actual inventory table name
        try (Statement statement = databaseConnection.connect().createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                // Set other fields as necessary
                inventory.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventory;
    }
}
