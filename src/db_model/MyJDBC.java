package db_model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MyJDBC {

    // JDBC Database connection
    private static final String DB_URL = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    // Validate the user
    public static User validateUser(String username, String password) {
        try {
            // Establish a connection to DB
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a sql query statement
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );

            // Set the parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                // Get id, name, and balance
                int userId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal balance = resultSet.getBigDecimal("current_balance");


                // Return the user object
                return new User(userId, username, password, name, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return null if the user does not exist
        return null;
    }

    public static boolean registerUser(String username, String password, String name) {
        try {
            // Check if the user does not exist
            if (!checkUsername(username)) {

                // Establish a connection to DB
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                // Create a sql query statement
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO users (username, password, name, current_balance) VALUES (?, ?, ?, ?)"
                );

                // Set the parameters
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setBigDecimal(4, new BigDecimal("0.00"));

                // Execute the query
                preparedStatement.executeUpdate();

                // Return true if the user is registered
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    // Check if the username exists already
    public static boolean checkUsername(String username) {
        try {
            // Establish a connection to DB
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a sql query statement
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            // Set the parameters
            preparedStatement.setString(1, username);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Return false if the username does not exist
            if (!resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addTransactionToDatabase(TransactionHistory transaction) {
        try {
            // Establish a connection to DB
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a sql query statement
            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_date) VALUES (?, ?, ?, NOW())"
            );

            // Set the parameters. NOW() is a MySQL function that returns the current date and time
            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());

            // Execute the query
            insertTransaction.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUserBalance(User user) {
        try {
            // Establish a connection to DB
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a sql query statement
            PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE users SET current_balance = ? WHERE id = ?"
            );

            // Set the parameters
            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());

            // Execute the query
            updateBalance.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean transfer(User user, String transferredUsername, float transferAmount) {
        try {
            // Connect to DB
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create query
            PreparedStatement transferTo = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            transferTo.setString(1, transferredUsername);

            ResultSet resultSet = transferTo.executeQuery();

            while(resultSet.next()) {
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("current_balance")
                );

                // Create transaction
                TransactionHistory transferTransaction = new TransactionHistory(
                        user.getId(),
                        "Transfer",
                        new BigDecimal(-transferAmount),
                        null
                );

                TransactionHistory recieveTransaction = new TransactionHistory(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null
                );

                // Update Transfer User
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateUserBalance(transferredUser);
                // Update User
                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));
                updateUserBalance(user);

                // add transaction to db
                addTransactionToDatabase(transferTransaction);
                addTransactionToDatabase(recieveTransaction);

                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<TransactionHistory> getTransactionHistory(User user){
        ArrayList<TransactionHistory> transactionHistory = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement selectAllTransaction = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ?"
            );
            selectAllTransaction.setInt(1, user.getId());

            ResultSet resultSet = selectAllTransaction.executeQuery();

            // check if there is a next result then add
            while(resultSet.next()){
                // create transaction obj
                TransactionHistory transaction = new TransactionHistory(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date")
                );

                // store into array list
                transactionHistory.add(transaction);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return transactionHistory;
    }
}

