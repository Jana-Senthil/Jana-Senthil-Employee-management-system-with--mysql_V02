package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.dao.ManagerDAO;
import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.util.PasswordUtil;
import org.example.validation.ValidationUtil;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ManagerDAO managerDAO = new ManagerDAO();

    // Add User
    public boolean addUser(String username,
                           String password,
                           String email,
                           String phone,
                           String role,
                           Integer employeeId,
                           Integer managerId) {

        if (!ValidationUtil.isValidName(username)) {
            return false;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            return false;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return false;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return false;
        }

        if (!ValidationUtil.isValidRole(role)) {
            return false;
        }

        // Employee user
        if (role.equalsIgnoreCase("EMPLOYEE")) {

            if (employeeId == null ||
                    !ValidationUtil.isValidId(employeeId)) {
                return false;
            }

            if (employeeDAO.getEmployeeById(employeeId) == null) {
                return false;
            }

            // Employee cannot have manager_id
            if (managerId != null) {
                return false;
            }
        }

        // Manager user
        if (role.equalsIgnoreCase("MANAGER")) {

            if (managerId == null ||
                    !ValidationUtil.isValidId(managerId)) {
                return false;
            }

            if (managerDAO.getManagerById(managerId) == null) {
                return false;
            }

            // Manager cannot have employee_id
            if (employeeId != null) {
                return false;
            }
        }

        //Admin user
        if (role.equalsIgnoreCase("ADMIN")) {

            // Admin is not linked to employee or manager
            if (employeeId != null || managerId != null) {
                return false;
            }
        }

        // Hash password before storing
        String passwordHash = PasswordUtil.hashPassword(password);

        return userDAO.addUser(
                username,
                passwordHash,
                email,
                phone,
                role,
                employeeId,
                managerId
        );
    }


    // Update username, email and phone
    public boolean updateUserProfile(int userId,
                                     String username,
                                     String email,
                                     String phone) {

        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        if (userDAO.getUserById(userId) == null) {
            return false;
        }

        if (!ValidationUtil.isValidName(username)) {
            return false;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return false;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return false;
        }

        return userDAO.updateUserProfile(
                userId,
                username,
                email,
                phone
        );
    }


    // Update password
    public boolean updatePassword(int userId,
                                  String password) {

        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            return false;
        }

        if (userDAO.getUserById(userId) == null) {
            return false;
        }

        String passwordHash =
                PasswordUtil.hashPassword(password);

        return userDAO.updatePassword(
                userId,
                passwordHash
        );
    }


    // Update role
    public boolean updateUserRole(int userId,
                                  String role,
                                  Integer employeeId,
                                  Integer managerId) {

        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        if (!ValidationUtil.isValidRole(role)) {
            return false;
        }

        if (userDAO.getUserById(userId) == null) {
            return false;
        }

        // Employee role
        if (role.equalsIgnoreCase("EMPLOYEE")) {

            if (employeeId == null ||
                    !ValidationUtil.isValidId(employeeId)) {
                return false;
            }

            if (employeeDAO.getEmployeeById(employeeId) == null) {
                return false;
            }

            if (managerId != null) {
                return false;
            }
        }

        // Manager role
        if (role.equalsIgnoreCase("MANAGER")) {

            if (managerId == null ||
                    !ValidationUtil.isValidId(managerId)) {
                return false;
            }

            if (managerDAO.getManagerById(managerId) == null) {
                return false;
            }

            if (employeeId != null) {
                return false;
            }
        }

        //Admin Role
        if (role.equalsIgnoreCase("ADMIN")) {

            if (employeeId != null || managerId != null) {
                return false;
            }
        }

        return userDAO.updateUserRole(
                userId,
                role,
                employeeId,
                managerId
        );
    }


    // Delete User
    public boolean deleteUser(int userId) {

        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        if (userDAO.getUserById(userId) == null) {
            return false;
        }

        return userDAO.deleteUser(userId);
    }


    // Get User by ID
    public User getUserById(int userId) {

        if (!ValidationUtil.isValidId(userId)) {
            return null;
        }

        return userDAO.getUserById(userId);
    }


    // Get User by Username
    public User getUserByUsername(String username) {

        if (!ValidationUtil.isValidName(username)) {
            return null;
        }

        return userDAO.getUserByUsername(username);
    }


    // Get All Users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}