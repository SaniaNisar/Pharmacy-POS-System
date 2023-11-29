package com.pharmacyPOS.data.entities;
public class SalesReport extends Report {
    private int productId;
    private String productName;
    private int totalQuantitySold;
    private double totalSalesAmount;

    // Constructor without product name
    public SalesReport(int productId, int totalQuantitySold, double totalSalesAmount) {
        this.productId = productId;
        this.totalQuantitySold = totalQuantitySold;
        this.totalSalesAmount = totalSalesAmount;
    }

    // Constructor with product name
    public SalesReport(int productId, String productName, int totalQuantitySold, double totalSalesAmount) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalSalesAmount = totalSalesAmount;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    // Setters
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "SalesReport{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", totalQuantitySold=" + totalQuantitySold +
                ", totalSalesAmount=" + totalSalesAmount +
                '}';
    }

    @Override
    public void display() {
        // Implement the logic to display sales report details
        System.out.println(this.toString());
    }
}
