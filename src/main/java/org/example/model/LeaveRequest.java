package org.example.model;
import java.time.LocalDate;

public class LeaveRequest {
    private int leaveId;
    private int employeeId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LocalDate appliedDate;
    private String status;
    private int managerId;
    private String managerComment;

    public LeaveRequest(int leaveId, int employeeId,
                        String leaveType, LocalDate startDate,
                        LocalDate endDate, String reason,
                        LocalDate appliedDate, String status,
                        int managerId, String managerComment) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.appliedDate = appliedDate;
        this.status = status;
        this.managerId = managerId;
        this.managerComment = managerComment;
    }

    //get the values
    public int getLeaveId() {
        return leaveId;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public String getLeaveType() {
        return leaveType;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public String getReason() {
        return reason;
    }
    public LocalDate getAppliedDate() {
        return appliedDate;
    }
    public String getStatus() {
        return status;
    }
    public int getManagerId() {
        return managerId;
    }
    public String getManagerComment() {
        return managerComment;
    }

    //get the values
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    @Override
    public String toString() {
        return "Leave Request\n" + "EmployeeId: " + employeeId +
                "\n" + "Leave Type: " + leaveType +
                "\n" + "StartDate: " + startDate +
                "\n" + "EndDate: " + endDate +
                "\n" + "Reason: " + reason +
                "\n" + "AppliedDate: " + appliedDate +
                "\n" + "Status: " + status +
                "\n" + "ManagerId: " + managerId +
                "\n" + "ManagerComment: " + managerComment + "\n";
    }
}
