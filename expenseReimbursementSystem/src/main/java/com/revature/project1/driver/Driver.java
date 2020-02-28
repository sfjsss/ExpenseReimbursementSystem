package com.revature.project1.driver;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.project1.util.ConnectionUtil;

public class Driver {
	
	public static void main(String[] args) {
		
		try {
			Connection c = ConnectionUtil.getConnection();
			String driverName = c.getMetaData().getDriverName();
			System.out.println(driverName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
