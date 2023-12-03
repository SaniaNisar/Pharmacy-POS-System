package com.pharmacyPOS.data.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class InventoryDaoTest {

    private InventoryDao inventoryDao;
    private DatabaseConnection databaseConnection;

    @BeforeEach
    void setUp() {
        // Initialize the mock objects
        databaseConnection = mock(DatabaseConnection.class);
        inventoryDao = new InventoryDao(databaseConnection);
    }

    @Test
    void getAllInventories_ShouldReturnListOfInventories() throws SQLException {
        // Arrange
        List<Inventory> expectedInventories = new ArrayList<>();
        expectedInventories.add(new Inventory(1, 123, 50, Date.valueOf("2023-01-01"), 10));
        expectedInventories.add(new Inventory(2, 456, 30, Date.valueOf("2023-02-01"), 5));

        // Mock the behavior of the database connection
        when(databaseConnection.connect()).thenReturn(mock(java.sql.Connection.class));

        // Act
        List<Inventory> actualInventories = inventoryDao.getAllInventory();

        // Assert
        assertEquals(expectedInventories, actualInventories);
    }
}
