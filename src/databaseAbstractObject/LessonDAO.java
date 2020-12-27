package databaseAbstractObject;

import objectClasses.Employee;
import objectClasses.Student;
import objectClasses.Lesson;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.sql.*;
import java.io.*;

public class LessonDAO {
	
	private Connection myConn;
	
	public LessonDAO() throws Exception {
		
		// Create connection
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user,password);
	}
	
	public List<Lesson> getAllLessons() throws Exception {
		List<Lesson> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM lesson");
			
			while (myRs.next()) {
				Lesson tempLesson = convertRowToLesson(myRs);
				list.add(tempLesson);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Lesson> searchLessons(int id) throws Exception {
		List<Lesson> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM lesson WHERE lesson_id=?");
			
			myStmt.setInt(1, id);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Lesson tempLesson = convertRowToLesson(myRs);
				list.add(tempLesson);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	private Lesson convertRowToLesson(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("lesson_id");
		Date d = myRs.getDate("lesson_date");
		int stu_id = myRs.getInt("lesson_studentId");
		int emp_id = myRs.getInt("lesson_employeeId");
		
		Lesson tempLesson = null;
		
		try {
			tempLesson = new Lesson(id, d, stu_id, emp_id);
		} catch (Exception ex) {
			System.out.println("Creating Lesson not successful");
		}
		
		return tempLesson;
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
//		LessonDAO dao = new LessonDAO();
//		System.out.println(dao.searchLessons(1));
//
//		System.out.println(dao.getAllLessons());
//	}	
}
