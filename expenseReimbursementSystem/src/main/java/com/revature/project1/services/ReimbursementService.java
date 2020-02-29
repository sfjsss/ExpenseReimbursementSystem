package com.revature.project1.services;

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
}
