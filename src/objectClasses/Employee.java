package objectClasses;

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
	
	public String getFname() {
		return this.fname;
	}
	
	public void setFanme(String fn) {
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
	
	@Override
	public String toString() {
		return String.format("Employee [id=%s, lastName=%s, firstName=%s, email=%s]", id, lname, fname, email);
	} 
}