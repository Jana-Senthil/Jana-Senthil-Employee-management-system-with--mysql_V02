package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.dao.LeaveRequestDAO;
import org.example.dao.ManagerDAO;
import org.example.model.LeaveRequest;
import org.example.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

public class LeaveRequestService {

    private final LeaveRequestDAO leaveRequestDAO = new LeaveRequestDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ManagerDAO managerDAO = new ManagerDAO();

    // Employee applies for leave
    public int addLeaveRequest(int employeeId,
                                   String leaveType,
                                   LocalDate startDate,
                                   LocalDate endDate,
                                   String leaveReason,
                                   LocalDate applyDate) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return 0;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return 0;
        }

        if (!ValidationUtil.isValidName(leaveType)) {
            return 0;
        }

        if (startDate == null || endDate == null || applyDate == null) {
            return 0;
        }

        if (startDate.isAfter(endDate)) {
            return 0;
        }

        if (applyDate.isAfter(startDate)) {
            return 0;
        }

        if (!ValidationUtil.isValidName(leaveReason)) {
            return 0;
        }

        return leaveRequestDAO.addLeaveReqest(
                employeeId,
                leaveType,
                startDate,
                endDate,
                leaveReason,
                applyDate
        );
    }


    // Employee updates a pending leave request
    public boolean updateLeaveRequestByEmployee(
            int leaveId,
            int employeeId,
            String leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String leaveReason,
            LocalDate applyDate) {

        if (!ValidationUtil.isValidId(leaveId)) {
            return false;
        }

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return false;
        }

        if (!ValidationUtil.isValidName(leaveType)) {
            return false;
        }

        if (startDate == null || endDate == null || applyDate == null) {
            return false;
        }

        if (startDate.isAfter(endDate)) {
            return false;
        }

        if (applyDate.isAfter(startDate)) {
            return false;
        }

        if (!ValidationUtil.isValidName(leaveReason)) {
            return false;
        }

        return leaveRequestDAO.updateLeaveRequestByEmployee(
                leaveId,
                employeeId,
                leaveType,
                startDate,
                endDate,
                leaveReason,
                applyDate
        );
    }


    // Manager approves/rejects leave
    public boolean updateLeaveRequestByManager(
            int leaveId,
            int managerId,
            String managerComment,
            String status) {

        if (!ValidationUtil.isValidId(leaveId)) {
            return false;
        }

        if (!ValidationUtil.isValidId(managerId)) {
            return false;
        }

        if (managerDAO.getManagerById(managerId) == null) {
            return false;
        }

        if (!isValidLeaveStatus(status)) {
            return false;
        }

        if (!ValidationUtil.isValidName(managerComment)) {
            return false;
        }

        return leaveRequestDAO.updateLeaveRequestByManager(
                leaveId,
                managerId,
                managerComment,
                status
        );
    }


    // Employee cancels pending leave
    public boolean deleteLeaveRequestByLeaveId(
            int leaveId,
            int employeeId) {

        if (!ValidationUtil.isValidId(leaveId)) {
            return false;
        }

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return false;
        }

        return leaveRequestDAO.deleteLeaveRequestByLeaveId(
                leaveId,
                employeeId
        );
    }


    // Get leave request by ID
    public LeaveRequest getLeaveRequestByLeaveId(int leaveId) {

        if (!ValidationUtil.isValidId(leaveId)) {
            return null;
        }

        return leaveRequestDAO.getLeaveRequestByLeaveId(leaveId);
    }


    // Get employee's leave requests
    public List<LeaveRequest> getLeaveRequestByEmployeeId(
            int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return List.of();
        }

        return leaveRequestDAO.getLeaveRequestByEmployeeId(employeeId);
    }


    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequest() {
        return leaveRequestDAO.getAllLeaveRequest();
    }
    

    private boolean isValidLeaveStatus(String status) {

        return status != null &&
                (status.equalsIgnoreCase("PENDING") ||
                        status.equalsIgnoreCase("APPROVED") ||
                        status.equalsIgnoreCase("REJECTED"));
    }
}