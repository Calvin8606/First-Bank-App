package guis;

import db_model.MyJDBC;

import javax.swing.*;
import java.awt.*;

public class RegisterGui extends BankFrame {
    public RegisterGui() {
        super("Register");
    }

    @Override
    protected void addGuiComponents() {
        // Create a new Banking JLabel
        JLabel bankLabel = new JLabel("Register Page");

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
        userLabel.setBounds(20, 60, super.getWidth() - 20, 24);
        userLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(userLabel);

        // Create a username text field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 100, super.getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(usernameField);

        // Create Name JLabel
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 160, super.getWidth() - 20, 24);
        nameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(nameLabel);

        // Create a First Name text field
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(20, 200, super.getWidth() - 50, 40);
        firstNameField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(firstNameField);

        // Create password JLabel
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 260, super.getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // Create a JpasswordField
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 300, super.getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(passwordField);

        // Create a re-type password label
        JLabel rePasswordLabel = new JLabel("Re-type Password:");
        rePasswordLabel.setBounds(20, 340, super.getWidth() - 50, 40);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        // Create a re-type password field
        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 380, super.getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(rePasswordField);

        // Create register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 460, super.getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(e -> {
            // Get the user input
            String username = usernameField.getText();
            String name = firstNameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String rePassword = String.valueOf(rePasswordField.getPassword());

            // Validate the user input
            if (validateUserInput(username, name, password, rePassword)) {
                // Register the user
                if (MyJDBC.registerUser(username, password, name)) {
                    // Dispose the current frame
                    RegisterGui.this.dispose();

                    // Create a new LoginGui object
                    new LoginGui().setVisible(true);

                    // Display a message
                    JOptionPane.showMessageDialog(RegisterGui.this, "Registration Successful");
                } else {
                    // Display an error message
                    JOptionPane.showMessageDialog(RegisterGui.this, "Registration Failed. Username already exists");
                }
            } else {
                // Display an error message
                JOptionPane.showMessageDialog(RegisterGui.this, "Registration Failed");
            }
        });
        add(registerButton);

        // Create login label
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Login Here</a></html>");
        loginLabel.setBounds(20, 510, super.getWidth() - 50, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);
    }

    // Validate the user input
    private boolean validateUserInput(String username, String name, String password, String rePassword) {
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            JOptionPane.showMessageDialog(RegisterGui.this, "All fields are required");
            return false;
        } else if (!password.equals(rePassword)) {
            JOptionPane.showMessageDialog(RegisterGui.this, "Passwords do not match");
            return false;
        } else if (password.length() < 8) {
            JOptionPane.showMessageDialog(RegisterGui.this, "Password must be at least 8 characters long");
            return false;
        }
        return true;
    }

}
