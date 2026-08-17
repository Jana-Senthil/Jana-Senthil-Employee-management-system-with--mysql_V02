package org.example.ui;

import org.example.util.SessionManager;

import java.util.Scanner;

public class EmployeeUI {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (SessionManager.isLoggedIn()) {

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
                    System.out.println("Mark Attendance");
                    break;

                case 5:
                    System.out.println("View Attendance");
                    break;

                case 6:
                    System.out.println("Apply Leave");
                    break;

                case 7:
                    System.out.println("Update Leave Request");
                    break;

                case 8:
                    System.out.println("Cancel Leave Request");
                    break;

                case 9:
                    System.out.println("View Leave History");
                    break;

                case 10:
                    SessionManager.logout();
                    System.out.println("Logged out successfully");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}