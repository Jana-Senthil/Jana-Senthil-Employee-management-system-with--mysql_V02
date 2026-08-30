package org.example.service;
import org.example.dao.DepartmentDAO;
import org.example.model.Department;
import org.example.validation.ValidationUtil;


import java.util.List;


public class DepartmentService {
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    public int addDepartment(String departmentName,String location) {
        if (!ValidationUtil.isValidName(departmentName)) {
            return 0;
        }

        if (!ValidationUtil.isValidName(location)) {
            return 0;
        }
        return  departmentDAO.addDepartment(departmentName,location);
    }

    public boolean updateDepartment(int departmentId,String departmentName,String location) {
        if (!ValidationUtil.isValidName(departmentName)) {
            return false;
        }

        if(!ValidationUtil.isValidId(departmentId)) {
            return false;
        }

        if (!ValidationUtil.isValidName(location)) {
            return false;
        }
        return  departmentDAO.updateDepartment(departmentId,departmentName,location);
    }

    public boolean deleteDepartment(int departmentId) {
        return  departmentDAO.deleteDepartment(departmentId);
    }

    public Department getDepartmentById(int departmentId) {
        if(!ValidationUtil.isValidId(departmentId)) {
            return  null;
        }
        return  departmentDAO.getDepartmentById(departmentId);
    }

    public List<Department> getAllDepartments() {
        return  departmentDAO.getAllDepartments();
    }

}
