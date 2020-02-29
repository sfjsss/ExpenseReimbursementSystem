package com.revature.project1.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.project1.daos.ReimbursementDao;
import com.revature.project1.daos.ReimbursementDaoImpl;
import com.revature.project1.models.Reimbursement;

public class ReimbursementService {

	private ReimbursementDao rd = new ReimbursementDaoImpl();
	
	public boolean submitReimbursement(Reimbursement r) {
		int result = rd.createNewReimbursement(r);
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<Reimbursement> getAllPendingReimbursementsById(int employeeId, String type) {
		
		List<Reimbursement> results = new ArrayList<>();
		
		if (employeeId != 0) {
			System.out.println("rd triggered");
			results = rd.getAllReimbursementsByEmployeeId(employeeId, type);
		}
		
		System.out.println("result size is " + results.size());
		return results;
	}
}
