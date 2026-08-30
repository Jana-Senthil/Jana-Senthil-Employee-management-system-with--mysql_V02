package org.example.service;

import org.example.dao.DepartmentDAO;
import org.example.dao.EmployeeDAO;
import org.example.dao.ManagerDAO;
import org.example.model.Manager;
import org.example.model.User;
import org.example.validation.ValidationUtil;

import java.util.List;

public class ManagerService {

    private final ManagerDAO managerDAO = new ManagerDAO();
    private final UserService userService = new UserService();

    public int addManager(String name,
                              String email,
                              String phone) {

        if (!ValidationUtil.isValidName(name)) {
            return 0;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return 0;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return 0;
        }

        return managerDAO.addManager(name, email, phone);
    }

    public boolean updateManager(int managerId,
                                 String name,
                                 String email,
                                 String phone) {

        if (!ValidationUtil.isValidId(managerId)) {
            return false;
        }

        if (!ValidationUtil.isValidName(name)) {
            return false;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return false;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return false;
        }

        return managerDAO.updateManager(
                managerId,
                name,
                email,
                phone
        );
    }

    public boolean permanentlyDeleteManager(
            int managerId,
            int userId) {

        if (!ValidationUtil.isValidId(managerId)) {
            return false;
        }

        if (!ValidationUtil.isValidId(userId)) {
            return false;
        }

        Manager manager =
                managerDAO.getManagerById(managerId);

        if (manager == null) {
            return false;
        }

        User user =
                userService.getUserById(userId);

        if (user == null) {
            return false;
        }

        // Verify user belongs to manager
        if (user.getManagerId() == null ||
                user.getManagerId() != managerId) {

            return false;
        }

        // Manager account must be inactive
        if (!"INACTIVE".equalsIgnoreCase(
                user.getAccountStatus())) {

            return false;
        }

        return managerDAO.permanentlyDeleteManager(
                managerId,
                userId
        );
    }

    public Manager getManagerById(int managerId) {

        if (!ValidationUtil.isValidId(managerId)) {
            return null;
        }

        return managerDAO.getManagerById(managerId);
    }

    public List<Manager> getAllManagers() {
        return managerDAO.getAllManagers();
    }
}