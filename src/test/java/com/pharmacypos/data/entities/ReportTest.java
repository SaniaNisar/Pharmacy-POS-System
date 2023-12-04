package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void display_ShouldNotThrowException() {
        Report report = new InventoryReport();
        Report report1 = new SalesReport();
        assertDoesNotThrow(() -> report.display());
    }

}
