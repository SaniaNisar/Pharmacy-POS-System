package com.pharmacyPOS.data.database;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class testDatabaseConnection {

    private static DatabaseConnection databaseConnection;

    @BeforeAll
    public static void setUp() {
        databaseConnection = new DatabaseConnection();
    }

    @Test
    public void testConnect() {
        Connection connection = databaseConnection.connect();
        assertNotNull(connection);
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDisconnect() {
        Connection connection = databaseConnection.connect();
        assertNotNull(connection);

        databaseConnection.disconnect();

        try {
            assertTrue(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnectFailure() {
        // Modify the database URL to an invalid one to simulate connection failure
        databaseConnection = new DatabaseConnection();
        databaseConnection.setDatabaseUrl("jdbc:mysql://localhost:3306/invalid_database");

        Connection connection = databaseConnection.connect();
        assertNull(connection);
    }

    @AfterAll
    public static void tearDown() {
        databaseConnection.disconnect();
    }
}
