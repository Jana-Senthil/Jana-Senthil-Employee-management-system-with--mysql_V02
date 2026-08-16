package org.example.dao;

import org.example.config.DBConnection;
import org.example.model.LeaveRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {

    public void addLeaveReqest(int employeeId, String leaveType,
                               LocalDate startDate, LocalDate endDate,
                               String leaveReason,LocalDate applyDate) {
        String sql= "insert into leave_request" +
                "(employee_id,leave_type,start_date,end_date,reason,applied_date) values(?,?,?,?,?,?)";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, employeeId);
            ps.setString(2, leaveType);
            ps.setDate(3,java.sql.Date.valueOf(startDate));
            ps.setDate(4,java.sql.Date.valueOf(endDate));
            ps.setString(5,leaveReason);
            ps.setDate(6,java.sql.Date.valueOf(applyDate));
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("leave request has been added successfully");
            }else{
                System.out.println("leave request has been added failed");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateLeaveRequestByManager(int leaveId, int manager_id, String manager_comment,String status){
        String sql = "update leave_request set manager_id = ?, manager_comment = ?, status = ? " +
                "where leave_id = ? ";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, manager_id);
            ps.setString(2, manager_comment);
            ps.setString(3, status);
            ps.setInt(4, leaveId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("leave request has been updated successfully");
            }else{
                System.out.println("leave request has been updated failed");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateLeaveRequestByEmployee(int leaveId,int employeeId, String leaveType,
                                             LocalDate startDate, LocalDate endDate,
                                             String leaveReason,LocalDate applyDate){
        String sql = "update leave_request set leave_type = ?, start_date = ?, end_date = ?, reason = ?, applied_date = ?" +
                "where leave_id = ? and employee_id = ? and status = 'Pending'";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, leaveType);
            ps.setDate(2,java.sql.Date.valueOf(startDate));
            ps.setDate(3,java.sql.Date.valueOf(endDate));
            ps.setString(4,leaveReason);
            ps.setDate(5,java.sql.Date.valueOf(applyDate));
            ps.setInt(6, leaveId);
            ps.setInt(7, employeeId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("leave request has been updated successfully");
            }else {
                System.out.println("leave request has been updated failed");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteLeaveRequestByLeaveId(int leaveId, int employeeId){
        String sql = "delete from leave_request where leave_id = ? and status = 'Pending' and employee_id = ? ";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, leaveId);
            ps.setInt(2, employeeId);
            int row = ps.executeUpdate();
            if(row>0){
                System.out.println("leave request has been deleted successfully");
            }else  {
                System.out.println("leave request has been deleted failed");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public LeaveRequest getLeaveRequestByLeaveId(int leaveId){
        String sql = "select * from leave_request where leave_id = ?";
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, leaveId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String leaveType = rs.getString("leave_type");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();
                String leaveReason = rs.getString("leave_reason");
                LocalDate applyDate = rs.getDate("applied_date").toLocalDate();
                String status = rs.getString("status");
                Integer managerId = rs.getInt("manager_id");
                String manager_comment = rs.getString("manager_comment");
                return new LeaveRequest(leaveId, employeeId, leaveType, startDate, endDate, leaveReason, applyDate, status, managerId, manager_comment);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<LeaveRequest> getLeaveRequestByEmployeeId(int employeeId){
        String sql = "select * from leave_request where employee_id = ?";
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int leaveId = rs.getInt("leave_id");
                String leaveType = rs.getString("leave_type");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();
                String leaveReason = rs.getString("reason");
                LocalDate applyDate = rs.getDate("applied_date").toLocalDate();
                String status = rs.getString("status");
                Integer managerId = rs.getObject("manager_id", Integer.class);
                String manager_comment = rs.getString("manager_comment");
                LeaveRequest leaveRequest = new LeaveRequest(leaveId, employeeId, leaveType, startDate, endDate, leaveReason, applyDate, status, managerId, manager_comment);
                leaveRequests.add(leaveRequest);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return leaveRequests;
    }

    public List<LeaveRequest> getAllLeaveRequest(){
        String sql = "select * from leave_request";
        List<LeaveRequest> leaveRequestLists = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int leaveId = rs.getInt("leave_id");
                int employeeId = rs.getInt("employee_id");
                String leaveType = rs.getString("leave_type");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();
                String leaveReason = rs.getString("reason");
                LocalDate applyDate = rs.getDate("applied_date").toLocalDate();
                String status = rs.getString("status");
                Integer managerId = rs.getObject("manager_id", Integer.class);
                String manager_comment = rs.getString("manager_comment");
                LeaveRequest leaveRequest = new LeaveRequest(leaveId, employeeId, leaveType, startDate, endDate, leaveReason, applyDate, status, managerId, manager_comment);
                leaveRequestLists.add(leaveRequest);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return leaveRequestLists;
    }

}
