package databaseAbstractObject;

import objectClasses.Employee;

import java.sql.*;
import java.util.*;
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
