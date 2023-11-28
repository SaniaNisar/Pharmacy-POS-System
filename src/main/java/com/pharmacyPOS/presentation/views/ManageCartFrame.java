package com.pharmacyPOS.presentation.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import com.pharmacyPOS.data.dao.CartDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.CartItem;
import com.pharmacyPOS.presentation.controllers.CartController;
import com.pharmacyPOS.service.CartService;

import java.util.List;

public class ManageCartFrame extends JFrame {
    private CartController cartController;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JButton checkoutButton;
    private int userId;

    public ManageCartFrame(int userId) {
        this.userId = userId;
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        this.cartController = new CartController(new CartService(new CartDao(c)));

        setupUI();
        loadCartItems();

        setTitle("Manage Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        cartTableModel = new DefaultTableModel();
        cartTableModel.setColumnIdentifiers(new String[]{"Product ID", "Name", "Quantity", "Price", "Actions"});
        cartTable = new JTable(cartTableModel) {
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 4) {
                    return new ButtonRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 4) {
                    return new ButtonEditor(new JCheckBox());
                }
                return super.getCellEditor(row, column);
            }
        };

        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(this::onCheckoutClicked);
        add(checkoutButton, BorderLayout.SOUTH);

        add(new JScrollPane(cartTable), BorderLayout.CENTER);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Remove" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int editedRow;

        public ButtonEditor(JCheckBox checkBox) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            editedRow = row;
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "Remove" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Perform action on button click (e.g., remove item from cart)
                int productId = (int) cartTableModel.getValueAt(editedRow, 0);
                removeItemFromCart(productId);
            }
            isPushed = false;
            return label;
        }

        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    }

    private void loadCartItems() {
        cartTableModel.setRowCount(0);
        try {
            List<CartItem> cartItems = cartController.getCartItems(userId);
            for (CartItem item : cartItems)
            {
                cartTableModel.addRow(new Object[]{
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice(),
                        "Remove" // Placeholder for action buttons
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading cart items", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCheckoutClicked(ActionEvent e) {
        try {
            // Implement checkout logic
            JOptionPane.showMessageDialog(this, "Checkout successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Checkout failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeItemFromCart(int productId) {
        try {
            Cart currentCart = cartController.getCurrentCart(userId);
            cartController.removeItemFromCart(currentCart.getCartId(), productId);
            JOptionPane.showMessageDialog(this, "Item removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error removing item from cart", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Reload the cart items to reflect the changes
        loadCartItems();
    }


    // Main method for demonstration purposes
    public static void main(String[] args) {
        int userId = 2; // Example user ID
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        CartController cartController = new CartController(new CartService(new CartDao(c))); // Initialize with actual cart controller
        new ManageCartFrame(userId);
    }
}
