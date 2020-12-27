package ca.sql_database.object_classes;

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
		
		// Establishes database connection
		Properties props = new Properties();
		props.load(new FileInputStream("db.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		myConn = DriverManager.getConnection(dburl, user, password);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.fname;
	}
	
	public String getLastName() {
		return this.lname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPlan() throws SQLException {
		// Since the student's plan is stored as an integer (plan_id) in the student table, we need to convert it to a string by finding the corresponding row in the plan table
		PreparedStatement getPlan = null;
		
		try {
			// Creates prepared statement to find the row of the plan corresponding to the integer in client_plan
			getPlan = myConn.prepareStatement("SELECT * FROM plan WHERE plan_id=?");
			
			getPlan.setInt(1, this.plan);
			
			// Executes query
			ResultSet myRs = getPlan.executeQuery(); 
			
			// If result is found, return it
			if (myRs.next()) {
				String toReturn = myRs.getString("plan_type");
				return toReturn;
			} else {
				return "";
			}
		} catch (Exception exc) {
			System.out.println("Unsuccessful query - Student find plan");
			return "";
		}
		finally {
			getPlan.close();
		}
	}
}
