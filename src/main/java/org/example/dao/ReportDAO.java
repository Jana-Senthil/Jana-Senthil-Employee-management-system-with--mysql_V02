package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public int getTotalEmployees() {

        String sql = "SELECT COUNT(*) FROM employee_details";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error while getting employee count: "
                            + e.getMessage()
            );
        }

        return 0;
    }


    public Employee getHighestSalaryEmployee() {

        String sql =
                "SELECT * FROM employee_details " +
                        "ORDER BY employee_salary DESC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return mapEmployee(rs);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error while getting highest salary employee: "
                            + e.getMessage()
            );
        }

        return null;
    }


    public Employee getLowestSalaryEmployee() {

        String sql =
                "SELECT * FROM employee_details " +
                        "ORDER BY employee_salary ASC LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return mapEmployee(rs);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error while getting lowest salary employee: "
                            + e.getMessage()
            );
        }

        return null;
    }


    public double getAverageSalary() {

        String sql =
                "SELECT AVG(employee_salary) " +
                        "FROM employee_details";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error while getting average salary: "
                            + e.getMessage()
            );
        }

        return 0;
    }


    public List<Employee> getEmployeesByDepartment(
            int departmentId) {

        List<Employee> employees =
                new ArrayList<>();

        String sql =
                "SELECT * FROM employee_details " +
                        "WHERE department_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, departmentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    employees.add(mapEmployee(rs));
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error while getting employees by department: "
                            + e.getMessage()
            );
        }

        return employees;
    }


    private Employee mapEmployee(ResultSet rs)
            throws Exception {

        int employeeId =
                rs.getInt("employee_id");

        String employeeName =
                rs.getString("employee_name");

        double employeeSalary =
                rs.getDouble("employee_salary");

        String employeeEmail =
                rs.getString("employee_email");

        String employeePhone =
                rs.getString("employee_phone");

        String status =
                rs.getString("status");

        String designation =
                rs.getString("designation");

        int departmentId =
                rs.getInt("department_id");

        return new Employee(
                employeeId,
                employeeName,
                employeeSalary,
                employeeEmail,
                employeePhone,
                status,
                designation,
                departmentId
        );
    }
}