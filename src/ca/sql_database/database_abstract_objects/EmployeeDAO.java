package ca.sql_database.database_abstract_objects;

import java.sql.*;
import java.util.*;

import ca.sql_database.object_classes.Employee;

import java.io.*;

public class EmployeeDAO {
	
	private Connection myConn;
	
	public EmployeeDAO() throws Exception {
		
		// Establish database Connection
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user,password);
	}
	
	// Method returns all employees that are in the database
	public List<Employee> getAllEmployees() throws Exception {
		List<Employee> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// Executes query
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM employee");
			
			// Adds all employees to the list
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
	
	// Method returns all employees that match the search
	public List<Employee> searchEmployees(String fname) throws Exception {
		List<Employee> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// Executes query
			fname += "%"; // Done so we can use the like command in SQL
			myStmt = myConn.prepareStatement("SELECT * FROM employee WHERE employee_fname LIKE ?");
			myStmt.setString(1, fname);
			myRs = myStmt.executeQuery();
			
			// Adds results to the list
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
	
	// Method adds row to the employee table
	public void createEmployee( String fname, String lname, String e) throws SQLException {
		PreparedStatement createEmployeeStatement = null;
		
		try {
			// Creates prepared statement and initializes place holders
			createEmployeeStatement = myConn.prepareStatement("INSERT INTO employee (employee_fname, employee_lname, employee_email) VALUES (?,?,?)");
			
			createEmployeeStatement.setString(1, fname); 
			createEmployeeStatement.setString(2, lname);
			createEmployeeStatement.setString(3, e);
	 		
			// Executes query
			createEmployeeStatement.executeUpdate();
		}
		finally {
			createEmployeeStatement.close();
		}
	}
	
	// Method updates a row in the employee table
	public void updateEmployee(String fname, String lname, String email, int id) throws SQLException {
		PreparedStatement updateEmployeeStatement = null;

		try {
			// Creates prepared statement and initializes place holders
			updateEmployeeStatement = myConn.prepareStatement("UPDATE employee SET employee_fname=?, employee_lname=?, employee_email=? WHERE employee_id=?");
			
			updateEmployeeStatement.setString(1, fname);
			updateEmployeeStatement.setString(2, lname);
			updateEmployeeStatement.setString(3, email);
			updateEmployeeStatement.setInt(4, id);
	 		
			// Executes query
			updateEmployeeStatement.executeUpdate();
		}
		finally {
			updateEmployeeStatement.close();
		}
		
	}
	
	// Method removes a row in the employee table
	public void deleteEmployee(int id) throws SQLException {
		PreparedStatement deleteEmployeeStatement = null;
		
		try {
			// Creates prepared statement and initializes place holders
			deleteEmployeeStatement = myConn.prepareStatement("DELETE FROM employee WHERE employee_id=?");
			
			deleteEmployeeStatement.setInt(1, id);
	  		
			// Executes query
			deleteEmployeeStatement.executeUpdate();
		}
		finally {
			deleteEmployeeStatement.close();
		}		
	}

	// Converts a row in the employee table to an Employee object
	private Employee convertRowToEmployee(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("employee_id");
		String fname = myRs.getString("employee_fname");
		String lname = myRs.getString("employee_lname");
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
}
