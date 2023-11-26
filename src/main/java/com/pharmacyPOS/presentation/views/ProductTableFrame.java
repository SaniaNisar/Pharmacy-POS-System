package com.pharmacyPOS.presentation.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

class ProductTableFrame extends JFrame {
    public ProductTableFrame(List<String> products)
    {
        setTitle("Product Table");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        DefaultTableModel productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Product Name");

        JTable productTable = new JTable(productTableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        for (String productName : products) {
            productTableModel.addRow(new Object[]{productName});
        }

        getContentPane().add(tableScrollPane);
        setVisible(true);
    }
}