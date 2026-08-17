package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.Session;
import org.example.model.User;
import org.example.util.PasswordUtil;
import org.example.validation.ValidationUtil;

public class LoginService {

    private final UserDAO userDAO = new UserDAO();

    public Session login(String username, String password) {

        if (!ValidationUtil.isValidName(username)) {
            return null;
        }

        if (password == null || password.isBlank()) {
            return null;
        }

        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        if (!PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash())) {
            return null;
        }

        return new Session(user);
    }
}