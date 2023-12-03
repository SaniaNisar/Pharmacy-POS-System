package com.pharmacyPOS.data.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Date;

class InventoryTest {

    @Test
    void gettersAndSetters_ShouldWorkAsExpected() {
        // Arrange
        int inventoryId = 1;
        int productId = 123;
        int quantity = 50;
        Date expiryDate = Date.valueOf("2023-01-01");
        int lowStockThreshold = 10;

        // Act
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryId);
        inventory.setProductId(productId);
        inventory.setQuantity(quantity);
        inventory.setExpiryDate(expiryDate);
        inventory.setLowStockThreshold(lowStockThreshold);

        // Assert
        assertEquals(inventoryId, inventory.getInventoryId());
        assertEquals(productId, inventory.getProductId());
        assertEquals(quantity, inventory.getQuantity());
        assertEquals(expiryDate, inventory.getExpiryDate());
        assertEquals(lowStockThreshold, inventory.getLowStockThreshold());
    }

    @Test
    void toString_ShouldReturnExpectedString() {
        // Arrange
        int inventoryId = 1;
        int productId = 123;
        int quantity = 50;

        Inventory inventory = new Inventory(inventoryId, productId, quantity, Date.valueOf("2023-01-01"), 10);

        // Act
        String result = inventory.toString();

        // Assert
        assertTrue(result.contains("inventoryId=" + inventoryId));
        assertTrue(result.contains("productId=" + productId));
        assertTrue(result.contains("quantity=" + quantity));
    }

    // Add more tests as needed, especially if there are other methods or logic in the Inventory class.
}
