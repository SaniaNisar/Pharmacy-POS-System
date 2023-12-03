package com.pharmacyPOS.data.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class InventoryReportTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        InventoryReport report = new InventoryReport();

        // Act
        report.setProductId(1);
        report.setProductName("TestProduct");
        report.setCurrentQuantity(50);
        report.setPrice(25.99);

        // Assert
        assertEquals(1, report.getProductId());
        assertEquals("TestProduct", report.getProductName());
        assertEquals(50, report.getCurrentQuantity());
        assertEquals(25.99, report.getPrice(), 0.01); // Using delta for double comparison
    }

    @Test
    void toString_ShouldReturnFormattedString() {
        // Arrange
        InventoryReport report = new InventoryReport();
        report.setProductId(1);
        report.setProductName("TestProduct");
        report.setCurrentQuantity(50);
        report.setPrice(25.99);

        // Act
        String result = report.toString();

        // Assert
        assertEquals("InventoryReport{productId=1, productName='TestProduct', currentQuantity=50, price=25.99}", result);
    }

    @Test
    void display_ShouldPrintToConsole() {
        // Arrange
        InventoryReport report = new InventoryReport();
        report.setProductId(1);
        report.setProductName("TestProduct");
        report.setCurrentQuantity(50);
        report.setPrice(25.99);

        // Act
        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        report.display();
        System.setOut(System.out); // Reset System.out

        // Assert
        assertEquals("InventoryReport{productId=1, productName='TestProduct', currentQuantity=50, price=25.99}\n", outputStream.toString());
    }
}
