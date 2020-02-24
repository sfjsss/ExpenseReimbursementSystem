package com.revature.project1.daos;

import java.util.List;

import com.revature.project1.models.Reimbursement;

public interface ReimbursementDao {

	public List<Reimbursement> getAllReimbursementsByEmployeeId(int employeeId);
}
