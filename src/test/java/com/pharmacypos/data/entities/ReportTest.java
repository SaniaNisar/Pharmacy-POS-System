package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void display_ShouldNotThrowException() {
        // Arrange
        Report report = new InventoryReport();
        Report report1 = new SalesReport();
        // Act & Assert
        assertDoesNotThrow(() -> report.display());
    }

    // Create additional test cases as needed for specific functionality
}
