package SQL_Database;

import java.sql.*;

public class Driver {
	
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
	
 
	public static void main(String[] args) {
		
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


			/* Print data */
			
//			while (myRs.next()) {
//				System.out.println(myRs.getString("plan_type"));
//			}
			
			/* Insert data */
			
//			String sq1 = "insert into employee " + " (employee_fname, employee_lname, employee_email) " + " values ('Jim', 'Joe', 'jimjoe@gmail.com')";
//			myStmt.executeUpdate(sq1);
//			
//			String sq2 = "insert into employee " + " (employee_fname, employee_lname, employee_email) " + " values ('Tim', 'Cook', 'timcook@gmail.com')";
//			myStmt.executeUpdate(sq2);
//			
//			String sq3 = "insert into employee " + " (employee_fname, employee_lname, employee_email) " + " values ('Jeff', 'Ball', 'jeffball@gmail.com')";
//			myStmt.executeUpdate(sq3);
			
			// Make new student using prepared statement
			create_employee(myConn, "Timmy", "Turner", "tt@gmail.com");
			
			/* Updating data */
			
//			String sq4 = "update employee " + " set employee_email='aaaaa@gmail.com'" + " where employee_fname='Jeff'";
//			myStmt.executeUpdate(sq4);
			
			/* Delete data */ 
			
//			String sq5 = "delete from employee where employee_fname='Jim'";
//			int affectedRows = myStmt.executeUpdate(sq5); // Executed statement and returns number of deleted rows (Don't need to return tho)
//			System.out.println("Affected rows: " + affectedRows); // Prints the number of rows that are affected (deleted)
			
			
			
		} catch (Exception exc) {
			System.out.println("Failed");
			exc.printStackTrace();
		}
	}
}
