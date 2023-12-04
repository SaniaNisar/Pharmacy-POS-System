package com.pharmacyPOS.data.entities;

import java.sql.Date;

public class SalesReport extends Report {
    private int productId;
    private String productName;
    private int totalQuantitySold;
    private double totalSalesAmount;
    private Date saleDate; // Added field to store the date of the sale

    // Constructor with product name and sale date
    public SalesReport(int productId, String productName, int totalQuantitySold, double totalSalesAmount, Date saleDate) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalSalesAmount = totalSalesAmount;
        this.saleDate = saleDate; // Initialize the sale date
    }

    public SalesReport() {

    }


    public int getProductId() {
        return productId;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "SalesReport{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", totalQuantitySold=" + totalQuantitySold +
                ", totalSalesAmount=" + totalSalesAmount +
                ", saleDate=" + saleDate +
                '}';
    }

    @Override
    public void display() {
        // Implement the logic to display sales report details
        System.out.println(this.toString());
    }
}
