package com.revature.project1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.project1.delegates.AuthDelegate;
import com.revature.project1.delegates.EmployeeDelegate;
import com.revature.project1.delegates.ViewDelegate;

public class RequestHelper {
	
	private ViewDelegate viewDelegate = new ViewDelegate();
	private AuthDelegate authDelegate = new AuthDelegate();
	private EmployeeDelegate employeeDelegate = new EmployeeDelegate();

	public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if (path.startsWith("/api/")) {
			
		} else {
			viewDelegate.resolveView(request, response);
		}
	}
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getServletPath();
		switch(path) {
		case "/authenticate":
			authDelegate.authenticate(request, response);
			break;
		case "/api/employees":
			employeeDelegate.registerEmployee(request, response);
			break;
		default:
			response.sendError(405);
		}
	}
}
