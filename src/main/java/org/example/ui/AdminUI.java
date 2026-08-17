package org.example.ui;

import org.example.util.SessionManager;

import java.util.Scanner;

public class AdminUI {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (SessionManager.isLoggedIn() && SessionManager.isAdmin()) {

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
                    System.out.println("Department Management");
                    break;

                case 2:
                    System.out.println("Employee Management");
                    break;

                case 3:
                    System.out.println("Manager Management");
                    break;

                case 4:
                    System.out.println("User Management");
                    break;

                case 5:
                    System.out.println("Attendance Management");
                    break;

                case 6:
                    System.out.println("Leave Request Management");
                    break;

                case 7:
                    System.out.println("Reports");
                    break;

                case 8:
                    SessionManager.logout();
                    System.out.println("Logged out successfully.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}