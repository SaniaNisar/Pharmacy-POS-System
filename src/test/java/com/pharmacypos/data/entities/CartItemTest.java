package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.entities.CartItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CartItemTest {

    private CartItem cartItem;

    @Before
    public void setUp() {
        cartItem = new CartItem(1, "Product A", 3, 20.0);
    }

    @Test
    public void testGetProductId() {
        assertEquals(1, cartItem.getProductId());
    }

    @Test
    public void testGetProductName() {
        assertEquals("Product A", cartItem.getProductName());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(3, cartItem.getQuantity());
    }

    @Test
    public void testGetPrice() {
        assertEquals(20.0, cartItem.getPrice(), 0.001);
    }

    @Test
    public void testSetProductId() {
        cartItem.setProductId(2);
        assertEquals(2, cartItem.getProductId());
    }

    @Test
    public void testSetProductName() {
        cartItem.setProductName("Product B");
        assertEquals("Product B", cartItem.getProductName());
    }

    @Test
    public void testSetQuantity() {
        cartItem.setQuantity(5);
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    public void testSetPrice() {
        cartItem.setPrice(25.0);
        assertEquals(25.0, cartItem.getPrice(), 0.001);
    }
}
