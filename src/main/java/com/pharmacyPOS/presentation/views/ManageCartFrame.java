package com.pharmacyPOS.presentation.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
import com.pharmacyPOS.data.entities.*;
import com.pharmacyPOS.presentation.controllers.CartController;
import com.pharmacyPOS.presentation.controllers.InventoryController;
import com.pharmacyPOS.service.CartService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageCartFrame extends JFrame {
    private CartController cartController;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JButton processOrderButton;
    private JButton clearButton;
    private int userId;
    private int currentOrderId; // To keep track of the current order ID
    private OrderDao orderDao; // Ensure that orderDao is initialized properly
    private JLabel totalAmountLabel;

    public ManageCartFrame(int userId) {
        this.userId = userId;
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        this.cartController = new CartController(new CartService(new CartDao(c)));
        this.orderDao = new OrderDao(c);

        totalAmountLabel = new JLabel("Total: $0.00");
        totalAmountLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        clearButton = new JButton("Clear Cart");
        clearButton.addActionListener(this::onclearButtonClicked);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(processOrderButton);
        buttonPanel.add(clearButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(totalAmountLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(new JScrollPane(cartTable), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    private void onclearButtonClicked(ActionEvent actionEvent) {
        try {
            Cart currentCart = cartController.getCurrentCart(userId);
            cartController.clearCart(currentCart.getCartId());
            loadCartItems();
//            cartTableModel.setRowCount(0); // Clear the table model
            // Clear the cart
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        refreshTotalAmount();
    }


    private void refreshTotalAmount() {
        try {
            double total = cartController.getCartTotal(userId);
            totalAmountLabel.setText("Total: $" + String.format("%.2f", total));
        } catch (Exception ex) {
            ex.printStackTrace();
            totalAmountLabel.setText("Error calculating total");
        }
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
                int cartId = getCurrentCartId();
                try {
                    cartController.removeItemFromCart(cartId,productId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cartTableModel.removeRow(editedRow); // Remove the row from the table model
            }
            isPushed = false;
            return label;
        }

        private int getCurrentCartId() {
            Cart cart = null;
            try {
                cart = cartController.getCurrentCart(userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int cartID=cart.getCartId();
            return cartID;
        }

        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    }

    private int findRowByProductId(int productId) {
        for (int row = 0; row < cartTableModel.getRowCount(); row++) {
            if ((int) cartTableModel.getValueAt(row, 0) == productId) {
                return row;
            }
        }
        return -1; // Return -1 if not found
    }

    private void loadCartItems() {
        cartTableModel.setRowCount(0); // Clear existing rows
        try {
            List<CartItem> cartItems = cartController.getCartItems(userId);
            for (CartItem item : cartItems) {
                int row = findRowByProductId(item.getProductId());
                if (row != -1) {
                    // Update quantity in the existing row
                    int existingQuantity = (int) cartTableModel.getValueAt(row, 2);
                    cartTableModel.setValueAt(existingQuantity + item.getQuantity(), row, 2);
                } else {
                    // Add new row for the product
                    cartTableModel.addRow(new Object[]{
                            item.getProductId(),
                            item.getProductName(),
                            item.getQuantity(),
                            item.getPrice(),
                            "Remove"
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading cart items", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshTotalAmount();
    }

    private void refreshCartDisplay() {
        // Implement logic to reload cart items and refresh the total amount
        loadCartItems();
        refreshTotalAmount();
    }
    // Main method for demonstration purposes
    public static void main(String[] args) {
        int userId = 2; // Example user ID
        DatabaseConnection c = new DatabaseConnection();
        c.connect();
        CartController cartController = new CartController(new CartService(new CartDao(c))); // Initialize with actual cart controller
        new ManageCartFrame(userId);
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

    private void onPayClicked(double totalAmount, String amountPaidStr, JFrame paymentFrame) throws SQLException {
        try {
            double amountPaid = Double.parseDouble(amountPaidStr);
            if (amountPaid >= totalAmount) {
                try {
                    Cart currentCart = cartController.getCurrentCart(userId);
                    List<OrderDetail> orderDetailsList = createOrderDetailsFromCart(currentCart);

                    // Convert List to Array
                    OrderDetail[] orderDetailsArray = new OrderDetail[orderDetailsList.size()];
                    orderDetailsArray = orderDetailsList.toArray(orderDetailsArray);

                    // Create Order object
                    Order order = new Order();
                    order.setUserId(userId);
                    order.setTimestamp(new Date());
                    order.setOrderDetails(orderDetailsArray);

                    // Save Order in the database (implement saveOrder in OrderDao)
                    orderDao.saveOrder(order);

                    JOptionPane.showMessageDialog(paymentFrame, "Payment successful! Change: " + (amountPaid - totalAmount), "Payment", JOptionPane.INFORMATION_MESSAGE);
                    // invoice generation logic willl be added here
                    new POSReceipt(order,totalAmount, amountPaid, "Sania");
                    JOptionPane.showMessageDialog(this, "Invoice generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    paymentFrame.dispose(); // Close the payment frame

                    // Clear the cart
                    cartController.clearCart(currentCart.getCartId());

                    // Update inventory
//                    updateInventory(orderDetailsList);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to generate invoice", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(paymentFrame, "Insufficient amount paid.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(paymentFrame, "Invalid amount entered.", "Payment Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private InventoryController inventorycontroller; // Make sure this is initialized

    /**
     * Updates the inventory based on the order details.
     * @param orderDetailsList A list of OrderDetail objects representing the items ordered.
     */
    private void updateInventory(List<OrderDetail> orderDetailsList) throws SQLException {
        for (OrderDetail orderDetail : orderDetailsList) {
            int productId = orderDetail.getProductId();
            int orderedQuantity = orderDetail.getQuantity();

            // Retrieve the current inventory for this product
            Inventory inventory = inventorycontroller.getInventoryByProductId(productId);
            if (inventory != null) {
                // Check if enough inventory is available
                if (inventory.getQuantity() >= orderedQuantity) {
                    // Decrease the inventory quantity
                    int newQuantity = inventory.getQuantity() - orderedQuantity;
                    inventory.setQuantity(newQuantity);
                    inventorycontroller.updateInventoryItem(inventory);
                } else {
                    // Handle insufficient inventory
                    throw new SQLException("Insufficient inventory for product ID: " + productId);
                }
            } else {
                // Handle case where inventory item doesn't exist
                throw new SQLException("Inventory item not found for product ID: " + productId);
            }
        }
    }

    private List<OrderDetail> createOrderDetailsFromCart(Cart cart) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (SaleItem item : cart.getItems()) {
            OrderDetail detail = new OrderDetail(currentOrderId, item.getProductId(), item.getQuantity(), item.getPrice());
            orderDetails.add(detail);
        }
        return orderDetails;
    }

    private Order createOrderFromCart() throws SQLException {
        Cart currentCart = cartController.getCurrentCart(userId);
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (SaleItem item : currentCart.getItems()) {
            OrderDetail detail = new OrderDetail(0, 0, item.getProductId(), item.getQuantity(), item.getPrice());
            orderDetails.add(detail);
        }

        Order order = new Order(0, userId, new Timestamp(System.currentTimeMillis()));
        order.setOrderDetails(orderDetails.toArray(new OrderDetail[0]));
        return order;
    }

    private void onProcessOrderClicked(ActionEvent actionEvent) {
        try {
            Cart currentCart = cartController.getCurrentCart(userId);
            if (currentCart != null && !currentCart.getItems().isEmpty()) {
                double totalAmount = cartController.getCartTotal(userId);

                // Confirm with the user to process the order
                int confirm = JOptionPane.showConfirmDialog(this, "Confirm to process the order worth " + String.format("%.2f", totalAmount), "Confirm Order", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    processOrder(currentCart);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cart is empty.", "No items to process", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing order", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processOrder(Cart cart) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (SaleItem item : cart.getItems())
        {
            // Assuming CartItem has the same or similar fields as SaleItem
            OrderDetail detail = new OrderDetail(0, 0, item.getProductId(), item.getQuantity(), item.getPrice());
            orderDetails.add(detail);
        }

        Order order = new Order(0, userId, new Timestamp(System.currentTimeMillis()));
        order.setOrderDetails(orderDetails.toArray(new OrderDetail[0]));

        orderDao.saveOrder(order);

        // Clear the cart after processing the order
        cartController.clearCart(cart.getCartId());

        JOptionPane.showMessageDialog(this, "Order processed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }


}

