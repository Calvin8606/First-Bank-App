package guis;

import db_model.User;

import javax.swing.*;

public abstract class BankFrame extends JFrame {
    // Store User object
    protected User user;

    public BankFrame(String title) {
        initialize(title);
    }

    public BankFrame(String title, User user) {
        this.user = user;
        initialize(title);
    }

    private void initialize(String title) {
        // Set the title of the frame
        setTitle(title);

        // 420x600 pixels
        setSize(420, 600);

        // absolute layout which allows us to set the position of the components
        setLayout(null);

        // Close the application when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cannot resize the frame
        setResizable(false);

        // Center the frame
        setLocationRelativeTo(null);

        // Add the components to the frame
        addGuiComponents();
    }
    // Abstract method to add the components to the frame
    protected abstract void addGuiComponents();
}
