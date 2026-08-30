package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public int addUser(String username,
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
             PreparedStatement ps = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

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

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateUserProfile(int userId,
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

            return row>0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return   false;
        }
    }

    public boolean updatePassword(int userId, String passwordHash) {

        String sql = "UPDATE users " +
                "SET password_hash = ? " +
                "WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, passwordHash);
            ps.setInt(2, userId);

            int row = ps.executeUpdate();

            return row>0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserRole(int userId,
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

            return row>0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean permanentlyDeleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int row = ps.executeUpdate();

            return row>0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean inactiveUser(int userId) {
        String sql = "UPDATE users SET account_status = 'INACTIVE' WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int row = ps.executeUpdate();

            return row>0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean activeUser(int userId) {
        String sql = "UPDATE users SET account_status = 'ACTIVE' WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int row = ps.executeUpdate();

            return row>0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
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
                String account_status  = rs.getString("account_status");
                return new User(
                        user_id,
                        username,
                        email,
                        phone,
                        passwordHash,
                        role,
                        employeeId,
                        managerId,
                        account_status
                );
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
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
                String account_status =  rs.getString("account_status");

                return new User(
                        userId,
                        userName,
                        email,
                        phone,
                        passwordHash,
                        role,
                        employeeId,
                        managerId,
                        account_status
                );
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
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
                String account_status  = rs.getString("account_status");

                User user = new User(
                        userId,
                        username,
                        email,
                        phone,
                        passwordHash,
                        role,
                        employeeId,
                        managerId,
                        account_status
                );

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    public User getUserByEmployeeId(int employeeId) {

        String sql =
                "SELECT * FROM users WHERE employee_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("role"),
                            rs.getObject("employee_id", Integer.class),
                            rs.getObject("manager_id", Integer.class),
                            rs.getString("account_status")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database Error: " + e.getMessage()
            );
            e.printStackTrace();
        }

        return null;
    }

    public boolean deactivateUserByEmployeeId(int employeeId) {

        String sql =
                "UPDATE users " +
                        "SET account_status = 'INACTIVE' " +
                        "WHERE employee_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, employeeId);

            int row = ps.executeUpdate();

            return row > 0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean activateUserByEmployeeId(int employeeId) {

        String sql =
                "UPDATE users " +
                        "SET account_status = 'ACTIVE' " +
                        "WHERE employee_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, employeeId);

            int row = ps.executeUpdate();

            return row > 0;

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}



