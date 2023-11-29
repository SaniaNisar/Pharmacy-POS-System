package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.CartItem;
import com.pharmacyPOS.service.CartService;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.SaleItem;

import java.sql.SQLException;
import java.util.List;

public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Create a new Cart
    public int createCart(Cart cart) throws SQLException {
        return cartService.createCart(cart);
    }

    // Read a Cart by ID
    public Cart getCartById(int cartId) throws SQLException {
        return cartService.getCartById(cartId);
    }

    // Update a Cart (This could be to add or remove items from the cart)
    public void updateCart(Cart cart) throws SQLException {
        cartService.updateCart(cart);
    }

    // Delete a Cart by ID
    public void deleteCart(int cartId) throws SQLException {
        cartService.deleteCart(cartId);
    }

    // Helper method to add an item to a cart
    public void addItemToCart(int cartId, SaleItem item) throws SQLException {
        cartService.addItemToCart(cartId, item);
    }

    // Helper method to remove an item from a cart
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
            Cart cart = getCurrentCart(userId); // Assuming you have a method to get the current Cart
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
}
