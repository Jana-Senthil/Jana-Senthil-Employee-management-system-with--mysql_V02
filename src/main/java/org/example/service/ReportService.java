package org.example.service;

import org.example.dao.ReportDAO;
import org.example.model.Employee;
import org.example.validation.ValidationUtil;

import java.util.List;

public class ReportService {

    private final ReportDAO reportDAO =
            new ReportDAO();


    public int getTotalEmployees() {

        return reportDAO.getTotalEmployees();
    }


    public Employee getHighestSalaryEmployee() {

        return reportDAO.getHighestSalaryEmployee();
    }


    public Employee getLowestSalaryEmployee() {

        return reportDAO.getLowestSalaryEmployee();
    }


    public double getAverageSalary() {

        return reportDAO.getAverageSalary();
    }


    public List<Employee> getEmployeesByDepartment(
            int departmentId) {

        if (!ValidationUtil.isValidId(
                departmentId)) {

            return List.of();
        }

        return reportDAO.getEmployeesByDepartment(
                departmentId
        );
    }
}