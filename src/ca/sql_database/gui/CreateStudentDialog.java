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

import ca.sql_database.database_abstract_objects.StudentDAO;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class CreateStudentDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameInput;
	private JTextField lastNameInput;
	private JTextField emailInput;
	
	private StudentDAO studentDAO;
	private Gui gui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateStudentDialog dialog = new CreateStudentDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CreateStudentDialog(Gui currentScreen, StudentDAO stuDAO) {
		this(); // Calls default constructor
		studentDAO = stuDAO;
		gui = currentScreen;
	}

	/**
	 * Create the dialog.
	 */
	public CreateStudentDialog() {
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
		
		JList planInput = new JList();
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
				JButton okButton = new JButton("Create Student");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// get the employee info from gui
						String fname = firstNameInput.getText();
						String lname = lastNameInput.getText();
						String email = emailInput.getText();
						int plan = planInput.getSelectedIndex() + 1;
						
						try {
							// save to the database
							studentDAO.createStudent(fname, lname, email, plan);

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
