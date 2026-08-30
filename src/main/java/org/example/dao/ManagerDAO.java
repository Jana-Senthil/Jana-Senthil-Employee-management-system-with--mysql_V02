package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
    public int addManager(String name,String email,String phone){
        String sql = "INSERT INTO manager_details(manager_name,manager_email,manager_phone) VALUES (?,?,?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )){
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,phone);
            int row = ps.executeUpdate();
            if (row > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public boolean updateManager(int managerId,String name,String email,String phone){
        String sql = "update manager_details set manager_name=?,manager_email=?,manager_phone=? " +
                "where manager_id=? ";
        try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,phone);
            ps.setInt(4,managerId);
            int row = ps.executeUpdate();
            return row>0;
        }catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean permanentlyDeleteManager(int managerId, int userId) {

        String deleteUser =
                "DELETE FROM users WHERE user_id = ? AND manager_id = ?";

        String deleteManager =
                "DELETE FROM manager_details WHERE manager_id = ?";

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // 1. Delete manager's user account
            try (PreparedStatement ps =
                         connection.prepareStatement(deleteUser)) {

                ps.setInt(1, userId);
                ps.setInt(2, managerId);

                int userRows = ps.executeUpdate();

                if (userRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // 2. Delete manager details
            try (PreparedStatement ps =
                         connection.prepareStatement(deleteManager)) {

                ps.setInt(1, managerId);

                int managerRows = ps.executeUpdate();

                if (managerRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Both operations succeeded
            connection.commit();

            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            System.out.println(
                    "Permanent manager deletion failed: "
                            + e.getMessage()
            );

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Manager getManagerById(int managerId){
        String sql = "SELECT * FROM manager_details WHERE manager_id=?";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,managerId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int manager_id = rs.getInt("manager_id");
                String name = rs.getString("manager_name");
                String email = rs.getString("manager_email");
                String phone = rs.getString("manager_phone");
                return new Manager(manager_id,name,email,phone);
            }else{
                return null;
            }
        }catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Manager> getAllManagers(){
        String sql = "SELECT * FROM manager_details";
        List<Manager> managers = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int manager_id = rs.getInt("manager_id");
                String name = rs.getString("manager_name");
                String email = rs.getString("manager_email");
                String phone = rs.getString("manager_phone");
                Manager manager = new Manager(manager_id,name,email,phone);
                managers.add(manager);
            }
        }catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return managers;
        }
        return managers;
    }

}

