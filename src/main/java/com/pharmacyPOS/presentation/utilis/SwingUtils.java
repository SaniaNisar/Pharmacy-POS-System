package com.pharmacyPOS.presentation.utilis;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {

    /**
     * Creates a standard JLabel with a specified text.
     *
     * @param text The text for the JLabel.
     * @return A JLabel with the specified text.
     */
    public static JLabel createLabel(String text) {
        return new JLabel(text);
    }

    /**
     * Creates a JTextField with a specified width.
     *
     * @param width The width of the JTextField.
     * @return A JTextField with the specified width.
     */
    public static JTextField createTextField(int width) {
        JTextField textField = new JTextField();
        textField.setColumns(width);
        return textField;
    }

    /**
     * Creates a JButton with specified text.
     *
     * @param text The text for the JButton.
     * @return A JButton with the specified text.
     */
    public static JButton createButton(String text) {
        return new JButton(text);
    }

    /**
     * Sets a component's font size.
     *
     * @param component The component to set the font size for.
     * @param size      The font size.
     */
    public static void setFontSize(JComponent component, float size) {
        component.setFont(component.getFont().deriveFont(size));
    }

    /**
     * Centers a window (JFrame) on the screen.
     *
     * @param frame The JFrame to center.
     */
    public static void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                screenSize.width / 2 - frame.getSize().width / 2,
                screenSize.height / 2 - frame.getSize().height / 2
        );
    }

    // Additional utility methods for UI components can be added here...
}
