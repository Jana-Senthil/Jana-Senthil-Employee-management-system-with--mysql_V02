package org.example.ui;

import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.model.LeaveRequest;
import org.example.model.Manager;
import org.example.model.Session;
import org.example.service.AttendanceService;
import org.example.service.EmployeeService;
import org.example.service.LeaveRequestService;
import org.example.service.ManagerService;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.example.util.SessionManager;

//import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ManagerUI {

    private final Scanner scanner = new Scanner(System.in);

    private final ManagerService managerService =
            new ManagerService();

    private final EmployeeService employeeService =
            new EmployeeService();

    private final AttendanceService attendanceService =
            new AttendanceService();

    private final LeaveRequestService leaveRequestService =
            new LeaveRequestService();

    private final UserService userService =
            new UserService();

    private final ReportService reportService =
            new ReportService();


    // =========================================================
    // MAIN MENU
    // =========================================================

    public void showMenu() {

        while (SessionManager.isLoggedIn()
                && SessionManager.isManager()) {

            System.out.println("\n===== Manager Menu =====");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. View Employees");
            System.out.println("5. Manage Employees");
            System.out.println("6. View Attendance");
            System.out.println("7. View Leave Requests");
            System.out.println("8. Approve Leave");
            System.out.println("9. Reject Leave");
            System.out.println("10. Reports");
            System.out.println("11. Logout");

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
                    viewEmployees();
                    break;

                case 5:
                    manageEmployees();
                    break;

                case 6:
                    viewAttendance();
                    break;

                case 7:
                    viewLeaveRequests();
                    break;

                case 8:
                    approveLeave();
                    break;

                case 9:
                    rejectLeave();
                    break;

                case 10:
                    showReports();
                    break;

                case 11:
                    SessionManager.logout();
                    System.out.println(
                            "Logged out successfully."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


    // =========================================================
    // 1. VIEW PROFILE
    // =========================================================

    private void viewProfile() {

        System.out.println(
                "\n===== Manager Profile ====="
        );

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            System.out.println(
                    "Session not found."
            );
            return;
        }

        Integer managerId =
                session.getManagerId();

        if (managerId == null) {
            System.out.println(
                    "Manager account is not linked."
            );
            return;
        }

        Manager manager =
                managerService.getManagerById(
                        managerId
                );

        if (manager == null) {
            System.out.println(
                    "Manager profile not found."
            );
            return;
        }

        System.out.println(
                "User ID     : "
                        + session.getUserId()
        );

        System.out.println(
                "Username    : "
                        + session.getUsername()
        );

        System.out.println(
                "Manager ID  : "
                        + manager.getManagerId()
        );

        System.out.println(
                "Name        : "
                        + manager.getManagerName()
        );

        System.out.println(
                "Email       : "
                        + manager.getManagerEmail()
        );

        System.out.println(
                "Phone       : "
                        + manager.getManagerPhone()
        );

        System.out.println(
                "Role        : "
                        + session.getRole()
        );
    }


    // =========================================================
    // 2. UPDATE PROFILE
    // =========================================================

    private void updateProfile() {

        System.out.println(
                "\n===== Update Profile ====="
        );

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            System.out.println(
                    "Session not found."
            );
            return;
        }

        Integer managerId =
                session.getManagerId();

        if (managerId == null) {
            System.out.println(
                    "Manager account is not linked."
            );
            return;
        }

        Manager manager =
                managerService.getManagerById(
                        managerId
                );

        if (manager == null) {
            System.out.println(
                    "Manager profile not found."
            );
            return;
        }

        System.out.print(
                "Enter new name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Enter new email: "
        );

        String email =
                scanner.nextLine();

        System.out.print(
                "Enter new phone: "
        );

        String phone =
                scanner.nextLine();


        boolean managerUpdated =
                managerService.updateManager(
                        managerId,
                        name,
                        email,
                        phone
                );

        if (!managerUpdated) {
            System.out.println(
                    "Failed to update manager profile."
            );
            return;
        }


        /*
         * The users table also stores email and phone.
         * Keep the manager's user account synchronized.
         */

        boolean userUpdated =
                userService.updateUserProfile(
                        session.getUserId(),
                        session.getUsername(),
                        email,
                        phone
                );

        if (userUpdated) {

            System.out.println(
                    "Profile updated successfully."
            );

        } else {

            System.out.println(
                    "Manager profile updated, but "
                            + "user account could not be updated."
            );
        }
    }


    // =========================================================
    // 3. CHANGE PASSWORD
    // =========================================================

    private void changePassword() {

        System.out.println(
                "\n===== Change Password ====="
        );

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {
            System.out.println(
                    "Session not found."
            );
            return;
        }

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
    // 4. VIEW EMPLOYEES
    // =========================================================

    private void viewEmployees() {

        System.out.println(
                "\n===== All Employees ====="
        );

        List<Employee> employees =
                employeeService.getAllEmployees();

        if (employees == null ||
                employees.isEmpty()) {

            System.out.println(
                    "No employees found."
            );

            return;
        }

        for (Employee employee : employees) {

            displayEmployee(employee);

            System.out.println(
                    "--------------------------------"
            );
        }
    }


    // =========================================================
    // 5. MANAGE EMPLOYEES
    // =========================================================

    private void manageEmployees() {

        boolean running = true;

        while (running) {

            System.out.println(
                    "\n===== Manage Employees ====="
            );

            System.out.println(
                    "1. Add Employee"
            );

            System.out.println(
                    "2. Update Employee"
            );

            System.out.println(
                    "3. Delete Employee"
            );

            System.out.println(
                    "4. Search Employee"
            );

            System.out.println(
                    "5. View All Employees"
            );

            System.out.println(
                    "6. Back"
            );

            System.out.print(
                    "Enter choice: "
            );

            int choice =
                    scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    updateEmployee();
                    break;

                case 3:
                    deleteEmployee();
                    break;

                case 4:
                    searchEmployee();
                    break;

                case 5:
                    viewEmployees();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


    // =========================================================
    // ADD EMPLOYEE
    // =========================================================

    private void addEmployee() {

        System.out.println(
                "\n===== Add Employee ====="
        );

        System.out.print(
                "Enter employee name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Enter salary: "
        );

        double salary =
                scanner.nextDouble();

        scanner.nextLine();

        System.out.print(
                "Enter email: "
        );

        String email =
                scanner.nextLine();

        System.out.print(
                "Enter phone: "
        );

        String phone =
                scanner.nextLine();

        System.out.print(
                "Enter status (ACTIVE/INACTIVE/RESIGNED): "
        );

        String status =
                scanner.nextLine()
                        .toUpperCase();

        System.out.print(
                "Enter designation: "
        );

        String designation =
                scanner.nextLine();

        System.out.print(
                "Enter department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();


        boolean result =
                employeeService.addEmployee(
                        name,
                        salary,
                        email,
                        phone,
                        status,
                        designation,
                        departmentId
                );

        if (result) {

            System.out.println(
                    "Employee added successfully."
            );

        } else {

            System.out.println(
                    "Failed to add employee."
            );
        }
    }


    // =========================================================
    // UPDATE EMPLOYEE
    // =========================================================

    private void updateEmployee() {

        System.out.println(
                "\n===== Update Employee ====="
        );

        System.out.print(
                "Enter employee ID: "
        );

        int employeeId =
                scanner.nextInt();

        scanner.nextLine();

        Employee employee =
                employeeService.getEmployeeById(
                        employeeId
                );

        if (employee == null) {

            System.out.println(
                    "Employee not found."
            );

            return;
        }

        System.out.print(
                "Enter new employee name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Enter new salary: "
        );

        double salary =
                scanner.nextDouble();

        scanner.nextLine();

        System.out.print(
                "Enter new email: "
        );

        String email =
                scanner.nextLine();

        System.out.print(
                "Enter new phone: "
        );

        String phone =
                scanner.nextLine();

        System.out.print(
                "Enter new status "
                        + "(ACTIVE/INACTIVE/RESIGNED): "
        );

        String status =
                scanner.nextLine()
                        .toUpperCase();

        System.out.print(
                "Enter new designation: "
        );

        String designation =
                scanner.nextLine();

        System.out.print(
                "Enter new department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();


        boolean result =
                employeeService.updateEmployee(
                        employeeId,
                        name,
                        salary,
                        email,
                        phone,
                        status,
                        designation,
                        departmentId
                );

        if (result) {

            System.out.println(
                    "Employee updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update employee."
            );
        }
    }


    // =========================================================
    // DELETE EMPLOYEE
    // =========================================================

    private void deleteEmployee() {

        System.out.println(
                "\n===== Delete Employee ====="
        );

        System.out.print(
                "Enter employee ID: "
        );

        int employeeId =
                scanner.nextInt();

        scanner.nextLine();

        Employee employee =
                employeeService.getEmployeeById(
                        employeeId
                );

        if (employee == null) {

            System.out.println(
                    "Employee not found."
            );

            return;
        }

        displayEmployee(employee);

        System.out.print(
                "\nAre you sure you want to delete "
                        + "this employee? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Delete operation cancelled."
            );

            return;
        }

        boolean result =
                employeeService.deleteEmployee(
                        employeeId
                );

        if (result) {

            System.out.println(
                    "Employee deleted successfully."
            );

        } else {

            System.out.println(
                    "Failed to delete employee."
            );
        }
    }


    // =========================================================
    // SEARCH EMPLOYEE
    // =========================================================

    private void searchEmployee() {

        System.out.println(
                "\n===== Search Employee ====="
        );

        System.out.print(
                "Enter employee ID: "
        );

        int employeeId =
                scanner.nextInt();

        scanner.nextLine();

        Employee employee =
                employeeService.getEmployeeById(
                        employeeId
                );

        if (employee == null) {

            System.out.println(
                    "Employee not found."
            );

            return;
        }

        displayEmployee(employee);
    }


    // =========================================================
    // DISPLAY EMPLOYEE
    // =========================================================

    private void displayEmployee(
            Employee employee) {

        System.out.println(
                "Employee ID   : "
                        + employee.getEmployeeId()
        );

        System.out.println(
                "Name          : "
                        + employee.getEmployeeName()
        );

        System.out.println(
                "Salary        : "
                        + employee.getEmployeeSalary()
        );

        System.out.println(
                "Email         : "
                        + employee.getEmployeeEmail()
        );

        System.out.println(
                "Phone         : "
                        + employee.getEmployeePhone()
        );

        System.out.println(
                "Status        : "
                        + employee.getStatus()
        );

        System.out.println(
                "Designation   : "
                        + employee.getDesignation()
        );

        System.out.println(
                "Department ID : "
                        + employee.getDepartmentId()
        );
    }


    // =========================================================
    // 6. VIEW ATTENDANCE
    // =========================================================

    private void viewAttendance() {

        System.out.println(
                "\n===== All Attendance ====="
        );

        List<Attendance> attendanceList =
                attendanceService.getAllAttendance();

        if (attendanceList == null ||
                attendanceList.isEmpty()) {

            System.out.println(
                    "No attendance records found."
            );

            return;
        }

        for (Attendance attendance :
                attendanceList) {

            System.out.println(
                    "--------------------------------"
            );

            System.out.println(
                    "Employee ID : "
                            + attendance.getEmployeeId()
            );

            System.out.println(
                    "Date        : "
                            + attendance.getAttendanceDate()
            );

            System.out.println(
                    "Session No  : "
                            + attendance.getSessionNo()
            );

            System.out.println(
                    "Check In    : "
                            + attendance.getCheckIn()
            );

            System.out.println(
                    "Check Out   : "
                            + attendance.getCheckOut()
            );

            System.out.println(
                    "Mode        : "
                            + attendance.getMode()
            );
        }

        System.out.println(
                "--------------------------------"
        );
    }


    // =========================================================
    // 7. VIEW LEAVE REQUESTS
    // =========================================================

    private void viewLeaveRequests() {

        System.out.println(
                "\n===== All Leave Requests ====="
        );

        List<LeaveRequest> leaveRequests =
                leaveRequestService
                        .getAllLeaveRequest();

        if (leaveRequests == null ||
                leaveRequests.isEmpty()) {

            System.out.println(
                    "No leave requests found."
            );

            return;
        }

        for (LeaveRequest leave :
                leaveRequests) {

            displayLeaveRequest(leave);

            System.out.println(
                    "--------------------------------"
            );
        }
    }


    // =========================================================
    // DISPLAY LEAVE REQUEST
    // =========================================================

    private void displayLeaveRequest(
            LeaveRequest leave) {

        System.out.println(
                "Leave ID        : "
                        + leave.getLeaveId()
        );

        System.out.println(
                "Employee ID     : "
                        + leave.getEmployeeId()
        );

        System.out.println(
                "Leave Type      : "
                        + leave.getLeaveType()
        );

        System.out.println(
                "Start Date      : "
                        + leave.getStartDate()
        );

        System.out.println(
                "End Date        : "
                        + leave.getEndDate()
        );

        System.out.println(
                "Reason          : "
                        + leave.getReason()
        );

        System.out.println(
                "Applied Date    : "
                        + leave.getAppliedDate()
        );

        System.out.println(
                "Status          : "
                        + leave.getStatus()
        );

        System.out.println(
                "Manager ID      : "
                        + leave.getManagerId()
        );

        System.out.println(
                "Manager Comment : "
                        + leave.getManagerComment()
        );
    }


    // =========================================================
    // 8. APPROVE LEAVE
    // =========================================================

    private void approveLeave() {

        processLeave("APPROVED");
    }


    // =========================================================
    // 9. REJECT LEAVE
    // =========================================================

    private void rejectLeave() {

        processLeave("REJECTED");
    }


    // =========================================================
    // PROCESS LEAVE
    // =========================================================

    private void processLeave(
            String status) {

        Session session =
                SessionManager.getCurrentSession();

        if (session == null) {

            System.out.println(
                    "Session not found."
            );

            return;
        }

        Integer managerId =
                session.getManagerId();

        if (managerId == null) {

            System.out.println(
                    "Manager account is not linked."
            );

            return;
        }

        System.out.println(
                "\n===== "
                        + status
                        + " LEAVE ====="
        );

        System.out.print(
                "Enter leave ID: "
        );

        int leaveId =
                scanner.nextInt();

        scanner.nextLine();

        LeaveRequest leave =
                leaveRequestService
                        .getLeaveRequestByLeaveId(
                                leaveId
                        );

        if (leave == null) {

            System.out.println(
                    "Leave request not found."
            );

            return;
        }

        displayLeaveRequest(leave);

        if (!leave.getStatus()
                .equalsIgnoreCase("PENDING")) {

            System.out.println(
                    "Only PENDING leave requests "
                            + "can be processed."
            );

            return;
        }

        System.out.print(
                "Enter manager comment: "
        );

        String comment =
                scanner.nextLine();

        System.out.print(
                "Are you sure you want to "
                        + status.toLowerCase()
                        + " this leave? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Operation cancelled."
            );

            return;
        }

        boolean result =
                leaveRequestService
                        .updateLeaveRequestByManager(
                                leaveId,
                                managerId,
                                comment,
                                status
                        );

        if (result) {

            System.out.println(
                    "Leave request "
                            + status.toLowerCase()
                            + " successfully."
            );

        } else {

            System.out.println(
                    "Failed to process leave request."
            );
        }
    }


    // =========================================================
    // 10. REPORTS
    // =========================================================

    private void showReports() {

        boolean running = true;

        while (running) {

            System.out.println(
                    "\n===== Reports ====="
            );

            System.out.println(
                    "1. Total Employees"
            );

            System.out.println(
                    "2. Highest Salary Employee"
            );

            System.out.println(
                    "3. Lowest Salary Employee"
            );

            System.out.println(
                    "4. Average Salary"
            );

            System.out.println(
                    "5. Employees By Department"
            );

            System.out.println(
                    "6. Back"
            );

            System.out.print(
                    "Enter choice: "
            );

            int choice =
                    scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:
                    totalEmployees();
                    break;

                case 2:
                    highestSalaryEmployee();
                    break;

                case 3:
                    lowestSalaryEmployee();
                    break;

                case 4:
                    averageSalary();
                    break;

                case 5:
                    employeesByDepartment();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


    // =========================================================
    // TOTAL EMPLOYEES
    // =========================================================

    private void totalEmployees() {

        int total =
                reportService.getTotalEmployees();

        System.out.println(
                "\nTotal Employees : "
                        + total
        );
    }


    // =========================================================
    // HIGHEST SALARY
    // =========================================================

    private void highestSalaryEmployee() {

        Employee employee =
                reportService
                        .getHighestSalaryEmployee();

        if (employee == null) {

            System.out.println(
                    "No employees found."
            );

            return;
        }

        System.out.println(
                "\n===== Highest Salary Employee ====="
        );

        displayEmployee(employee);
    }


    // =========================================================
    // LOWEST SALARY
    // =========================================================

    private void lowestSalaryEmployee() {

        Employee employee =
                reportService
                        .getLowestSalaryEmployee();

        if (employee == null) {

            System.out.println(
                    "No employees found."
            );

            return;
        }

        System.out.println(
                "\n===== Lowest Salary Employee ====="
        );

        displayEmployee(employee);
    }


    // =========================================================
    // AVERAGE SALARY
    // =========================================================

    private void averageSalary() {

        double average =
                reportService.getAverageSalary();

        System.out.println(
                "\nAverage Salary : "
                        + average
        );
    }


    // =========================================================
    // EMPLOYEES BY DEPARTMENT
    // =========================================================

    private void employeesByDepartment() {

        System.out.print(
                "Enter department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();

        List<Employee> employees =
                reportService
                        .getEmployeesByDepartment(
                                departmentId
                        );

        if (employees == null ||
                employees.isEmpty()) {

            System.out.println(
                    "No employees found "
                            + "in this department."
            );

            return;
        }

        System.out.println(
                "\n===== Employees By Department ====="
        );

        for (Employee employee : employees) {

            displayEmployee(employee);

            System.out.println(
                    "--------------------------------"
            );
        }
    }
}