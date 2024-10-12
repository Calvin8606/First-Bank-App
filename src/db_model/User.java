package db_model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class User {
    private final int id;
    private final String username, password, name;
    private BigDecimal currentBalance;
    public User(int id, String username, String password, String name, BigDecimal currentBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal newBalance) {
        currentBalance = newBalance.setScale(2, RoundingMode.FLOOR);
    }
}
