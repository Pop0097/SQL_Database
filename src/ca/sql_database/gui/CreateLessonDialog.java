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
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class CreateLessonDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField employeeIdInput;
	private JTextField studentIdInput;
	private JTextField yearInput;
	
	private LessonDAO lessonDAO;
	private Gui gui;
	private JTextField monthInput;
	private JTextField dayInput;


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
		setBounds(100, 100, 450, 516);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		employeeIdInput = new JTextField();
		employeeIdInput.setBounds(131, 17, 302, 36);
		contentPanel.add(employeeIdInput);
		employeeIdInput.setColumns(10);
		
		studentIdInput = new JTextField();
		studentIdInput.setBounds(131, 80, 302, 36);
		studentIdInput.setColumns(10);
		contentPanel.add(studentIdInput);
		
		yearInput = new JTextField();
		yearInput.setBounds(131, 140, 302, 36);
		yearInput.setColumns(10);
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
		monthInput.setBounds(131, 204, 302, 36);
		monthInput.setColumns(10);
		contentPanel.add(monthInput);
		
		JLabel dayLabel = new JLabel("Day (DD):");
		dayLabel.setBounds(16, 278, 192, 16);
		contentPanel.add(dayLabel);
		
		dayInput = new JTextField();
		dayInput.setBounds(131, 268, 302, 36);
		dayInput.setColumns(10);
		contentPanel.add(dayInput);
		
		JLabel timeLabel = new JLabel("Time (HH:MM:SS):");
		timeLabel.setBounds(16, 358, 117, 16);
		contentPanel.add(timeLabel);
		
		JPanel timeInputPanel = new JPanel();
		timeInputPanel.setBounds(131, 327, 302, 76);
		contentPanel.add(timeInputPanel);
		timeInputPanel.setLayout(null);
		
		JScrollPane timeInputScrollPane = new JScrollPane();
		timeInputScrollPane.setBounds(0, 0, 302, 76);
		timeInputPanel.add(timeInputScrollPane);
		
		JList timeInput = new JList();
		timeInput.setBounds(0, 0, 302, 76);
		timeInputScrollPane.setViewportView(timeInput);
		timeInput.setModel(new AbstractListModel() {
			String[] values = new String[] {"09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		timeInput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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
						int time = timeInput.getSelectedIndex() + 1;
						
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
