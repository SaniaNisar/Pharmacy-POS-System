package com.pharmacyPOS.data.entities;

import java.util.List;

public abstract class ItemContainer {
    protected List<SaleItem> items; // A list to hold items

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    public abstract double getTotal(); // An abstract method to calculate the total cost

}
