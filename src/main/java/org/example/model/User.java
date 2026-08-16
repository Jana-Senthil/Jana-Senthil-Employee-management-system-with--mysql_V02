package org.example.model;

public class User {
    private int userId;
    private String username;
    private String email;
    private String phone;
    private String passwordHash;
    private String role;
    private Integer employeeId;
    private Integer managerId;

    public User(int userId, String username,
                String email, String phone,
                String passwordHash, String role,
                Integer employeeId, Integer managerId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.role = role;
        this.employeeId = employeeId;
        this.managerId = managerId;
    }

    //set the values
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getPasswordHash() {
        return passwordHash;
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
