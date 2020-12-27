package ca.sql_database.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Cursor;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import ca.sql_database.database_access_objects.EmployeeDAO;
import ca.sql_database.database_access_objects.LessonDAO;
import ca.sql_database.database_access_objects.StudentDAO;
import ca.sql_database.object_classes.Employee;
import ca.sql_database.object_classes.Lesson;
import ca.sql_database.object_classes.Student;

public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField searchField;
	private JTable databaseInformation;
	
	private EmployeeDAO employeeDAO;
	private StudentDAO studentDAO;
	private LessonDAO lessonDAO;
	
	// Used to keep track of the tables being displayed
	private enum tableStatus {EMPLOYEES, STUDENTS, LESSONS};
	private tableStatus stat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		
		// Create DAOs
		try {
			employeeDAO = new EmployeeDAO();
			studentDAO = new StudentDAO();
			lessonDAO = new LessonDAO();
			stat = tableStatus.EMPLOYEES; // Default table displayed is Employees
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(Gui.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		setResizable(false);
		setAutoRequestFocus(false);
		setTitle("MacBryte Database");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel mainScreen = new JPanel();
		mainScreen.setBounds(0, 0, 998, 449);
		contentPane.add(mainScreen);
		mainScreen.setLayout(null);
		
		JPanel tableDisplay = new JPanel();
		tableDisplay.setBounds(202, 80, 790, 363);
		mainScreen.add(tableDisplay);
		tableDisplay.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 790, 363);
		tableDisplay.add(scrollPane);
		
		databaseInformation = new JTable();
		scrollPane.setViewportView(databaseInformation);
		
		// Prints initial data (Employees)
		printAllEmployees();
		
		JPanel header = new JPanel();
		header.setBackground(new Color(65, 105, 225));
		header.setBounds(6, 6, 986, 62);
		mainScreen.add(header);
		header.setLayout(null);
		
		searchField = new JTextField();
		searchField.setBounds(197, 6, 429, 50);
		header.add(searchField);
		searchField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Gets searched value
					String search = searchField.getText();
					searchField.setText("");
					
					if (stat == tableStatus.EMPLOYEES) {
						// Declares list
						List<Employee> employees = new ArrayList<>(); 
						
						// Initializes based on if search was made
						if (search != null && search.trim().length() > 0) {
							employees = employeeDAO.searchEmployees(search);
						} else {
							employees = employeeDAO.getAllEmployees();
						}
						
						// Prints search results
						EmployeeTableModel model = new EmployeeTableModel(employees);
						
						databaseInformation.setModel(model);
					} else if (stat == tableStatus.STUDENTS) {
						// Declares list
						List<Student> students = new ArrayList<>();
						
						// Initializes based on if search was made
						if (search != null && search.trim().length() > 0) {
							students = studentDAO.searchStudents(search);
						} else {
							students = studentDAO.getAllStudents();
						}
						
						// Prints search results
						StudentTableModel model = new StudentTableModel(students);

						databaseInformation.setModel(model);
					} else if (stat == tableStatus.LESSONS) {
						// Declares list
						List<Lesson> lessons = new ArrayList<>();
						
						// Initializes based on if search was made
						if (search != null && search.trim().length() > 0) {
							lessons = lessonDAO.searchLessons(search);
						} else {
							lessons = lessonDAO.getAllLessons();
						}
						
						// Prints search results
						LessonTableModel model = new LessonTableModel(lessons);
						
						databaseInformation.setModel(model);
					}
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(Gui.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		searchButton.setBackground(Color.GRAY);
		searchButton.setBounds(6, 12, 169, 40);
		header.add(searchButton);
		
		JLabel tableTitle = new JLabel("Employees");
		tableTitle.setForeground(Color.WHITE);
		tableTitle.setBackground(Color.GREEN);
		tableTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		tableTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		tableTitle.setBounds(649, 15, 331, 33);
		header.add(tableTitle);
		
		JPanel sideBar = new JPanel();
		sideBar.setBackground(new Color(65, 105, 225));
		sideBar.setBounds(6, 80, 184, 363);
		mainScreen.add(sideBar);
		sideBar.setLayout(null);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens appropriate dialog based on table currently displayed.
				if (stat == tableStatus.EMPLOYEES) {
					EmployeeDialog dialog = new EmployeeDialog(Gui.this, employeeDAO, null, false);
					dialog.setVisible(true);
				} else if (stat == tableStatus.STUDENTS) {
					StudentDialog dialog = new StudentDialog(Gui.this, studentDAO, null, false);
					dialog.setVisible(true);
				} else if (stat == tableStatus.LESSONS) {
					LessonDialog dialog = new LessonDialog(Gui.this, lessonDAO, null, false);
					dialog.setVisible(true); 
				}
			}
		});
		
		createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		createButton.setBackground(new Color(128, 128, 128));
		createButton.setBounds(6, 18, 169, 40);
		sideBar.add(createButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Gets selected row
				int row = databaseInformation.getSelectedRow();
				
				// Returns error if row is not selected
				if (row < 0) {
					JOptionPane.showMessageDialog(Gui.this, "You must select a row", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Based of the current table displayed, the following lines create a temporary object and open the appropriate dialog with updateMode set true.
				if (stat == tableStatus.EMPLOYEES) {
					Employee tempEmp = (Employee) databaseInformation.getValueAt(row, EmployeeTableModel.OBJECT_COL);
					
					EmployeeDialog dialog = new EmployeeDialog(Gui.this, employeeDAO, tempEmp, true);
					dialog.setVisible(true);
				} else if (stat == tableStatus.STUDENTS) {
					Student tempStu = (Student) databaseInformation.getValueAt(row, StudentTableModel.OBJECT_COL);
					
					StudentDialog dialog = new StudentDialog(Gui.this, studentDAO, tempStu, true);
					dialog.setVisible(true);
				} else if (stat == tableStatus.LESSONS) {
					Lesson tempLess = (Lesson) databaseInformation.getValueAt(row, LessonTableModel.OBJECT_COL);
					
					LessonDialog dialog = new LessonDialog(Gui.this, lessonDAO, tempLess, true);
					dialog.setVisible(true); 
				}
			}
		});
		updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateButton.setBackground(new Color(128, 128, 128));
		updateButton.setBounds(6, 70, 169, 40);
		sideBar.add(updateButton);
		
		JButton switchViewButton = new JButton("Switch Table");
		switchViewButton.addActionListener(new ActionListener() {
			// When button is pressed, the table displayed is changed. Sequence is: Employee --> Student --> Lesson --> Employee ... (repeats)
			public void actionPerformed(ActionEvent e) {
				if (stat == tableStatus.EMPLOYEES) {
					stat = tableStatus.STUDENTS;
					printAllStudents();
					tableTitle.setText("Students");
				} else if (stat == tableStatus.STUDENTS) {
					stat = tableStatus.LESSONS;
					printAllLessons();
					tableTitle.setText("Lessons");
				} else if (stat == tableStatus.LESSONS) {
					stat = tableStatus.EMPLOYEES;
					printAllEmployees();
					tableTitle.setText("Employees");
				}
			}
		});
		switchViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switchViewButton.setBackground(new Color(128, 128, 128));
		switchViewButton.setBounds(6, 122, 169, 40);
		sideBar.add(switchViewButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Gets selected row
				int row = databaseInformation.getSelectedRow();
				
				// Returns error if row is not selected
				if (row < 0) {
					JOptionPane.showMessageDialog(Gui.this, "You must select a row", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Gets confirmation before deleting 
				int response = JOptionPane.showConfirmDialog(Gui.this, "Are you sure you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				// If user says no, end process
				if (response != JOptionPane.YES_OPTION) { 
					return;
				}
				
				try {
					// Based on table currently displayed, get a temporary object for the deleted row, call delete__() in the appropriate DAO, and update the GUI.
					if (stat == tableStatus.EMPLOYEES) {
						Employee tempEmp = (Employee) databaseInformation.getValueAt(row, EmployeeTableModel.OBJECT_COL);
						employeeDAO.deleteEmployee(tempEmp.getId());
						
						refreshList();
						
						JOptionPane.showMessageDialog(Gui.this, "Employee deleted succesfully.", "Employee Delete", JOptionPane.INFORMATION_MESSAGE);
					} else if (stat == tableStatus.STUDENTS) {
						Student tempStu = (Student) databaseInformation.getValueAt(row, StudentTableModel.OBJECT_COL);
						studentDAO.deleteStudent(tempStu.getId());
						
						refreshList();
						
						JOptionPane.showMessageDialog(Gui.this, "Student deleted succesfully.", "Student Delete", JOptionPane.INFORMATION_MESSAGE);
					} else if (stat == tableStatus.LESSONS) {
						Lesson tempLess = (Lesson) databaseInformation.getValueAt(row, LessonTableModel.OBJECT_COL);
						lessonDAO.deleteLesson(tempLess.getId());
						
						refreshList();
						
						JOptionPane.showMessageDialog(Gui.this, "Lesson deleted succesfully.", "Lesson Delete", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception exc) {
					if (stat == tableStatus.EMPLOYEES) {
						JOptionPane.showMessageDialog(Gui.this, "Error deleting employee: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} else if (stat == tableStatus.STUDENTS) {
						JOptionPane.showMessageDialog(Gui.this, "Error deleting student: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} else if (stat == tableStatus.LESSONS) {
						JOptionPane.showMessageDialog(Gui.this, "Error deleting lesson: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		deleteButton.setBackground(new Color(128, 128, 128));
		deleteButton.setBounds(6, 174, 169, 40);
		sideBar.add(deleteButton);
	}
	
	
	private void printAllEmployees() {
		try {
			List<Employee> employees = new ArrayList<>(); 
			employees = employeeDAO.getAllEmployees(); 
			EmployeeTableModel startingModel = new EmployeeTableModel(employees); 
			databaseInformation.setModel(startingModel);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(Gui.this, "Error: "+ exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void printAllStudents() {
		try {
			List<Student> students = new ArrayList<>(); 
			students = studentDAO.getAllStudents(); 
			StudentTableModel startingModel = new StudentTableModel(students); 
			databaseInformation.setModel(startingModel);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(Gui.this, "Error: "+ exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void printAllLessons() {
		try {
			List<Lesson> lessons = new ArrayList<>(); 
			lessons = lessonDAO.getAllLessons(); 
			LessonTableModel startingModel = new LessonTableModel(lessons); 
			databaseInformation.setModel(startingModel);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(Gui.this, "Error: "+ exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// Populates JTable by calling appropriate method
	public void refreshList() {
		if (stat == tableStatus.EMPLOYEES) {
			printAllEmployees();
		} else if (stat == tableStatus.STUDENTS) {
			printAllStudents();
		} else if (stat == tableStatus.LESSONS) {
			printAllLessons();
		}
	}
}
