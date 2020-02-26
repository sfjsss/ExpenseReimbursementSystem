package com.revature.project1.daos;

import java.util.List;

import com.revature.project1.models.Employee;

public interface EmployeeDao {

	public int createEmployee(Employee e);
	public Employee isEmailExist(String email);
	public int updateEmployee(Employee e);
	public List<Employee> getAllEmployees();
}
