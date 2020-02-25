package com.revature.project1.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.project1.models.Employee;
import com.revature.project1.util.ConnectionUtil;

public class EmployeeDaoImpl implements EmployeeDao {

	public int createEmployee(Employee e) {
		String sql = "insert into employee (email, employee_type, first_name, last_name, pass) values (?, ?, ?, ?, ?)";
		int affectedRows = 0;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, e.getEmail());
			ps.setString(2, e.getEmployee_type());
			ps.setString(3, e.getFirst_name());
			ps.setString(4, e.getLast_name());
			ps.setString(5, e.getPass());
			
			affectedRows = ps.executeUpdate();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return affectedRows;
	}

	@Override
	public Employee isEmailExist(String email) {
		String sql = "select * from employee where email = ?";
		ResultSet rs = null;
		Employee em = null;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				em = new Employee();
				em.setEmployee_id(rs.getInt(1));
				em.setEmail(rs.getString(2));
				em.setEmployee_type(rs.getString(3));
				em.setFirst_name(rs.getString(4));
				em.setLast_name(rs.getString(5));
				em.setPass(rs.getString(6));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return em;
	}

	@Override
	public int updateEmployee(Employee e) {
		String sql = "update employee set email = ?, first_name = ?, last_name = ?, pass = ? where employee_id = ?";
		int affectedRows = 0;
		
		try (Connection c = ConnectionUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, e.getEmail());
			ps.setString(2, e.getFirst_name());
			ps.setString(3, e.getLast_name());
			ps.setString(4, e.getPass());
			ps.setInt(5, e.getEmployee_id());
			
			affectedRows = ps.executeUpdate();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return affectedRows;
	}
	
	

}
