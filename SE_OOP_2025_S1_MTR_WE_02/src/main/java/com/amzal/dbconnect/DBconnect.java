package com.amzal.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.amzal.constants.AppConstants;
import com.amzal.util.ConfigManager;

public class DBconnect {
    // Prevent instantiation of this class
    private DBconnect() {}
    
    /**
     * Gets a database connection using properties from the configuration file
     * @return Connection object or null if connection fails
     */
    public static Connection getConnection() {
        try {
            // Load the database driver
            String driver = ConfigManager.getProperty(AppConstants.DB_DRIVER_KEY);
            Class.forName(driver);
            
            // Get connection parameters from properties
            String url = ConfigManager.getProperty(AppConstants.DB_URL_KEY);
            String user = ConfigManager.getProperty(AppConstants.DB_USERNAME_KEY);
            String password = ConfigManager.getProperty(AppConstants.DB_PASSWORD_KEY);
            
            // Get and return connection
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

//package com.amzal.dbconnect;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBconnect {
//    private static final String URL = "jdbc:mysql://localhost:3306/musicstore";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
//
//    // Prevent instantiation of this class
//    private DBconnect() {}
//
//    public static Connection getConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}

