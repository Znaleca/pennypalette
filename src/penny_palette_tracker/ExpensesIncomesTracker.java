package penny_palette_tracker;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;

public class ExpensesIncomesTracker extends JFrame {

    private final ExpenseIncomeTableModel tableModel;
    private final JTable table;
    private final JButton deleteButton;

    private final JTextField dateField;
    private final JTextField descriptionField;
    private final JTextField amountField;
    private final JComboBox<String> typeComboBox;
    private final JButton addButton;
    private final JLabel balanceLabel;
    private double balance;

    public ExpensesIncomesTracker() {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to Set FlatDarkLaf LookAndFeel");
        }

UIManager.put("TextField.foreground", Color.WHITE);  // Change text color to black
UIManager.put("TextField.background", Color.BLACK);  // Yellow background
UIManager.put("TextField.caretForeground", Color.RED);
UIManager.put("ComboBox.foreground", Color.WHITE);
UIManager.put("ComboBox.selectionForeground", new Color(61, 204, 154));
UIManager.put("ComboBox.selectionBackground", Color.WHITE);
UIManager.put("Button.foreground", Color.BLACK);
UIManager.put("Button.background", new Color(134, 190, 60));
UIManager.put("Label.foreground", new Color(134, 190, 6));
UIManager.put("Table.background", new Color(150, 186, 220));


        
        Font customFont = new Font("Arial", Font.PLAIN, 20);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Button.font", customFont);
       
        balance = 0.0;
        tableModel = new ExpenseIncomeTableModel();

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        dateField = new JTextField(10);
        descriptionField = new JTextField(20);
        amountField = new JTextField(10);
        typeComboBox = new JComboBox<>(new String[]{"Expense", "Income"});

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addEntry());

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED);  // Set the background color to red
        deleteButton.addActionListener(e -> deleteEntry());

        balanceLabel = new JLabel("Balance: ₱" + balance);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Date/Event"));
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Description"));
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Type"));
        inputPanel.add(typeComboBox);

        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(balanceLabel);
        setLayout(new BorderLayout());

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("PennyPalette: Expense Tracker & Budget Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void setUIColors() {
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.BLACK);
        UIManager.put("TextField.caretForeground", Color.RED);
        UIManager.put("ComboBox.foreground", Color.WHITE);
        UIManager.put("ComboBox.selectionForeground", new Color(61, 204, 154));
        UIManager.put("ComboBox.selectionBackground", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Button.background", Color.GREEN);
        UIManager.put("Label.foreground", Color.YELLOW);
        UIManager.put("Table.background", Color.BLACK);
    }

    private void setUIFonts() {
        Font customFont = new Font("Arial", Font.PLAIN, 20);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Button.font", customFont);
    }

    private void addEntry() {
        String date = dateField.getText();
        String description = descriptionField.getText();
        String amountStr = amountField.getText();
        String type = (String) typeComboBox.getSelectedItem();
        double amount;

        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter the Amount", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Amount Format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("Expense".equals(type)) {
            amount *= -1;
        }

        ExpenseIncomeEntry entry = new ExpenseIncomeEntry(date, description, amount, type);
        tableModel.addEntry(entry);

        balance += amount;
        balanceLabel.setText("Balance: ₱" + balance);

        clearInputFields();
    }

    private void deleteEntry() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ExpenseIncomeEntry entry = tableModel.getEntry(selectedRow);
        tableModel.removeEntry(selectedRow);

        balance -= entry.getAmount();
        balanceLabel.setText("Balance: ₱" + balance);

        // Notify the table about the removed row
        tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
    }

    private void clearInputFields() {
        dateField.setText("");
        descriptionField.setText("");
        amountField.setText("");
        typeComboBox.setSelectedIndex(0);
    }
}