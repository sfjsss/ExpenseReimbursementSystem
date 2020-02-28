package com.revature.project1.delegates;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.project1.services.EmployeeService;

public class AuthDelegate {
	
	private EmployeeService es = new EmployeeService();

	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		String token = es.authenticateEmployee(email, password);
		if (token != null) {
			response.setHeader("authorization", token);
		} else {
			response.sendError(401);
		}
	}
}
