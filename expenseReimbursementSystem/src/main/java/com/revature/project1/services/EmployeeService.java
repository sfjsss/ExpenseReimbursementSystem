package com.revature.project1.services;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.project1.daos.EmployeeDao;
import com.revature.project1.daos.EmployeeDaoImpl;
import com.revature.project1.models.Employee;

public class EmployeeService {
	
	private EmployeeDao ed = new EmployeeDaoImpl();

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
}
