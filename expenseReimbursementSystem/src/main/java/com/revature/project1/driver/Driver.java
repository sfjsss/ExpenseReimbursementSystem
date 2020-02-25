package com.revature.project1.driver;

import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.project1.daos.EmployeeDao;
import com.revature.project1.daos.EmployeeDaoImpl;
import com.revature.project1.models.Employee;
import com.revature.project1.util.ConnectionUtil;

public class Driver {
	
	private static EmployeeDao ed = new EmployeeDaoImpl();

	public static void main(String[] args) {
		
		try {
			Connection c = ConnectionUtil.getConnection();
			String driverName = c.getMetaData().getDriverName();
			System.out.println(driverName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		createManager();
	}
	
	public static void createManager() {
		String email = "alan@l.com";
		String type = "manager";
		String first_name = "alan";
		String last_name = "li";
		String hashedPw = BCrypt.hashpw("password", BCrypt.gensalt());
		
		Employee manager = new Employee();
		manager.setEmail(email);
		manager.setEmployee_type(type);
		manager.setFirst_name(first_name);
		manager.setLast_name(last_name);
		manager.setPass(hashedPw);
		
		ed.createEmployee(manager);
	}

}
