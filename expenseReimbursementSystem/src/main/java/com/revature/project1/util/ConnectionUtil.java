package com.revature.project1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to load driver");
		}
		
		boolean isTest = Boolean.valueOf(System.getenv("DB_TEST"));
		if (isTest) {
			return getH2Connection();
		}
		
		String url = "jdbc:postgresql://localhost:5432/ers";
		String username = "macair";
		String password = "root";
		
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}
		
		return connection;
	}
	
	public static Connection getH2Connection() {
		
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:h2:~/test");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
