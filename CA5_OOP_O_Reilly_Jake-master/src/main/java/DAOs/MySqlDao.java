package DAOs;

import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// File by Jake O'Reilly

public class MySqlDao {
    public Connection getConnection() throws DaoException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/instrumentshopdb";
        String username = "root";
        String password = "";
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }

        catch (ClassNotFoundException e) {
            System.out.println("Failed to find driver class " + e.getMessage());
            System.exit(1);
        }

        catch (SQLException e) {
            System.out.println("Connection failed " + e.getMessage());
            System.exit(2);
        }
        return connection;
    }

    public void freeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        }

        catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
}