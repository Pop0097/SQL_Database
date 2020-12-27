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
	private Student stu;
	private Employee emp;
	
	private Connection myConn;
	
	public Lesson(int id, Date d, int stu_id, int emp_id, int t) throws Exception {
		this.id = id; 
		this.date = d;
		this.student_id = stu_id;
		this.employee_id = emp_id;
		this.time = t;
		
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
	
	public void setDate(Date nd) {
		this.date = nd;
	}
	
	public int getStudentId() {
		return this.student_id;
	}
	
	public void setStudentId(int id) {
		this.student_id = id;
	}
	
	public int getEmployeeId() {
		return this.employee_id;
	}
	
	public void setEmployeeId(int id) {
		this.employee_id = id;
	}
	
	public String getTime() throws SQLException {
		PreparedStatement getTime = null;
		
		try {
			getTime = myConn.prepareStatement("SELECT * FROM times WHERE time_id=?");
			
			getTime.setInt(1, this.time);
						
			ResultSet myRs = getTime.executeQuery(); 
			
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
	
	public void setTime(int t) {
		if (t >=1 && t <= 20) {
			this.time = t;
		} else {
			System.out.println("Time value out of bounds");
		}
	}
	
	@Override
	public String toString() {
		return String.format("Lesson [id=%s, S_id=%s, E_id=%s, Date=%s, Time=%s]", id, student_id, employee_id, date, time);
	} 
}
