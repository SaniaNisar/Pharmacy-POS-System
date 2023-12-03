package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.dao.CartDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.CartItem;
import com.pharmacyPOS.data.entities.SaleItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartDaoTest {

    private DatabaseConnection mockDatabaseConnection;
    private CartDao cartDao;

    @Before
    public void setUp() {
        mockDatabaseConnection = mock(DatabaseConnection.class);
        cartDao = new CartDao(mockDatabaseConnection);
    }

    @Test
    public void testCreateCart() throws SQLException {
        // Mock Connection and PreparedStatement
        when(mockDatabaseConnection.connect()).thenReturn(Mockito.mock(java.sql.Connection.class));
        when(mockDatabaseConnection.connect().prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenReturn(Mockito.mock(java.sql.PreparedStatement.class));

        // Mock ResultSet
        java.sql.ResultSet mockResultSet = Mockito.mock(java.sql.ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Mock executeUpdate
        when(mockDatabaseConnection.connect().prepareStatement(Mockito.anyString(), Mockito.anyInt()).executeUpdate()).thenReturn(1);

        Cart cart = new Cart();
        cart.setUserId(1);
        int cartId = cartDao.createCart(cart);
        assertEquals(1, cartId);
    }

    @Test
    public void testGetCartById() throws SQLException {
        // Mock Connection, PreparedStatement, and ResultSet
        java.sql.Connection mockConnection = Mockito.mock(java.sql.Connection.class);
        when(mockDatabaseConnection.connect()).thenReturn(mockConnection);

        java.sql.PreparedStatement mockPreparedStatement = Mockito.mock(java.sql.PreparedStatement.class);
        when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);

        java.sql.ResultSet mockResultSet = Mockito.mock(java.sql.ResultSet.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("product_id")).thenReturn(1);
        when(mockResultSet.getInt("quantity")).thenReturn(2);
        when(mockResultSet.getDouble("price_at_time_of_addition")).thenReturn(10.0);

        Cart cart = cartDao.getCartById(1);

        assertNotNull(cart);
        assertEquals(1, cart.getCartId());
        assertEquals(1, cart.getItems().size());
        SaleItem saleItem = cart.getItems().get(0);
        assertEquals(1, saleItem.getProductId());
        assertEquals(2, saleItem.getQuantity());
        assertEquals(10.0, saleItem.getPrice(), 0.001);
    }

    // Add more test cases for other methods...
}
