package com.revature.project1.services;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.revature.project1.daos.EmployeeDao;
import com.revature.project1.daos.EmployeeDaoImpl;
import com.revature.project1.models.Employee;

public class EmployeeService {
	
	private EmployeeDao ed = new EmployeeDaoImpl();
	private EmailService es = new EmailService();

	public int createEmployee(Employee e) {
		return ed.createEmployee(e);
	}
	
	public String authenticateEmployee(String email, String password) {
		Employee employee = ed.isEmailExist(email);
		String token = null;
		
		if (employee != null && BCrypt.checkpw(password, employee.getPass())) {
			token = employee.getEmployee_id() + "&" + employee.getEmployee_type() + "&" + employee.getEmail() + "&" + employee.getFirst_name() + "&" + employee.getLast_name();
		}
		
		return token;
	}
	
	public boolean isUserValid(String email, int employee_id) {
		Employee employee = ed.isEmailExist(email);
		
		if (employee != null && employee.getEmployee_id() == employee_id) {
			return true;
		} else {
			return false;
		}
	}
	
	public Employee getEmployeeByEmail(String email) {
		return ed.isEmailExist(email);
	}
	
	public boolean updateEmployeeProfile(Employee e) {
		int result = ed.updateEmployee(e);
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<Employee> getAllEmployees() {
		return ed.getAllEmployees();
	}
	
	public boolean resetPassword(String email) {
		String randomGeneratedPassword = RandomStringUtils.randomAlphabetic(8);
		int result = ed.resetPassword(email, randomGeneratedPassword);
		if (result != 0) {
			try {
				es.sendEmail(email, "ERS: your password has been reset", "Your password has been reset to " + randomGeneratedPassword + ". Please login and change it.");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
