package ca.sql_database.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

import ca.sql_database.database_abstract_objects.LessonDAO;

public class CreateLessonDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField employeeIdInput;
	private JTextField studentIdInput;
	private JTextField yearInput;
	
	private LessonDAO lessonDAO;
	private Gui gui;
	private JTextField monthInput;
	private JTextField dayInput;
	private JTextField timeInput;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateLessonDialog dialog = new CreateLessonDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CreateLessonDialog(Gui currentScreen, LessonDAO lessDAO) {
		this(); // Calls default constructor
		lessonDAO = lessDAO;
		gui = currentScreen;
	}

	/**
	 * Create the dialog.
	 */
	public CreateLessonDialog() {
		setTitle("Schedule Lesson");
		setBounds(100, 100, 450, 461);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		employeeIdInput = new JTextField();
		employeeIdInput.setBounds(131, 17, 302, 36);
		contentPanel.add(employeeIdInput);
		employeeIdInput.setColumns(10);
		
		studentIdInput = new JTextField();
		studentIdInput.setColumns(10);
		studentIdInput.setBounds(131, 80, 302, 36);
		contentPanel.add(studentIdInput);
		
		yearInput = new JTextField();
		yearInput.setColumns(10);
		yearInput.setBounds(131, 140, 302, 36);
		contentPanel.add(yearInput);
		
		JLabel yearLabel = new JLabel("Year (YYYY):");
		yearLabel.setBounds(16, 150, 192, 16);
		contentPanel.add(yearLabel);
		
		JLabel studentIdLabel = new JLabel("Student ID:");
		studentIdLabel.setBounds(16, 90, 117, 16);
		contentPanel.add(studentIdLabel);
		
		JLabel employeeIdLabel = new JLabel("Employee ID:");
		employeeIdLabel.setBounds(16, 27, 117, 16);
		contentPanel.add(employeeIdLabel);
		
		JLabel monthLabel = new JLabel("Month (MM):");
		monthLabel.setBounds(16, 214, 192, 16);
		contentPanel.add(monthLabel);
		
		monthInput = new JTextField();
		monthInput.setColumns(10);
		monthInput.setBounds(131, 204, 302, 36);
		contentPanel.add(monthInput);
		
		JLabel dayLabel = new JLabel("Day (DD):");
		dayLabel.setBounds(16, 278, 192, 16);
		contentPanel.add(dayLabel);
		
		dayInput = new JTextField();
		dayInput.setColumns(10);
		dayInput.setBounds(131, 268, 302, 36);
		contentPanel.add(dayInput);
		
		JLabel timeLabel = new JLabel("Time (HH:MM:SS):");
		timeLabel.setBounds(16, 339, 117, 16);
		contentPanel.add(timeLabel);
		
		timeInput = new JTextField();
		timeInput.setColumns(10);
		timeInput.setBounds(131, 329, 302, 36);
		contentPanel.add(timeInput);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Schedule Lesson");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// get the employee info from gui
						int empId = Integer.parseInt(employeeIdInput.getText());
						int stuId = Integer.parseInt(studentIdInput.getText()); 
						String dateStr = yearInput.getText() + "-" + monthInput.getText() + "-" + dayInput.getText();
						Date date = Date.valueOf(dateStr);
						String timeStr = timeInput.getText();
						Time time = Time.valueOf(timeStr);
						
						try {
							// save to the database
							lessonDAO.createLesson(stuId, empId, date, time);

							// close dialog
							setVisible(false);
							dispose();

							// refresh gui list
							gui.refreshList();
							
							// show success message
							JOptionPane.showMessageDialog(gui, "Employee added succesfully.", "Employee Added", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception exc) {
							JOptionPane.showMessageDialog(gui, "Error saving employee: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
