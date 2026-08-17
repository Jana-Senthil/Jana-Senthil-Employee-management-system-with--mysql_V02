package org.example.model;

public class Session {

    private final User user;

    public Session(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return user.getUserId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getRole() {
        return user.getRole();
    }

    public Integer getEmployeeId() {
        return user.getEmployeeId();
    }

    public Integer getManagerId() {
        return user.getManagerId();
    }
}