package com.pharmacyPOS.data.entities;

import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;


public class Cart extends ItemContainer
{
    private int userId;
    private int cartId;
    private Date dateCreated;

    public Cart(int uid)
    {
        this.userId=uid;
    }

    // Constructor
    public Cart() {
        this.items = new ArrayList<>(); // Initialize the list of items
    }

    // Adds an item to the cart
    public void addItem(SaleItem item) {
        this.items.add(item);
    }

    // Removes an item from the cart
    public void removeItem(SaleItem item) {
        this.items.remove(item);
    }

    // Clears all items from the cart
    public void clearItems() {
        this.items.clear();
    }

    // Calculates the total cost of all items in the cart
    @Override
    public double getTotal()
    {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public SaleItem getItemByProductId(int productId) {
            for (SaleItem item : items) {
                if (item.getProductId() == productId) {
                    return item;
                }
            }
            return null; // Return null if the item is not found
        }



    public int getCartId() {
        return cartId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId()
    {
        return this.userId;
    }
}
