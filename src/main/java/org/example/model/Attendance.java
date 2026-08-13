package org.example.model;
import java.time.LocalDate;
import java.time.LocalTime;
public class Attendance {
    private int employeeId;
    private int sessionNo;
    private LocalDate attendanceDate;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private String mode;

    public Attendance(int employeeId, int sessionNo,
                      LocalDate attendanceDate, LocalTime checkIn,
                      LocalTime checkOut, String mode) {
        this.employeeId = employeeId;
        this.sessionNo = sessionNo;
        this.attendanceDate = attendanceDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.mode = mode;
    }

    //get the values
    public int getEmployeeId() {
        return employeeId;
    }
    public int getSessionNo() {
        return sessionNo;
    }
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
    public LocalTime getCheckIn() {
        return checkIn;
    }
    public LocalTime getCheckOut() {
        return checkOut;
    }
    public String getMode() {
        return mode;
    }

    //set the values
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public void setSessionNo(int sessionNo) {
        this.sessionNo = sessionNo;
    }
    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }
    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    @Override
    public String toString() {
        return "Attendance " +
                "\n employeeId = " + employeeId +
                "\n sessionNo = " + sessionNo +
                "\n attendanceDate = " + attendanceDate+
                "\n checkIn = " + checkIn +
                "\n checkOut = " + checkOut +
                "\n mode = " + mode;
    }
}
