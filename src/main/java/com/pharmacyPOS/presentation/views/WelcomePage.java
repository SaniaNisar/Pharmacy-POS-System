package com.pharmacyPOS.presentation.views;
import com.pharmacyPOS.data.dao.UserDao;
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

        setTitle("POS System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(135, 206, 250)); // Sky blue
        //getContentPane().setBackground(new Color(0,0,139));

        JLabel welcomeLabel = new JLabel("Welcome to the POS System");
        welcomeLabel.setForeground(new Color(0,0,139)); // Dark blue
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adding padding

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        signUpButton.setBackground(new Color(0, 0, 139));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false); // Remove the focus border
        signUpButton.setPreferredSize(new Dimension(240, 30));

        loginButton.setBackground(new Color(0, 0, 139)); // Dark blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Remove the focus border
        loginButton.setPreferredSize(new Dimension(240, 30)); // Decreased width, increased height

        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI login = new LoginGUI(conn);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpPage(new UserDao(conn));
            }
        });

        setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.add(signUpButton);
        buttons.add(loginButton);
        add(welcomeLabel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[]args)
    {
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        new WelcomePage(c);
    }
}
