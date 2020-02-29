package com.revature.project1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.project1.delegates.AuthDelegate;
import com.revature.project1.delegates.EmployeeDelegate;
import com.revature.project1.delegates.ReimbursementDelegate;
import com.revature.project1.delegates.ViewDelegate;

public class RequestHelper {
	
	private ViewDelegate viewDelegate = new ViewDelegate();
	private AuthDelegate authDelegate = new AuthDelegate();
	private EmployeeDelegate employeeDelegate = new EmployeeDelegate();
	private ReimbursementDelegate rd = new ReimbursementDelegate();

	public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if (path.startsWith("/api/")) {
			if (!authDelegate.isAuthorized(request, response)) {
				response.sendError(401);
				return;
			}
		} else {
			viewDelegate.resolveView(request, response);
		}
	}
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getServletPath();
		if (path.startsWith("/api/")) {
			if (!authDelegate.isAuthorized(request, response)) {
				response.sendError(401);
				return;
			}
			
			String record = path.substring(5);
			if (record.startsWith("employees")) {
				employeeDelegate.registerEmployee(request, response);
			} else {
				response.sendError(404, "Request record(s) not found."); 
			}
		} else {
			switch(path) {
			case "/authenticate":
				authDelegate.authenticate(request, response);
				break;
			case "/submit-reimbursement":
				rd.submitReimbursement(request, response);
				break;
			default:
				response.sendError(405);
			}
		}
	}
}
