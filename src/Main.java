import guis.LoginGui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a new LoginGui object
                new LoginGui().setVisible(true);
//                new RegisterGui().setVisible(true);
//                new BankAppGui(
//                        new User(1, "Username", "Password", "Name", new BigDecimal("20.00")))
//                        .setVisible(true);
            }
        });
        System.getenv("DB_URL");
    }
}
