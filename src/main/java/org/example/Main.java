package org.example;

import org.example.config.DBConnection;
import org.example.dao.*;
import org.example.model.*;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        // Add
        departmentDAO.addDepartment("IT", "Chennai");
        departmentDAO.addDepartment("IT", "Chenn");
        // View by ID
        Department department = departmentDAO.getDepartmentById(1);

        if (department != null) {
            System.out.println(department);
        } else {
            System.out.println("Department not found");
        }

        // View all
        List<Department> departments = departmentDAO.getAllDepartments();

        for (Department d : departments) {
            System.out.println(d);
        }
    }
}