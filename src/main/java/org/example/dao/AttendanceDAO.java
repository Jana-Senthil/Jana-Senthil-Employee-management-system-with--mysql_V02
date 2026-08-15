package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.Attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AttendanceDAO {
    public void addAttendance(LocalDate date, int sessionNo, LocalTime checkIn,String mode,int employeeId) {
        String sql = "INSERT INTO attendance(attendance_date,session_no,check_in,mode,employee_id) VALUES(?,?,?,?,?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDate(1, java.sql.Date.valueOf(date));
            ps.setInt(2, sessionNo);
            ps.setTime(3, java.sql.Time.valueOf(checkIn));
            ps.setString(4, mode);
            ps.setInt(5, employeeId);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Successfully attendance marked");
            }else {
                System.out.println("Failed to mark attendance");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateAttendance(LocalDate date, int sessionNo, LocalTime checkOut,int employeeId) {
        String sql = "update attendance set check_out=?" +
                " where session_no=? "+
                " and attendance_date =? " +
                " and employee_id = ? ";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setTime(1, java.sql.Time.valueOf(checkOut));
            ps.setInt(2, sessionNo);
            ps.setDate(3, java.sql.Date.valueOf(date));
            ps.setInt(4, employeeId);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Successfully attendance marked");
            }else{
                System.out.println("Failed to mark attendance");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Attendance getAttendance(int sessionNo,LocalDate date,int employeeId) {
        String sql = "select * from attendance " +
                "where session_no=? and attendance_date=? and employee_id=? ";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
           ps.setInt(1, sessionNo);
           ps.setDate(2, java.sql.Date.valueOf(date));
           ps.setInt(3, employeeId);
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
//               LocalDate attendanceDate = rs.getDate("attendance_date").toLocalDate();
//               int sessionNoId = rs.getInt("session_no");
//               int employee_id = rs.getInt("employee_id");
               LocalTime checkIn = rs.getTime("check_in").toLocalTime();
               LocalTime checkOut = null;
               if (rs.getTime("check_out") != null) {
                   checkOut = rs.getTime("check_out").toLocalTime();
               }
               String mode = rs.getString("mode");
                return new Attendance(employeeId,sessionNo,date,checkIn,checkOut,mode);
           }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Attendance> getAttendanceByEmployeeId(int employeeId) {
        String sql = "select * from attendance where employee_id=? ";
        List<Attendance> attendanceList = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,employeeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate attendanceDate = rs.getDate("attendance_date").toLocalDate();
                int sessionNoId = rs.getInt("session_no");
                int employee_id = rs.getInt("employee_id");
                LocalTime checkIn = rs.getTime("check_in").toLocalTime();
                LocalTime checkOut = null;
                if (rs.getTime("check_out") != null) {
                    checkOut = rs.getTime("check_out").toLocalTime();
                }
                String mode = rs.getString("mode");
                Attendance attendance = new Attendance(employee_id,sessionNoId,attendanceDate,checkIn,checkOut,mode);
                attendanceList.add(attendance);
            }
            return attendanceList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return attendanceList;
    }

    public List<Attendance> getALLAttendance() {
        String sql = "select * from attendance ";
        List<Attendance> attendanceList = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate attendanceDate = rs.getDate("attendance_date").toLocalDate();
                int sessionNoId = rs.getInt("session_no");
                int employee_id = rs.getInt("employee_id");
                LocalTime checkIn = rs.getTime("check_in").toLocalTime();
                LocalTime checkOut = null;
                if (rs.getTime("check_out") != null) {
                    checkOut = rs.getTime("check_out").toLocalTime();
                }
                String mode = rs.getString("mode");
                Attendance attendance = new Attendance(employee_id,sessionNoId,attendanceDate,checkIn,checkOut,mode);
                attendanceList.add(attendance);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return attendanceList;
    }
}
