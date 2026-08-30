package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public int addEmployee(String employeeName, double employeeSalary,
                            String employeeEmail,String phone,
                            String status, String designation,
                            int departmentId) {
        String sql = "insert into employee_details "+
                "(employee_name,employee_salary,employee_email,employee_phone,status,designation,department_id) values(?,?,?,?,?,?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )){
            ps.setString(1,employeeName);
            ps.setDouble(2,employeeSalary);
            ps.setString(3,employeeEmail);
            ps.setString(4,phone);
            ps.setString(5,status);
            ps.setString(6,designation);
            ps.setInt(7,departmentId);
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

    public boolean updateEmployee(int employeeId, String employeeName,
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
        return row>0;
        }catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return  false;
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
                return null;
            }
        }catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return  null;
        }
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
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return employees;
        }
        return employees;
    }

    public boolean deactivateEmployee(int employeeId) {

        String employeeSql =
                "UPDATE employee_details " +
                        "SET status = 'INACTIVE' " +
                        "WHERE employee_id = ?";

        String userSql =
                "UPDATE users " +
                        "SET account_status = 'INACTIVE' " +
                        "WHERE employee_id = ?";

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            // 1. Deactivate employee
            try (PreparedStatement ps =
                         connection.prepareStatement(employeeSql)) {

                ps.setInt(1, employeeId);

                int employeeRows = ps.executeUpdate();

                if (employeeRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // 2. Deactivate linked user
            try (PreparedStatement ps =
                         connection.prepareStatement(userSql)) {

                ps.setInt(1, employeeId);

                int userRows = ps.executeUpdate();

                if (userRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Both succeeded
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
                    "Employee deactivation failed: "
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

    public boolean activateEmployee(int employeeId) {

        String employeeSql =
                "UPDATE employee_details " +
                        "SET status = 'ACTIVE' " +
                        "WHERE employee_id = ?";

        String userSql =
                "UPDATE users " +
                        "SET account_status = 'ACTIVE' " +
                        "WHERE employee_id = ?";

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            // 1. Activate employee
            try (PreparedStatement ps =
                         connection.prepareStatement(employeeSql)) {

                ps.setInt(1, employeeId);

                int employeeRows = ps.executeUpdate();

                if (employeeRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // 2. Activate linked user
            try (PreparedStatement ps =
                         connection.prepareStatement(userSql)) {

                ps.setInt(1, employeeId);

                int userRows = ps.executeUpdate();

                if (userRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Both succeeded
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
                    "Employee activation failed: "
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

    public boolean permanentlyDeleteEmployee(int employeeId) {

        String deleteAttendance =
                "DELETE FROM attendance WHERE employee_id=?";

        String deleteLeaveRequests =
                "DELETE FROM leave_request WHERE employee_id=?";

        String deleteUser =
                "DELETE FROM users WHERE employee_id=?";

        String deleteEmployee =
                "DELETE FROM employee_details WHERE employee_id=?";

        Connection connection = null;

        try {

            connection = DBConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // 1. Delete attendance
            try (PreparedStatement ps =
                         connection.prepareStatement(deleteAttendance)) {

                ps.setInt(1, employeeId);
                ps.executeUpdate();
            }

            // 2. Delete leave requests
            try (PreparedStatement ps =
                         connection.prepareStatement(deleteLeaveRequests)) {

                ps.setInt(1, employeeId);
                ps.executeUpdate();
            }

            // 3. Delete employee user account
            try (PreparedStatement ps =
                         connection.prepareStatement(deleteUser)) {

                ps.setInt(1, employeeId);
                ps.executeUpdate();
            }

            // 4. Delete employee details
            try (PreparedStatement ps =
                         connection.prepareStatement(deleteEmployee)) {

                ps.setInt(1, employeeId);

                int rows = ps.executeUpdate();

                if (rows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Everything succeeded
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
                    "Permanent employee deletion failed: "
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

}
