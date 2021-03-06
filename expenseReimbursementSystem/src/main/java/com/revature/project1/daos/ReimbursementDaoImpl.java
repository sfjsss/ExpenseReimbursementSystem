package com.revature.project1.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.project1.models.Employee;
import com.revature.project1.models.Reimbursement;
import com.revature.project1.util.ConnectionUtil;

public class ReimbursementDaoImpl implements ReimbursementDao {

	@Override
	public List<Reimbursement> getAllReimbursementsByEmployeeId(int employeeId, String status) {
		
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id full join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = ? and reimbursement_status = ? order by reimbursement_id desc";
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
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String parsedTime = dateFormat.format(rs.getTimestamp(3));
				
				rb.setReimbursement_time(parsedTime);
				rb.setReimbursement_amount(rs.getDouble(4));
				rb.setReimbursement_description(rs.getString(5));
				rb.setReceipt_name(rs.getString(6));
				rb.setReceipt_path(rs.getString(7));
				rb.setReimbursement_status(rs.getString(8));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(11));
				requester.setEmail(rs.getString(12));
				requester.setEmployee_type(rs.getString(13));
				requester.setFirst_name(rs.getString(14));
				requester.setLast_name(rs.getString(15));
				requester.setPass(rs.getString(16));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(17));
				processor.setEmail(rs.getString(18));
				processor.setEmployee_type(rs.getString(19));
				processor.setFirst_name(rs.getString(20));
				processor.setLast_name(rs.getString(21));
				processor.setPass(rs.getString(22));
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
		String sql = "insert into reimbursement (reimbursement_type, reimbursement_time, reimbursement_amount, reimbursement_description, receipt_name, receipt_path, reimbursement_status, requester_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
		int affectedRows = 0;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, r.getReimbursement_type());
			
			Timestamp timestamp = null;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDate = dateFormat.parse(r.getReimbursement_time());
				timestamp = new Timestamp(parsedDate.getTime());
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
			
			ps.setTimestamp(2, timestamp);
			ps.setDouble(3, r.getReimbursement_amount());
			ps.setString(4, r.getReimbursement_description());
			ps.setString(5, r.getReceipt_name());
			ps.setString(6, r.getReceipt_path());
			ps.setString(7, r.getReimbursement_status());
			ps.setInt(8, r.getRequester().getEmployee_id());
			
			affectedRows = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByStatus(String status) {
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id full join employee as processor on reimbursement.processor_id = processor.employee_id where reimbursement_status = ? order by reimbursement_id desc";
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
				rb.setReimbursement_time(rs.getString(3));
				rb.setReimbursement_amount(rs.getDouble(4));
				rb.setReimbursement_description(rs.getString(5));
				rb.setReceipt_name(rs.getString(6));
				rb.setReceipt_path(rs.getString(7));
				rb.setReimbursement_status(rs.getString(8));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(11));
				requester.setEmail(rs.getString(12));
				requester.setEmployee_type(rs.getString(13));
				requester.setFirst_name(rs.getString(14));
				requester.setLast_name(rs.getString(15));
				requester.setPass(rs.getString(16));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(17));
				processor.setEmail(rs.getString(18));
				processor.setEmployee_type(rs.getString(19));
				processor.setFirst_name(rs.getString(20));
				processor.setLast_name(rs.getString(21));
				processor.setPass(rs.getString(22));
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
	public String updateReimbursementStatus(int managerId, int reimbursementId, String status) {

		String sql = "{call updateReimbursement(?, ?, ?)}";
		ResultSet rs = null;
		String email = null;
		
		try (Connection c = ConnectionUtil.getConnection();
				CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, managerId);
			cs.setInt(2, reimbursementId);
			cs.setString(3, status);
	
			cs.execute();
			
			rs = cs.getResultSet();
			
			while (rs.next()) {
				email = rs.getString(1);
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
		
		return email;
	}

	@Override
	public List<Reimbursement> getAllResolvedReimbursementById(int employeeId) {
		
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = ? and reimbursement_status != 'pending' order by reimbursement_id desc";
		ResultSet rs = null;
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setInt(1, employeeId);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Reimbursement rb = new Reimbursement();
				rb.setReimbursement_id(rs.getInt(1));
				rb.setReimbursement_type(rs.getString(2));
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String parsedTime = dateFormat.format(rs.getTimestamp(3));
				
				rb.setReimbursement_time(parsedTime);
				rb.setReimbursement_amount(rs.getDouble(4));
				rb.setReimbursement_description(rs.getString(5));
				rb.setReceipt_name(rs.getString(6));
				rb.setReceipt_path(rs.getString(7));
				rb.setReimbursement_status(rs.getString(8));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(11));
				requester.setEmail(rs.getString(12));
				requester.setEmployee_type(rs.getString(13));
				requester.setFirst_name(rs.getString(14));
				requester.setLast_name(rs.getString(15));
//				requester.setPass(rs.getString(16));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(17));
				processor.setEmail(rs.getString(18));
				processor.setEmployee_type(rs.getString(19));
				processor.setFirst_name(rs.getString(20));
				processor.setLast_name(rs.getString(21));
//				processor.setPass(rs.getString(22));
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
	public List<Reimbursement> getAllResolvedReimbursements() {
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where reimbursement_status != 'pending' order by reimbursement_id desc";
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try (Connection c = ConnectionUtil.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql)) {
									
			while (rs.next()) {
				Reimbursement rb = new Reimbursement();
				rb.setReimbursement_id(rs.getInt(1));
				rb.setReimbursement_type(rs.getString(2));
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String parsedTime = dateFormat.format(rs.getTimestamp(3));
				
				rb.setReimbursement_time(parsedTime);
				rb.setReimbursement_amount(rs.getDouble(4));
				rb.setReimbursement_description(rs.getString(5));
				rb.setReceipt_name(rs.getString(6));
				rb.setReceipt_path(rs.getString(7));
				rb.setReimbursement_status(rs.getString(8));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(11));
				requester.setEmail(rs.getString(12));
				requester.setEmployee_type(rs.getString(13));
				requester.setFirst_name(rs.getString(14));
				requester.setLast_name(rs.getString(15));
//				requester.setPass(rs.getString(16));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(17));
				processor.setEmail(rs.getString(18));
				processor.setEmployee_type(rs.getString(19));
				processor.setFirst_name(rs.getString(20));
				processor.setLast_name(rs.getString(21));
//				processor.setPass(rs.getString(22));
				rb.setProcessor(processor);
				
				reimbursements.add(rb);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reimbursements;	
	}

	@Override
	public List<Reimbursement> getAllReimbursementsByTypeAndName(String type, String firstName, String lastName) {
		
		String sql;
		if (type.equals("pending")) {
			sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id full join employee as processor on reimbursement.processor_id = processor.employee_id where reimbursement_status = 'pending' and requester.first_name = ? and requester.last_name = ? order by reimbursement_id desc";
		} else {
			sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id full join employee as processor on reimbursement.processor_id = processor.employee_id where reimbursement_status != 'pending' and requester.first_name = ? and requester.last_name = ? order by reimbursement_id desc";
		}
				
		ResultSet rs = null;
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Reimbursement rb = new Reimbursement();
				rb.setReimbursement_id(rs.getInt(1));
				rb.setReimbursement_type(rs.getString(2));
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String parsedTime = dateFormat.format(rs.getTimestamp(3));
				
				rb.setReimbursement_time(parsedTime);
				rb.setReimbursement_amount(rs.getDouble(4));
				rb.setReimbursement_description(rs.getString(5));
				rb.setReceipt_name(rs.getString(6));
				rb.setReceipt_path(rs.getString(7));
				rb.setReimbursement_status(rs.getString(8));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(11));
				requester.setEmail(rs.getString(12));
				requester.setEmployee_type(rs.getString(13));
				requester.setFirst_name(rs.getString(14));
				requester.setLast_name(rs.getString(15));
//				requester.setPass(rs.getString(16));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(17));
				processor.setEmail(rs.getString(18));
				processor.setEmployee_type(rs.getString(19));
				processor.setFirst_name(rs.getString(20));
				processor.setLast_name(rs.getString(21));
//				processor.setPass(rs.getString(22));
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
		
		System.out.println(reimbursements.size());
		return reimbursements;			
	}

}
