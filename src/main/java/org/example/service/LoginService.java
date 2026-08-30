package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.Session;
import org.example.model.User;
import org.example.util.PasswordUtil;
import org.example.validation.ValidationUtil;

public class LoginService {

    private final UserDAO userDAO = new UserDAO();

    public Session login(String username, String password) {

        // Validate username
        if (!ValidationUtil.isValidName(username)) {
            return null;
        }

        // Validate password
        if (password == null || password.isBlank()) {
            return null;
        }

        // Find user
        User user =
                userDAO.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        // Check account status
        if (!"ACTIVE".equalsIgnoreCase(
                user.getAccountStatus())) {

            return null;
        }

        // Verify password
        if (!PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash())) {

            return null;
        }

        // Create session
        return new Session(user);
    }
}