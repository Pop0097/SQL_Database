package ca.sql_database.object_classes;

import java.util.Properties;
import java.io.FileInputStream;
import java.sql.*;

public class Lesson {
	private int id;
	private Date date;
	private Time time;
	private int student_id;
	private int employee_id;
	private Student stu;
	private Employee emp;
	
	private Connection myConn;
	
	public Lesson(int id, Date d, int stu_id, int emp_id, Time t) throws Exception {
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
	
	public Time getTime() {
		return this.time;
	}
	
	public void setTime(Time t) {
		this.time = t;
	}
	
	@Override
	public String toString() {
		return String.format("Lesson [id=%s, S_id=%s, E_id=%s, Date=%s]", id, student_id, employee_id, date);
	} 
}
