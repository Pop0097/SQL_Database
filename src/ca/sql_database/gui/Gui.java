package ca.sql_database.gui;

import java.awt.BorderLayout;
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

import ca.sql_database.database_abstract_objects.EmployeeDAO;
import ca.sql_database.database_abstract_objects.LessonDAO;
import ca.sql_database.database_abstract_objects.StudentDAO;

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
			stat = tableStatus.EMPLOYEES;
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(Gui.this, "Error: "+ exc, "Error", JOptionPane.ERROR_MESSAGE);
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
		try {
			List<Employee> startingEmployees = new ArrayList<>(); 
			startingEmployees = employeeDAO.getAllEmployees(); // Initializes based on if search was made
			EmployeeTableModel startingModel = new EmployeeTableModel(startingEmployees); // Prints search results
			databaseInformation.setModel(startingModel);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(Gui.this, "Error: "+ exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
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
					String search = searchField.getText();
					
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
						List<Student> students = new ArrayList<>();
						
						if (search != null && search.trim().length() > 0) {
							students = studentDAO.searchStudents(search);
						} else {
							students = studentDAO.getAllStudents();
						}
						
						// Prints search results
						StudentTableModel model = new StudentTableModel(students);
						
						databaseInformation.setModel(model);
					} else if (stat == tableStatus.LESSONS) {
						List<Lesson> lessons = new ArrayList<>();
						
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
		createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		createButton.setBackground(new Color(128, 128, 128));
		createButton.setBounds(6, 18, 169, 40);
		sideBar.add(createButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		updateButton.setBackground(new Color(128, 128, 128));
		updateButton.setBounds(6, 70, 169, 40);
		sideBar.add(updateButton);
		
		JButton switchViewButton = new JButton("Switch Table");
		switchViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switchViewButton.setBackground(new Color(128, 128, 128));
		switchViewButton.setBounds(6, 122, 169, 40);
		sideBar.add(switchViewButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		deleteButton.setBackground(new Color(128, 128, 128));
		deleteButton.setBounds(6, 174, 169, 40);
		sideBar.add(deleteButton);
	}
}
