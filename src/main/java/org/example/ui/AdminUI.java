package org.example.ui;

import org.example.model.Attendance;
import org.example.model.Employee;
import org.example.model.LeaveRequest;
import org.example.model.Manager;
import org.example.model.User;
import org.example.service.AttendanceService;
import org.example.service.EmployeeService;
import org.example.service.LeaveRequestService;
import org.example.service.ManagerService;
import org.example.service.ReportService;
import org.example.service.UserService;
import org.example.util.SessionManager;

import java.util.List;
import java.util.Scanner;

public class AdminUI {

    private final Scanner scanner = new Scanner(System.in);

    private final DepartmentUI departmentUI =
            new DepartmentUI();

    private final EmployeeService employeeService =
            new EmployeeService();

    private final ManagerService managerService =
            new ManagerService();

    private final UserService userService =
            new UserService();

    private final AttendanceService attendanceService =
            new AttendanceService();

    private final LeaveRequestService leaveRequestService =
            new LeaveRequestService();

    private final ReportService reportService =
            new ReportService();


    // =========================================================
    // MAIN MENU
    // =========================================================

    public void showMenu() {

        while (SessionManager.isLoggedIn()
                && SessionManager.isAdmin()) {

            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Manage Departments");
            System.out.println("2. Manage Employees");
            System.out.println("3. Manage Managers");
            System.out.println("4. Manage Users");
            System.out.println("5. View Attendance");
            System.out.println("6. View Leave Requests");
            System.out.println("7. Reports");
            System.out.println("8. Logout");

            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    departmentUI.showMenu();
                    break;

                case 2:
                    manageEmployees();
                    break;

                case 3:
                    manageManagers();
                    break;

                case 4:
                    manageUsers();
                    break;

                case 5:
                    viewAttendance();
                    break;

                case 6:
                    viewLeaveRequests();
                    break;

                case 7:
                    showReports();
                    break;

                case 8:
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
    // 2. MANAGE EMPLOYEES
    // =========================================================

    private void manageEmployees() {

        boolean running = true;

        while (running) {

            System.out.println(
                    "\n===== Employee Management ====="
            );

            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Deactivate Employee");
            System.out.println("4. Activate Employee");
            System.out.println("5. Permanently Delete Employee");
            System.out.println("6. Search Employee");
            System.out.println("7. View All Employees");
            System.out.println("8. Back");

            System.out.print("Enter choice: ");

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
                    deactivateEmployee();
                    break;

                case 4:
                    activeEmployee();
                    break;

                case 5:
                    permanentlyDeleteEmployee();
                    break;

                case 6:
                    searchEmployee();
                    break;

                case 7:
                    viewAllEmployees();
                    break;

                case 8:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


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
                "Enter status "
                        + "(ACTIVE/INACTIVE/RESIGNED): "
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

        int result =
                employeeService.addEmployee(
                        name,
                        salary,
                        email,
                        phone,
                        status,
                        designation,
                        departmentId
                );

        if (result > 0) {

            System.out.println(
                    "Employee added successfully."
            );
            System.out.println(
                    "Employee ID: "+result
            );
            System.out.println(
                    "Create a User ID for this Employee ID : " + result
            );
            addUser();

        } else {

            System.out.println(
                    "Failed to add employee."
            );
        }
    }


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
                "Enter new name: "
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


    private void deactivateEmployee() {

        System.out.println(
                "\n===== Deactivate Employee ====="
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
                "Are you sure? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Deactivate operation cancelled."
            );

            return;
        }

        boolean result =
                employeeService.deactivateEmployee(
                        employeeId
                );

        if (result) {

            System.out.println(
                    "Employee deactivate successfully."
            );

        } else {

            System.out.println(
                    "Failed to deactivate employee."
            );
        }
    }


    private void activeEmployee() {

        System.out.println(
                "\n===== Activate Employee ====="
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

        if (employee.getStatus()
                .equalsIgnoreCase("ACTIVE")) {

            System.out.println(
                    "Employee is already active."
            );

            return;
        }

        System.out.print(
                "\nAre you sure you want to "
                        + "activate this employee? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Activation cancelled."
            );

            return;
        }

        boolean result =
                employeeService.activateEmployee(
                        employeeId
                );

        if (result) {

            System.out.println(
                    "Employee activated successfully."
            );

        } else {

            System.out.println(
                    "Failed to activate employee."
            );
        }
    }
    private void permanentlyDeleteEmployee() {

        System.out.println("\n===== Permanently Delete Employee =====");

        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Employee User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Employee employee =
                employeeService.getEmployeeById(employeeId);

        User user =
                userService.getUserById(userId);

        // Check employee
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        // Check user
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        // Check whether this user belongs to this employee
        if (user.getEmployeeId() == null ||
                user.getEmployeeId() != employeeId) {

            System.out.println(
                    "This user does not belong to the entered employee."
            );
            return;
        }

        // Check employee status
        if (!employee.getStatus()
                .equalsIgnoreCase("INACTIVE")) {

            System.out.println(
                    "Employee must be INACTIVE before permanent deletion."
            );
            return;
        }

        // Check user account status
        if (!user.getAccountStatus()
                .equalsIgnoreCase("INACTIVE")) {

            System.out.println(
                    "User account must be INACTIVE before permanent deletion."
            );
            return;
        }

        // Display details
        System.out.println("\nEmployee Details:");
        System.out.println(employee);

        System.out.println("\nUser Details:");
        System.out.println(user);

        // Confirmation
        System.out.print(
                "\nAre you sure you want to permanently delete this employee? (yes/no): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        // Then delete EMPLOYEE
        boolean employeeDeleted =
                employeeService.permanentlyDeleteEmployee(employeeId);

        if (employeeDeleted) {

            System.out.println(
                    "Employee and user account permanently deleted successfully."
            );

        } else {

            System.out.println(
                    "User was deleted, but employee deletion failed."
            );
        }
    }

    private void searchEmployee() {

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


    private void viewAllEmployees() {

        List<Employee> employees =
                employeeService.getAllEmployees();

        if (employees == null ||
                employees.isEmpty()) {

            System.out.println(
                    "No employees found."
            );

            return;
        }

        System.out.println(
                "\n===== All Employees ====="
        );

        for (Employee employee :
                employees) {

            displayEmployee(employee);

            System.out.println(
                    "--------------------------------"
            );
        }
    }


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
    // 3. MANAGE MANAGERS
    // =========================================================

    private void manageManagers() {

        boolean running = true;

        while (running) {

            System.out.println(
                    "\n===== Manager Management ====="
            );

            System.out.println("1. Add Manager");
            System.out.println("2. Update Manager");
            System.out.println("3. Delete Manager");
            System.out.println("4. Activate Employee");
            System.out.println("4. Search Manager");
            System.out.println("5. View All Managers");
            System.out.println("6. Back");

            System.out.print("Enter choice: ");

            int choice =
                    scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:
                    addManager();
                    break;

                case 2:
                    updateManager();
                    break;

                case 3:
                    permanentlyDeleteManager();
                    break;

                case 4:
                    searchManager();
                    break;

                case 5:
                    viewAllManagers();
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


    private void addManager() {

        System.out.println(
                "\n===== Add Manager ====="
        );

        System.out.print(
                "Enter manager name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Enter manager email: "
        );

        String email =
                scanner.nextLine();

        System.out.print(
                "Enter manager phone: "
        );

        String phone =
                scanner.nextLine();

        int result =
                managerService.addManager(
                        name,
                        email,
                        phone
                );

        if (result > 0) {

            System.out.println(
                    "Manager added successfully."
            );
            System.out.println("Manager ID is "+ result);
            System.out.println("Create a User ID and Password for this manager Id ; " + result);
            addUser();

        } else {

            System.out.println(
                    "Failed to add manager."
            );
        }
    }


    private void updateManager() {

        System.out.println(
                "\n===== Update Manager ====="
        );

        System.out.print(
                "Enter manager ID: "
        );

        int managerId =
                scanner.nextInt();

        scanner.nextLine();

        Manager manager =
                managerService.getManagerById(
                        managerId
                );

        if (manager == null) {

            System.out.println(
                    "Manager not found."
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

        boolean result =
                managerService.updateManager(
                        managerId,
                        name,
                        email,
                        phone
                );

        if (result) {

            System.out.println(
                    "Manager updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update manager."
            );
        }
    }


    private void permanentlyDeleteManager() {

        System.out.println(
                "\n===== Permanently Delete Manager ====="
        );

        System.out.print("Enter Manager ID: ");
        int managerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Manager User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Manager manager =
                managerService.getManagerById(managerId);

        if (manager == null) {
            System.out.println("Manager not found.");
            return;
        }

        User user =
                userService.getUserById(userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        // Check user belongs to this manager
        if (user.getManagerId() == null ||
                user.getManagerId() != managerId) {

            System.out.println(
                    "This user does not belong to the selected manager."
            );

            return;
        }

        // Check account status
        if (!"INACTIVE".equalsIgnoreCase(
                user.getAccountStatus())) {

            System.out.println(
                    "Manager user account is ACTIVE."
            );

            System.out.println(
                    "Manager account must be INACTIVE "
                            + "before permanent deletion."
            );

            return;
        }

        System.out.println("\nManager Details:");
        displayManager(manager);

        System.out.println("\nUser Details:");
        System.out.println(user);

        System.out.print(
                "\nAre you sure you want to permanently delete "
                        + "this manager? (yes/no): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Delete operation cancelled."
            );

            return;
        }

        boolean deleted =
                managerService.permanentlyDeleteManager(
                        managerId,
                        userId
                );

        if (deleted) {

            System.out.println(
                    "Manager and user account permanently deleted successfully."
            );

        } else {

            System.out.println(
                    "Failed to permanently delete manager."
            );
        }
    }


    private void searchManager() {

        System.out.print(
                "Enter manager ID: "
        );

        int managerId =
                scanner.nextInt();

        scanner.nextLine();

        Manager manager =
                managerService.getManagerById(
                        managerId
                );

        if (manager == null) {

            System.out.println(
                    "Manager not found."
            );

            return;
        }

        displayManager(manager);
    }


    private void viewAllManagers() {

        List<Manager> managers =
                managerService.getAllManagers();

        if (managers == null ||
                managers.isEmpty()) {

            System.out.println(
                    "No managers found."
            );

            return;
        }

        System.out.println(
                "\n===== All Managers ====="
        );

        for (Manager manager :
                managers) {

            displayManager(manager);

            System.out.println(
                    "--------------------------------"
            );
        }
    }


    private void displayManager(
            Manager manager) {

        System.out.println(
                "Manager ID : "
                        + manager.getManagerId()
        );

        System.out.println(
                "Name       : "
                        + manager.getManagerName()
        );

        System.out.println(
                "Email      : "
                        + manager.getManagerEmail()
        );

        System.out.println(
                "Phone      : "
                        + manager.getManagerPhone()
        );
    }


    // =========================================================
    // 4. MANAGE USERS
    // =========================================================

    private void manageUsers() {

        boolean running = true;

        while (running) {

            System.out.println(
                    "\n===== User Management ====="
            );

            System.out.println("1. Add User");
            System.out.println("2. Update User Profile");
            System.out.println("3. Change User Password");
            System.out.println("4. Update User Role");
            System.out.println("5. Inactive User");
            System.out.println("6. Active User");
            System.out.println("7. Delete User");
            System.out.println("8. Search User");
            System.out.println("9. View All Users");
            System.out.println("10. Back");

            System.out.print("Enter choice: ");

            int choice =
                    scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:
                    addUser();
                    break;

                case 2:
                    updateUserProfile();
                    break;

                case 3:
                    changeUserPassword();
                    break;

                case 4:
                    updateUserRole();
                    break;

                case 5:
                    inactiveUser();
                    break;

                case 6:
                    activeUser();
                    break;

                case 7:
                    permanentlyDeleteUser();
                    break;

                case 8:
                    searchUser();
                    break;

                case 9:
                    viewAllUsers();
                    break;

                case 10:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


 private void addUser() {

        System.out.println(
                "\n===== Add User ====="
        );

        System.out.print(
                "Enter username: "
        );

        String username =
                scanner.nextLine();

        System.out.print(
                "Enter password: "
        );

        String password =
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

        String role =
                getUserRole();

        if (role == null) {
            return;
        }

        Integer employeeId = null;
        Integer managerId = null;

        if (role.equals("EMPLOYEE")) {

            System.out.print(
                    "Enter employee ID: "
            );

            employeeId =
                    scanner.nextInt();

            scanner.nextLine();

        } else if (role.equals("MANAGER")) {

            System.out.print(
                    "Enter manager ID: "
            );

            managerId =
                    scanner.nextInt();

            scanner.nextLine();
        }

        int userId =
                userService.addUser(
                        username,
                        password,
                        email,
                        phone,
                        role,
                        employeeId,
                        managerId
                );

        if (userId >0) {

            System.out.println(
                    "User added successfully."
            );
            System.out.println("User ID is: " + userId);

        } else {

            System.out.println(
                    "Failed to add user."
            );
        }
    }


    private String getUserRole() {

        System.out.println(
                "1. EMPLOYEE"
        );

        System.out.println(
                "2. MANAGER"
        );

        System.out.println(
                "3. ADMIN"
        );

        System.out.print(
                "Enter role: "
        );

        int choice =
                scanner.nextInt();

        scanner.nextLine();

        return switch (choice) {
            case 1 -> "EMPLOYEE";
            case 2 -> "MANAGER";
            case 3 -> "ADMIN";
            default -> {
                System.out.println(
                        "Invalid role."
                );

                yield null;
            }
        };
    }


    private void updateUserProfile() {

        System.out.println(
                "\n===== Update User Profile ====="
        );

        System.out.print(
                "Enter user ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();

        User user =
                userService.getUserById(
                        userId
                );

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.print(
                "Enter new username: "
        );

        String username =
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

        boolean result =
                userService.updateUserProfile(
                        userId,
                        username,
                        email,
                        phone
                );

        if (result) {

            System.out.println(
                    "User profile updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update user profile."
            );
        }
    }


    private void changeUserPassword() {

        System.out.println(
                "\n===== Change User Password ====="
        );

        System.out.print(
                "Enter user ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();

        User user =
                userService.getUserById(
                        userId
                );

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.print(
                "Enter new password: "
        );

        String password =
                scanner.nextLine();

        System.out.print(
                "Confirm password: "
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
                        userId,
                        password
                );

        if (result) {

            System.out.println(
                    "Password updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update password."
            );
        }
    }


    private void updateUserRole() {

        System.out.println(
                "\n===== Update User Role ====="
        );

        System.out.print(
                "Enter user ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();

        User user =
                userService.getUserById(
                        userId
                );

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        String role =
                getUserRole();

        if (role == null) {
            return;
        }

        Integer employeeId = null;
        Integer managerId = null;

        if (role.equals("EMPLOYEE")) {

            System.out.print(
                    "Enter employee ID: "
            );

            employeeId =
                    scanner.nextInt();

            scanner.nextLine();

        } else if (role.equals("MANAGER")) {

            System.out.print(
                    "Enter manager ID: "
            );

            managerId =
                    scanner.nextInt();

            scanner.nextLine();
        }

        boolean result =
                userService.updateUserRole(
                        userId,
                        role,
                        employeeId,
                        managerId
                );

        if (result) {

            System.out.println(
                    "User role updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update user role."
            );
        }
    }


    private void inactiveUser() {

        System.out.println(
                "\n===== Inactive User ====="
        );

        System.out.print(
                "Enter user ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();

        User user =
                userService.getUserById(
                        userId
                );

        if (!user.getAccountStatus().equalsIgnoreCase("active")) {

            System.out.println(
                    "User ID is already in Inactive."
            );

            return;
        }

        displayUser(user);

        System.out.print(
                "Are you sure? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Inactive operation cancelled."
            );

            return;
        }

        boolean result =
                userService.deactiveUser(
                        userId
                );

        if (result) {

            System.out.println(
                    "User inactive successfully."
            );

        } else {

            System.out.println(
                    "Failed to inactive user."
            );
        }
    }

    private void activeUser() {

        System.out.println(
                "\n===== Active User ====="
        );

        System.out.print(
                "Enter user ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();

        User user =
                userService.getUserById(
                        userId
                );

        if (user.getAccountStatus().equalsIgnoreCase("active")) {

            System.out.println(
                    "User ID is already in active."
            );

            return;
        }

        displayUser(user);

        System.out.print(
                "Are you sure? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Active operation cancelled."
            );

            return;
        }

        boolean result =
                userService.activeUser(
                        userId
                );

        if (result) {

            System.out.println(
                    "User active successfully."
            );

        } else {

            System.out.println(
                    "Failed to active user."
            );
        }
    }

    private void permanentlyDeleteUser() {

        System.out.println("\n===== Permanently Delete User =====");

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userService.getUserById(userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\nUser Details:");
        System.out.println(user);

        // Check account status
        if (!"INACTIVE".equalsIgnoreCase(
                user.getAccountStatus())) {

            System.out.println(
                    "User account is ACTIVE."
            );

            System.out.println(
                    "User must be INACTIVE before permanent deletion."
            );

            return;
        }

        System.out.print(
                "\nAre you sure you want to permanently delete this user? (yes/no): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        boolean deleted =
                userService.permanentlyDeleteUser(userId);

        if (deleted) {
            System.out.println(
                    "User account permanently deleted successfully."
            );
        } else {
            System.out.println(
                    "Failed to permanently delete user."
            );
        }
    }


    private void searchUser() {

        System.out.print(
                "Enter user ID: "
        );

        int userId =
                scanner.nextInt();

        scanner.nextLine();

        User user =
                userService.getUserById(
                        userId
                );

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        displayUser(user);
    }


    private void viewAllUsers() {

        List<User> users =
                userService.getAllUsers();

        if (users == null ||
                users.isEmpty()) {

            System.out.println(
                    "No users found."
            );

            return;
        }

        System.out.println(
                "\n===== All Users ====="
        );

        for (User user : users) {

            displayUser(user);

            System.out.println(
                    "--------------------------------"
            );
        }
    }


    private void displayUser(User user) {

        System.out.println(
                "User ID     : "
                        + user.getUserId()
        );

        System.out.println(
                "Username    : "
                        + user.getUsername()
        );

        System.out.println(
                "Email       : "
                        + user.getEmail()
        );

        System.out.println(
                "Phone       : "
                        + user.getPhone()
        );

        System.out.println(
                "Role        : "
                        + user.getRole()
        );

        System.out.println(
                "Employee ID : "
                        + user.getEmployeeId()
        );

        System.out.println(
                "Manager ID  : "
                        + user.getManagerId()
        );
    }


    // =========================================================
    // 5. VIEW ATTENDANCE
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
    }


    // =========================================================
    // 6. VIEW LEAVE REQUESTS
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


    private void displayLeaveRequest(LeaveRequest leave) {

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
    // 7. REPORTS
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


    private void totalEmployees() {

        int total =
                reportService.getTotalEmployees();

        System.out.println(
                "\nTotal Employees : "
                        + total
        );
    }


    private void highestSalaryEmployee() {

        Employee employee =
                reportService
                        .getHighestSalaryEmployee();

        if (employee == null) {

            System.out.println(
                    "No employee found."
            );

            return;
        }

        System.out.println(
                "\n===== Highest Salary Employee ====="
        );

        displayEmployee(employee);
    }


    private void lowestSalaryEmployee() {

        Employee employee =
                reportService
                        .getLowestSalaryEmployee();

        if (employee == null) {

            System.out.println(
                    "No employee found."
            );

            return;
        }

        System.out.println(
                "\n===== Lowest Salary Employee ====="
        );

        displayEmployee(employee);
    }


    private void averageSalary() {

        double average =
                reportService.getAverageSalary();

        System.out.println(
                "\nAverage Salary : "
                        + average
        );
    }


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
                    "No employees found."
            );

            return;
        }

        for (Employee employee :
                employees) {

            displayEmployee(employee);

            System.out.println(
                    "--------------------------------"
            );
        }
    }
}