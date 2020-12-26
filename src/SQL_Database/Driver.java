package SQL_Database;

import java.sql.*;

import java.util.Scanner;

public class Driver {
	
	
	/* CREATE FUNCTIONS */
	
	
	public static void create_employee(Connection myConn, String fname, String lname, String email) throws SQLException {
		PreparedStatement create_employee_st = myConn.prepareStatement("INSERT INTO EMPLOYEE (employee_fname, employee_lname, employee_email) VALUES (?,?,?)");
		
		create_employee_st.setString(1, fname);
		create_employee_st.setString(2, lname);
		create_employee_st.setString(3, email);
		// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
		create_employee_st.executeUpdate();
		create_employee_st.close();
		
	}
	
	public static void create_student(Connection myConn, String fname, String lname, String email, int plan) throws SQLException {
		PreparedStatement create_student_st = myConn.prepareStatement("INSERT INTO STUDENT (client_fname, client_lname, client_email, client_plan) VALUES (?,?,?,?)");
		
		create_student_st.setString(1, fname);
		create_student_st.setString(2, lname);
		create_student_st.setString(3, email);
		create_student_st.setInt(4, plan);
		// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
		create_student_st.executeUpdate();
		create_student_st.close();
	
	}
	
	public static void create_lesson(Connection myConn, int emp_id, int stu_id, Date date) throws SQLException {
		PreparedStatement create_lesson_st = myConn.prepareStatement("INSERT INTO LESSON(lesson_studentId, lesson_employeeId, lesson_date) VALUES (?,?,?)");

		create_lesson_st.setInt(1, stu_id);
		create_lesson_st.setInt(2, emp_id);
		// https://stackoverflow.com/questions/18614836/using-setdate-in-preparedstatement
		create_lesson_st.setDate(3, date);
		// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
		create_lesson_st.executeUpdate();
		create_lesson_st.close();
	}
	
	
	/* DELETE FUNCTIONS */
	
	
	public static void delete_employee(Connection myConn, String email) throws SQLException {
		PreparedStatement delete_employee_st = myConn.prepareStatement("DELETE FROM employee WHERE employee_email=?");
		
		delete_employee_st.setString(1, email);
		delete_employee_st.executeUpdate();
		delete_employee_st.close();
	}
	
	public static void delete_Student(Connection myConn, String email) throws SQLException {
		PreparedStatement delete_student_st = myConn.prepareStatement("DELETE FROM student WHERE client_email=?");
		
		delete_student_st.setString(1, email);
		delete_student_st.executeUpdate();
		delete_student_st.close();
	}
	
	public static void delete_lesson(Connection myConn, int id) throws SQLException {
		PreparedStatement delete_lesson_st = myConn.prepareStatement("DELETE FROM lesson WHERE lesson_id=?");
		
		delete_lesson_st.setInt(1, id);
		delete_lesson_st.executeUpdate();
		delete_lesson_st.close();
	}
	
	
	/* SEARCH FUNCTIONS */
	
	
	public static void search_employees(Connection myConn, String name) throws SQLException {
		PreparedStatement search_employees_st = myConn.prepareStatement("SELECT * FROM employee WHERE employee_fname=?");
		
		search_employees_st.setString(1, name);
		search_employees_st.executeQuery();
	}
	
	public static void search_students(Connection myConn, String name) throws SQLException {
		PreparedStatement search_student_st = myConn.prepareStatement("SELECT * FROM student WHERE client_fname=?");
		
		search_student_st.setString(1, name);
		search_student_st.executeQuery();
	}
	
	public static void search_lessons_from_student(Connection myConn, int id) throws SQLException {
		PreparedStatement search_lesson_st = myConn.prepareStatement("SELECT * FROM lesson WHERE lesson_studentId=?");
		
		search_lesson_st.setInt(1, id);
		search_lesson_st.executeQuery();
	}
	
	public static void search_lessons_from_employee(Connection myConn, int id) throws SQLException {
		PreparedStatement search_lesson_st = myConn.prepareStatement("SELECT * FROM lesson WHERE lesson_employeeId=?");
		
		search_lesson_st.setInt(1, id);
		search_lesson_st.executeQuery();
	}
	
	
	/* UPDATE FUNCTIONS */
	
	
	public static void update_employees(Connection myConn, String fname, String lname, String email, int id) throws SQLException {
		PreparedStatement update_employee_st = myConn.prepareStatement("UPDATE employee SET employee_fname=?, employee_lname=?, employee_email=? WHERE employee_id=?");
		
		update_employee_st.setString(1, fname);
		update_employee_st.setString(2, lname);
		update_employee_st.setString(3, email);
		update_employee_st.setInt(4, id);
		// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
		update_employee_st.executeUpdate();
		update_employee_st.close();
		
	}
	
	public static void update_students(Connection myConn, String fname, String lname, String email, int plan, int id) throws SQLException {
		PreparedStatement update_student_st = myConn.prepareStatement("UPDATE student SET client_fname=?, client_lname=?, client_email=?, client_plan=? WHERE employee_id=?");
		
		update_student_st.setString(1, fname);
		update_student_st.setString(2, lname);
		update_student_st.setString(3, email);
		update_student_st.setInt(4, plan);
		update_student_st.setInt(5, id);
		// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
		update_student_st.executeUpdate();
		update_student_st.close();
	
	}
	
	public static void update_lessons(Connection myConn, int emp_id, int stu_id, Date date, int id) throws SQLException {
		PreparedStatement update_lesson_st = myConn.prepareStatement("UPDATE lesson SET lesson_studentId=?, lesson_employee_id=?, lesson_date=?, WHERE lesson_id=?");

		update_lesson_st.setInt(1, stu_id);
		update_lesson_st.setInt(2, emp_id);
		update_lesson_st.setDate(3, date);
		update_lesson_st.setInt(4, id);
		// For CUD operations, make sure you use executeUpdate() and that you .close() it afterwards. For R operations, use executeQuery()
		update_lesson_st.executeUpdate();
		update_lesson_st.close();
	}
	
	
	/* HELPERS */ 
	
	
	private static boolean get_confirmation() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Is it okay to save?  yes/no: ");
		String input = scanner.nextLine();

		scanner.close();

		return input.equalsIgnoreCase("yes");
	}
	
	
	/* MISC */
	
	
	// Just here for practice. Will not be used in final product
	public static void practice_crud_operations(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
		/* Print data */
		
		myRs = myStmt.executeQuery("select * from plan");
		
		while (myRs.next()) {
			System.out.println(myRs.getString("plan_type"));
		}
		
		/* Insert data */
		
		String sq1 = "insert into employee " + " (employee_fname, employee_lname, employee_email) " + " values ('Jim', 'Joe', 'jimjoe@gmail.com')";
		myStmt.executeUpdate(sq1);
		
		String sq2 = "insert into employee " + " (employee_fname, employee_lname, employee_email) " + " values ('Tim', 'Cook', 'timcook@gmail.com')";
		myStmt.executeUpdate(sq2);
		
		String sq3 = "insert into employee " + " (employee_fname, employee_lname, employee_email) " + " values ('Jeff', 'Ball', 'jeffball@gmail.com')";
		myStmt.executeUpdate(sq3);
		
		// Make new student using prepared statement
		create_employee(myConn, "Timmy", "Turner", "tt@gmail.com");
		
		/* Updating data */
		
		String sq4 = "update employee " + " set employee_email='aaaaa@gmail.com'" + " where employee_fname='Jeff'";
		myStmt.executeUpdate(sq4);
		
		/* Delete data */ 
		
		String sq5 = "delete from employee where employee_fname='Jim'";
		int affectedRows = myStmt.executeUpdate(sq5); // Executed statement and returns number of deleted rows (Don't need to return tho)
		System.out.println("Affected rows: " + affectedRows); // Prints the number of rows that are affected (deleted)
		
	}
	
	
	/* MAIN */
	
 
	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:mysql://localhost:3306/database_project";
		String user = "root";
		String pass = "12345678";
		
		Connection myConn = null;
		ResultSet myRs = null;
		Statement myStmt = null;
		
		try {
			// Get connection to database
			myConn = DriverManager.getConnection(url, user, pass);
			// create a statement
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from plan");
			
//			practice_crud_operations(myConn, myStmt, myRs); // Used for practice
			
			// Setting up interface for transactions
			myConn.setAutoCommit(false); // This ensures that the user can confirm changes
			
			
			
			/* INSERT CHANGES/QUERIES HERE */	
			update_employees(myConn, "D", "R", "d@gmail.com", 10);
			
			
			
			boolean confirmation = get_confirmation(); // Will make a button to toggle this value
			
			if (confirmation) {
				myConn.commit();
			} else {
				myConn.rollback();
			}
		} catch (Exception exc) {
			System.out.println("Failed");
			exc.printStackTrace();
		}
	}
}
