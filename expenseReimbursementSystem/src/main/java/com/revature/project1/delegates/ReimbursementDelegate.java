package com.revature.project1.delegates;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
		
		Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsedDate = dateFormat.parse(date);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
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
		String file_path = "/Users/macair/Desktop/revature/projectOne/receipts_storage/" + fileName;
		System.out.println(type);
		System.out.println(date);
		System.out.println(timestamp);
		System.out.println(amount);
		System.out.println(parsedAmount);
		System.out.println(description);
		System.out.println(file);
		System.out.println(fileName);
		System.out.println(file_path);
		
		try {
			file.write(file_path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Employee requester = new Employee();
		requester.setEmployee_id(userId);
				
		Reimbursement reim = new Reimbursement();
		reim.setReimbursement_type(type);
		reim.setReimbursement_time(timestamp);
		reim.setReimbursement_amount(parsedAmount);
		reim.setReimbursement_description(description);
		reim.setReceipt_name(fileName);
		reim.setReceipt_path(file_path);
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
}
