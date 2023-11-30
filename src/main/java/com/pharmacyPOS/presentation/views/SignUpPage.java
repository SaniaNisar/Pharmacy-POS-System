package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.UserDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.User;
import com.pharmacyPOS.presentation.controllers.UserController;
import com.pharmacyPOS.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private UserController userController;

    public SignUpPage(UserDao userDao) {
        this.userController = new UserController(new UserService(userDao));

        setTitle("Sign Up");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 80, 80, 25);
        panel.add(roleLabel);

        String[] roles = {"Manager", "Sales Assistant"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(100, 80, 165, 25);
        panel.add(roleComboBox);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(10, 120, 80, 25);
        panel.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
    }

    private void signUp() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            username = JOptionPane.showInputDialog(this, "Please enter a username: ");
            if (username == null || username.isEmpty()) {
                // User cancelled or entered an empty string
                return; // Stop the signUp process
            }
            usernameField.setText(username); // Set the text field with the entered value
        }

        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        if (password.isEmpty()) {
            password = JOptionPane.showInputDialog(this, "Please enter a password: ");
            if (password == null || password.isEmpty()) {
                // User cancelled or entered an empty string
                return; // Stop the signUp process
            }
            passwordField.setText(password); // Set the password field with the entered value
        }

        String role = (String) roleComboBox.getSelectedItem();

        try {
            int generatedUserId = userController.createUserReturnKey(new User(0, username, password, role));

            if (generatedUserId != -1) {
                JOptionPane.showMessageDialog(this, "Sign Up Successful. Generated User ID: " + generatedUserId);
                dispose(); // Close the signup window after successful signup
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another username.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection(); // Initialize your database connection
        UserDao userDao = new UserDao(databaseConnection);
        new SignUpPage(userDao);
    }
}
