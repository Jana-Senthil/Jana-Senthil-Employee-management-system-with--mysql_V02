package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void addUser(String username,
                        String passwordHash,
                        String email,
                        String phone,
                        String role,
                        Integer employeeId,
                        Integer managerId) {

        String sql = "INSERT INTO users " +
                "(username, password_hash, email, phone, role, employee_id, manager_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, role);

            // Integer can be null
            if (employeeId != null) {
                ps.setInt(6, employeeId);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            if (managerId != null) {
                ps.setInt(7, managerId);
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("User added successfully");
            } else {
                System.out.println("Failed to add user");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserProfile(int userId,
                                  String username,
                                  String email,
                                  String phone) {

        String sql = "UPDATE users " +
                "SET username = ?, email = ?, phone = ? " +
                "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, userId);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("User profile updated successfully");
            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatePassword(int userId, String passwordHash) {

        String sql = "UPDATE users " +
                "SET password_hash = ? " +
                "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, passwordHash);
            ps.setInt(2, userId);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Password updated successfully");
            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserRole(int userId,
                               String role,
                               Integer employeeId,
                               Integer managerId) {

        String sql = "UPDATE users " +
                "SET role = ?, employee_id = ?, manager_id = ? " +
                "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, role);

            if (employeeId != null) {
                ps.setInt(2, employeeId);
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            if (managerId != null) {
                ps.setInt(3, managerId);
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.setInt(4, userId);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("User role updated successfully");
            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String passwordHash = rs.getString("password_hash");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                Integer employeeId =
                        rs.getObject("employee_id", Integer.class);

                Integer managerId =
                        rs.getObject("manager_id", Integer.class);

                return new User(
                        user_id,
                        username,
                        email,
                        phone,
                        passwordHash,
                        role,
                        employeeId,
                        managerId
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");
                String userName = rs.getString("username");
                String passwordHash = rs.getString("password_hash");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                Integer employeeId =
                        rs.getObject("employee_id", Integer.class);

                Integer managerId =
                        rs.getObject("manager_id", Integer.class);

                return new User(
                        userId,
                        userName,
                        email,
                        phone,
                        passwordHash,
                        role,
                        employeeId,
                        managerId
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String passwordHash = rs.getString("password_hash");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                Integer employeeId =
                        rs.getObject("employee_id", Integer.class);

                Integer managerId =
                        rs.getObject("manager_id", Integer.class);

                User user = new User(
                        userId,
                        username,
                        email,
                        phone,
                        passwordHash,
                        role,
                        employeeId,
                        managerId
                );

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

}

