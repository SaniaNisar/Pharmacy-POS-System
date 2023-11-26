package com.pharmacyPOS.presentation.views;

import com.pharmacyPOS.data.dao.ProductDao;
import com.pharmacyPOS.service.ProductService;
import com.pharmacyPOS.service.InventoryService;
import com.pharmacyPOS.data.entities.Product;
import com.pharmacyPOS.data.entities.Inventory;
import com.pharmacyPOS.data.database.DatabaseConnection;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
//import java.sql.Date;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;



public class TrackExpiredProductsFrame extends JFrame {

    private ProductService productService;
    private InventoryService inventoryService;
    private JTable productsTable;
    private DatabaseConnection connection;

    public TrackExpiredProductsFrame(DatabaseConnection connection) {
        this.connection = connection;
        productService = new ProductService(new ProductDao(connection));
        inventoryService = new InventoryService(connection);

        setTitle("Expired Products Tracker");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        productsTable = new JTable();
        productsTable.setModel(new DefaultTableModel(new Object[]{"Product ID", "Name", "Description", "Price", "Expiration Date", "Quantity", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Action" column (Remove button) is editable
            }
        });

        loadProductsData();

        // Add a custom cell renderer for the Remove button
        productsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());

        // Add a custom cell editor for the Remove button
        // Set custom cell renderers and editors for the "Remove" button
        productsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), productsTable));

        //productsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadProductsData() {
        DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
        model.setRowCount(0); // Clear existing data

        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            Inventory inventory = inventoryService.getInventoryByProductId(product.getProductId());
            Date currentDate = new Date();
            boolean isExpired = currentDate.after(product.getExpirationDate());
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getExpirationDate().toString(),
                    inventory != null ? inventory.getQuantity() : "N/A",
                    isExpired ? "Remove" : "" // Button text only for expired products
            });
        }
    }

    // Custom renderer for the Remove button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setForeground(Color.WHITE);
            setBackground(Color.RED);
            return this;
        }
    }
    // Custom editor for the Remove button
    /*class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean isClicked;
        private int editingRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    isClicked = true;
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            editingRow = row;
            button.setText((value == null) ? "" : value.toString());
            button.setForeground(Color.WHITE);
            button.setBackground(Color.RED);
            return button;
        }

        public Object getCellEditorValue() {
            if (isClicked) {
                // Get the product ID from the row that the button was clicked in
                int productIdToRemove = (int) productsTable.getValueAt(editingRow, 0);
                inventoryService.deleteInventoryItem(productIdToRemove); // Assuming inventory_id is the same as product_id
                productService.deleteProduct(productIdToRemove);
                ((DefaultTableModel) productsTable.getModel()).removeRow(editingRow);
                JOptionPane.showMessageDialog(button, "Product removed");
            }
            isClicked = false;
            return "Remove";
        }

        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
     */

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private boolean isClicked;
        private JTable table;
        private int editingRow;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                isClicked = true;
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.editingRow = row;
            button.setText((value == null) ? "" : value.toString());
            button.setForeground(Color.WHITE);
            button.setBackground(Color.RED);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isClicked && editingRow >= 0 && editingRow < table.getModel().getRowCount()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String expirationDateString = (String) table.getValueAt(editingRow, 4);
                try {
                    Date expirationDate = dateFormat.parse(expirationDateString);
                    Date currentDate = new Date();
                    boolean isExpired = currentDate.after(expirationDate);

                    if (isExpired) {
                        SwingUtilities.invokeLater(() -> {
                            int productIdToRemove = (int) table.getValueAt(editingRow, 0);
                            productService.deleteProduct(productIdToRemove);
                            inventoryService.deleteInventoryItem(productIdToRemove);
                            ((DefaultTableModel) table.getModel()).removeRow(editingRow);
                            JOptionPane.showMessageDialog(button, "Expired product removed");
                        });
                    } else {
                        JOptionPane.showMessageDialog(button, "This product is not expired and cannot be removed.");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            isClicked = false;
            return "Remove";
        }

        @Override
        public boolean stopCellEditing() {
            isClicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseConnection connection = new DatabaseConnection();
            connection.connect();
            TrackExpiredProductsFrame frame = new TrackExpiredProductsFrame(connection);
            frame.setVisible(true);
        });
    }
}
