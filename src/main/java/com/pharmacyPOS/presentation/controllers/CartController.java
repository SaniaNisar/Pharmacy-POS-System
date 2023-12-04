package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.dao.InventoryDao;
import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.CartItem;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.service.CartService;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.SaleItem;
import com.pharmacyPOS.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class CartController {

    private CartService cartService;
    private ProductService productService; // Assuming you have a ProductService

    // Constructor
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect();
        this.productService = new ProductService(new ProductDao(connection));
    }


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Create a new Cart
    public int createCart(Cart cart) throws SQLException {
        return cartService.createCart(cart);
    }

    public void updateCartItemQuantity(int cartId, int productId, int newQuantity) throws SQLException {
        cartService.updateCartItemQuantity(cartId, productId, newQuantity);
    }

    // Read a Cart by ID
    public Cart getCartById(int cartId) throws SQLException {
        return cartService.getCartById(cartId);
    }

    public void deleteCart(int cartId) throws SQLException {
        cartService.deleteCart(cartId);
    }

    public void addItemToCart(int cartId, SaleItem item) throws SQLException {
        cartService.addItemToCart(cartId, item);
    }

    public void removeItemFromCart(int cartId, int productId) throws SQLException {
        cartService.removeItemFromCart(cartId, productId);
    }

    public Cart getCurrentCart(int id) throws SQLException
    {
        return (cartService.getCurrentCart(id));
    }

    public List<CartItem> getCartItems(int cartId) throws SQLException {
        return (cartService.getCartItems(cartId));
    }
    public double getCartTotal(int userId) {
        try {
            Cart cart = getCurrentCart(userId);
            if (cart != null) {
                return cart.getTotal();
            } else {
                return 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exceptions as needed
        }
        return 0.0;
    }
    private InventoryDao inventoryDao;
    public void decrementInventory(int productId, int quantity) throws SQLException {
        // Retrieve current inventory for the product
        DatabaseConnection conn = new DatabaseConnection();
        conn.connect();
        inventoryDao = new InventoryDao(conn);
        Inventory inventory = inventoryDao.getInventoryByProductId(productId);
        if (inventory != null && inventory.getQuantity() >= quantity) {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryDao.updateInventoryItem(inventory);
        } else {
            throw new SQLException("Insufficient inventory for product ID: " + productId);
        }
    }

    public int getCartItemQuantity(int cartId, int productId) throws SQLException
    {
        return (cartService.getCartItemQuantity(cartId,productId));
    }

    public void clearCart(int cartId) {
       cartService.clearCart(cartId);
    }

}
