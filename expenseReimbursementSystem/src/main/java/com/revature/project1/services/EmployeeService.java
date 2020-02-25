package com.revature.project1.services;

import com.revature.project1.daos.EmployeeDao;
import com.revature.project1.daos.EmployeeDaoImpl;
import com.revature.project1.models.Employee;

public class EmployeeService {
	
	private EmployeeDao ed = new EmployeeDaoImpl();

	public int createEmployee(Employee e) {
		return ed.createEmployee(e);
	}
	
}
