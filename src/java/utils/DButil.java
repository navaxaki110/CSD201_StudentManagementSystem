/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String DB_URL = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=StudentDB;";
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL, "sa", "12345");
        } catch (Exception e) {
            // Log notice if local DB is not active
            System.out.println("Notice: Local SQL Server not connected. Using file-based storage: " + e.getMessage());
        }
        return conn;
    }
}
