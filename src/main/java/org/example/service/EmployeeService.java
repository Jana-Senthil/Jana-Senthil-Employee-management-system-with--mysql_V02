package org.example.service;

import org.example.dao.DepartmentDAO;
import org.example.dao.EmployeeDAO;
import org.example.model.Employee;
import org.example.model.User;
import org.example.validation.ValidationUtil;

import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final UserService userService = new UserService();
    public int addEmployee(String employeeName, double employeeSalary,
                               String employeeEmail,String phone,
                               String status, String designation,
                               int departmentId) {
        if(!ValidationUtil.isValidName(employeeName)){
            return 0;
        }
        if(departmentDAO.getDepartmentById(departmentId)==null){
            return 0;
        }
        if(!ValidationUtil.isValidSalary(employeeSalary)){
            return 0;
        }
        if(!ValidationUtil.isValidEmail(employeeEmail)){
            return 0;
        }
        if(!ValidationUtil.isValidPhone(phone)){
            return 0;
        }
        if(!ValidationUtil.isValidEmployeeStatus(status)){
            return 0;
        }
        if(!ValidationUtil.isValidName(designation)){
            return 0;
        }
        if(!ValidationUtil.isValidId(departmentId)){
            return 0;
        }
        return employeeDAO.addEmployee(employeeName, employeeSalary, employeeEmail, phone, status, designation, departmentId);
    }

    public boolean updateEmployee(int employeeId, String employeeName, double employeeSalary,
                                  String employeeEmail,String phone,
                                  String status, String designation,
                                  int departmentId){
        if(!ValidationUtil.isValidName(employeeName)){
            return false;
        }
        if(departmentDAO.getDepartmentById(departmentId)==null){
            return false;
        }
        if(!ValidationUtil.isValidSalary(employeeSalary)){
            return false;
        }
        if(!ValidationUtil.isValidEmail(employeeEmail)){
            return false;
        }
        if(!ValidationUtil.isValidPhone(phone)){
            return false;
        }
        if(!ValidationUtil.isValidEmployeeStatus(status)){
            return false;
        }
        if(!ValidationUtil.isValidName(designation)){
            return false;
        }
        if(!ValidationUtil.isValidId(departmentId)){
            return false;
        }
        if(!ValidationUtil.isValidId(employeeId)){
            return false;
        }
        return employeeDAO.updateEmployee(employeeId, employeeName, employeeSalary, employeeEmail, phone, status, designation, departmentId);
    }

    public boolean deactivateEmployee(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return false;
        }

        return employeeDAO.deactivateEmployee(employeeId);
    }
    // Activate employee
    public boolean activateEmployee(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return false;
        }

        return employeeDAO.activateEmployee(employeeId);
    }

    public boolean permanentlyDeleteEmployee(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        Employee employee =
                employeeDAO.getEmployeeById(employeeId);

        if (employee == null) {
            return false;
        }

        // Employee must be INACTIVE
        if (!"INACTIVE".equalsIgnoreCase(
                employee.getStatus())) {

            return false;
        }

        // Find the employee's user account
        User user =
                userService.getUserByEmployeeId(employeeId);

        if (user == null) {
            return false;
        }

        // User account must also be INACTIVE
        if (!"INACTIVE".equalsIgnoreCase(
                user.getAccountStatus())) {

            return false;
        }

        return employeeDAO.permanentlyDeleteEmployee(employeeId);
    }

    public Employee getEmployeeById(int employeeId){
        if(!ValidationUtil.isValidId(employeeId)){
            return null;
        }
        return employeeDAO.getEmployeeById(employeeId);
    }

    public List<Employee> getAllEmployees(){
        return employeeDAO.getAllEmployees();
    }

}
