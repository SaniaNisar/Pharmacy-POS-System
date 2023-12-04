package com.pharmacyPOS.data.entities;

public class User {
    private int userId;          // Unique identifier for the user
    private String username;     // Username of the user
    private String passwordHash; // Hashed password for secure storage
    private String role;         // Role of the user (e.g., "Manager", "Sales Assistant")

    // Default constructor
    public User() {
    }

    // Parameterized constructor for all fields
    public User(int userId, String username, String passwordHash, String role) {
        this.userId = userId;
        this.username = username;
        this.setPasswordHash(passwordHash); // Use the setter to enforce the length constraint
        this.role = role;
    }

    public User(String newuser, String hashedpassword, String user)
    {

    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /*public void setPasswordHash(String passwordHash) {
        if (passwordHash != null && passwordHash.length() <= 13) {
            this.passwordHash = passwordHash;
        } else {
            throw new IllegalArgumentException("Password hash length must be 13 characters or less.");
        }
    }
     */

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Override the toString() method for debugging purposes
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    // Optionally, you can override equals() and hashCode() methods as well.
}
