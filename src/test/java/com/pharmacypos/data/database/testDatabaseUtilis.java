package com.pharmacyPOS.data.database;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class testDatabaseUtilis {

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Set up an in-memory H2 database for testing
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        // Create a table for testing
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE test_table (id INT, name VARCHAR(255))");
        }
    }

    @Test
    public void testCloseConnection() throws SQLException {
        assertDoesNotThrow(() -> DatabaseUtils.close(connection));
        assertTrue(connection.isClosed());
    }

    @Test
    public void testCloseStatement() throws SQLException {
        Statement statement = connection.createStatement();
        assertDoesNotThrow(() -> DatabaseUtils.close(statement));
        assertTrue(statement.isClosed());
    }

    @Test
    public void testCloseResultSet() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1");
        assertDoesNotThrow(() -> DatabaseUtils.close(resultSet));
        assertTrue(resultSet.isClosed());
        statement.close();
    }

    @Test
    public void testCloseAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1");

        assertDoesNotThrow(() -> DatabaseUtils.closeAll(connection, statement, resultSet));
        assertTrue(connection.isClosed());
        assertTrue(statement.isClosed());
        assertTrue(resultSet.isClosed());
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        // Close the connection after all tests
        if (connection != null) {
            connection.close();
        }
    }
}
