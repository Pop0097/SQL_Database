package ca.sql_database.database_abstract_objects;

import java.sql.*;
import java.util.*;

import ca.sql_database.object_classes.Employee;

import java.sql.*;
import java.io.*;

public class EmployeeDAO {
	
	private Connection myConn;
	
	public EmployeeDAO() throws Exception {
		
		// Create connection
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user,password);
	}
	
	public List<Employee> getAllEmployees() throws Exception {
		List<Employee> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM employee");
			
			while (myRs.next()) {
				Employee tempEmp = convertRowToEmployee(myRs);
				list.add(tempEmp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Employee> searchEmployees(String fname) throws Exception {
		List<Employee> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			fname += "%"; // Done so we can use the like command in SQL
			myStmt = myConn.prepareStatement("SELECT * FROM employee WHERE employee_fname LIKE ?");
			
			myStmt.setString(1, fname);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Employee tempEmp = convertRowToEmployee(myRs);
				list.add(tempEmp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void createEmployee( String fname, String lname, String e) throws SQLException {
		PreparedStatement createEmployeeStatement = null;
		
		try {
			createEmployeeStatement = myConn.prepareStatement("INSERT INTO employee (employee_fname, employee_lname, employee_email) VALUES (?,?,?)");
			
			createEmployeeStatement.setString(1, fname); 
			createEmployeeStatement.setString(2, lname);
			createEmployeeStatement.setString(3, e);
	 		
			createEmployeeStatement.executeUpdate();
		}
		finally {
			createEmployeeStatement.close();
		}
	}
	
	public void deleteEmployee(int id) throws SQLException {
		PreparedStatement deleteEmployeeStatement = myConn.prepareStatement("DELETE FROM employee WHERE employee_id=?");

		deleteEmployeeStatement.setInt(1, id);
  		
		deleteEmployeeStatement.executeUpdate();
		deleteEmployeeStatement.close();
	}
	
	public void updateEmployee(String fname, String lname, String email, int id) throws SQLException {
		PreparedStatement updateEmployeeStatement = myConn.prepareStatement("UPDATE employee SET employee_fname=?, employee_lname=?, employee_email=? WHERE employee_id=?");

		updateEmployeeStatement.setString(1, fname);
		updateEmployeeStatement.setString(2, lname);
		updateEmployeeStatement.setString(3, email);
		updateEmployeeStatement.setInt(4, id);
 		
		updateEmployeeStatement.executeUpdate();
		updateEmployeeStatement.close();
	}

	private Employee convertRowToEmployee(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("employee_id");
		String lname = myRs.getString("employee_fname");
		String fname = myRs.getString("employee_lname");
		String email = myRs.getString("employee_email");
		
		Employee tempEmployee = new Employee(id, fname, lname, email);
		
		return tempEmployee;
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}
	
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}
	
//	public static void main(String[] args) throws Exception {
//		
//		EmployeeDAO dao = new EmployeeDAO();
//		System.out.println(dao.searchEmployees("Ji"));
//
//		System.out.println(dao.getAllEmployees());
//	}
}
