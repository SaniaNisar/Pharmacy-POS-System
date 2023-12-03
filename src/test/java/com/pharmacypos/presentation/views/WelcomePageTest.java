package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.presentation.views.WelcomePage;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class WelcomePageTest {

    private WelcomePage welcomePage;

    @Before
    public void setUp() {
        // Assuming DatabaseConnection is correctly implemented
        DatabaseConnection dbConnection = new DatabaseConnection();
        welcomePage = new WelcomePage(dbConnection);
    }

    @Test
    public void testWelcomePageComponents() {
        // Check if the frame title is set correctly
        assertEquals("POS System", welcomePage.getTitle());

        // Check for the existence of expected components
        boolean hasLoginButton = false;
        boolean hasSignUpButton = false;
        for (Component component : welcomePage.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals("Login")) {
                    hasLoginButton = true;
                } else if (button.getText().equals("Sign Up")) {
                    hasSignUpButton = true;
                }
            }
        }
        assertTrue(hasLoginButton);
        assertTrue(hasSignUpButton);
    }
}
