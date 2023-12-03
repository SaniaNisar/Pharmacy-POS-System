package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Sale;
import com.pharmacyPOS.data.entities.SaleItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class testSalesDao {

    private static DatabaseConnection databaseConnection;
    private static SalesDao salesDao;

    @BeforeAll
    public static void setUp() {
        // Initialize database connection and SalesDao instance
        databaseConnection = new DatabaseConnection();
        salesDao = new SalesDao(databaseConnection);

        // Ensure the required tables are created in the database
        createTestTables();
    }

    @Test
    public void testCreateSale() {
        // Create a sample sale
        Sale sale = new Sale();
        sale.setSaleDate(new Date(System.currentTimeMillis()));
        sale.setTotalCost(50.0);

        SaleItem saleItem1 = new SaleItem();
        saleItem1.setProductId(1);
        saleItem1.setQuantity(2);
        saleItem1.setPrice(25.0);

        SaleItem saleItem2 = new SaleItem();
        saleItem2.setProductId(2);
        saleItem2.setQuantity(1);
        saleItem2.setPrice(25.0);

        sale.addItem(saleItem1);
        sale.addItem(saleItem2);

        // Execute the createSale method and check for any exceptions
        try {
            salesDao.createSale(sale);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the saleId has been assigned
        assertNotNull(sale.getSaleId());
    }

    @Test
    public void testGetSalesByDateRange() {
        // Assuming there are sales in the database within this date range
        Date startDate = Date.valueOf("2023-01-01");
        Date endDate = Date.valueOf("2023-12-31");

        // Execute the getSalesByDateRange method
        try {
            List<Sale> sales = salesDao.getSalesByDateRange(startDate, endDate);
            // Add assertions based on your database content and expectations
            // For example, check if the returned sales list is not null or empty
            assertNotNull(sales);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        // Clean up any test data or resources if needed
    }

    private static void createTestTables() {
        // Create test tables in the database if not already present
        // You may need to replace this with your actual table creation logic
        // For simplicity, assuming sales, sale_items tables exist in the database

        String createSalesTableSQL = "CREATE TABLE IF NOT EXISTS sales ("
                + "sale_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "sale_date DATE,"
                + "total_cost DOUBLE"
                + ")";

        String createSaleItemsTableSQL = "CREATE TABLE IF NOT EXISTS sale_items ("
                + "sale_item_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "sale_id INT,"
                + "product_id INT,"
                + "quantity INT,"
                + "price DOUBLE,"
                + "FOREIGN KEY (sale_id) REFERENCES sales(sale_id)"
                + ")";

        try (java.sql.Connection conn = databaseConnection.connect();
             java.sql.Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createSalesTableSQL);
            stmt.executeUpdate(createSaleItemsTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
