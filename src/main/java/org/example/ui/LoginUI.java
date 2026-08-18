package org.example.ui;

import org.example.model.Session;
import org.example.service.LoginService;
import org.example.util.SessionManager;

import java.util.Scanner;

public class LoginUI {

    private final LoginService loginService =
            new LoginService();

    private final Scanner scanner =
            new Scanner(System.in);


    public void start() {

        System.out.println(
                "\n===== Employee Management System ====="
        );

        System.out.println(
                "============== LOGIN =============="
        );

        System.out.print(
                "Username: "
        );

        String username =
                scanner.nextLine();

        System.out.print(
                "Password: "
        );

        String password =
                scanner.nextLine();


        // Login
        Session session =
                loginService.login(
                        username,
                        password
                );


        // Login failed
        if (session == null) {

            System.out.println(
                    "Invalid username or password."
            );

            return;
        }


        // Start session
        SessionManager.startSession(
                session
        );


        System.out.println(
                "\nLogin successful."
        );

        System.out.println(
                "Welcome "
                        + session.getUsername()
        );


        // =====================================================
        // ROLE BASED REDIRECTION
        // =====================================================

        if (SessionManager.isEmployee()) {

            new EmployeeUI().showMenu();

        } else if (SessionManager.isManager()) {

            new ManagerUI().showMenu();

        } else if (SessionManager.isAdmin()) {

            new AdminUI().showMenu();

        } else {

            System.out.println(
                    "Invalid user role."
            );

            SessionManager.logout();
        }
    }
}