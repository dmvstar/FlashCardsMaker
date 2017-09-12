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
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class MainApp {

	private JFrame frame;
	private JTextField tfDataPath;
	private JTextField tfTemplatePath;
	private JTextField tfOutName;

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
		sl_panel_main.putConstraint(SpringLayout.WEST, lblNewLabel, 21, SpringLayout.WEST, panel_main);
		panel_main.add(lblNewLabel);

		tfDataPath = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.WEST, tfDataPath, 119, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblNewLabel, -22, SpringLayout.WEST, tfDataPath);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, tfDataPath, -140, SpringLayout.SOUTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, lblNewLabel, 0, SpringLayout.SOUTH, tfDataPath);
		panel_main.add(tfDataPath);
		tfDataPath.setColumns(10);

		JButton btnSelectData = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.SOUTH, btnSelectData, -140, SpringLayout.SOUTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, tfDataPath, -16, SpringLayout.WEST, btnSelectData);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnSelectData, -59, SpringLayout.EAST, panel_main);
		btnSelectData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectDataSourceDir();
			}
		});
		panel_main.add(btnSelectData);

		JLabel lblReportTemplate = new JLabel("Report template:");
		sl_panel_main.putConstraint(SpringLayout.WEST, lblReportTemplate, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, lblReportTemplate, -85, SpringLayout.SOUTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblReportTemplate, -356, SpringLayout.EAST, panel_main);
		panel_main.add(lblReportTemplate);

		JButton btnProcess = new JButton("Process");
		sl_panel_main.putConstraint(SpringLayout.WEST, btnProcess, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, btnProcess, -10, SpringLayout.SOUTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnProcess, -356, SpringLayout.EAST, panel_main);
		panel_main.add(btnProcess);

		JComboBox cbTemplate = new JComboBox();
		sl_panel_main.putConstraint(SpringLayout.WEST, cbTemplate, 0, SpringLayout.WEST, tfDataPath);
		sl_panel_main.putConstraint(SpringLayout.EAST, cbTemplate, -75, SpringLayout.EAST, panel_main);
		panel_main.add(cbTemplate);

		JButton btnSelectTemplate = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.NORTH, btnSelectTemplate, 67, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnSelectTemplate, 408, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnSelectTemplate, -10, SpringLayout.EAST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnSelectData, 0, SpringLayout.EAST, btnSelectTemplate);
		panel_main.add(btnSelectTemplate);

		JMenuBar menuBar = new JMenuBar();
		sl_panel_main.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, menuBar, 97, SpringLayout.WEST, panel_main);
		panel_main.add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		JLabel lblPathToTemplate = new JLabel("Path to template:");
		sl_panel_main.putConstraint(SpringLayout.WEST, lblPathToTemplate, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, lblPathToTemplate, -12, SpringLayout.NORTH, lblReportTemplate);
		panel_main.add(lblPathToTemplate);
		
		tfTemplatePath = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.NORTH, tfTemplatePath, 68, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, tfTemplatePath, 119, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblPathToTemplate, -8, SpringLayout.WEST, tfTemplatePath);
		sl_panel_main.putConstraint(SpringLayout.EAST, tfTemplatePath, -16, SpringLayout.WEST, btnSelectTemplate);
		tfTemplatePath.setColumns(10);
		panel_main.add(tfTemplatePath);
		
		JLabel lblOutputName = new JLabel("Output name:");
		sl_panel_main.putConstraint(SpringLayout.WEST, lblOutputName, 0, SpringLayout.WEST, lblNewLabel);
		panel_main.add(lblOutputName);
		
		tfOutName = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblOutputName, 3, SpringLayout.NORTH, tfOutName);
		sl_panel_main.putConstraint(SpringLayout.NORTH, tfOutName, 122, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, cbTemplate, -6, SpringLayout.NORTH, tfOutName);
		sl_panel_main.putConstraint(SpringLayout.WEST, tfOutName, 0, SpringLayout.WEST, tfDataPath);
		sl_panel_main.putConstraint(SpringLayout.EAST, tfOutName, -75, SpringLayout.EAST, panel_main);
		tfOutName.setColumns(10);
		panel_main.add(tfOutName);

		JPanel panel_down = new JPanel();
		frame.getContentPane().add(panel_down, BorderLayout.SOUTH);
		panel_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		panel_down.add(btnCancel);

		JPanel panel_right = new JPanel();
		frame.getContentPane().add(panel_right, BorderLayout.EAST);
	}

	protected void selectDataSourceDir() {
		int ret = -1;
		System.err.println("selectDataSourceDir");
		JFileChooser fc = new JFileChooser();
		ret = fc.showDialog(frame, "Choose file...");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.err.println(file);
			tfDataPath.setText(file.getAbsolutePath());
		}
	}
}
