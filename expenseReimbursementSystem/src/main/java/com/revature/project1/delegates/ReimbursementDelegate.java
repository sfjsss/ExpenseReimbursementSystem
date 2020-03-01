package com.revature.project1.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project1.models.Employee;
import com.revature.project1.models.Reimbursement;
import com.revature.project1.services.EmployeeService;
import com.revature.project1.services.ReimbursementService;

public class ReimbursementDelegate {

	private EmployeeService es = new EmployeeService();
	private ReimbursementService rs = new ReimbursementService();
	
	public void submitReimbursement(HttpServletRequest request, HttpServletResponse response) {
		
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String userEmail = request.getParameter("userEmail");
		if (userId == null || userEmail == null || !es.isUserValid(userEmail, userId)) {
			try {
				response.sendError(401);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		String type = request.getParameter("type");
		String date = request.getParameter("date");		
		String amount = request.getParameter("amount");
		Double parsedAmount = Double.parseDouble(amount);
		String description = request.getParameter("description");
		Part file = null;
		try {
			file = request.getPart("file");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		String fileName = Paths.get(file.getSubmittedFileName()).getFileName().toString();
		String file_path = "/Users/macair/Desktop/revature/projectOne/localServer/receipts_storage/" + fileName;
		
		if (!fileName.equals("")) {
			try {
				file.write(file_path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		Employee requester = new Employee();
		requester.setEmployee_id(userId);
				
		Reimbursement reim = new Reimbursement();
		reim.setReimbursement_type(type);
		reim.setReimbursement_time(date);
		reim.setReimbursement_amount(parsedAmount);
		reim.setReimbursement_description(description);
		reim.setReceipt_name(fileName);
		reim.setReceipt_path("/receipts_storage/" + fileName);
		reim.setReimbursement_status("pending");
		reim.setRequester(requester);
		
		boolean result = rs.submitReimbursement(reim);
		
		if (result) {
			try {
				response.sendRedirect("/employee-pending?submission=true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.sendError(400);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getReimbursements(HttpServletRequest request, HttpServletResponse response) {
				
		Integer employeeId = Integer.parseInt(request.getParameter("employeeId"));
		String type = request.getParameter("type");
		List<Reimbursement> reims = new ArrayList<>();
		
		if (employeeId != 0 && type.equals("pending")) {
			reims = rs.getAllPendingReimbursementsById(employeeId, type);
		} else if (employeeId == 0 && type.equals("pending")) {
			reims = rs.getAllReimbursementsByStatus(type);
		} else if (employeeId != 0 && type.equals("resolved")) {
			reims = rs.getAllResolvedReimbursementsById(employeeId);
		} else {
			try {
				response.sendError(404, "Request record(s) not found.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try (PrintWriter pw = response.getWriter()) {
			pw.write(new ObjectMapper().writeValueAsString(reims));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateReimbursement(HttpServletRequest request, HttpServletResponse response) {
		
		Integer managerId = Integer.parseInt(request.getParameter("managerId"));
		Integer reimbursementId = Integer.parseInt(request.getParameter("reimbursementId"));
		String operation = request.getParameter("operation");
		
		boolean result = rs.updateReimbursement(managerId, reimbursementId, operation);
		
		if (result) {
			response.setStatus(200);
		} else {
			try {
				response.sendError(400);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
