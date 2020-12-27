package ca.sql_database.database_abstract_objects;

import java.sql.*;
import java.util.*;

import ca.sql_database.object_classes.Student;

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
	
	public void createStudent(String fname, String lname, String email, int plan) throws SQLException {
		PreparedStatement createStudentStatement = null;
		
		try {
			createStudentStatement = myConn.prepareStatement("INSERT INTO student (client_fname, client_lname, client_email, client_plan) VALUES (?,?,?,?)");
			
			createStudentStatement.setString(1, fname); 
			createStudentStatement.setString(2, lname);
			createStudentStatement.setString(3, email);
			createStudentStatement.setInt(4, plan);
	 		
			createStudentStatement.executeUpdate();
		}
		finally {
			createStudentStatement.close();
		}
	}
	
	public void updateStudent(String fname, String lname, String email, int plan, int id) throws SQLException {
		PreparedStatement updateStudentStatement = null;
		
		try {
			updateStudentStatement = myConn.prepareStatement("UPDATE student SET client_fname=?, client_lname=?, client_email=?, client_plan=? WHERE client_id=?");
			
			updateStudentStatement.setString(1, fname); 
			updateStudentStatement.setString(2, lname);
			updateStudentStatement.setString(3, email);
			updateStudentStatement.setInt(4, plan);
			updateStudentStatement.setInt(5, id);
	 		
			updateStudentStatement.executeUpdate();
		}
		finally {
			updateStudentStatement.close();
		}
	}
	
	public void deleteStudent(int id) throws SQLException {
		PreparedStatement deleteStudentStatement = null;
		
		try {
			deleteStudentStatement = myConn.prepareStatement("DELETE FROM student WHERE client_id=?");
			
			deleteStudentStatement.setInt(1, id);
	  		
			deleteStudentStatement.executeUpdate();
			deleteStudentStatement.close();
		}
		finally {
			deleteStudentStatement.close();
		}		
	}
	
	private Student convertRowToStudent(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("client_id");
		String fname = myRs.getString("client_fname");
		String lname = myRs.getString("client_lname");
		String email = myRs.getString("client_email");
		int plan = myRs.getInt("client_plan");
				
		Student tempStudent = null;
		
		try {
			tempStudent = new Student(id, fname, lname, email, plan);
		} catch (Exception exc) {
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
