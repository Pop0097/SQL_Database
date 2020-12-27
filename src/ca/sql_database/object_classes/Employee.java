package ca.sql_database.object_classes;

public class Employee {
	private int id;
	private String fname;
	private String lname;
	private String email;
	
	public Employee(int id, String fn, String ln, String e) {
		this.id = id;
		this.fname = fn;
		this.lname = ln;
		this.email = e;
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
}
