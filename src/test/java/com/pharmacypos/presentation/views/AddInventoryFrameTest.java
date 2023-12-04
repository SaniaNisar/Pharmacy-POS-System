package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.presentation.controllers.ReplenishmentController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.ActionEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AddInventoryFrameTest {

    @Mock
    private ReplenishmentController mockReplenishmentController;
    private AddInventoryFrame addInventoryFrame;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        addInventoryFrame = new AddInventoryFrame(mockReplenishmentController);
    }

    @Test
    public void testUIInitialization() {
        assertNotNull(addInventoryFrame.getProductIdField());
        assertNotNull(addInventoryFrame.getQuantityField());
        assertNotNull(addInventoryFrame.getExpiryDateField());
        assertNotNull(addInventoryFrame.getLowStockThresholdField());
        assertNotNull(addInventoryFrame.getAddButton());
    }

    @Test
    public void testAddInventoryValidInput() {
        addInventoryFrame.getProductIdField().setText("1");
        addInventoryFrame.getQuantityField().setText("10");
        addInventoryFrame.getExpiryDateField().setText("2023-12-31");
        addInventoryFrame.getLowStockThresholdField().setText("5");

        addInventoryFrame.onAddButtonClicked(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(mockReplenishmentController).replenishInventory(1, 10, java.sql.Date.valueOf("2023-12-31"), 5);
    }

    @Test
    public void testAddInventoryInvalidInput() {
        addInventoryFrame.getProductIdField().setText("invalid");
        addInventoryFrame.getQuantityField().setText("invalid");
        addInventoryFrame.getExpiryDateField().setText("invalid");
        addInventoryFrame.getLowStockThresholdField().setText("invalid");

        addInventoryFrame.onAddButtonClicked(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        // Verify that replenishInventory is not called due to invalid input
        verify(mockReplenishmentController, never()).replenishInventory(anyInt(), anyInt(), any(), anyInt());
    }
}
