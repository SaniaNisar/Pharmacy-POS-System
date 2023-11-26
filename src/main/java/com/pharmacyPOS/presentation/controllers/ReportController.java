package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.InventoryReport;
import com.pharmacyPOS.data.entities.SalesReport;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.service.SalesService;

import java.util.List;

public class ReportController {

    private SalesService salesReportService;
    private InventoryService inventoryReportService;

    public ReportController(SalesService salesReportService, InventoryService inventoryReportService) {
        this.salesReportService = salesReportService;
        this.inventoryReportService = inventoryReportService;
    }

    /**
     * Generates a sales report for a given period.
     *
     * @param startDate Start date for the report period.
     * @param endDate   End date for the report period.
     * @return List of sales report data.
     */
    /*public List<SalesReport> generateSalesReport(String startDate, String endDate) {
        return salesReportService.generateSalesReport(startDate, endDate);
    }*/

    /**
     * Generates an inventory report of current stock levels.
     *
     * @return List of inventory report data.
     */
    public List<InventoryReport> generateInventoryReport() {
        return inventoryReportService.generateInventoryReport();
    }


    // Additional reporting methods as required...

    // Note: Depending on your needs, you may need to provide parameters to these methods,
    // such as date ranges for sales reports, or filter criteria for inventory reports.
}
