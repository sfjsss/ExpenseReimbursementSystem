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
	public List<Reimbursement> getAllReimbursementsByEmployeeId(int employeeId) {
		
		String sql = "select * from reimbursement inner join employee as requester on reimbursement.requester_id = requester.employee_id inner join employee as processor on reimbursement.processor_id = processor.employee_id where requester.employee_id = ?";
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
				rb.setReimbursement_time(rs.getTimestamp(3));
				rb.setReimbursement_description(rs.getString(4));
				rb.setReceipt_path(rs.getString(5));
				rb.setReimbursement_status(rs.getString(6));
				
				Employee requester = new Employee();
				requester.setEmployee_id(rs.getInt(9));
				requester.setEmail(rs.getString(10));
				requester.setEmployee_type(rs.getString(11));
				requester.setFirst_name(rs.getString(12));
				requester.setLast_name(rs.getString(13));
				requester.setPass(rs.getString(14));
				rb.setRequester(requester);
				
				Employee processor = new Employee();
				processor.setEmployee_id(rs.getInt(15));
				processor.setEmail(rs.getString(16));
				processor.setEmployee_type(rs.getString(17));
				processor.setFirst_name(rs.getString(18));
				processor.setLast_name(rs.getString(19));
				processor.setPass(rs.getString(20));
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

}
