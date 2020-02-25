package com.revature.project1.driver;

import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.project1.models.Employee;
import com.revature.project1.services.EmailService;
import com.revature.project1.services.EmployeeService;
import com.revature.project1.util.ConnectionUtil;

public class Driver {
	
	private static EmployeeService es = new EmployeeService();
	private static EmailService ems = new EmailService();
	
	public static void main(String[] args) {
		
		try {
			Connection c = ConnectionUtil.getConnection();
			String driverName = c.getMetaData().getDriverName();
			System.out.println(driverName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ems.sendEmail("alandron06281990@gmail.com", "sent from java", "does this work?");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createManager(String email_input, String first_name_input, String last_name_input, String pass_input) {
		String email = email_input;
		String type = "manager";
		String first_name = first_name_input;
		String last_name = last_name_input;
		String hashedPw = BCrypt.hashpw(pass_input, BCrypt.gensalt());
		
		Employee manager = new Employee();
		manager.setEmail(email);
		manager.setEmployee_type(type);
		manager.setFirst_name(first_name);
		manager.setLast_name(last_name);
		manager.setPass(hashedPw);
		
		es.createEmployee(manager);
	}

}
