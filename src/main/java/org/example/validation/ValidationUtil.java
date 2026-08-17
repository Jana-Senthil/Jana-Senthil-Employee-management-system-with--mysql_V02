package org.example.validation;

public class ValidationUtil {

    private ValidationUtil() {
    }

    // Text validation
    public static boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }

    // Email
    public static boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Indian 10-digit phone number
    public static boolean isValidPhone(String phone) {
        return phone != null &&
                phone.matches("^[6-9][0-9]{9}$");
    }

    // Password
    public static boolean isValidPassword(String password) {
        return password != null &&
                password.matches(
                        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"
                );
    }

    // Salary
    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }

    // ID
    public static boolean isValidId(int id) {
        return id > 0;
    }

    // Status
    public static boolean isValidEmployeeStatus(String status) {
        return status != null &&
                (status.equalsIgnoreCase("ACTIVE") ||
                        status.equalsIgnoreCase("INACTIVE") ||
                        status.equalsIgnoreCase("RESIGNED"));
    }

    // Role
    public static boolean isValidRole(String role) {
        return role != null &&
                (role.equalsIgnoreCase("EMPLOYEE") ||
                        role.equalsIgnoreCase("MANAGER") ||
                        role.equalsIgnoreCase("ADMIN"));
    }
}
