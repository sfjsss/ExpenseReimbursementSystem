package com.revature.project1.services;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.revature.project1.daos.ReimbursementDao;
import com.revature.project1.daos.ReimbursementDaoImpl;
import com.revature.project1.models.Reimbursement;

public class ReimbursementService {

	private ReimbursementDao rd = new ReimbursementDaoImpl();
	private EmailService es = new EmailService();
	
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
			results = rd.getAllReimbursementsByEmployeeId(employeeId, type);
		}
		
		return results;
	}
	
	public List<Reimbursement> getAllReimbursementsByStatusAndName(String type, String firstName, String lastName) {
		return rd.getAllReimbursementsByTypeAndName(type, firstName, lastName);
	}
	
	public List<Reimbursement> getAllResolvedReimbursementsById(int employeeId) {
		return rd.getAllResolvedReimbursementById(employeeId);
	}
	
	public List<Reimbursement> getAllResolvedReimbursements() {
		return rd.getAllResolvedReimbursements();
	}
	
	public List<Reimbursement> getAllReimbursementsByStatus(String type) {	
		 return rd.getAllReimbursementsByStatus(type);
	}
	
	public boolean updateReimbursement(int managerId, int reimbursementId, String status) {
		String email = rd.updateReimbursementStatus(managerId, reimbursementId, status);
		if (email != null) {
			try {
				es.sendEmail(email, "ERS: you have a new update", "Your reimbursement with id: " + reimbursementId + " has been " + status + "\nLogin to your ERS to view detail.");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
