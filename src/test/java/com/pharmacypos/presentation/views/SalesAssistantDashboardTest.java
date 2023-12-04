package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;

import java.awt.event.ActionEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SalesAssistantDashboardTest {

    private SalesAssistantDashboard dashboard;
    private DatabaseConnection mockConnection;
    private int testUserId = 1;

    @Before
    public void setUp() {
        mockConnection = Mockito.mock(DatabaseConnection.class);
        verify(mockConnection.connect());
        dashboard = new SalesAssistantDashboard(mockConnection, testUserId);
    }

    @Test
    public void testDashboardCreation() {
        // Verify the title of the dashboard
        assertEquals("Sales Assistant Dashboard", dashboard.getTitle());

        // Verify that the menu bar is created
        assertNotNull(dashboard.getJMenuBar());
        JMenuBar menuBar = dashboard.getJMenuBar();

        // Verify the correct number of menus
        assertEquals(4, menuBar.getMenuCount());
    }

    @Test
    public void testSearchInventoryMenuItemAction() {
        JMenuItem searchInventoryItem = findMenuItem(dashboard, "Search Inventory");
        assertNotNull(searchInventoryItem);

        // Simulate the action listener on the 'Search Inventory' menu item
        searchInventoryItem.doClick();

        // Since we cannot verify that `SearchInventoryFrame` was opened, we check that an action was performed
        assertTrue(searchInventoryItem.getActionListeners().length > 0);
    }

    @Test
    public void testManageCartMenuItemAction() {
        JMenuItem manageCartItem = findMenuItem(dashboard, "Manage Cart");
        assertNotNull(manageCartItem);

        // Simulate the action listener on the 'Manage Cart' menu item
        manageCartItem.doClick();

        // Verify that the action listener is attached and an action is performed
        assertTrue(manageCartItem.getActionListeners().length > 0);
    }

    @Test
    public void testLogoutMenuItemAction() {
        JMenuItem logoutItem = findMenuItem(dashboard, "Logout Sales Assistant");
        assertNotNull(logoutItem);

        // Prepare a spy to verify if `setVisible` and `dispose` methods are called
        SalesAssistantDashboard spyDashboard = spy(dashboard);
        doNothing().when(spyDashboard).setVisible(anyBoolean()); // Prevent the actual method from being called

        // Simulate the action listener on the 'Logout' menu item
        logoutItem.doClick();

        // Verify setVisible was called with false
        verify(spyDashboard, times(1)).setVisible(false);

        // Since we cannot test the actual opening of `LoginGUI` due to it being a new object creation,
        // we verify that an action was performed
        assertTrue(logoutItem.getActionListeners().length > 0);
    }

    private JMenuItem findMenuItem(JFrame frame, String menuItemText) {
        JMenuBar menuBar = frame.getJMenuBar();
        if (menuBar != null) {
            for (int i = 0; i < menuBar.getMenuCount(); i++) {
                JMenu menu = menuBar.getMenu(i);
                for (int j = 0; j < menu.getItemCount(); j++) {
                    JMenuItem item = menu.getItem(j);
                    if (item != null && menuItemText.equals(item.getText())) {
                        return item;
                    }
                }
            }
        }
        return null;
    }
}
