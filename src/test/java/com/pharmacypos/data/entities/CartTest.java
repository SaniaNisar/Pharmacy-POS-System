package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.SaleItem;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CartTest {

    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart(1);
    }

    @Test
    public void testAddItem() {
        SaleItem saleItem = new SaleItem(1, 2, 10.0);
        cart.addItem(saleItem);

        assertEquals(1, cart.getItems().size());
        assertEquals(saleItem, cart.getItems().get(0));
    }

    @Test
    public void testRemoveItem() {
        SaleItem saleItem = new SaleItem(1, 2, 10.0);
        cart.addItem(saleItem);

        cart.removeItem(saleItem);

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testClearItems() {
        SaleItem saleItem = new SaleItem(1, 2, 10.0);
        cart.addItem(saleItem);

        cart.clearItems();

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testGetTotal() {
        SaleItem item1 = new SaleItem(1, 2, 10.0);
        SaleItem item2 = new SaleItem(2, 3, 15.0);

        cart.addItem(item1);
        cart.addItem(item2);

        assertEquals(2 * 10.0 + 3 * 15.0, cart.getTotal(), 0.001);
    }

    @Test
    public void testGetItemByProductId() {
        SaleItem item1 = new SaleItem(1, 2, 10.0);
        SaleItem item2 = new SaleItem(2, 3, 15.0);

        cart.addItem(item1);
        cart.addItem(item2);

        SaleItem foundItem = cart.getItemByProductId(2);

        assertNotNull(foundItem);
        assertEquals(item2, foundItem);
    }

    @Test
    public void testGetItemByProductIdNotFound() {
        SaleItem item1 = new SaleItem(1, 2, 10.0);
        SaleItem item2 = new SaleItem(2, 3, 15.0);

        cart.addItem(item1);
        cart.addItem(item2);

        SaleItem foundItem = cart.getItemByProductId(3);

        assertNull(foundItem);
    }

    @Test
    public void testGetUserId() {
        assertEquals(1, cart.getUserId());
    }

    @Test
    public void testSetUserId() {
        cart.setUserId(2);
        assertEquals(2, cart.getUserId());
    }

    @Test
    public void testGetCartId() {
        cart.setCartId(3);
        assertEquals(3, cart.getCartId());
    }

    @Test
    public void testGetDateCreated() {
        Date dateCreated = Date.valueOf("2023-12-01");
        cart.setDateCreated(dateCreated);
        assertEquals(dateCreated, cart.getDateCreated());
    }

    @Test
    public void testSetCartId() {
        cart.setCartId(4);
        assertEquals(4, cart.getCartId());
    }

    @Test
    public void testSetDateCreated() {
        Date dateCreated = Date.valueOf("2023-12-02");
        cart.setDateCreated(dateCreated);
        assertEquals(dateCreated, cart.getDateCreated());
    }
}
