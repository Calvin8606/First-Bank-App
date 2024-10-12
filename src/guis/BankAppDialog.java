package view;

import model.MyJDBC;
import model.TransactionHistory;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
public class BankAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankAppGui bankAppGui;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField, enterUserField;
    private JButton actionButton;

    public BankAppDialog(BankAppGui bankAppGui, User user) {
        // Set size
        setSize(400, 400);

        // Can't interact with background until dialog is closed
        setModal(true);

        // Set dialog in the center of the screen
        setLocationRelativeTo(bankAppGui);

        // Set close operation
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Can't resize the dialog
        setResizable(false);

        // Set layout
        setLayout(null);

        // reference gui and user
        this.bankAppGui = bankAppGui;
        this.user = user;

        addCurrentBalanceAndAmount();
    }

    public void addCurrentBalanceAndAmount() {
        // Create current balance label
        balanceLabel = new JLabel("Current Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // Create enter amount label
        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        // Create enter amount field
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
    }

    public void addActionButton(String actionType) {
        actionButton = new JButton(actionType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Enter Username:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        // Create enter user field
        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }

    private void handleTransaction(String transactionType, float amount) {
        TransactionHistory transaction;
        // Check if the transaction type is deposit and update the user balance
        if (transactionType.equalsIgnoreCase("Deposit")) {
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amount)));
            // Create a new transaction history object
            transaction = new TransactionHistory(user.getId(), transactionType, new BigDecimal(amount), null);
        } else {
            // Else withdraw and update the user balance
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amount)));
            // Create a new transaction history object
            transaction = new TransactionHistory(user.getId(), transactionType, new BigDecimal(-amount), null);
        }
        // Add the transaction to the database
        if (MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateUserBalance(user)) {
            // Show a success message
            JOptionPane.showMessageDialog(this, "Transaction Successful");
            // Reset the fields and update the balance
            resetFieldsAndUpdateBalance();
        } else {
            // Show an error message
            JOptionPane.showMessageDialog(this, "Transaction Failed");
        }
    }

    private void resetFieldsAndUpdateBalance() {
        // Reset the fields
        enterAmountField.setText("");

        // Only reset if transfer is clicked
        if (enterUserField != null) {
            enterUserField.setText("");
        }
        // Update the balance
        balanceLabel.setText("Current Balance: $" + user.getCurrentBalance());

        // Update balance on main gui
        bankAppGui.getDisplayBalanceField().setText("$" + user.getCurrentBalance());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the action command
        String action = e.getActionCommand();

        // Get the amount entered
        float amount = Float.parseFloat(enterAmountField.getText());

        if (action.equalsIgnoreCase("Deposit")) {
            System.out.println(action);
            // Handle deposit
            handleTransaction(action, amount);

        }
    }
}
