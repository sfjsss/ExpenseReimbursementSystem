package com.revature.project1.delegates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project1.models.Employee;
import com.revature.project1.services.EmailService;
import com.revature.project1.services.EmployeeService;

public class EmployeeDelegate {
	
	private EmployeeService es = new EmployeeService();
	private EmailService emailService = new EmailService();
	
	public void registerEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Employee employee = new Employee();
		employee.setEmail(request.getParameter("email"));
		employee.setFirst_name(request.getParameter("first_name"));
		employee.setLast_name(request.getParameter("last_name"));
		employee.setEmployee_type("associate");
		String randomGeneratedPassword = RandomStringUtils.randomAlphabetic(8);
		String hashedPw = BCrypt.hashpw(randomGeneratedPassword, BCrypt.gensalt());
		employee.setPass(hashedPw);
		
		if (employee.getEmail().equals("") || employee.getFirst_name().equals("") || employee.getLast_name().equals("")) {
			response.sendError(400);
			return;
		}
		
		int result = es.createEmployee(employee);
		if (result != 0) {
			try {
				emailService.sendEmail(request.getParameter("email"), "ERS: you have been registered", "email: " + request.getParameter("email") + "\npassword: " + randomGeneratedPassword + " \nPlease change your password after your logged in.");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else {
			response.sendError(400);
		}
	}
	
	public void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try (BufferedReader requestReader = request.getReader()) {
			String newEmployee = requestReader.readLine();
			ObjectMapper om = new ObjectMapper();
			Employee ne = om.readValue(newEmployee, Employee.class);
			
			String hashedPw = BCrypt.hashpw(ne.getPass(), BCrypt.gensalt());
			ne.setPass(hashedPw);
			
			if (es.updateEmployeeProfile(ne)) {
				response.setStatus(200);
			} else {
				response.sendError(400);
			}
		}	
	}
	
	public void getAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Employee> employees = es.getAllEmployees();
		
		try (PrintWriter pw = response.getWriter()) {
			pw.write(new ObjectMapper().writeValueAsString(employees));
		}
	}
	
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) {
		es.resetPassword(request.getParameter("email"));
	}
}
