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
	
	public boolean isAuthorized(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getHeader("authorization");
		
		if (authToken != null) {
			String[] decodedToken = authToken.split("&");
			if (es.isUserValid(decodedToken[2], Integer.parseInt(decodedToken[0]))) {
				return true;
			}
		}
		return false;
	}
}
