package org.example.service;

import org.example.dao.ManagerDAO;
import org.example.model.Manager;
import org.example.validation.ValidationUtil;

import java.util.List;

public class ManagerService {

    private final ManagerDAO managerDAO = new ManagerDAO();

    public boolean addManager(String name,
                              String email,
                              String phone) {

        if (!ValidationUtil.isValidName(name)) {
            return false;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return false;
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return false;
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

    public boolean deleteManager(int managerId) {

        if (!ValidationUtil.isValidId(managerId)) {
            return false;
        }

        return managerDAO.deleteManager(managerId);
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