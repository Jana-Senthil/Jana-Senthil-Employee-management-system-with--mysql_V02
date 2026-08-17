package org.example.service;

import org.example.dao.AttendanceDAO;
import org.example.dao.EmployeeDAO;
import org.example.model.Attendance;
import org.example.validation.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    // Check-in
    public boolean addAttendance(LocalDate date,
                                 int sessionNo,
                                 LocalTime checkIn,
                                 String mode,
                                 int employeeId) {

        if (date == null) {
            return false;
        }

        if (!ValidationUtil.isValidId(sessionNo)) {
            return false;
        }

        if (checkIn == null) {
            return false;
        }

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return false;
        }

        if (!isValidMode(mode)) {
            return false;
        }

        return attendanceDAO.addAttendance(
                date,
                sessionNo,
                checkIn,
                mode,
                employeeId
        );
    }


    // Check-out
    public boolean updateAttendance(LocalDate date,
                                    int sessionNo,
                                    LocalTime checkOut,
                                    int employeeId) {

        if (date == null) {
            return false;
        }

        if (!ValidationUtil.isValidId(sessionNo)) {
            return false;
        }

        if (checkOut == null) {
            return false;
        }

        if (!ValidationUtil.isValidId(employeeId)) {
            return false;
        }

        if (employeeDAO.getEmployeeById(employeeId) == null) {
            return false;
        }

        Attendance attendance =
                attendanceDAO.getAttendance(
                        sessionNo,
                        date,
                        employeeId
                );

        if (attendance == null) {
            return false;
        }

        if (attendance.getCheckOut() != null) {
            return false;
        }

        if (!checkOut.isAfter(attendance.getCheckIn())) {
            return false;
        }

        return attendanceDAO.updateAttendance(
                date,
                sessionNo,
                checkOut,
                employeeId
        );
    }


    // Get one attendance record
    public Attendance getAttendance(int sessionNo,
                                    LocalDate date,
                                    int employeeId) {

        if (!ValidationUtil.isValidId(sessionNo)) {
            return null;
        }

        if (date == null) {
            return null;
        }

        if (!ValidationUtil.isValidId(employeeId)) {
            return null;
        }

        return attendanceDAO.getAttendance(
                sessionNo,
                date,
                employeeId
        );
    }


    // Get attendance of one employee
    public List<Attendance> getAttendanceByEmployeeId(int employeeId) {

        if (!ValidationUtil.isValidId(employeeId)) {
            return List.of();
        }

        return attendanceDAO.getAttendanceByEmployeeId(employeeId);
    }


    // Get all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceDAO.getAllAttendance();
    }


    private boolean isValidMode(String mode) {

        return mode != null &&
                (
                        mode.equalsIgnoreCase("OFFICE") ||
                                mode.equalsIgnoreCase("REMOTE")
                );
    }
}