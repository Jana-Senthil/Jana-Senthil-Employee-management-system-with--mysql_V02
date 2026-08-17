package org.example.ui;

import org.example.model.Session;
import org.example.service.LoginService;
import org.example.util.SessionManager;

import java.util.Scanner;

public class LoginUI {

    private final LoginService loginService = new LoginService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {

        System.out.println("===== Employee Management System =====");
        System.out.println("Login");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Session session = loginService.login(username, password);

        if (session == null) {
            System.out.println("Invalid username or password");
            return;
        }

        SessionManager.startSession(session);

        System.out.println("Login successful");
        System.out.println("Welcome " + session.getUsername());

        if (SessionManager.isEmployee()) {

            new EmployeeUI().showMenu();

        } else if (SessionManager.isManager()) {

            new ManagerUI().showMenu();

        } else if (SessionManager.isAdmin()) {

            new AdminUI().showMenu();
        }
    }
}