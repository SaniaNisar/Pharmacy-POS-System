package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.SalesDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Sale;
import com.pharmacyPOS.data.entities.SaleItem;
import com.pharmacyPOS.data.entities.SalesReport;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class SalesReportGeneration {
    private SalesDao salesDao;

    public SalesReportGeneration(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    public List<SalesReport> getDailySalesReport(Date date) {
        return getSalesReport(date, date);
    }

    public List<SalesReport> getWeeklySalesReport(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        Date endDate = new Date(calendar.getTime().getTime());
        return getSalesReport(startDate, endDate);
    }

    public List<SalesReport> getMonthlySalesReport(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = new Date(calendar.getTime().getTime());

        return getSalesReport(startDate, endDate);
    }

    private List<SalesReport> getSalesReport(Date startDate, Date endDate) {
        try {
            List<Sale> sales = salesDao.getSalesByDateRange(startDate, endDate);
            Map<Integer, SalesReport> reportMap = new HashMap<>();

            for (Sale sale : sales) {
                List<SaleItem> items = salesDao.getSaleItems(sale.getSaleId());
                sale.setItems(items);

                for (SaleItem item : items) {
                    int productId = item.getProductId();
                    String productName = salesDao.getProductNameById(productId); // Fetch product name
                    SalesReport report = reportMap.getOrDefault(productId,
                            new SalesReport(productId, productName, 0, 0.0, sale.getSaleDate()));

                    report.setTotalQuantitySold(report.getTotalQuantitySold() + item.getQuantity());
                    report.setTotalSalesAmount(report.getTotalSalesAmount() + (item.getPrice() * item.getQuantity()));
                    reportMap.put(productId, report);
                }
            }

            return new ArrayList<>(reportMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public static void main(String[] args) {
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();
        SalesDao salesDao = new SalesDao(conn);

        SalesReportGeneration reportGen = new SalesReportGeneration(salesDao);

        // Use a known date range where sales are present
        Date testDate = Date.valueOf("2023-12-03");
        List<SalesReport> dailyReports = reportGen.getMonthlySalesReport(2023,12);

        if (dailyReports.isEmpty()) {
            System.out.println("No reports generated for the day.");
        } else {
            for (SalesReport report : dailyReports) {
                System.out.println(report);
            }
        }
    }
}
