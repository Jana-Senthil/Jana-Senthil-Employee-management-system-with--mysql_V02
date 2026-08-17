package org.example.service;

import org.example.dao.DepartmentDAO;
import org.example.dao.EmployeeDAO;
import org.example.model.Employee;
import org.example.validation.ValidationUtil;

import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO= new EmployeeDAO();
    private final DepartmentDAO departmentDAO= new DepartmentDAO();
    public boolean addEmployee(String employeeName, double employeeSalary,
                               String employeeEmail,String phone,
                               String status, String designation,
                               int departmentId) {
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

    public boolean deleteEmployee(int employeeId){
        if(!ValidationUtil.isValidId(employeeId)){
            return false;
        }
        return employeeDAO.deleteEmployee(employeeId);
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
