package org.example.model;

public class Employee {
    private int employeeId;
    private String employeeName;
    private double employeeSalary;
    private String employeeEmail;
    private String employeePhone;
    private String status;
    private String designation;
    private int departmentId;

    public Employee(int employeeID, String employeeName,
                    double employeeSalary, String employeeEmail,
                    String employeePhone, String status,
                    String designation, int departmentID) {
        this.employeeId = employeeID;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
        this.status = status;
        this.designation = designation;
        this.departmentId = departmentID;
    }


    //get the values
    public int getEmployeeId() {
        return employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public double getEmployeeSalary() {
        return employeeSalary;
    }
    public String getEmployeeEmail() {
        return employeeEmail;
    }
    public String getEmployeePhone() {
        return employeePhone;
    }
    public String getStatus() {
        return status;
    }
    public String getDesignation() {
        return designation;
    }
    public int getDepartmentId() {
        return departmentId;
    }

    //set the values
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public void setDepartmentId(int departmentID) {
        this.departmentId = departmentID;
    }

    @Override
    public String toString() {
        return "Employee Details\n" +
                " Employee Id = " + employeeId +
                " \nEmployeeName = " + employeeName +
                " \nEmployeeSalary = " + employeeSalary +
                " \nEmployeeEmail = " + employeeEmail +
                " \nEmployeePhone = " + employeePhone +
                " \nStatus = " + status +
                " \nDesignation = " + designation +
                " \nDepartmentID = " + departmentId;
    }

}
