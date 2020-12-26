package SQL_Database;

import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/database_project";
		String user = "root";
		String pass = "12345678";

		try {
			// Get connection to database
			Connection myConn = DriverManager.getConnection(url, user, pass);

			// create a statement
			Statement myStmt = myConn.createStatement();

			/* Print data */
//			ResultSet myRs = myStmt.executeQuery("select * from plan");
//
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
			
			/* Updating data */
//			String sq4 = "update employee " + " set employee_email='aaaaa@gmail.com'" + " where employee_fname='Jeff'";
//			myStmt.executeUpdate(sq4);
			
			/* Delete data */ 
//			String sq5 = "delete from employee where employee_fname='Jim'";
//			int affectedRows = myStmt.executeUpdate(sq5); // Executed statement and returns number of deleted rows (Don't need to return tho)
//			System.out.println("Affected rows: " + affectedRows); // Prints the number of rows that are affected (deleted)
			
		} catch (Exception exc) {
			System.out.println("Hello");
			exc.printStackTrace();
		}
	}
}
