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
import com.pharmacyPOS.data.dao.OrderDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.Cart;
import com.pharmacyPOS.data.entities.CartItem;
import com.pharmacyPOS.data.entities.Order;
import com.pharmacyPOS.presentation.controllers.CartController;
import com.pharmacyPOS.service.CartService;

import java.util.List;

public class ManageCartFrame extends JFrame {
    private CartController cartController;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JButton processOrderButton;
    private int userId;
    private int currentOrderId; // To keep track of the current order ID
    private OrderDao orderDao; // Ensure that orderDao is initialized properly

    public ManageCartFrame(int userId) {
        this.userId = userId;
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        this.cartController = new CartController(new CartService(new CartDao(c)));
        this.orderDao = new OrderDao(c);

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

        processOrderButton = new JButton("Process Order");
        processOrderButton.addActionListener(this::onProcessOrderClicked);
        add(processOrderButton, BorderLayout.SOUTH);

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
            for (CartItem item : cartItems) {
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

    private void onProcessOrderClicked(ActionEvent e) {
        // Calculate the total amount (replace with actual calculation logic)
        double totalAmount = calculateTotalAmount();

        // Show the initial processing message
        JOptionPane.showMessageDialog(this, "Processing your Order!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Create the payment frame
        JFrame paymentFrame = new JFrame("Payment");
        paymentFrame.setSize(300, 200);
        paymentFrame.setLayout(new FlowLayout());
        paymentFrame.setResizable(false); // Prevent resizing the frame

        // Add components to the payment frame
        JLabel totalAmountLabel = new JLabel("Total Amount: ");
        JTextField totalAmountField = new JTextField(20);
        totalAmountField.setText(String.format("%.2f", totalAmount));
        totalAmountField.setEditable(false);

        JLabel amountPaidLabel = new JLabel("Amount Paid: ");
        JTextField amountPaidField = new JTextField(20);

        JButton payButton = new JButton("Generate Invoice");
        JButton cancelOrder = new JButton("Cancel Order");
        payButton.addActionListener(event -> onPayClicked(totalAmount, amountPaidField.getText(), paymentFrame));
        cancelOrder.addActionListener(event -> cancellationOrder(paymentFrame));

        paymentFrame.add(totalAmountLabel);
        paymentFrame.add(totalAmountField);
        paymentFrame.add(amountPaidLabel);
        paymentFrame.add(amountPaidField);
        paymentFrame.add(payButton);
        paymentFrame.add(cancelOrder);

        // Center the payment frame on the screen
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setVisible(true);
    }

    private void cancellationOrder(JFrame paymentFrame) {
        int confirm = JOptionPane.showConfirmDialog(
                paymentFrame,
                "Are you sure you want to cancel this order?",
                "Cancel Order",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
//                orderDao.deleteOrder(orderId);
                JOptionPane.showMessageDialog(paymentFrame, "Order cancelled successfully.", "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(paymentFrame, "Failed to cancel order", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                paymentFrame.dispose();
            }
        }
    }

    private double calculateTotalAmount() {
        double total = 0.0;
        try {
             total = cartController.getCartTotal(userId);
//            // Now you can use this total to display in the UI
//            JOptionPane.showMessageDialog(this, "Total Cart Amount: " + total, "Cart Total", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error calculating cart total", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return total;
    }

    private void onPayClicked(double totalAmount, String amountPaidStr, JFrame paymentFrame) {
        try {
            double amountPaid = Double.parseDouble(amountPaidStr);
            if (amountPaid >= totalAmount) {
                JOptionPane.showMessageDialog(paymentFrame, "Payment successful! Change: " + (amountPaid - totalAmount), "Payment", JOptionPane.INFORMATION_MESSAGE);
                paymentFrame.dispose(); // Close the payment frame
            } else {
                JOptionPane.showMessageDialog(paymentFrame, "Insufficient amount paid.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(paymentFrame, "Invalid amount entered.", "Payment Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}