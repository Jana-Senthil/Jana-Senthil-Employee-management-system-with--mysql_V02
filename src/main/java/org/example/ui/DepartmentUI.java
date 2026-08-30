package org.example.ui;

import org.example.model.Department;
import org.example.service.DepartmentService;

import java.util.List;
import java.util.Scanner;

public class DepartmentUI {

    private final DepartmentService departmentService =
            new DepartmentService();

    private final Scanner scanner =
            new Scanner(System.in);


    // =========================================================
    // MAIN MENU
    // =========================================================

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
                    searchDepartment();
                    break;

                case 5:
                    viewAllDepartments();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


    // =========================================================
    // 1. ADD DEPARTMENT
    // =========================================================

    private void addDepartment() {

        System.out.println(
                "\n===== Add Department ====="
        );

        System.out.print(
                "Enter department name: "
        );

        String departmentName =
                scanner.nextLine();

        System.out.print(
                "Enter location: "
        );

        String location =
                scanner.nextLine();

        int departmentId =
                departmentService.addDepartment(
                        departmentName,
                        location
                );

        if (departmentId > 0) {

            System.out.println(
                    "Department added successfully."
            );
            System.out.println(
                    "Department ID: " + departmentId
            );

        } else {

            System.out.println(
                    "Failed to add department."
            );
        }
    }


    // =========================================================
    // 2. UPDATE DEPARTMENT
    // =========================================================

    private void updateDepartment() {

        System.out.println(
                "\n===== Update Department ====="
        );

        System.out.print(
                "Enter department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();

        Department department =
                departmentService.getDepartmentById(
                        departmentId
                );

        if (department == null) {

            System.out.println(
                    "Department not found."
            );

            return;
        }

        System.out.println(
                "\nCurrent Department Details:"
        );

        displayDepartment(department);

        System.out.print(
                "\nEnter new department name: "
        );

        String departmentName =
                scanner.nextLine();

        System.out.print(
                "Enter new location: "
        );

        String location =
                scanner.nextLine();

        boolean result =
                departmentService.updateDepartment(
                        departmentId,
                        departmentName,
                        location
                );

        if (result) {

            System.out.println(
                    "Department updated successfully."
            );

        } else {

            System.out.println(
                    "Failed to update department."
            );
        }
    }


    // =========================================================
    // 3. DELETE DEPARTMENT
    // =========================================================

    private void deleteDepartment() {

        System.out.println(
                "\n===== Delete Department ====="
        );

        System.out.print(
                "Enter department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();

        Department department =
                departmentService.getDepartmentById(
                        departmentId
                );

        if (department == null) {

            System.out.println(
                    "Department not found."
            );

            return;
        }

        displayDepartment(department);

        System.out.print(
                "\nAre you sure you want to "
                        + "delete this department? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Delete operation cancelled."
            );

            return;
        }

        boolean result =
                departmentService.deleteDepartment(
                        departmentId
                );

        if (result) {

            System.out.println(
                    "Department deleted successfully."
            );

        } else {

            System.out.println(
                    "Failed to delete department."
            );
        }
    }


    // =========================================================
    // 4. SEARCH DEPARTMENT
    // =========================================================

    private void searchDepartment() {

        System.out.println(
                "\n===== Search Department ====="
        );

        System.out.print(
                "Enter department ID: "
        );

        int departmentId =
                scanner.nextInt();

        scanner.nextLine();

        Department department =
                departmentService.getDepartmentById(
                        departmentId
                );

        if (department == null) {

            System.out.println(
                    "Department not found."
            );

            return;
        }

        displayDepartment(department);
    }


    // =========================================================
    // 5. VIEW ALL DEPARTMENTS
    // =========================================================

    private void viewAllDepartments() {

        System.out.println(
                "\n===== All Departments ====="
        );

        List<Department> departments =
                departmentService.getAllDepartments();

        if (departments == null ||
                departments.isEmpty()) {

            System.out.println(
                    "No departments found."
            );

            return;
        }

        for (Department department :
                departments) {

            displayDepartment(department);

            System.out.println(
                    "--------------------------------"
            );
        }
    }


    // =========================================================
    // DISPLAY DEPARTMENT
    // =========================================================

    private void displayDepartment(
            Department department) {

        System.out.println(
                "Department ID   : "
                        + department.getDepartmentId()
        );

        System.out.println(
                "Department Name : "
                        + department.getDepartmentName()
        );

        System.out.println(
                "Location        : "
                        + department.getLocation()
        );
    }
}