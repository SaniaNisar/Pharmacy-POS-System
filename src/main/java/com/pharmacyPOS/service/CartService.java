package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.CartDao;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.CartItem;
import com.pharmacyPOS.data.entities.SaleItem;

import java.sql.SQLException;
import java.util.List;

public class CartService {

    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    // Create a new Cart
    public int createCart(Cart cart) throws SQLException {
        return cartDao.createCart(cart);
    }

    // Read a Cart by ID
    public Cart getCartById(int cartId) throws SQLException {
        return cartDao.getCartById(cartId);
    }

    // Update a Cart (This could be to add or remove items from the cart)
    public void updateCart(Cart cart) throws SQLException {
        cartDao.updateCart(cart);
    }

    // Delete a Cart by ID
    public void deleteCart(int cartId) throws SQLException {
        cartDao.deleteCart(cartId);
    }

    // Helper method to add an item to a cart
    public void addItemToCart(int cartId, SaleItem item) throws SQLException {
        cartDao.addItemToCart(cartId, item);
    }

    // Helper method to remove an item from a cart
    public void removeItemFromCart(int cartId, int productId) throws SQLException {
        cartDao.removeItemFromCart(cartId, productId);
    }

    public Cart getCurrentCart(int id) throws SQLException
    {
        return (cartDao.getCurrentCart(id));
    }

    public List<CartItem> getCartItems(int cartId) throws SQLException {
        return (cartDao.getCartItems(cartId));
    }

}
