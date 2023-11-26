package com.pharmacyPOS.data.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    // Utility method for closing the connection
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Logging exception
                e.printStackTrace();
            }
        }
    }

    // Utility method for closing the statement
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // Logging exception
                e.printStackTrace();
            }
        }
    }

    // Utility method for closing the result set
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // Logging exception
                e.printStackTrace();
            }
        }
    }

    // Utility method to close all resources at once
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }
}
