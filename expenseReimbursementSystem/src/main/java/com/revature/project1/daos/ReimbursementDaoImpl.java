package com.revature.project1.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.project1.models.Employee;
import com.revature.project1.models.Reimbursement;
import com.revature.project1.util.ConnectionUtil;

public class ReimbursementDaoImpl implements ReimbursementDao {

	@Override
	public List<Reimbursement> getAllReimbursementsByEmployeeId(int employeeId, String status) {
		
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = ? and reimbursement_status = ?";
		ResultSet rs = null;
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setInt(1, employeeId);
			ps.setString(2, status);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Reimbursement rb = new Reimbursement();
				rb.setReimbursement_id(rs.getInt(1));
				rb.setReimbursement_type(rs.getString(2));
				rb.setReimbursement_time(rs.getTimestamp(3));
				rb.setReimbursement_description(rs.getString(4));
				rb.setReceipt_name(rs.getString(5));
				rb.setReceipt_path(rs.getString(6));
				rb.setReimbursement_status(rs.getString(7));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(10));
				requester.setEmail(rs.getString(11));
				requester.setEmployee_type(rs.getString(12));
				requester.setFirst_name(rs.getString(13));
				requester.setLast_name(rs.getString(14));
				requester.setPass(rs.getString(15));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(16));
				processor.setEmail(rs.getString(17));
				processor.setEmployee_type(rs.getString(18));
				processor.setFirst_name(rs.getString(19));
				processor.setLast_name(rs.getString(20));
				processor.setPass(rs.getString(21));
				rb.setProcessor(processor);
				
				reimbursements.add(rb);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return reimbursements;
	}

	@Override
	public int createNewReimbursement(Reimbursement r) {
		String sql = "insert into reimbursement (reimbursement_type, reimbursement_time, reimbursement_description, receipt_name, receipt_path, reimbursement_status, requester_id, processor_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
		int affectedRows = 0;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, r.getReimbursement_type());
			ps.setTimestamp(2, r.getReimbursement_time());
			ps.setString(3, r.getReimbursement_description());
			ps.setString(4, r.getReceipt_name());
			ps.setString(5, r.getReceipt_path());
			ps.setString(6, r.getReimbursement_status());
			ps.setInt(7, r.getRequester().getEmployee_id());
			ps.setInt(8, r.getProcessor().getEmployee_id());
			
			affectedRows = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByStatus(String status) {
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where reimbursement_status = ?";
		ResultSet rs = null;
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, status);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Reimbursement rb = new Reimbursement();
				rb.setReimbursement_id(rs.getInt(1));
				rb.setReimbursement_type(rs.getString(2));
				rb.setReimbursement_time(rs.getTimestamp(3));
				rb.setReimbursement_description(rs.getString(4));
				rb.setReceipt_name(rs.getString(5));
				rb.setReceipt_path(rs.getString(6));
				rb.setReimbursement_status(rs.getString(7));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(10));
				requester.setEmail(rs.getString(11));
				requester.setEmployee_type(rs.getString(12));
				requester.setFirst_name(rs.getString(13));
				requester.setLast_name(rs.getString(14));
				requester.setPass(rs.getString(15));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(16));
				processor.setEmail(rs.getString(17));
				processor.setEmployee_type(rs.getString(18));
				processor.setFirst_name(rs.getString(19));
				processor.setLast_name(rs.getString(20));
				processor.setPass(rs.getString(21));
				rb.setProcessor(processor);
				
				reimbursements.add(rb);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return reimbursements;
	}

	@Override
	public int updateReimbursementStatus(int managerId, int reimbursementId, String status) {

		String sql = "update reimbursement set reimbursement_status = ?, processor_id = ? where reimbursement_id = ?";
		int affectedRows = 0;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, status);
			ps.setInt(2, managerId);
			ps.setInt(3, reimbursementId);
	
			affectedRows = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

}
