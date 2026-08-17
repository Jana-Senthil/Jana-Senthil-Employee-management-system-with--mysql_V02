package org.example.ui;

import org.example.util.SessionManager;

import java.util.Scanner;

public class ManagerUI {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (SessionManager.isLoggedIn()) {

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

            switch (choice) {

                case 1:
                    System.out.println("View Profile");
                    break;

                case 2:
                    System.out.println("Update Profile");
                    break;

                case 3:
                    System.out.println("Change Password");
                    break;

                case 4:
                    System.out.println("View Employees");
                    break;

                case 5:
                    System.out.println("Manage Employees");
                    break;

                case 6:
                    System.out.println("View Attendance");
                    break;

                case 7:
                    System.out.println("View Leave Requests");
                    break;

                case 8:
                    System.out.println("Approve Leave");
                    break;

                case 9:
                    System.out.println("Reject Leave");
                    break;

                case 10:
                    System.out.println("Reports");
                    break;

                case 11:
                    SessionManager.logout();
                    System.out.println("Logged out successfully");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}