package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SaleItemTest {

    @Test
    void setProductId_ShouldSetProductId() {
        // Arrange
        SaleItem saleItem = new SaleItem();
        int productId = 123;

        // Act
        saleItem.setProductId(productId);

        // Assert
        assertEquals(productId, saleItem.getProductId());
    }

    @Test
    void setQuantity_ShouldSetQuantity() {
        // Arrange
        SaleItem saleItem = new SaleItem();
        int quantity = 5;

        // Act
        saleItem.setQuantity(quantity);

        // Assert
        assertEquals(quantity, saleItem.getQuantity());
    }

    @Test
    void setPrice_ShouldSetPrice() {
        // Arrange
        SaleItem saleItem = new SaleItem();
        double price = 20.5;

        // Act
        saleItem.setPrice(price);

        // Assert
        assertEquals(price, saleItem.getPrice());
    }

    // Add more test cases for other functionalities and edge cases

}
