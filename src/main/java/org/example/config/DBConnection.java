package org.example.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static String url= "jdbc:mysql://localhost:3306/employee_management";
    private static String user="root";
    private static String password="102003@Jana";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

}
