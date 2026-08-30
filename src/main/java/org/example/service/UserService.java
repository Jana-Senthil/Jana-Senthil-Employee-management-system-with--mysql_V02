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
    public int addUser(String username,
                           String password,
                           String email,
                           String phone,
                           String role,
                           Integer employeeId,
                           Integer managerId) {

        if (!ValidationUtil.isValidName(username)) {
            return 0;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            return 0;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return 0;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return 0;
        }

        if (!ValidationUtil.isValidRole(role)) {
            return 0;
        }

        // Employee user
        if (role.equalsIgnoreCase("EMPLOYEE")) {

            if (employeeId == null ||
                    !ValidationUtil.isValidId(employeeId)) {
                return 0;
            }

            if (employeeDAO.getEmployeeById(employeeId) == null) {
                return 0;
            }

            // Employee cannot have manager_id
            if (managerId != null) {
                return 0;
            }
        }

        // Manager user
        if (role.equalsIgnoreCase("MANAGER")) {

            if (managerId == null ||
                    !ValidationUtil.isValidId(managerId)) {
                return 0;
            }

            if (managerDAO.getManagerById(managerId) == null) {
                return 0;
            }

            // Manager cannot have employee_id
            if (employeeId != null) {
                return 0;
            }
        }

        //Admin user
        if (role.equalsIgnoreCase("ADMIN")) {

            // Admin is not linked to employee or manager
            if (employeeId != null || managerId != null) {
                return 0;
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
    public boolean permanentlyDeleteUser(int userId) {

        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        User user = userDAO.getUserById(userId);

        if (user == null) {
            return false;
        }

        // User must be INACTIVE before permanent deletion
        if (!"INACTIVE".equalsIgnoreCase(
                user.getAccountStatus())) {

            return false;
        }

        return userDAO.permanentlyDeleteUser(userId);
    }

    //INACTIVE USERS
    public boolean deactiveUser(int userId){
        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        if (userDAO.getUserById(userId) == null) {
            return false;
        }

        return userDAO.inactiveUser(userId);
    }

    //ACTITVE USER
    public boolean activeUser(int userId){
        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        if (userDAO.getUserById(userId) == null) {
            return false;
        }

        return userDAO.activeUser(userId);
    }



    // Get User by ID
    public User getUserById(int userId) {

        if (!ValidationUtil.isValidId(userId)) {
            return null;
        }

        return userDAO.getUserById(userId);
    }

    public User getUserByEmployeeId(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return null;
        }

        return userDAO.getUserByEmployeeId(employeeId);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }


    // Deactivate user account by employee ID
    public boolean deactivateUserByEmployeeId(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        return userDAO.deactivateUserByEmployeeId(employeeId);
    }


    // Activate user account by employee ID
    public boolean activateUserByEmployeeId(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        return userDAO.activateUserByEmployeeId(employeeId);
    }

}