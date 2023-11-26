package com.pharmacyPOS.presentation.views;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.presentation.views.LoginGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {

    private DatabaseConnection conn;

    public WelcomePage(DatabaseConnection c) {
        conn = c;

        // Set the frame properties
        setTitle("POS System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color to sky blue
        getContentPane().setBackground(new Color(135, 206, 250)); // Sky blue

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome to the POS System");
        welcomeLabel.setForeground(new Color(0, 0, 139)); // Dark blue
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adding padding

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 0, 139)); // Dark blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Remove the focus border
        loginButton.setPreferredSize(new Dimension(100, 30)); // Decreased width, increased height

        // Center-align text
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your login logic here
                LoginGUI login = new LoginGUI(conn);
            }
        });

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the frame
        add(welcomeLabel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[]args)
    {
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        new WelcomePage(c);
    }
}
