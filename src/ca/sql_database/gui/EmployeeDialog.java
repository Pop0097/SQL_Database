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

import ca.sql_database.data_access_objects.EmployeeDAO;
import ca.sql_database.object_classes.Employee;

public class EmployeeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameInput;
	private JTextField lastNameInput;
	private JTextField emailInput;
	
	private EmployeeDAO employeeDAO;
	private Gui gui;
	
	private Employee prevEmployee = null;
	private boolean updateMode = false;
	private int employeeId = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EmployeeDialog dialog = new EmployeeDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EmployeeDialog(Gui currentScreen, EmployeeDAO empDAO, Employee prevEmp, boolean modeUpdate) {
		this(); // Calls default constructor
		employeeDAO = empDAO;
		gui = currentScreen;
		prevEmployee = prevEmp;
		updateMode = modeUpdate; // If updateMode is true, then we are updating. Else, we are creating.
		
		// If prevEmployee is defined, then we get its ID
		if (prevEmployee != null) {
			employeeId = prevEmp.getId(); 
		}
		
		if (updateMode) {
			setTitle("Update Employee"); // If we are updating, we change the title of the dialog 
			
			fillInTextFields(prevEmployee); // Fills in the text inputs in the form
		}
	}
	
	private void fillInTextFields(Employee emp) {
		firstNameInput.setText(emp.getFirstName());
		lastNameInput.setText(emp.getLastName());
		emailInput.setText(emp.getEmail());	
	}

	/**
	 * Create the dialog.
	 */
	public EmployeeDialog() {
		setTitle("Create Employee");
		setBounds(100, 100, 450, 261);
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

		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(16, 27, 117, 16);
		contentPanel.add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(16, 90, 117, 16);
		contentPanel.add(lastNameLabel);
		
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
						
						try {
							// Save to the database
							if (updateMode) { // Updating
								employeeDAO.updateEmployee(fname, lname, email, employeeId);
							} else { // Creating
								employeeDAO.createEmployee(fname, lname, email);
							}

							// Close dialog
							setVisible(false);
							dispose();

							// Refresh GUI list
							gui.refreshList();
							
							// Show success message
							if (updateMode) { // Updating 
								JOptionPane.showMessageDialog(gui, "Employee updated succesfully.", "Employee Updated", JOptionPane.INFORMATION_MESSAGE);
							} else { // Creating
								JOptionPane.showMessageDialog(gui, "Employee added succesfully.", "Employee Added", JOptionPane.INFORMATION_MESSAGE);
							}
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
