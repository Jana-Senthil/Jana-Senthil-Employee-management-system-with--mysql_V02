package org.example.model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role;
    private Integer employeeId;
    private Integer managerId;

    public User(int userId, String username,
                String password, String role,
                Integer employeeId, Integer managerId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.employeeId = employeeId;
        this.managerId = managerId;
    }

    //set the values
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }


    //get the values
    public int getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public Integer getEmployeeId() {
        return employeeId;
    }
    public Integer getManagerId() {
        return managerId;
    }

    @Override
    public String toString() {
        return "User Details" +
                "\n" + "User Id = " + userId +
                "\n" + "User Name = " + username +
                "\n" + "Role = " + role +
                "\n" + "EmployeeId = " + employeeId +
                "\n" + "ManagerId = " + managerId;
    }
}
