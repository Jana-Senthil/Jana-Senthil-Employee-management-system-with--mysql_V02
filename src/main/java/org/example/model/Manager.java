package org.example.model;

public class Manager {
    private int managerId;
    private String managerName;
    private String managerEmail;
    private String managerPhone;

    public Manager(int managerId, String managerName,
                   String managerEmail, String managerPhone) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
        this.managerPhone = managerPhone;
    }

    //get the values
    public int getManagerId() {
        return managerId;
    }
    public String getManagerName() {
        return managerName;
    }
    public String getManagerEmail() {
        return managerEmail;
    }
    public String getManagerPhone() {
        return managerPhone;
    }

    //set the values
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }
    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    @Override
    public String toString() {
        return "Manager Details\n" +
                " Manager Id = " +
                managerId +
                " \nManager Name = " +
                managerName +
                " \nManager Email = " +
                managerEmail +
                " \nManager Phone = " +
                managerPhone;
    }
}
