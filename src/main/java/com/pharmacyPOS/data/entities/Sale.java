package com.pharmacyPOS.data.entities;

import java.sql.CallableStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int saleId;
    private List<SaleItem> items; // Items associated with the sale
    private double totalCost;
    private Date saleDate;

    // Constructor
    public Sale() {
        this.items = new ArrayList<>();
        // Initialize other fields if necessary
    }

    // Getter for the list of sale items
    public List<SaleItem> getItems() {
        return items;
    }

    // Setter for the total cost of the sale
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    // Additional fields and methods
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    // You might also want to include methods to add or remove items
    public void addItem(SaleItem item) {
        this.items.add(item);
        recalculateTotalCost();
    }

    public void removeItem(SaleItem item) {
        this.items.remove(item);
        recalculateTotalCost();
    }

    // Recalculate the total cost after any change in the items
    private void recalculateTotalCost() {
        totalCost = 0;
        for (SaleItem item : items) {
            totalCost += item.getPrice() * item.getQuantity();
        }
    }
    /**
     * Retrieves the sale date.
     * @return The date of the sale.
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * Sets the sale date.
     * @param saleDate The date of the sale to set.
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
