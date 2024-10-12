package guis;

import db_model.MyJDBC;
import db_model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGui extends BankFrame {
    public LoginGui() {
        super("Login Page");
    }

    // Add the components to the frame
    @Override
    protected void addGuiComponents() {
        // Create a new Banking JLabel
        JLabel bankLabel = new JLabel("Calvin's Banking Application Login");

        // Set the bounds of the label
        bankLabel.setBounds(0, 20, super.getWidth(), 40);

        // Set the font of the label
        bankLabel.setFont(new Font("Dialog", Font.BOLD, 20));

        // Center the text of the label
        bankLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add bankLabel to the frame
        add(bankLabel);

        // Create username JLabel
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 120, super.getWidth() - 20, 24);
        userLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(userLabel);

        // Create a username text field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, super.getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(usernameField);

        // Create password JLabel
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 280, super.getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // Create a JpasswordField
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, super.getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(passwordField);

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 460, super.getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            // Get the username and password
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            // Validate the user
            User user = MyJDBC.validateUser(username, password);

            // If the user is not null
            if (user != null) {
                // Dispose the current frame
                LoginGui.this.dispose();

                // Create a new BankAppGui object
                BankAppGui bankAppGui = new BankAppGui(user);
                bankAppGui.setVisible(true);

                // Display a message
                JOptionPane.showMessageDialog(bankAppGui, "Login Successful");
            } else {
                // Display an error message
                JOptionPane.showMessageDialog(LoginGui.this, "Invalid Username or Password");
            }
        });
        add(loginButton);

        // Create register label
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(20, 510, super.getWidth() - 50, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registerLabel);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Dispose the current frame
                LoginGui.this.dispose();

                // Create a new RegisterGui object
                new RegisterGui().setVisible(true);
            }
        });
    }
}
