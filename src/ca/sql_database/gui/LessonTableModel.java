package ca.sql_database.gui;

import ca.sql_database.object_classes.Lesson;

import java.util.List;

import java.sql.Date;

import javax.swing.table.AbstractTableModel;

public class LessonTableModel extends AbstractTableModel {
	
	private static final int ID_COL = 0;
	private static final int STUDENT_ID_COL = 1;
	private static final int EMPLOYEE_ID_COL = 2;
	private static final int DATE_COL = 3;

	private String[] columnNames = {"ID", "Student ID", "Employee ID", "Date and Time"};
	private List<Lesson> lessons;

	public LessonTableModel(List<Lesson> less) {
		lessons = less;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return lessons.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Lesson tempLesson = lessons.get(row);

		switch (col) {
			case ID_COL:
				return tempLesson.getId();
			case STUDENT_ID_COL:
				return tempLesson.getStudentId();
			case EMPLOYEE_ID_COL:
				return tempLesson.getEmployeeid();
			case DATE_COL:
				return tempLesson.getDate();
			default:
				return tempLesson.getId();
		}
	}
	
	// Gives class of data element so we know if we are dealing with a string, double, int, etc.
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
