package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public void addEmployee(String employeeName, double employeeSalary,
                            String employeeEmail,String phone,
                            String status, String designation,
                            int departmentId) {
        String sql = "insert into employee_details "+
                "(employee_name,employee_salary,employee_email,employee_phone,status,designation,department_id) values(?,?,?,?,?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,employeeName);
            ps.setDouble(2,employeeSalary);
            ps.setString(3,employeeEmail);
            ps.setString(4,phone);
            ps.setString(5,status);
            ps.setString(6,designation);
            ps.setInt(7,departmentId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("Employee added successfully");
            }else {
                System.out.println("Error adding employee");
            }
        }catch (SQLException e){
            if (e.getErrorCode() == 1062) {
                System.out.println("Employee already exists.");
            } else {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    public void updateEmployee(int employeeId, String employeeName,
                               double employeeSalary, String employeeEmail,
                               String phone,String status,
                               String designation, int departmentId){
        String sql = "update employee_details " +
                "set employee_name = ?, employee_salary = ?, employee_email = ?, employee_phone = ?, status = ?, designation = ?, department_id = ? " +
                "where employee_id = ? ";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
        ps.setString(1,employeeName);
        ps.setDouble(2,employeeSalary);
        ps.setString(3,employeeEmail);
        ps.setString(4,phone);
        ps.setString(5,status);
        ps.setString(6,designation);
        ps.setInt(7,departmentId);
        ps.setInt(8,employeeId);
        int row = ps.executeUpdate();
        if(row>0){
            System.out.println("Employee updated successfully");
        }else  {
            System.out.println("Error updating employee");
        }
        }catch (SQLException e){
            if (e.getErrorCode() == 1062) {
                System.out.println("Employee already exists.");
            } else {
                System.out.println("Database error: " + e.getMessage());
            }

        }
    }

    public void deleteEmployee(int employeeId){
        String sql = "delete from employee_details where employee_id = ?";
        try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, employeeId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("Employee deleted successfully");
            }else{
                System.out.println("Error deleting employee");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Employee getEmployeeById(int employeeId){
        String sql = "select * from employee_details where employee_id = ?";
        try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int employee_Id = rs.getInt("employee_id");
                String employee_name = rs.getString("employee_name");
                double employee_salary = rs.getDouble("employee_salary");
                String employee_email = rs.getString("employee_email");
                String employee_phone = rs.getString("employee_phone");
                String status = rs.getString("status");
                String designation = rs.getString("designation");
                int department_id = rs.getInt("department_id");
                return new Employee(employee_Id,employee_name,employee_salary,employee_email,employee_phone,status,designation,department_id);
            }else{
                System.out.println("Employee not found");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Employee> getAllEmployees(){
        String sql = "select * from employee_details";
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String employeeName = rs.getString("employee_name");
                double employeeSalary = rs.getDouble("employee_salary");
                String employeeEmail = rs.getString("employee_email");
                String employeePhone = rs.getString("employee_phone");
                String status = rs.getString("status");
                String designation = rs.getString("designation");
                int departmentId = rs.getInt("department_id");
                Employee employee = new Employee(employeeId, employeeName, employeeSalary, employeeEmail, employeePhone, status, designation, departmentId);
                employees.add(employee);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }

}
