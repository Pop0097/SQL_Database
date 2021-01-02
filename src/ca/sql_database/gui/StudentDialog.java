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
import java.awt.event.ActionEvent;

import ca.sql_database.data_access_objects.StudentDAO;
import ca.sql_database.object_classes.Student;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class StudentDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameInput;
	private JTextField lastNameInput;
	private JTextField emailInput;
	private JList planInput;
	
	private StudentDAO studentDAO;
	private Gui gui;
	
	private Student prevStudent = null;
	boolean updateMode = false;
	private int studentId = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StudentDialog dialog = new StudentDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StudentDialog(Gui currentScreen, StudentDAO stuDAO, Student prevStu, boolean modeUpdate) {
		this(); // Calls default constructor
		studentDAO = stuDAO;
		gui = currentScreen;
		prevStudent = prevStu;
		updateMode = modeUpdate; // If updateMode is true, then we are updating. Else, we are creating.
		
		// If prevStudent is defined, then we get its ID
		if (prevStudent != null) {
			studentId = prevStu.getId();
		}
		
		if (updateMode) {
			setTitle("Update Student"); // If we are updating, we change the title of the dialog
			
			fillInInputFields(prevStudent); // Fills in the text inputs in the form
		}
	}
	
	private void fillInInputFields(Student stu) {
		firstNameInput.setText(stu.getFirstName());
		lastNameInput.setText(stu.getLastName());
		emailInput.setText(stu.getEmail());
		
		try {
			planInput.setSelectedValue(stu.getPlan(), true);
		} catch (Exception exc) {}		
	}

	/**
	 * Create the dialog.
	 */
	public StudentDialog() {
		setTitle("Create Student");
		setBounds(100, 100, 450, 379);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		firstNameInput = new JTextField();
		firstNameInput.setBounds(103, 17, 330, 36);
		contentPanel.add(firstNameInput);
		firstNameInput.setColumns(10);
		
		lastNameInput = new JTextField();
		lastNameInput.setColumns(10);
		lastNameInput.setBounds(103, 80, 330, 36);
		contentPanel.add(lastNameInput);
		
		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(103, 140, 330, 36);
		contentPanel.add(emailInput);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(16, 150, 61, 16);
		contentPanel.add(emailLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(16, 90, 117, 16);
		contentPanel.add(lastNameLabel);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(16, 27, 117, 16);
		contentPanel.add(firstNameLabel);
		
		JLabel planLabel = new JLabel("Plan:");
		planLabel.setBounds(16, 234, 61, 16);
		contentPanel.add(planLabel);
		
		planInput = new JList();
		planInput.setModel(new AbstractListModel() {
			String[] values = new String[] {"Lyte", "Basic", "Plus", "Pro", "Membership"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		planInput.setVisibleRowCount(5);
		planInput.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		planInput.setBounds(103, 199, 330, 90);
		contentPanel.add(planInput);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Get info from GUI
						String fname = firstNameInput.getText();
						String lname = lastNameInput.getText();
						String email = emailInput.getText();
						int plan = planInput.getSelectedIndex() + 1;
						
						try {
							// Save to the database
							if (updateMode) { // Update
								studentDAO.updateStudent(fname, lname, email, plan, studentId);
							} else { // Create
								studentDAO.createStudent(fname, lname, email, plan);
							}

							// Close dialog
							setVisible(false);
							dispose();

							// Refresh GUI list
							gui.refreshList();
							
							// Show success message
							if (updateMode) { // Update
								JOptionPane.showMessageDialog(gui, "Student updated succesfully.", "Student Updated", JOptionPane.INFORMATION_MESSAGE);
							} else { // Create
								JOptionPane.showMessageDialog(gui, "Student added succesfully.", "Student Added", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (Exception exc) {
							JOptionPane.showMessageDialog(gui, "Error saving student: " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
