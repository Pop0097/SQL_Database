package objectClasses;

import java.util.Properties;
import java.io.FileInputStream;
import java.sql.*;

public class Student {
	private int id;
	private String fname;
	private String lname;
	private String email;
	private int plan;
	
	private Connection myConn;
	
	public Student(int id, String fn, String ln, String e, int p) throws Exception {
		this.id = id;
		this.fname = fn;
		this.lname = ln;
		this.email = e;
		this.plan = p;
		
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
	
	public String getFname() {
		return this.fname;
	}
	
	public void setFname(String fn) {
		this.fname = fn;
	}
	
	public String getLname() {
		return this.lname;
	}
	
	public void setLname(String ln) {
		this.lname = ln;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	public String getPlan() {
		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM plan WHERE plan_id=" + this.plan);
			return myRs.getString("plan_type");
		} catch (Exception ex) {
			System.out.println("Unsuccessful query - Student find plan");
			return "";
		}
	}
	
	public void setPlan(int p) {
		if (p >= 1 && p <= 5) {
			this.plan = p;
		} else {
			System.out.println("Plan value out of bounds");
		}
	}
	
	@Override
	public String toString() {
		return String.format("Student [id=%s, lastName=%s, firstName=%s, email=%s, plan=%s]", id, lname, fname, email, plan);
	} 
}
