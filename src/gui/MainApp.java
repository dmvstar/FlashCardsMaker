package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class MainApp {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_main = new JPanel();
		frame.getContentPane().add(panel_main, BorderLayout.CENTER);
		SpringLayout sl_panel_main = new SpringLayout();
		panel_main.setLayout(sl_panel_main);
		
		JLabel lblNewLabel = new JLabel("New label");
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblNewLabel, 25, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, lblNewLabel, 21, SpringLayout.WEST, panel_main);
		panel_main.add(lblNewLabel);
		
		textField = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.WEST, textField, 16, SpringLayout.EAST, lblNewLabel);
		panel_main.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.EAST, textField, -16, SpringLayout.WEST, btnNewButton);
		sl_panel_main.putConstraint(SpringLayout.NORTH, btnNewButton, -5, SpringLayout.NORTH, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnNewButton, -59, SpringLayout.EAST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, panel_main);
		panel_main.add(btnNewButton);
		
		JLabel label = new JLabel("New label");
		sl_panel_main.putConstraint(SpringLayout.NORTH, label, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, lblNewLabel);
		panel_main.add(label);
		
		JButton btnProcess = new JButton("Process");
		sl_panel_main.putConstraint(SpringLayout.WEST, btnProcess, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, btnProcess, -10, SpringLayout.SOUTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnProcess, 111, SpringLayout.WEST, panel_main);
		panel_main.add(btnProcess);
		
		JComboBox comboBox = new JComboBox();
		sl_panel_main.putConstraint(SpringLayout.SOUTH, textField, -6, SpringLayout.NORTH, comboBox);
		sl_panel_main.putConstraint(SpringLayout.NORTH, comboBox, 46, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, comboBox, 16, SpringLayout.EAST, label);
		sl_panel_main.putConstraint(SpringLayout.EAST, comboBox, -75, SpringLayout.EAST, panel_main);
		panel_main.add(comboBox);
		
		JPanel panel_down = new JPanel();
		frame.getContentPane().add(panel_down, BorderLayout.SOUTH);
		panel_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		panel_down.add(btnCancel);
		
		JPanel panel_right = new JPanel();
		frame.getContentPane().add(panel_right, BorderLayout.EAST);
	}
}
