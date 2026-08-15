package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
    public void addManager(String name,String email,String phone){
        String sql = "INSERT INTO manager_details(manager_name,manager_email,manager_phone) VALUES (?,?,?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,phone);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("Manager added successfully");
            }else{
                System.out.println("Manager not added successfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateManager(int managerId,String name,String email,String phone){
        String sql = "update manager_details set manager_name=?,manager_email=?,manager_phone=? " +
                "where manager_id=? ";
        try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,phone);
            ps.setInt(4,managerId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("Manager updated successfully");
            }else{
                System.out.println("Manager not updated successfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteManager(int managerId){
        String sql = "DELETE FROM manager_details WHERE manager_id=?";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,managerId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("Manager deleted successfully");
            }else{
                System.out.println("Manager not deleted successfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
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
                System.out.println("Manager not found");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Manager> getAllManagers(){
        String sql = "SELECT * FROM manager_details";
        List<Manager> managers = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int manager_id = rs.getInt("manager_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Manager manager = new Manager(manager_id,name,email,phone);
                managers.add(manager);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return managers;
    }
}
