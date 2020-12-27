package ca.sql_database.object_classes;

import java.util.Properties;
import java.io.FileInputStream;
import java.sql.*;

public class Lesson {
	private int id;
	private Date date;
	private int time;
	private int student_id;
	private int employee_id;
	
	private Connection myConn;
	
	public Lesson(int id, Date d, int stu_id, int emp_id, int t) throws Exception {
		this.id = id; 
		this.date = d;
		this.student_id = stu_id;
		this.employee_id = emp_id;
		this.time = t;
		
		// Establishes database connection
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user,password);
	}
	
	public int getId() {
		return this.id;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getStudentId() {
		return this.student_id;
	}
	
	public int getEmployeeId() {
		return this.employee_id;
	}
	
	public String getTime() throws SQLException { 
		// Since the lesson time is stored as an integer (time_id) in the lesson table, we need to convert it to a string by finding the corresponding row in the time table
		PreparedStatement getTime = null;
		
		try {
			// Creates prepared statement to find the row of the time corresponding to the integer in lesson_time
			getTime = myConn.prepareStatement("SELECT * FROM times WHERE time_id=?");
			
			getTime.setInt(1, this.time);
			
			// Executes query
			ResultSet myRs = getTime.executeQuery(); 
			
			// If result is found, return it
			if (myRs.next()) {
				String toReturn = myRs.getString("time_value");
				return toReturn;
			} else {
				return "";
			}
		} catch (Exception exc) {
			System.out.println("Unsuccessful query - Time find value");
			return "";
		}
		finally {
			getTime.close();
		}
	}
}
