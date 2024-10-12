package guis;

import db_model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankAppGui extends BankFrame implements ActionListener {
    private JLabel displayBalanceLabel;
    public JLabel getDisplayBalanceLabel() {
        return displayBalanceLabel;
    }

    public BankAppGui(User user) {
        super("Bank Application", user);
    }

    @Override
    protected void addGuiComponents() {
        // Welcome message
        String message = "<html>" +
                "<body style='text-align: center;'>" +
                "<b>Welcome " + user.getName() + "</b></body></html>";
        // Create welcome label
        JLabel welcomeLabel = new JLabel(message);
        welcomeLabel.setBounds(0, 20, super.getWidth() - 10, 40);
        welcomeLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        // Create current balance label
        JLabel balanceLabel = new JLabel("Balance");
        balanceLabel.setBounds(0, 80, super.getWidth() - 10, 30);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // Display balance label
        displayBalanceLabel = new JLabel("$" + user.getCurrentBalance());
        displayBalanceLabel.setBounds(15, 120, super.getWidth() - 50, 40);
        displayBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        displayBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(displayBalanceLabel);

        // Create deposit button
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 180, super.getWidth() - 50, 50);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 22));
        depositButton.addActionListener(this);
        add(depositButton);

        // Create withdraw button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 250, super.getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 22));
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        // Create transfer button
        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 320, super.getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 22));
        transferButton.addActionListener(this);
        add(transferButton);

        // Create transaction history button
        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setBounds(15, 390, super.getWidth() - 50, 50);
        transactionHistoryButton.setFont(new Font("Dialog", Font.BOLD, 22));
        transactionHistoryButton.addActionListener(this);
        add(transactionHistoryButton);

        // Create logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 500, super.getWidth() - 50, 50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 22));
        logoutButton.addActionListener(this);
        add(logoutButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the action command
        String action = e.getActionCommand();

        // Check the action
        if (action.equalsIgnoreCase("Logout")) {
            // Redirect to the login page
            new LoginGui().setVisible(true);

            // Dispose the current frame
            this.dispose();

            // Display a message
            JOptionPane.showMessageDialog(this, "Logout Successful");

            // Stop code execution
            return;
        }

        // Create dialog box
        BankAppDialog bankAppDialog = new BankAppDialog(this, user);

        // Set the dialog box title
        bankAppDialog.setTitle(action);

        // if the action is Deposit, Withdraw, or Transfer
        if (action.equalsIgnoreCase("Deposit") || action.equalsIgnoreCase("Withdraw")
                || action.equalsIgnoreCase("Transfer")) {
            // Add balance fields
            bankAppDialog.addCurrentBalanceAndAmount();
            // Add action button
            bankAppDialog.addActionButton(action);
            // Set up transfer action
            if (action.equalsIgnoreCase("Transfer")) {
                bankAppDialog.addUserField();
            }
        } else if (action.equalsIgnoreCase("Transaction History")) {
            bankAppDialog.addTransactionHistoryComponents();
        }
        // Set the dialog box visible
        bankAppDialog.setVisible(true);
    }
}
