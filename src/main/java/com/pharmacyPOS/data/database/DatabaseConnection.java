package com.pharmacyPOS.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PharmacyPOS";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    // Init connection object
    private Connection connection;
    // Init properties object
    private Properties properties;

    // Create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        return properties;
    }

    // Connect database
   // public Connection connect() {
     //   if (connection == null) {
       //     try {
         //       Class.forName(DATABASE_DRIVER);
           //     connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            //} catch (ClassNotFoundException | SQLException e) {
                // Handle the exception appropriately
              //  e.printStackTrace();
            //}
        //}
        //return connection;
    //}

    public Connection connect() {
        try {
            Class.forName(DATABASE_DRIVER);
            return DriverManager.getConnection(DATABASE_URL, getProperties());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }


//    public void addProductNameColumnToInventory() {
//        String sql = "ALTER TABLE inventory ADD COLUMN product_name VARCHAR(255)";
//        try (Connection conn = ();
//             Statement stmt = conn.createStatement()) {
//            stmt.execute(sql);
//            System.out.println("Column 'product_name' added to 'inventory' table.");
//        } catch (SQLException e) {
//            // It's possible that the column already exists, so handle this exception appropriately.
//            e.printStackTrace();
//        }
//    }

    // Disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
