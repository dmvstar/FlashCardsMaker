package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.SpringLayout;

import report.FlashCardReport;
import utils.SimpleProperties;

import javax.swing.DefaultComboBoxModel;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainApp {

	private static final String KEY_CONFIG_PATH_DATA = "path_data";
	private static final String KEY_CONFIG_PATH_REPO = "path_report";
	private static final String KEY_CONFIG_PATH_OUT = "name_out";
	private static final String KEY_CONFIG_PATH_TPL = "name_template";
	
	
	private JFrame frame;
	private JTextField tfDataPath;
	private JTextField tfTemplatePath;
	private JTextField tfOutName;
	private JComboBox<String> cbTemplateList;
	private JLabel lblProgress;
	
	
	private SimpleProperties mSimpleProperties;
	private String mDefaultDataFile;
	private File mDefaultTplDir;
	private String mDefaultOutFile;
	private List<String> mReportList;
    private String mSelectedReportTpl = null;
	
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
		loadConfig();
	}

	private void loadConfig() {
		try {
			mSimpleProperties = new SimpleProperties();
			String s = ".";
			s = mSimpleProperties.get(KEY_CONFIG_PATH_DATA);
			if(s.length()==0) s = ".";
			mDefaultDataFile = s;
			tfDataPath.setText(s);
			s = ".";
			s = mSimpleProperties.get(KEY_CONFIG_PATH_REPO);
			mDefaultTplDir = new File(s);
			tfTemplatePath.setText(s);
			
			loadTemplateList();
			
			s = "";
			s = mSimpleProperties.get(KEY_CONFIG_PATH_OUT);
			mDefaultOutFile = s;
			tfOutName.setText(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	private void loadTemplateList() {
		if(mDefaultTplDir.exists() && mDefaultTplDir.isDirectory()) {
			mReportList = new ArrayList();
			for (final File fileEntry : mDefaultTplDir.listFiles()) {
					if(fileEntry.isFile() && fileEntry.getName().endsWith(".jrxml")){
						if (mSelectedReportTpl == null) mSelectedReportTpl = fileEntry.getName();
						System.out.println(fileEntry.getName());
		            	mReportList.add(fileEntry.getName());
					}
		    }
			if(mReportList.size()>0) {
				cbTemplateList.setModel(new DefaultComboBoxModel(mReportList.toArray()));
				System.out.println(mSelectedReportTpl);
				//cbTemplateList.addItemListener(aListener);
			}
		}
		
	}


	
	private void saveConfig() {
		try {
			mSimpleProperties.put(KEY_CONFIG_PATH_DATA, tfDataPath.getText());
			mSimpleProperties.put(KEY_CONFIG_PATH_REPO, tfTemplatePath.getText());
			mSimpleProperties.put(KEY_CONFIG_PATH_OUT, tfOutName.getText());
			mSimpleProperties.put(KEY_CONFIG_PATH_TPL, tfTemplatePath.getText());
			mSimpleProperties = new SimpleProperties();

			mDefaultDataFile = tfDataPath.getText();
			mDefaultTplDir = new File(tfTemplatePath.getText());
			mDefaultOutFile = tfOutName.getText();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 601, 321);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel_main = new JPanel();
		frame.getContentPane().add(panel_main, BorderLayout.CENTER);
		SpringLayout sl_panel_main = new SpringLayout();
		panel_main.setLayout(sl_panel_main);

		JLabel lblNewLabel = new JLabel("Path to data:");
		panel_main.add(lblNewLabel);

		tfDataPath = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.NORTH, tfDataPath, 19, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, tfDataPath, 42, SpringLayout.EAST, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.EAST, tfDataPath, -100, SpringLayout.EAST, panel_main);
		panel_main.add(tfDataPath);
		tfDataPath.setColumns(10);

		JButton btnSelectData = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.NORTH, btnSelectData, 19, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnSelectData, 6, SpringLayout.EAST, tfDataPath);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnSelectData, -10, SpringLayout.EAST, panel_main);
		btnSelectData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectDataSourceFile();
			}
		});
		panel_main.add(btnSelectData);

		JLabel lblReportTemplate = new JLabel("Report template:");
		sl_panel_main.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, lblReportTemplate);
		sl_panel_main.putConstraint(SpringLayout.WEST, lblReportTemplate, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblReportTemplate);
		panel_main.add(lblReportTemplate);

		JButton btnProcess = new JButton("Process");
		sl_panel_main.putConstraint(SpringLayout.WEST, btnProcess, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnProcess, -10, SpringLayout.EAST, panel_main);
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveConfig();
				makeReport();
			}
		});
		sl_panel_main.putConstraint(SpringLayout.SOUTH, btnProcess, -10, SpringLayout.SOUTH, panel_main);
		panel_main.add(btnProcess);

		cbTemplateList = new JComboBox();
		cbTemplateList.addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent arg0) {
					
					selectTemplate();
		           
		        }


		});

		sl_panel_main.putConstraint(SpringLayout.WEST, cbTemplateList, 190, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblReportTemplate, -42, SpringLayout.WEST, cbTemplateList);
		sl_panel_main.putConstraint(SpringLayout.EAST, cbTemplateList, -100, SpringLayout.EAST, panel_main);
		panel_main.add(cbTemplateList);

		JButton btnSelectTemplate = new JButton("...");
		sl_panel_main.putConstraint(SpringLayout.NORTH, btnSelectTemplate, 15, SpringLayout.SOUTH, btnSelectData);
		sl_panel_main.putConstraint(SpringLayout.WEST, btnSelectTemplate, 0, SpringLayout.WEST, btnSelectData);
		sl_panel_main.putConstraint(SpringLayout.EAST, btnSelectTemplate, -10, SpringLayout.EAST, panel_main);
		btnSelectTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectTemplateDir();
			}
		});
		panel_main.add(btnSelectTemplate);

		JMenuBar menuBar = new JMenuBar();
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.SOUTH, menuBar);
		sl_panel_main.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, menuBar, 97, SpringLayout.WEST, panel_main);
		panel_main.add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveConfig();
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		JLabel lblPathToTemplate = new JLabel("Path to template:");
		sl_panel_main.putConstraint(SpringLayout.WEST, lblPathToTemplate, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblReportTemplate, 27, SpringLayout.SOUTH, lblPathToTemplate);
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblPathToTemplate, 27, SpringLayout.SOUTH, lblNewLabel);
		panel_main.add(lblPathToTemplate);
		
		tfTemplatePath = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.EAST, tfTemplatePath, -100, SpringLayout.EAST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblPathToTemplate, -42, SpringLayout.WEST, tfTemplatePath);
		sl_panel_main.putConstraint(SpringLayout.NORTH, cbTemplateList, 20, SpringLayout.SOUTH, tfTemplatePath);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, tfDataPath, -17, SpringLayout.NORTH, tfTemplatePath);
		sl_panel_main.putConstraint(SpringLayout.NORTH, tfTemplatePath, 61, SpringLayout.NORTH, panel_main);
		sl_panel_main.putConstraint(SpringLayout.WEST, tfTemplatePath, 190, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, tfTemplatePath, -167, SpringLayout.SOUTH, panel_main);
		tfTemplatePath.setColumns(10);
		panel_main.add(tfTemplatePath);
		
		JLabel lblOutputName = new JLabel("Output name:");
		sl_panel_main.putConstraint(SpringLayout.NORTH, lblOutputName, 30, SpringLayout.SOUTH, lblReportTemplate);
		sl_panel_main.putConstraint(SpringLayout.WEST, lblOutputName, 21, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblOutputName, 0, SpringLayout.EAST, lblNewLabel);
		panel_main.add(lblOutputName);
		
		tfOutName = new JTextField();
		sl_panel_main.putConstraint(SpringLayout.NORTH, tfOutName, 16, SpringLayout.SOUTH, cbTemplateList);
		sl_panel_main.putConstraint(SpringLayout.WEST, tfOutName, 190, SpringLayout.WEST, panel_main);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, tfOutName, -47, SpringLayout.NORTH, btnProcess);
		sl_panel_main.putConstraint(SpringLayout.EAST, tfOutName, -100, SpringLayout.EAST, panel_main);
		tfOutName.setColumns(10);
		panel_main.add(tfOutName);
		
		lblProgress = new JLabel("Progress...");
		sl_panel_main.putConstraint(SpringLayout.WEST, lblProgress, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel_main.putConstraint(SpringLayout.SOUTH, lblProgress, -6, SpringLayout.NORTH, btnProcess);
		sl_panel_main.putConstraint(SpringLayout.EAST, lblProgress, 0, SpringLayout.EAST, btnSelectData);
		panel_main.add(lblProgress);

		JPanel panel_down = new JPanel();
		frame.getContentPane().add(panel_down, BorderLayout.SOUTH);
		panel_down.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveConfig();
				frame.dispose();
			}
		});
		panel_down.add(btnCancel);

		JPanel panel_right = new JPanel();
		frame.getContentPane().add(panel_right, BorderLayout.EAST);
	}

	protected void makeReport() {
		
		lblProgress.setText("Start...");
		
		
		new Thread(){
		    public void run(){
		    	try {
					FlashCardReport runner;
					runner = new FlashCardReport(lblProgress);
					runner.setDataFile(mDefaultDataFile);
					runner.setReportTemplate(mDefaultTplDir+"/"+mSelectedReportTpl);
					runner.setOutputName(mDefaultTplDir+"/"+mDefaultOutFile);
					runner.path();			
					lblProgress.setText("Process...");
					runner.process();
					lblProgress.setText("Done...");
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}.start();
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlashCardReport runner;
					runner = new FlashCardReport(lblProgress);
					runner.setDataFile(mDefaultDataFile);
					runner.setReportTemplate(mDefaultTplDir+"/"+mSelectedReportTpl);
					runner.setOutputName(mDefaultTplDir+"/"+mDefaultOutFile);
					runner.path();			
					lblProgress.setText("Process...");
					runner.process();
					lblProgress.setText("Done...");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
		
	}

	protected void selectDataSourceFile() {
		int ret = -1;
		System.err.println("selectDataSourceDir");
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory( new File(mDefaultDataFile));
		//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		ret = fc.showDialog(frame, "Choose file...");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.err.println(file);
			tfDataPath.setText(file.getAbsolutePath());
		}
	}
	
	protected void selectTemplateDir() {
		int ret = -1;
		System.err.println("selectDataSourceDir");
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory( mDefaultTplDir );
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		ret = fc.showDialog(frame, "Choose file...");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.err.println(file);
			tfTemplatePath.setText(file.getAbsolutePath());
		}
	}
	
	
	private void selectTemplate() {
		System.err.println(cbTemplateList.getSelectedItem());
        mSelectedReportTpl = cbTemplateList.getSelectedItem().toString();
        if(tfOutName.getText().length()==0) {
        	tfOutName.setText(mSelectedReportTpl.replaceAll(".jrxml", ""));
        	
        }
	}
}
