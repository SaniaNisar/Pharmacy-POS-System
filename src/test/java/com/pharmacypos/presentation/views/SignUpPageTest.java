package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.UserDao;
import com.pharmacyPOS.data.entities.User;
import com.pharmacyPOS.presentation.controllers.UserController;
import com.pharmacyPOS.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SignUpPageTest {
    @Mock
    private UserDao mockUserDao;
    private UserService userService;
    private UserController userController;
    private SignUpPage signUpPage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(mockUserDao);
        userController = new UserController(userService);
        signUpPage = new SignUpPage(mockUserDao);
    }

    @Test
    public void testSignUpPageComponentsInitialization() {
        JPanel panel = (JPanel) signUpPage.getContentPane().getComponent(0);
        assertNotNull("Panel should be initialized", panel);
        assertEquals("Panel should have 6 components", 6, panel.getComponentCount());
    }

    @Test
    public void testSignUpFunctionality() {
        // Find components
        JTextField usernameField = (JTextField) findComponent(signUpPage, JTextField.class, 0);
        JPasswordField passwordField = (JPasswordField) findComponent(signUpPage, JPasswordField.class, 0);
        JComboBox<?> roleComboBox = (JComboBox<?>) findComponent(signUpPage, JComboBox.class, 0);
        JButton signUpButton = (JButton) findComponent(signUpPage, JButton.class, 0);

        // Set field values
        usernameField.setText("newUser");
        passwordField.setText("newPass");
        roleComboBox.setSelectedItem("Manager");

        // Prepare service responses and captors
        verify(mockUserDao).createUser(any(User.class)); // Mock the DAO to return a user ID

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Simulate button click
        signUpButton.doClick();

        // Verify that the userController.createUser method was called with the right parameters
        verify(mockUserDao).createUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals("newUser", capturedUser.getUsername());
        assertEquals("newPass", capturedUser.getPasswordHash());
        assertEquals("Manager", capturedUser.getRole());
    }

    private Component findComponent(Container container, Class<?> componentClass, int instanceIndex) {
        // This helper method traverses the container hierarchy and finds the nth instance of a component class
        int instanceCount = 0;
        for (Component comp : container.getComponents()) {
            if (componentClass.isInstance(comp)) {
                if (instanceCount == instanceIndex) {
                    return comp;
                }
                instanceCount++;
            } else if (comp instanceof Container) {
                Component found = findComponent((Container) comp, componentClass, instanceIndex - instanceCount);
                if (found != null) return found;
            }
        }
        return null;
    }
}
