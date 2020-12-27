package ca.sql_database.database_abstract_objects;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import ca.sql_database.object_classes.Employee;
import ca.sql_database.object_classes.Lesson;
import ca.sql_database.object_classes.Student;

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
	
	public List<Lesson> searchLessons(String id) throws Exception {
		List<Lesson> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM lesson WHERE lesson_id=?");
			
			int value = Integer.parseInt(id);
			
			myStmt.setInt(1, value);
			
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
	
	public void createLesson(int stuId, int empId, Date date, int time) throws SQLException {
		PreparedStatement createLessonStatement = null;		
		try {
			createLessonStatement = myConn.prepareStatement("INSERT INTO LESSON(lesson_studentId, lesson_employeeId, lesson_date, lesson_time) VALUES (?,?,?,?)");
			
			createLessonStatement.setInt(1, stuId);
			createLessonStatement.setInt(2, empId);
			createLessonStatement.setDate(3, date);
			createLessonStatement.setInt(4, time);
			// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
			createLessonStatement.executeUpdate();
		}
		finally {
			createLessonStatement.close();
		}
	}
	
	public void updateLesson(int stuId, int empId, Date date, int time, int id) throws SQLException {
		PreparedStatement updateLessonStatement = null;		
		try {
			updateLessonStatement = myConn.prepareStatement("UPDATE lesson SET lesson_studentId=?, lesson_employeeId=?, lesson_date=?, lesson_time=? WHERE lesson_id=?");
			
			updateLessonStatement.setInt(1, stuId);
			updateLessonStatement.setInt(2, empId);
			updateLessonStatement.setDate(3, date);
			updateLessonStatement.setInt(4, time);
			updateLessonStatement.setInt(5, id);
			// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
			updateLessonStatement.executeUpdate();
		}
		finally {
			updateLessonStatement.close();
		}
	}
	
	private Lesson convertRowToLesson(ResultSet myRs) throws SQLException {
		int id = myRs.getInt("lesson_id");
		Date d = myRs.getDate("lesson_date");
		int stu_id = myRs.getInt("lesson_studentId");
		int emp_id = myRs.getInt("lesson_employeeId");
		int time = myRs.getInt("lesson_time");
		
		Lesson tempLesson = null;
		
		try {
			tempLesson = new Lesson(id, d, stu_id, emp_id, time);
		} catch (Exception exc) {
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
