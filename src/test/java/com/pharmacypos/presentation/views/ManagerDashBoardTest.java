package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.presentation.views.ManagerDashboard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ManagerDashboardTest {

    private ManagerDashboard managerDashboard;
    private JMenuBar menuBar;

    @Mock
    private DatabaseConnection mockDatabaseConnection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        verify(mockDatabaseConnection.connect());

        managerDashboard = new ManagerDashboard(mockDatabaseConnection);
        menuBar = managerDashboard.getJMenuBar();
    }

    @Test
    public void testDashboardInitialization() {
        assertNotNull("Menu bar should be initialized", menuBar);
        assertEquals("Menu bar should have 4 menus", 4, menuBar.getMenuCount());
    }

    @Test
    public void testInventoryMenuItemAction() {
        JMenuItem manageInventoryItem = findMenuItem("Manage Inventory");
        assertNotNull(manageInventoryItem);
        simulateMenuItemClick(manageInventoryItem);

        // Verify that the InventoryManagementPage is opened
        // This verification might be limited due to the nature of the action
    }

    @Test
    public void testLogoutMenuItemAction() {
        JMenuItem logoutItem = findMenuItem("Logout Manager");
        assertNotNull(logoutItem);
        simulateMenuItemClick(logoutItem);

        // Verify that the logout process is initiated
        // Actual verification of logout might be limited or need a different approach
    }

    // Helper method to find a menu item by its text
    private JMenuItem findMenuItem(String text) {
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            for (int j = 0; j < menu.getItemCount(); j++) {
                JMenuItem item = menu.getItem(j);
                if (item != null && text.equals(item.getText())) {
                    return item;
                }
            }
        }
        return null;
    }

    // Helper method to simulate a menu item click
    private void simulateMenuItemClick(JMenuItem menuItem) {
        for (ActionListener listener : menuItem.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(menuItem, ActionEvent.ACTION_PERFORMED, null));
        }
    }
}
