package com.revature.project1.daos;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.project1.models.Employee;
import com.revature.project1.util.ConnectionUtil;

public class EmployeeDaoImplTest {

	private EmployeeDao ed = new EmployeeDaoImpl();
	
	@BeforeClass
	public static void setUp() throws SQLException, FileNotFoundException {
		try (Connection c = ConnectionUtil.getConnection()) {
			RunScript.execute(c, new FileReader("setup.sql"));
		}
	}
	
	@Test
	public void testCreateEmployeeWithValidE() {
		Employee e = new Employee();
		e.setEmail("test@test.com");
		e.setEmployee_type("manager");
		e.setFirst_name("alan");
		e.setLast_name("li");
		e.setPass("password");
		
		int result = ed.createEmployee(e);
		assertEquals(1, result);
	}
	
	@Test
	public void testIsEmailExistWithValidEmail() {
		Employee e = new Employee();
		e.setEmployee_id(1);
		e.setEmail("alan@l.com");
		e.setEmployee_type("employee");
		e.setFirst_name("alan");
		e.setLast_name("li");
		e.setPass("password");
		
		Employee result = ed.isEmailExist("alan@l.com");
		assertEquals(e, result);
	}
	
	@AfterClass
	public static void tearDown() throws SQLException, FileNotFoundException {
		try (Connection c = ConnectionUtil.getConnection()) {
			RunScript.execute(c, new FileReader("teardown.sql"));
		}
	}
}
