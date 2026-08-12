package org.example;

import org.example.config.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();
            System.out.println("Connected to database successfully");
        }catch (SQLException e){
            System.out.println("Connection Failed");
        }

        System.out.println("hello\n"+"world");
        }
    }