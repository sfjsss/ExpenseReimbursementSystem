package com.revature.project1.driver;

import java.sql.Connection;
import java.sql.SQLException;

//import org.mindrot.jbcrypt.BCrypt;
//
//import com.revature.project1.models.Employee;
//import com.revature.project1.services.EmployeeService;
import com.revature.project1.util.ConnectionUtil;

public class Driver {
	
//	private static EmployeeService es = new EmployeeService();
	
	public static void main(String[] args) {
		
		try {
			Connection c = ConnectionUtil.getConnection();
			String driverName = c.getMetaData().getDriverName();
			System.out.println(driverName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//create a manager
//		Employee m = new Employee();
//		m.setEmail("alan@l.com");
//		m.setEmployee_type("manager");
//		m.setFirst_name("Alan");
//		m.setLast_name("Li");
//		String hashedPW = BCrypt.hashpw("password", BCrypt.gensalt());
//		m.setPass(hashedPW);
//		es.createEmployee(m);
		
	}

}
