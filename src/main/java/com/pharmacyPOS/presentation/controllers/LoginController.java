package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController
{
    private DatabaseConnection conn;
    public LoginController(DatabaseConnection c)
    {
        conn=c;
    }
    public boolean validateUser(String username, String password, String role)
    {
        // Implement the logic to validate the user from the database
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ? AND role = ?";

        try (Connection connection = conn.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If there's a matching user, return true
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false; // Return false if there's an error or no matching user
    }
}