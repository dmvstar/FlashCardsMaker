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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainApp {

	private JFrame frame;
	private JTextField tfDataPath;

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
		frame.setBounds(100, 100, 493, 273);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_main = new JPanel();
		frame.getContentPane().add(panel_main, BorderLayout.CENTER);
		SpringLayout sl_panel_main = new SpringLayout();
		panel_main.setLayout(sl_panel_main);
		
		JLabel lblNewLabel = new JLabel("Path to data:");
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblNewLabel, 25, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, lblNewLabel, 21, SpringLayout.WEST, panel_main);
		panel_main.add(lblNewLabel);
		
		tfDataPath = new JTextField();
		panel_main.add(tfDataPath);
		tfDataPath.setColumns(10);
		
		JButton btnSelectData = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.EAST, tfDataPath, -16, SpringLayout.WEST, btnSelectData);
		sl_panel_main.putConstraint(SpringLayout.NORTH, btnSelectData, -5, SpringLayout.NORTH, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnSelectData, -59, SpringLayout.EAST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnSelectData, -10, SpringLayout.EAST, panel_main);
		panel_main.add(btnSelectData);
		
		JLabel lblReportTemplate = new JLabel("Report template:");
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblReportTemplate, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.WEST, lblReportTemplate, 0, SpringLayout.WEST, lblNewLabel);
		panel_main.add(lblReportTemplate);
		
		JButton btnProcess = new JButton("Process");
		sl_panel_main.putConstraint(SpringLayout.WEST, btnProcess, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, btnProcess, -10, SpringLayout.SOUTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnProcess, 111, SpringLayout.WEST, panel_main);
		panel_main.add(btnProcess);
		
		JComboBox cbTemplate = new JComboBox();
		sl_panel_main.putConstraint(SpringLayout.WEST, tfDataPath, 0, SpringLayout.WEST, cbTemplate);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, tfDataPath, -6, SpringLayout.NORTH, cbTemplate);
		sl_panel_main.putConstraint(SpringLayout.NORTH, cbTemplate, 46, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, cbTemplate, 16, SpringLayout.EAST, lblReportTemplate);
		sl_panel_main.putConstraint(SpringLayout.EAST, cbTemplate, -75, SpringLayout.EAST, panel_main);
		panel_main.add(cbTemplate);
		
		JButton btnSelectTemplate = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.NORTH, btnSelectTemplate, 6, SpringLayout.SOUTH, btnSelectData);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnSelectTemplate, 0, SpringLayout.WEST, btnSelectData);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnSelectTemplate, 0, SpringLayout.EAST, btnSelectData);
		panel_main.add(btnSelectTemplate);
		
		JMenuBar menuBar = new JMenuBar();
		sl_panel_main.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, menuBar, 97, SpringLayout.WEST, panel_main);
		panel_main.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JPanel panel_down = new JPanel();
		frame.getContentPane().add(panel_down, BorderLayout.SOUTH);
		panel_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		panel_down.add(btnCancel);
		
		JPanel panel_right = new JPanel();
		frame.getContentPane().add(panel_right, BorderLayout.EAST);
	}
}
