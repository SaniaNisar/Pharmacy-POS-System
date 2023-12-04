package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.UserDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.presentation.controllers.LoginController;
import com.pharmacyPOS.presentation.controllers.UserController;
import com.pharmacyPOS.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI {

    private DatabaseConnection conn;

    public LoginGUI(DatabaseConnection c)
    {
        this.conn = c;
        // Creating the frame
        JFrame frame = new JFrame("Pharmacy POS Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Creating the panel at the bottom and adding components
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Adding radio buttons for role selection
        JRadioButton managerButton = new JRadioButton("Manager");
        JRadioButton assistantButton = new JRadioButton("Sales Assistant");
        ButtonGroup group = new ButtonGroup();
        group.add(managerButton);
        group.add(assistantButton);

        // Adding components to the panel
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(managerButton);
        panel.add(assistantButton);
        panel.add(loginButton);

        // Setting the frame layout
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);

        // Action Listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                //String usernameforid=userText.getText();
                String password = new String(passwordText.getPassword());
                String role = managerButton.isSelected() ? "Manager" : "Sales Assistant";

                // Check if the user is valid in the database
                boolean isValidUser = validateUser(user, password, role);

                if (isValidUser) {
                    System.out.println("Login successful for " + user + " as " + role);
                    frame.dispose(); // Close the login window

                    // Open the appropriate dashboard based on the user's role
                    if (role.equals("Manager")) {
                        SwingUtilities.invokeLater(() -> {
                            new ManagerDashboard(conn).setVisible(true);
                        });
                    } else if (role.equals("Sales Assistant"))
                    {
                        SwingUtilities.invokeLater(() -> {

                            UserController userController = new UserController(new UserService(new UserDao(conn)));
                            int id = userController.getUserIdByUsername(user);
                            new SalesAssistantDashboard(conn, id);
                        });
                    }
                } else {
                    // Invalid user, display an error message or take appropriate action
                    System.out.println("Invalid login attempt for " + user + " as " + role);
                    JOptionPane.showMessageDialog(null, "Invalid login credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validateUser(String username, String password, String role)
    {
        LoginController loginController = new LoginController(conn);
        return (loginController.validateUser(username, password, role));
    }
}
