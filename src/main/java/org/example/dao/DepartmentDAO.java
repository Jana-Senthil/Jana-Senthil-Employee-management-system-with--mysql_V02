package org.example.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.config.DBConnection;
import org.example.model.Department;

public class DepartmentDAO {
    public boolean addDepartment(String departmentName,String location){
//        String sql = "insert into department_details values (?,?)";
        String sql = "INSERT INTO department_details " +
                "(department_name, location) VALUES (?, ?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,departmentName);
            ps.setString(2,location);
            int row = ps.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            return   false;
        }
    }

    public boolean updateDepartment(int departmentId,String departmentName,String location){
        String sql = "UPDATE department_details " +
                "SET department_name = ?, location = ? "+
                "where department_id = ?";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,departmentName);
            ps.setString(2,location);
            ps.setInt(3,departmentId);
            int row = ps.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            return  false;
        }
    }

    public boolean deleteDepartment(int departmentId){
        String sql = "delete from department_details " +
                "where department_id = ?";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,departmentId);
            int row = ps.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            return false;
        }
    }

    public Department getDepartmentById(int departmentId){
        String sql = "select * from department_details " +
                "where department_id = ? ";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,departmentId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int department_id = rs.getInt("department_id");
                String department_name = rs.getString("department_name");
                String location = rs.getString("location");
                return new Department(department_id,department_name,location);
            }else{
                return null;
            }

        }catch (SQLException e){
            return  null;
        }
    }

    public List<Department> getAllDepartments(){
        List<Department> departments = new ArrayList<>();
        String sql = "select * from department_details";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int department_id = rs.getInt("department_id");
                String department_name = rs.getString("department_name");
                String location = rs.getString("location");
                Department department = new Department(department_id,department_name,location);
                departments.add(department);
            }
        }catch (SQLException e){
            return departments;
        }
        return departments;
    }

}
