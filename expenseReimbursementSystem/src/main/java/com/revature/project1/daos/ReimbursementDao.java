package com.revature.project1.daos;

import java.util.List;

import com.revature.project1.models.Reimbursement;

public interface ReimbursementDao {

	public List<Reimbursement> getAllReimbursementsByEmployeeId(int employeeId, String status);
	public List<Reimbursement> getAllReimbursementsByStatus(String status);
	public int createNewReimbursement(Reimbursement r);
	public int updateReimbursementStatus(int managerId, int reimbursementId, String status);
}
