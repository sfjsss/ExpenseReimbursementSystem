package com.revature.project1.driver;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.revature.project1.models.Employee;
import com.revature.project1.services.EmployeeService;
//
//import com.revature.project1.models.Employee;
//import com.revature.project1.services.EmployeeService;
import com.revature.project1.util.ConnectionUtil;

public class Driver {
	
	private static EmployeeService es = new EmployeeService();
	
	public static void main(String[] args) {
		
		try {
			Connection c = ConnectionUtil.getConnection();
			String driverName = c.getMetaData().getDriverName();
			System.out.println(driverName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createAManager(String email, String firstName, String lastName) {
		Employee m = new Employee();
		m.setEmail(email);
		m.setEmployee_type("manager");
		m.setFirst_name(firstName);
		m.setLast_name(lastName);
		String hashedPW = BCrypt.hashpw("password", BCrypt.gensalt());
		m.setPass(hashedPW);
		es.createEmployee(m);
	}
	
	public static String generateRandomString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}

}
