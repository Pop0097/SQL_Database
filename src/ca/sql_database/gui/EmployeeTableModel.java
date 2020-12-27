package ca.sql_database.gui;

import ca.sql_database.object_classes.Employee;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel extends AbstractTableModel {
	
	public static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int LAST_NAME_COL = 2;
	private static final int EMAIL_COL = 3;

	private String[] columnNames = {"ID", "First Name", "Last Name", "Email"};
	private List<Employee> employees;

	public EmployeeTableModel(List<Employee> emp) {
		employees = emp;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return employees.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	// Gets the value at a specific row and column
	@Override
	public Object getValueAt(int row, int col) {

		Employee tempEmployee = employees.get(row);

		switch (col) {
			case ID_COL:
				return tempEmployee.getId();
			case FIRST_NAME_COL:
				return tempEmployee.getFirstName();
			case LAST_NAME_COL:
				return tempEmployee.getLastName();
			case EMAIL_COL:
				return tempEmployee.getEmail();
			case OBJECT_COL:
				return tempEmployee;
			default:
				return tempEmployee.getLastName();
		}
	}
	
	// Gives class of data element so we know if we are dealing with a string, double, int, etc.
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
