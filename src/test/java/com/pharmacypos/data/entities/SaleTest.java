package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    @Test
    void addItem_ShouldIncreaseTotalCost() {
        // Arrange
        Sale sale = new Sale();
        SaleItem item = new SaleItem(1, 2, 20);

        // Act
        sale.addItem(item);

        // Assert
        assertEquals(item.getPrice() * item.getQuantity(), sale.getTotalCost());
    }

    @Test
    void removeItem_ShouldDecreaseTotalCost() {
        // Arrange
        Sale sale = new Sale();
        SaleItem item = new SaleItem(1, 2, 20);
        sale.addItem(item);

        // Act
        sale.removeItem(item);

        // Assert
        assertEquals(0, sale.getTotalCost());
    }

}
