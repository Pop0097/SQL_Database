package ca.sql_database.gui;

import ca.sql_database.object_classes.Student;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class StudentTableModel extends AbstractTableModel {
	
	private static final int ID_COL = 0;
	private static final int LAST_NAME_COL = 1;
	private static final int FIRST_NAME_COL = 2;
	private static final int EMAIL_COL = 3;
	private static final int PLAN_COL = 4;

	private String[] columnNames = {"ID", "First Name", "Last Name", "Email", "Plan"};
	private List<Student> students;

	public StudentTableModel(List<Student> stu) {
		students = stu;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return students.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Student tempStudent = students.get(row);

		switch (col) {
			case ID_COL:
				return tempStudent.getId();
			case FIRST_NAME_COL:
				return tempStudent.getFirstName();
			case LAST_NAME_COL:
				return tempStudent.getLastName();
			case EMAIL_COL:
				return tempStudent.getEmail();
			case PLAN_COL:
				return tempStudent.getPlan();
			default:
				return tempStudent.getLastName();
		}
	}
	
	// Gives class of data element so we know if we are dealing with a string, double, int, etc.
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
