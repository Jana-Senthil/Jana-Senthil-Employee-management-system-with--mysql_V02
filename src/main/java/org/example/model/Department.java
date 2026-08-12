package org.example.model;

public class Department {
    private int departmentId;
    private String departmentName;
    private String location;

//    constructor
    public Department(int departmentId, String departmentName, String location) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
    }

    //get the values
    public int getDepartmentId() {
        return departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public String getLocation() {
        return location;
    }

    //set the values
    public void setLocation(String location) {
        this.location = location;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department \n" + "DepartmentId=" + departmentId + ",\n departmentName=" + departmentName + ",\n location=" + location;
    }
}
