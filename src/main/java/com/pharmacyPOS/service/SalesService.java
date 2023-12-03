package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.SalesDao;
import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.entities.Sale;
import com.pharmacyPOS.data.entities.SaleItem;
import com.pharmacyPOS.data.entities.Inventory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SalesService {
    private SalesDao salesDao;
    private InventoryDao inventoryDao;
    private Product product;

    public SalesService(SalesDao salesDao, InventoryDao inventoryDao) {
        this.salesDao = salesDao;
        this.inventoryDao = inventoryDao;
    }
    public void SaleService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    public List<Sale> getAllSales() {
        return salesDao.getAllSales();
    }

    public List<Product> getAllInventory() {
        return salesDao.getAllInventory();
    }

    public SalesService(SalesDao saleDao)
    {
        this.salesDao=saleDao;
    }

    public String getProductNameById(int productId)
    {
        return (salesDao.getProductNameById(productId));
    }

    /**
     * Processes a sales transaction.
     *
     * @param sale The sale transaction to be processed.
     * @return The processed sale with updated information (like total cost).
     */
    public Sale processSale(Sale sale) throws SQLException {
        double totalCost = 0;
        List<SaleItem> items = sale.getItems();

        for (SaleItem item : items) {
            // Update inventory quantities
            Inventory inventory = inventoryDao.getInventoryByProductId(item.getProductId());
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventoryDao.updateInventoryItem(inventory);

            // Calculate total cost
            totalCost += item.getPrice() * item.getQuantity();
        }

        sale.setTotalCost(totalCost);
        salesDao.createSale(sale);
        return sale;
    }

    /**
     * Retrieves sales records within a certain period.
     *
     * @param startDate Start date of the period.
     * @param endDate End date of the period.
     * @return List of sales within the given period.
     */
    public List<Sale> getSalesByPeriod(Date startDate, Date endDate) throws SQLException {
        return salesDao.getSalesByDateRange(startDate, endDate);
    }

    public Product getProductById(int productId) {
        return product.getProduct(productId);
    }

    // Additional sales-related methods...
}
