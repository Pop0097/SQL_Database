package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class databaseProjectGui extends JFrame {

	private JPanel contentPane;
	private JTextField search_field;
	private JTable database_information;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					databaseProjectGui frame = new databaseProjectGui();
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
	public databaseProjectGui() {
		setAutoRequestFocus(false);
		setTitle("MacBryte Database");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel employees_screen = new JPanel();
		employees_screen.setBounds(0, 0, 998, 449);
		contentPane.add(employees_screen);
		employees_screen.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBackground(new Color(65, 105, 225));
		header.setBounds(6, 6, 986, 62);
		employees_screen.add(header);
		header.setLayout(null);
		
		search_field = new JTextField();
		search_field.setBounds(197, 6, 429, 50);
		header.add(search_field);
		search_field.setColumns(10);
		
		JButton search_button = new JButton("Search");
		search_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		search_button.setBackground(Color.GRAY);
		search_button.setBounds(6, 12, 169, 40);
		header.add(search_button);
		
		JLabel table_title = new JLabel("Employees");
		table_title.setForeground(Color.WHITE);
		table_title.setBackground(Color.GREEN);
		table_title.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		table_title.setHorizontalAlignment(SwingConstants.RIGHT);
		table_title.setBounds(649, 15, 331, 33);
		header.add(table_title);
		
		JPanel side_bar = new JPanel();
		side_bar.setBackground(new Color(65, 105, 225));
		side_bar.setBounds(6, 80, 184, 363);
		employees_screen.add(side_bar);
		side_bar.setLayout(null);
		
		JButton create_button = new JButton("Create");
		create_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		create_button.setBackground(new Color(128, 128, 128));
		create_button.setBounds(6, 18, 169, 40);
		side_bar.add(create_button);
		
		JButton update_button = new JButton("Update");
		update_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		update_button.setBackground(new Color(128, 128, 128));
		update_button.setBounds(6, 70, 169, 40);
		side_bar.add(update_button);
		
		JButton switch_view_button = new JButton("Switch Table");
		switch_view_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		switch_view_button.setBackground(new Color(128, 128, 128));
		switch_view_button.setBounds(6, 122, 169, 40);
		side_bar.add(switch_view_button);
		
		JButton delete_button = new JButton("Delete");
		delete_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		delete_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		delete_button.setBackground(new Color(128, 128, 128));
		delete_button.setBounds(6, 174, 169, 40);
		side_bar.add(delete_button);
		
		JPanel table_display = new JPanel();
		table_display.setBounds(202, 80, 790, 363);
		employees_screen.add(table_display);
		table_display.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 790, 363);
		table_display.add(scrollPane);
		
		database_information = new JTable();
		scrollPane.setViewportView(database_information);
	}
}
