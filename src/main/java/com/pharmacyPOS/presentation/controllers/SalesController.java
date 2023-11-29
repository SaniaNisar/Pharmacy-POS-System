package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.entities.Sale;
import com.pharmacyPOS.service.SalesService;

import java.util.List;

public class SalesController {
    private SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    public List<Sale> getSaleList() {
        return salesService.getAllSales();
    }

    public String getProductNameById(int productId) {
        // Assuming salesService has a method to get a product by ID.
        Product product = salesService.getProductById(productId);
        return product != null ? product.getName() : "Product not found";
    }

    public void displayAllSales() {
        List<Sale> sales = salesService.getAllSales();
        // Here you would have logic to display sales,
        // For console-based UI, it could be print statements
        // For GUI, it could be updating a component
        sales.forEach(sale -> {
            System.out.println("Sale ID: " + sale.getSaleId());
            System.out.println("Date: " + sale.getSaleDate());
            System.out.println("Total Cost: " + sale.getTotalCost());
            sale.getItems().forEach(item -> {
                System.out.println(" - Product ID: " + item.getProductId() +
                        ", Quantity: " + item.getQuantity() +
                        ", Price: " + item.getPrice());
            });
            System.out.println("------");
        });
    }

    public void displayInventory() {
        List<Product> inventory = salesService.getAllInventory();
        // Similar to displayAllSales, you would display the inventory
        inventory.forEach(product -> {
            System.out.println("Product ID: " + product.getProductId() +
                    ", Name: " + product.getName() +
                    ", Price: " + product.getPrice() );
        });
    }
}
