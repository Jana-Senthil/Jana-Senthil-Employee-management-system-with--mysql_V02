package org.example.ui;

import org.example.model.Department;
import org.example.service.DepartmentService;

import java.util.List;
import java.util.Scanner;

public class DepartmentUI {

    private final DepartmentService departmentService = new DepartmentService();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== Department Management =====");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. Search Department");
            System.out.println("5. View All Departments");
            System.out.println("6. Back");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addDepartment();
                    break;

                case 2:
                    updateDepartment();
                    break;

                case 3:
                    deleteDepartment();
                    break;

                case 4:
                    getDepartmentById();
                    break;

                case 5:
                    getAllDepartments();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addDepartment() {

        System.out.print("Enter department name: ");
        String departmentName = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        boolean result =
                departmentService.addDepartment(
                        departmentName,
                        location
                );

        if (result) {
            System.out.println("Department added successfully.");
        } else {
            System.out.println("Failed to add department.");
        }
    }

    private void updateDepartment() {

        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new department name: ");
        String departmentName = scanner.nextLine();

        System.out.print("Enter new location: ");
        String location = scanner.nextLine();

        boolean result =
                departmentService.updateDepartment(
                        departmentId,
                        departmentName,
                        location
                );

        if (result) {
            System.out.println("Department updated successfully.");
        } else {
            System.out.println("Failed to update department.");
        }
    }

    private void deleteDepartment() {

        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine();

        System.out.print(
                "Are you sure you want to delete this department? (yes/no): "
        );

        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        boolean result =
                departmentService.deleteDepartment(departmentId);

        if (result) {
            System.out.println("Department deleted successfully.");
        } else {
            System.out.println("Failed to delete department.");
        }
    }

    private void getDepartmentById() {

        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine();

        Department department =
                departmentService.getDepartmentById(departmentId);

        if (department == null) {
            System.out.println("Department not found.");
            return;
        }

        System.out.println("\n===== Department Details =====");
        System.out.println("Department ID   : " + department.getDepartmentId());
        System.out.println("Department Name : " + department.getDepartmentName());
        System.out.println("Location        : " + department.getLocation());
    }

    private void getAllDepartments() {

        List<Department> departments =
                departmentService.getAllDepartments();

        if (departments == null || departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        System.out.println("\n===== All Departments =====");

        for (Department department : departments) {

            System.out.println(
                    "ID: " + department.getDepartmentId() +
                            " | Name: " + department.getDepartmentName() +
                            " | Location: " + department.getLocation()
            );
        }
    }
}
