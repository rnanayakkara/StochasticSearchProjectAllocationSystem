package cs.resourceallocationapp.app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cs.resourceallocationapp.model.CandidateAssignment;
import cs.resourceallocationapp.model.CandidateSolution;
import cs.resourceallocationapp.model.PreferenceTable;
import cs.resourceallocationapp.model.StudentEntry;
import cs.resourceallocationapp.utility.FileTypeFilter;
import cs.resourceallocationapp.utility.ResourceAllocationTableRender;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class ResourceAllocationForm {

	private JFrame frmStochasticSearchProject;
	private JTextField txtFilePath;
	private JTextField txtMaxPrefs;
	private JTable tblPreferences;
	private JTable tblPreview;
	private PreferenceTable studentTable;
	private Vector<StudentEntry> studentData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResourceAllocationForm window = new ResourceAllocationForm();
					window.frmStochasticSearchProject.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ResourceAllocationForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {

		frmStochasticSearchProject = new JFrame();
		frmStochasticSearchProject.setBackground(new Color(95, 158, 160));
		frmStochasticSearchProject.setResizable(false);
		frmStochasticSearchProject.setTitle("Stochastic Search Project Allocation System");
		frmStochasticSearchProject.setBounds(100, 100, 1024, 640);
		frmStochasticSearchProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStochasticSearchProject.getContentPane().setLayout(null);
		frmStochasticSearchProject.setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon(getClass().getResource("ProjectIcon.png"));
		frmStochasticSearchProject.setIconImage(img.getImage());
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 72, 998, 64);
		frmStochasticSearchProject.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblFilePath = new JLabel("File Path");
		lblFilePath.setBounds(10, 10, 74, 14);
		panel.add(lblFilePath);

		txtFilePath = new JTextField();
		txtFilePath.setToolTipText("Add file path");
		txtFilePath.setBounds(94, 9, 282, 20);
		panel.add(txtFilePath);
		txtFilePath.setColumns(10);

		JLabel lblStudents = new JLabel("No.of Students          - ");
		lblStudents.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStudents.setBounds(675, 17, 155, 14);
		panel.add(lblStudents);

		JLabel lblNoOfStudents = new JLabel("N/A");
		lblNoOfStudents.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNoOfStudents.setBounds(827, 17, 32, 14);
		panel.add(lblNoOfStudents);

		JLabel lblPrefSolution = new JLabel("N/A");
		lblPrefSolution.setBounds(827, 42, 32, 14);
		panel.add(lblPrefSolution);
		lblPrefSolution.setForeground(Color.BLACK);
		lblPrefSolution.setFont(new Font("Tahoma", Font.BOLD, 13));

		JButton btnUpload = new JButton("Upload");
		btnUpload.setForeground(new Color(255, 255, 255));
		btnUpload.setBackground(new Color(95, 158, 160));
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Create a file chooser
					JFileChooser fc = new JFileChooser();
					FileFilter tabbedFilter = new FileTypeFilter(".tsv", "Tab Separated Values");
					// FileFilter textFilter = new FileTypeFilter(".txt", "Text
					// Files");
					// FileFilter xlsFilter = new FileTypeFilter(".xlsx",
					// "Microsoft
					// Excel Documents");

					fc.addChoosableFileFilter(tabbedFilter);
					/*
					 * fc.addChoosableFileFilter(xlsFilter);
					 * fc.addChoosableFileFilter(textFilter);
					 */
					fc.setAcceptAllFileFilterUsed(false);
					// In response to a button click:
					int returnVal = fc.showOpenDialog(fc);

					// Handle open button action
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						txtFilePath.setText(fc.getSelectedFile().getPath());
						txtFilePath.setForeground(new Color(0, 0, 0));

						studentTable = new PreferenceTable(fc.getSelectedFile().getAbsolutePath());
						studentData = studentTable.getAllStudentEntries();

						DefaultTableModel table = new DefaultTableModel(
								new String[] { "Student Name", "Preference(s)", "Preferred Preference", "Description" },
								0);

						for (int i = 1; i < studentData.size(); i++) {
							Vector<String> row = new Vector<String>();
							String description = "N/A";
							row.addElement(studentData.elementAt(i).getStudentName());
							row.addElement(studentData.elementAt(i).getOrderedPreferences().toString().substring(1,
									studentData.elementAt(i).getOrderedPreferences().toString().length() - 1));
							if (studentData.elementAt(i).getIsPreAssigned()) {
								row.addElement(studentData.elementAt(i).getPreAssignedProject());
								description = "Pre assigned project is available";
							} else {
								row.addElement("N/A");
							}
							row.addElement(description);
							table.addRow(row);
						}
						tblPreview.setModel(table);
						resizeTable(tblPreview);
						table.fireTableDataChanged();
						lblNoOfStudents.setText(String.valueOf(studentData.size() - 1));
					} else {
						txtFilePath.setText("");
					}
				} catch (NullPointerException n) {
					dispalyError("Invalid file path. Please try again.");
				} catch (Exception ex) {
					dispalyError("Error when uploading file. Please try again.");
				}
			}
		});

		btnUpload.setToolTipText("Upload student text file");
		btnUpload.setIcon(new ImageIcon(
				ResourceAllocationForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/UpFolder.gif")));
		btnUpload.setBounds(400, 9, 125, 23);
		panel.add(btnUpload);

		txtMaxPrefs = new JTextField();
		txtMaxPrefs.setToolTipText("Enter max no: of preferences");
		txtMaxPrefs.setBounds(94, 36, 86, 20);
		panel.add(txtMaxPrefs);
		txtMaxPrefs.setColumns(10);
		txtMaxPrefs.setText("10");
		txtMaxPrefs.setHorizontalAlignment(JTextField.RIGHT);
		;

		JLabel lblMaxPrefs = new JLabel("Max prefs");
		lblMaxPrefs.setBounds(10, 37, 74, 14);
		panel.add(lblMaxPrefs);

		JButton btnDownload = new JButton("Export");
		btnDownload.setBackground(new Color(95, 158, 160));
		btnDownload.setForeground(new Color(255, 255, 255));
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toExcel(tblPreferences);
			}
		});
		btnDownload.setBounds(869, 35, 119, 23);
		panel.add(btnDownload);
		btnDownload.setToolTipText("Export solution as Excel");

		JLabel lblMaxPrefError = new JLabel("");
		lblMaxPrefError.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMaxPrefError.setVisible(false);
		lblMaxPrefError.setForeground(Color.RED);
		lblMaxPrefError.setBounds(190, 39, 186, 14);
		panel.add(lblMaxPrefError);

		JLabel lblNewLabel = new JLabel("Energy (SA)");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(535, 15, 74, 14);
		panel.add(lblNewLabel);

		JLabel lblFitnessga = new JLabel("Fitness (GA)");
		lblFitnessga.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFitnessga.setBounds(535, 42, 74, 14);
		panel.add(lblFitnessga);

		JLabel lblEnergy = new JLabel("00");
		lblEnergy.setForeground(new Color(0, 128, 0));
		lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnergy.setBounds(619, 15, 46, 14);
		panel.add(lblEnergy);

		JLabel lblFitness = new JLabel("00");
		lblFitness.setForeground(new Color(34, 139, 34));
		lblFitness.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFitness.setBounds(619, 42, 46, 14);
		panel.add(lblFitness);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(95, 158, 160));
		tabbedPane.setBounds(10, 136, 998, 426);
		frmStochasticSearchProject.getContentPane().add(tabbedPane);

		JScrollPane panePreview = new JScrollPane();
		panePreview.setForeground(new Color(255, 255, 255));
		tabbedPane.addTab("Preview", null, panePreview, "Preview of upload data");

		tblPreview = new JTable();
		tblPreview.setName("tblPreview");
		tblPreview.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		tblPreview.setVisible(true);
		tblPreview.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Student Name", "Preference(s)", "Preferred Preference", "Description" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, Object.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblPreview.getColumnModel().getColumn(0).setPreferredWidth(30);
		tblPreview.getColumnModel().getColumn(1).setPreferredWidth(200);
		tblPreview.getColumnModel().getColumn(2).setPreferredWidth(40);
		tblPreview.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblPreview.setToolTipText("Uploaded student data");
		tblPreview.setDefaultRenderer(Object.class, new ResourceAllocationTableRender());
		panePreview.setViewportView(tblPreview);

		JScrollPane panelSolution = new JScrollPane();
		panelSolution.setForeground(new Color(255, 255, 255));
		tabbedPane.addTab("Preferred Solution", null, panelSolution, null);
		tabbedPane.setBackgroundAt(1, new Color(95, 158, 160));
		panelSolution.setToolTipText("Student preferences solution");

		tblPreferences = new JTable();
		tblPreferences.setName("StudentPreferences");
		tblPreferences.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Student Name", "Preferred Preference", "Is Valid", "Description" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Object.class, String.class, Object.class, String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblPreferences.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblPreferences.getColumnModel().getColumn(0).setMinWidth(100);
		tblPreferences.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblPreferences.getColumnModel().getColumn(1).setMinWidth(150);
		panelSolution.setViewportView(tblPreferences);
		tblPreferences.setDefaultRenderer(Object.class, new ResourceAllocationTableRender());

		JButton btnProcess = new JButton("Process");
		btnProcess.setForeground(new Color(255, 255, 255));
		btnProcess.setBackground(new Color(95, 158, 160));
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					txtFilePath.setText("");
					if (txtFilePath.getText().isEmpty() && studentTable == null) {
						txtFilePath.setText("Please upload a file and process");
						txtFilePath.setForeground(new Color(255, 0, 0));
					} else if (txtMaxPrefs.getText().isEmpty()) {
						lblMaxPrefError.setText("Please enter max pref");
						lblMaxPrefError.setVisible(true);
					} else if (Integer.parseInt(txtMaxPrefs.getText()) < studentTable.getMinimumMaxPref()) {
						lblMaxPrefError.setText("Max pref should be greater than " + studentTable.getMinimumMaxPref());
						lblMaxPrefError.setVisible(true);
					} else {
						lblMaxPrefError.setText("");
						lblMaxPrefError.setVisible(false);

						Integer energy = 0, fitness = 0;

						CandidateSolution bestSolution = new CandidateSolution();

						bestSolution = getSolution();
						energy = bestSolution.getEnergy();
						fitness = bestSolution.getFitness();

						lblEnergy.setText(energy.toString());
						lblFitness.setText(fitness.toString());

						populateSolutionTable(bestSolution);
					}
				} catch (NullPointerException n) {
					dispalyError("Invalid file path. Please try again.");
				} catch (Exception ex) {
					dispalyError("Error when processing data. Please try again.");
				}
			}

			// display solution in grid
			private void populateSolutionTable(CandidateSolution bestSolution) {
				Hashtable<String, CandidateAssignment> preferredSolution = bestSolution.getSolution();
				Iterator<String> it = preferredSolution.keySet().iterator();
				String description, isValid;
				Vector<String> projects = new Vector<String>();
				Integer validCount = 0;

				DefaultTableModel table = new DefaultTableModel(
						new String[] { "Student Name", "Preferred Preference", "Is Valid", "Description" }, 0);

				while (it.hasNext()) {
					String sname = it.next();
					CandidateAssignment candidateAssignment = new CandidateAssignment();
					candidateAssignment = preferredSolution.get(sname);
					Vector<String> row = new Vector<String>();

					row.addElement(sname);

					if (projects.contains(candidateAssignment.getAssignedProject())) {
						Object s = table.getValueAt(projects.indexOf(candidateAssignment.getAssignedProject()), 0);
						description = "Conflict with student : " + s.toString();
						isValid = "No";

					} else {
						description = "Valid assignment";
						isValid = "Yes";
						validCount += 1;
					}

					row.addElement(candidateAssignment.getAssignedProject());
					row.addElement(isValid);
					row.addElement(description);

					table.addRow(row);
					projects.addElement(candidateAssignment.getAssignedProject());
				}

				tblPreferences.setModel(table);
				resizeTable(tblPreferences);
				table.fireTableDataChanged();
				tabbedPane.setSelectedIndex(1);
				lblPrefSolution.setText(validCount.toString());
			}

		});

		btnProcess.setIcon(new ImageIcon(
				ResourceAllocationForm.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		btnProcess.setToolTipText("Process student data");
		btnProcess.setBounds(400, 36, 125, 23);
		panel.add(btnProcess);

		JLabel lblValidSolutions = new JLabel("No.of valid Solutions -");
		lblValidSolutions.setBounds(675, 42, 155, 14);
		panel.add(lblValidSolutions);
		lblValidSolutions.setForeground(Color.BLACK);
		lblValidSolutions.setFont(new Font("Tahoma", Font.BOLD, 13));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(84, 25, 924, 36);
		frmStochasticSearchProject.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblStochasticSearchProject = new JLabel("Stochastic Search Project Allocation System");
		lblStochasticSearchProject.setBounds(10, 10, 366, 17);
		panel_1.add(lblStochasticSearchProject);
		lblStochasticSearchProject.setFont(new Font("Tahoma", Font.BOLD, 16));

		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(790, 10, 55, 17);
		panel_1.add(lblDate);
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));

		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		java.util.Date today = new java.util.Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		label.setText(dateFormat.format(today));
		label.setBounds(832, 11, 111, 14);
		panel_1.add(label);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 573, 996, 27);
		frmStochasticSearchProject.getContentPane().add(panel_3);

		JLabel lblDesignedDeveloped = new JLabel("Designed & Developed by Abacus");
		lblDesignedDeveloped.setForeground(new Color(95, 158, 160));
		panel_3.add(lblDesignedDeveloped);
		
		JLabel lblFormImage = new JLabel("");
		lblFormImage.setBounds(10, 11, 64, 64);
		frmStochasticSearchProject.getContentPane().add(lblFormImage);
		lblFormImage.setIcon(new ImageIcon(ResourceAllocationForm.class.getResource("/cs/resourceallocationapp/app/ProjectIconForm.png")));
	}

	private void resizeTable(JTable table) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		switch (table.getName()) {
		case "tblPreview":
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(50);
			table.getColumnModel().getColumn(3).setPreferredWidth(75);
			break;
		case "StudentPreferences":
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(5);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		default:
			break;
		}

	}

	// returns the best solution
	private CandidateSolution getSolution() {
		studentTable.fillPreferencesOfAll(Integer.parseInt(txtMaxPrefs.getText()));
		studentData = studentTable.getStudentList();
		CandidateSolution bestSolution = new CandidateSolution();
		Integer lowestEnergy = 0, highestFitness = 0;

		// calculate energy and fitness for 10 times
		for (int i = 0; i < 10; i++) {

			PreferenceTable candidateTable = new PreferenceTable();
			Vector<StudentEntry> studentList = new Vector<StudentEntry>();

			for (int j = 1; j < studentData.size(); j++) {
				StudentEntry student = new StudentEntry();
				student = studentData.elementAt(j);
				CandidateAssignment candidateAssignment = new CandidateAssignment(student);
				studentList.addElement(candidateAssignment.getCandidate());
			}

			candidateTable.setStudentList(studentList);
			CandidateSolution candidateSolution = new CandidateSolution(candidateTable);

			if (i == 0) {
				bestSolution = candidateSolution;
				lowestEnergy = bestSolution.getEnergy();
				highestFitness = bestSolution.getFitness();
			} else {
				if (candidateSolution.getEnergy() < lowestEnergy && candidateSolution.getFitness() > highestFitness) {
					bestSolution = candidateSolution;
					lowestEnergy = bestSolution.getEnergy();
					highestFitness = bestSolution.getFitness();
				}
			}
		}
		return bestSolution;
	}

	@SuppressWarnings("resource")
	public void toExcel(JTable table) {
		try {
			File temp = File.createTempFile("studentPreferences", ".xls");

			String absolutePath = temp.getAbsolutePath();
			File file = new File(absolutePath);
			TableModel model = tblPreferences.getModel();
			FileWriter excel = new FileWriter(file);

			for (int i = 0; i < model.getColumnCount(); i++) {
				excel.write(model.getColumnName(i) + "\t");
			}

			excel.write("\n");

			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					excel.write(model.getValueAt(i, j).toString() + "\t");
				}
				excel.write("\n");
			}

			JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(file);

			int returnVal = fc.showSaveDialog(null);
			File destinationFile = null;

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				destinationFile = new File(fc.getSelectedFile().getAbsolutePath());

				if (!destinationFile.getAbsolutePath().equals(file.getAbsolutePath())) {

					model = tblPreferences.getModel();
					excel = new FileWriter(destinationFile);

					for (int i = 0; i < model.getColumnCount(); i++) {
						excel.write(model.getColumnName(i) + "\t");
					}

					excel.write("\n");

					for (int i = 0; i < model.getRowCount(); i++) {
						for (int j = 0; j < model.getColumnCount(); j++) {
							excel.write(model.getValueAt(i, j).toString() + "\t");
						}
						excel.write("\n");
					}
				}

			}

			if (!destinationFile.getAbsolutePath().equals(file.getAbsolutePath())) {
				file.delete();
			}
			excel.close();
			dispalySuccess("Successfully Saved");
		} catch (Exception e) {
			dispalyError("Error while exporting data. Please try again.");
		}
	}

	//display error messages
	private void dispalyError(String message) {
		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	//display success messages
	private void dispalySuccess(String message) {
		final JPanel panel = new JPanel();
		JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}
}
