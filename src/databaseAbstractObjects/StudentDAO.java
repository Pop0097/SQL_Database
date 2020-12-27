package databaseAbstractObjects;

import objectClasses.Student;

import java.sql.*;
import java.util.*;
import java.sql.*;
import java.io.*;

public class StudentDAO {
	
	private Connection myConn;
	
	public StudentDAO() throws Exception {
		
		// Create connection
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user,password);
	}
	
	public List<Student> getAllStudents() throws Exception {
		List<Student> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM student");
			
			while (myRs.next()) {
				Student tempStu = convertRowToStudent(myRs);
				list.add(tempStu);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Student> searchStudents(String fname) throws Exception {
		List<Student> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			fname += "%"; // Done so we can use the like command in SQL
			myStmt = myConn.prepareStatement("SELECT * FROM student WHERE client_fname LIKE ?");
			
			myStmt.setString(1, fname);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Student tempStu = convertRowToStudent(myRs);
				list.add(tempStu);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	private Student convertRowToStudent(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("client_id");
		String lname = myRs.getString("client_fname");
		String fname = myRs.getString("client_lname");
		String email = myRs.getString("client_email");
		int plan = myRs.getInt("client_plan");
		
		Student tempStudent = null;
		
		try {
			tempStudent = new Student(id, fname, lname, email, plan);
		} catch (Exception ex) {
			System.out.println("Creating student not successful");
		}
		
		return tempStudent;
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
//		StudentDAO dao = new StudentDAO();
//		System.out.println(dao.searchStudents("av"));
//
//		System.out.println(dao.getAllStudents());
//	}	
}
