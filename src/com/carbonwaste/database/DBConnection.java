package com.carbonwaste.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/carbon_waste_db";
    private static final String USER = "root";
    private static final String PASS = "meet123";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check if MySQL is running.");
            e.printStackTrace();
        }
        return conn;
    }
}