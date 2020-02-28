package com.revature.project1.delegates;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

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
		String hashedPw = BCrypt.hashpw("password", BCrypt.gensalt());
		employee.setPass(hashedPw);
		
		int result = es.createEmployee(employee);
		if (result != 0) {
			response.setStatus(200);
			try {
				emailService.sendEmail(request.getParameter("email"), "ERS: you have been registered", "email: " + request.getParameter("email") + "\npassword: password \nPlease change your password after your logged in.");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} else {
			response.sendError(400);
		}
	}
}
