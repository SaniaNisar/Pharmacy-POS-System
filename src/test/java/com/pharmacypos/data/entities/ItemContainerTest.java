package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ItemContainerTest {

    private static class TestItemContainer extends ItemContainer {
        @Override
        public double getTotal() {
            return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        }
    }

    @Test
    void getItems_ShouldReturnItemsList() {
        // Arrange
        ItemContainer container = new TestItemContainer();
        List<SaleItem> expectedItems = new ArrayList<>();
        expectedItems.add(new SaleItem(1, 2, 10.0));
        expectedItems.add(new SaleItem(2, 3, 15.0));
        container.setItems(expectedItems);

        // Act
        List<SaleItem> actualItems = container.getItems();

        // Assert
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void setItems_ShouldSetItemsList() {
        // Arrange
        ItemContainer container = new TestItemContainer();
        List<SaleItem> expectedItems = new ArrayList<>();
        expectedItems.add(new SaleItem(1, 2, 10.0));
        expectedItems.add(new SaleItem(2, 3, 15.0));

        // Act
        container.setItems(expectedItems);
        List<SaleItem> actualItems = container.getItems();

        // Assert
        assertEquals(expectedItems, actualItems);
    }

    @Test
    void getTotal_ShouldReturnTotalCost() {
        // Arrange
        ItemContainer container = new TestItemContainer();
        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(1, 2, 10.0));
        items.add(new SaleItem(2, 3, 15.0));
        container.setItems(items);

        // Act
        double total = container.getTotal();

        // Assert
        assertEquals(2 * 10.0 + 3 * 15.0, total);
    }
}
