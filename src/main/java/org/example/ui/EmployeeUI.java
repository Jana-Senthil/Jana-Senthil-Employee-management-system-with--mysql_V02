package org.example.ui;

import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.model.LeaveRequest;
import org.example.model.Session;
import org.example.service.AttendanceService;
import org.example.service.EmployeeService;
import org.example.service.LeaveRequestService;
import org.example.service.UserService;
import org.example.util.SessionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class EmployeeUI {

    private final Scanner scanner = new Scanner(System.in);

    private final EmployeeService employeeService =
            new EmployeeService();

    private final AttendanceService attendanceService =
            new AttendanceService();

    private final LeaveRequestService leaveRequestService =
            new LeaveRequestService();

    private final UserService userService =
            new UserService();


    public void showMenu() {

        while (SessionManager.isLoggedIn()
                && SessionManager.isEmployee()) {

            System.out.println("\n===== Employee Menu =====");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Mark Attendance");
            System.out.println("5. View Attendance");
            System.out.println("6. Apply Leave");
            System.out.println("7. Update Leave Request");
            System.out.println("8. Cancel Leave Request");
            System.out.println("9. View Leave History");
            System.out.println("10. Logout");

            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    viewProfile();
                    break;

                case 2:
                    updateProfile();
                    break;

                case 3:
                    changePassword();
                    break;

                case 4:
                    markAttendance();
                    break;

                case 5:
                    viewAttendance();
                    break;

                case 6:
                    applyLeave();
                    break;

                case 7:
                    updateLeaveRequest();
                    break;

                case 8:
                    cancelLeaveRequest();
                    break;

                case 9:
                    viewLeaveHistory();
                    break;

                case 10:
                    SessionManager.logout();
                    System.out.println("Logged out successfully.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // =========================================================
    // 1. VIEW PROFILE
    // =========================================================

    private void viewProfile() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            System.out.println(
                    "Employee account is not linked."
            );
            return;
        }

        Employee employee =
                employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            System.out.println(
                    "Employee profile not found."
            );
            return;
        }

        System.out.println("\n===== My Profile =====");

        System.out.println(
                "User ID       : " + session.getUserId()
        );

        System.out.println(
                "Username      : " + session.getUsername()
        );

        System.out.println(
                "Employee ID   : " + employee.getEmployeeId()
        );

        System.out.println(
                "Name          : " + employee.getEmployeeName()
        );

        System.out.println(
                "Email         : " + employee.getEmployeeEmail()
        );

        System.out.println(
                "Phone         : " + employee.getEmployeePhone()
        );

        System.out.println(
                "Salary        : " + employee.getEmployeeSalary()
        );

        System.out.println(
                "Designation   : " + employee.getDesignation()
        );

        System.out.println(
                "Status        : " + employee.getStatus()
        );

        System.out.println(
                "Department ID : " + employee.getDepartmentId()
        );

        System.out.println(
                "Role          : " + session.getRole()
        );
    }


    // =========================================================
    // 2. UPDATE PROFILE
    // =========================================================

    private void updateProfile() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        int userId =
                session.getUserId();

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            System.out.println(
                    "Employee account is not linked."
            );
            return;
        }

        Employee employee =
                employeeService.getEmployeeById(employeeId);

        if (employee == null) {
            System.out.println(
                    "Employee profile not found."
            );
            return;
        }

        System.out.println("\n===== Update Profile =====");

        System.out.print("Enter new username: ");
        String username = scanner.nextLine();

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        System.out.print("Enter new phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter the name");
        String name = scanner.nextLine();

        // Update users table
        boolean userUpdated =
                userService.updateUserProfile(
                        userId,
                        username,
                        email,
                        phone
                );

        if (!userUpdated) {
            System.out.println(
                    "Failed to update user profile."
            );
            return;
        }


        // Update employee_details table
        boolean employeeUpdated =
                employeeService.updateEmployee(
                        employee.getEmployeeId(),
                        name,
                        employee.getEmployeeSalary(),
                        email,
                        phone,
                        employee.getStatus(),
                        employee.getDesignation(),
                        employee.getDepartmentId()
                );

        if (!employeeUpdated) {

            System.out.println(
                    "User profile updated, but employee profile "
                            + "could not be updated."
            );

            return;
        }


        // Both updates successful
        System.out.println(
                "Profile updated successfully."
        );

        System.out.println(
                "Please login again to refresh your session."
        );

        SessionManager.logout();
    }
    // =========================================================
    // 3. CHANGE PASSWORD
    // =========================================================

    private void changePassword() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        System.out.println("\n===== Change Password =====");

        System.out.print(
                "Enter new password: "
        );

        String password =
                scanner.nextLine();

        System.out.print(
                "Confirm new password: "
        );

        String confirmPassword =
                scanner.nextLine();

        if (!password.equals(confirmPassword)) {

            System.out.println(
                    "Passwords do not match."
            );

            return;
        }

        boolean result =
                userService.updatePassword(
                        session.getUserId(),
                        password
                );

        if (result) {
            System.out.println(
                    "Password changed successfully."
            );
        } else {
            System.out.println(
                    "Failed to change password."
            );
        }
    }


    // =========================================================
    // 4. MARK ATTENDANCE
    // =========================================================

    private void markAttendance() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            System.out.println(
                    "Employee account is not linked."
            );
            return;
        }

        System.out.println("\n===== Attendance =====");
        System.out.println("1. Check In");
        System.out.println("2. Check Out");
        System.out.println("3. Back");

        System.out.print("Enter choice: ");

        int choice =
                scanner.nextInt();

        scanner.nextLine();

        switch (choice) {

            case 1:
                checkIn(employeeId);
                break;

            case 2:
                checkOut(employeeId);
                break;

            case 3:
                return;

            default:
                System.out.println(
                        "Invalid choice."
                );
        }
    }


    // =========================================================
    // CHECK IN
    // =========================================================

    private void checkIn(int employeeId) {

        LocalDate date =
                LocalDate.now();

        LocalTime checkIn =
                LocalTime.now();

        System.out.print(
                "Enter session number: "
        );

        int sessionNo =
                scanner.nextInt();

        scanner.nextLine();

        System.out.print(
                "Enter mode (OFFICE/WFH): "
        );

        String mode =
                scanner.nextLine()
                        .toUpperCase();

        boolean result =
                attendanceService.addAttendance(
                        date,
                        sessionNo,
                        checkIn,
                        mode,
                        employeeId
                );

        if (result) {

            System.out.println(
                    "Attendance marked successfully."
            );

            System.out.println(
                    "Session No : " + sessionNo
            );

            System.out.println(
                    "Date      : " + date
            );

            System.out.println(
                    "Check In  : " + checkIn
            );

            System.out.println(
                    "Mode      : " + mode
            );

        } else {

            System.out.println(
                    "Failed to mark attendance."
            );
        }
    }


    // =========================================================
    // CHECK OUT
    // =========================================================

    private void checkOut(int employeeId) {

        LocalDate date =
                LocalDate.now();

        LocalTime checkOut =
                LocalTime.now();

        System.out.print(
                "Enter session number: "
        );

        int sessionNo =
                scanner.nextInt();

        scanner.nextLine();

        boolean result =
                attendanceService.updateAttendance(
                        date,
                        sessionNo,
                        checkOut,
                        employeeId
                );

        if (result) {

            System.out.println(
                    "Check-out recorded successfully."
            );

            System.out.println(
                    "Check Out : " + checkOut
            );

        } else {

            System.out.println(
                    "Failed to record check-out."
            );
        }
    }


    // =========================================================
    // 5. VIEW ATTENDANCE
    // =========================================================

    private void viewAttendance() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            return;
        }

        List<Attendance> attendanceList =
                attendanceService.getAttendanceByEmployeeId(
                        employeeId
                );

        if (attendanceList == null ||
                attendanceList.isEmpty()) {

            System.out.println(
                    "No attendance records found."
            );

            return;
        }

        System.out.println(
                "\n===== Attendance History ====="
        );

        for (Attendance attendance :
                attendanceList) {

            System.out.println(
                    "--------------------------------"
            );

            System.out.println(
                    "Date       : "
                            + attendance.getAttendanceDate()
            );

            System.out.println(
                    "Session No : "
                            + attendance.getSessionNo()
            );

            System.out.println(
                    "Check In   : "
                            + attendance.getCheckIn()
            );

            System.out.println(
                    "Check Out  : "
                            + attendance.getCheckOut()
            );

            System.out.println(
                    "Mode       : "
                            + attendance.getMode()
            );
        }

        System.out.println(
                "--------------------------------"
        );
    }


    // =========================================================
    // 6. APPLY LEAVE
    // =========================================================

    private void applyLeave() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            return;
        }

        System.out.println("\n===== Apply Leave =====");

        String leaveType =
                getLeaveType();

        if (leaveType == null) {
            return;
        }

        System.out.print(
                "Enter start date (YYYY-MM-DD): "
        );

        LocalDate startDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Enter end date (YYYY-MM-DD): "
        );

        LocalDate endDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Enter reason: "
        );

        String reason =
                scanner.nextLine();

        LocalDate appliedDate =
                LocalDate.now();

        int leaveRequestId =
                leaveRequestService.addLeaveRequest(
                        employeeId,
                        leaveType,
                        startDate,
                        endDate,
                        reason,
                        appliedDate
                );

        if (leaveRequestId > 0) {


            System.out.println(
                    "Leave request submitted successfully.\n" +
                            "Your Leave Request no is " + leaveRequestId
            );

        } else {

            System.out.println(
                    "Failed to submit leave request."
            );
        }
    }


    // =========================================================
    // LEAVE TYPE
    // =========================================================

    private String getLeaveType() {

        System.out.println(
                "1. CASUAL"
        );

        System.out.println(
                "2. SICK"
        );

        System.out.println(
                "3. EARNED"
        );

        System.out.println(
                "4. EMERGENCY"
        );

        System.out.println(
                "5. Back"
        );

        System.out.print(
                "Enter leave type: "
        );

        int choice =
                scanner.nextInt();

        scanner.nextLine();

        return switch (choice) {
            case 1 -> "CASUAL";
            case 2 -> "SICK";
            case 3 -> "EARNED";
            case 4 -> "EMERGENCY";
            case 5 -> null;
            default -> {
                System.out.println(
                        "Invalid leave type."
                );

                yield null;
            }
        };
    }


    // =========================================================
    // 7. UPDATE LEAVE REQUEST
    // =========================================================

    private void updateLeaveRequest() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            return;
        }

        System.out.println(
                "\n===== Update Leave Request ====="
        );

        System.out.print(
                "Enter leave ID: "
        );

        int leaveId =
                scanner.nextInt();

        scanner.nextLine();

        LeaveRequest leaveRequest =
                leaveRequestService
                        .getLeaveRequestByLeaveId(
                                leaveId
                        );

        if (leaveRequest == null) {

            System.out.println(
                    "Leave request not found."
            );

            return;
        }

        if (leaveRequest.getEmployeeId()
                != employeeId) {

            System.out.println(
                    "You cannot update another employee's leave."
            );

            return;
        }

        if (!leaveRequest.getStatus()
                .equalsIgnoreCase("PENDING")) {

            System.out.println(
                    "Only pending leave requests can be updated."
            );

            return;
        }

        String leaveType =
                getLeaveType();

        if (leaveType == null) {
            return;
        }

        System.out.print(
                "Enter new start date (YYYY-MM-DD): "
        );

        LocalDate startDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Enter new end date (YYYY-MM-DD): "
        );

        LocalDate endDate =
                LocalDate.parse(
                        scanner.nextLine()
                );

        System.out.print(
                "Enter new reason: "
        );

        String reason =
                scanner.nextLine();

        LocalDate appliedDate =
                LocalDate.now();

        boolean result =
                leaveRequestService
                        .updateLeaveRequestByEmployee(
                                leaveId,
                                employeeId,
                                leaveType,
                                startDate,
                                endDate,
                                reason,
                                appliedDate
                        );

        if (result) {

            System.out.println(
                    "Leave request updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update leave request."
            );
        }
    }


    // =========================================================
    // 8. CANCEL LEAVE REQUEST
    // =========================================================

    private void cancelLeaveRequest() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            return;
        }

        System.out.println(
                "\n===== Cancel Leave Request ====="
        );

        System.out.print(
                "Enter leave ID: "
        );

        int leaveId =
                scanner.nextInt();

        scanner.nextLine();

        LeaveRequest leaveRequest =
                leaveRequestService
                        .getLeaveRequestByLeaveId(
                                leaveId
                        );

        if (leaveRequest == null) {

            System.out.println(
                    "Leave request not found."
            );

            return;
        }

        if (leaveRequest.getEmployeeId()
                != employeeId) {

            System.out.println(
                    "You cannot cancel another employee's leave."
            );

            return;
        }

        if (!leaveRequest.getStatus()
                .equalsIgnoreCase("PENDING")) {

            System.out.println(
                    "Only pending leave requests can be cancelled."
            );

            return;
        }

        System.out.print(
                "Are you sure? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Cancellation cancelled."
            );

            return;
        }

        boolean result =
                leaveRequestService
                        .deleteLeaveRequestByLeaveId(
                                leaveId,
                                employeeId
                        );

        if (result) {

            System.out.println(
                    "Leave request cancelled successfully."
            );

        } else {

            System.out.println(
                    "Failed to cancel leave request."
            );
        }
    }


    // =========================================================
    // 9. VIEW LEAVE HISTORY
    // =========================================================

    private void viewLeaveHistory() {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            return;
        }

        Integer employeeId =
                session.getEmployeeId();

        if (employeeId == null) {
            return;
        }

        List<LeaveRequest> leaveRequests =
                leaveRequestService
                        .getLeaveRequestByEmployeeId(
                                employeeId
                        );

        if (leaveRequests == null ||
                leaveRequests.isEmpty()) {

            System.out.println(
                    "No leave requests found."
            );

            return;
        }

        System.out.println(
                "\n===== Leave History ====="
        );

        for (LeaveRequest leave :
                leaveRequests) {

            System.out.println(
                    "--------------------------------"
            );

            System.out.println(
                    "Leave ID       : "
                            + leave.getLeaveId()
            );

            System.out.println(
                    "Leave Type     : "
                            + leave.getLeaveType()
            );

            System.out.println(
                    "Start Date     : "
                            + leave.getStartDate()
            );

            System.out.println(
                    "End Date       : "
                            + leave.getEndDate()
            );

            System.out.println(
                    "Reason         : "
                            + leave.getReason()
            );

            System.out.println(
                    "Applied Date   : "
                            + leave.getAppliedDate()
            );

            System.out.println(
                    "Status         : "
                            + leave.getStatus()
            );

            System.out.println(
                    "Manager ID     : "
                            + leave.getManagerId()
            );

            System.out.println(
                    "Manager Comment: "
                            + leave.getManagerComment()
            );
        }

        System.out.println(
                "--------------------------------"
        );
    }
}