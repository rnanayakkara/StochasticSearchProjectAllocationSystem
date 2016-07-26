/**
 * 
 */
package cs.resourceallocationapp.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import cs.resourceallocationapp.app.ResourceAllocation;

/**
 * @author Rasanjana Nanayakkara - 14209670 Represents a preference table that
 *         is loaded from a tab-delimited text file on disk
 */
public class PreferenceTable {

	private String fileName;
	private Vector<StudentEntry> studentList = new Vector<>();
	private Hashtable<String, StudentEntry> studentLookup = new Hashtable<String, StudentEntry>();
	private Vector<Vector<String>> studentData = new Vector<Vector<String>>();
	private Integer minimumMaxPref = 0;

	/**
	 * Constructor
	 */
	public PreferenceTable() {
		this.fileName = null;
	}

	/**
	 * Overloaded constructor *
	 * 
	 * @param fileName
	 */
	public PreferenceTable(String fileName) {
		this.fileName = fileName;
		setStudentData(loadContentFromFile(this.fileName));
	}

	/**
	 * loads content from file *
	 * 
	 * @param fileName
	 */
	private Vector<Vector<String>> loadContentFromFile(String fileName) {

		Vector<Vector<String>> objAllContent = new Vector<Vector<String>>();
		String line;
		FileInputStream objInputStream = null;
		BufferedReader objInput = null;

		try {
			objInputStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("File is not found!");
			e.printStackTrace();
		}

		objInput = new BufferedReader(new InputStreamReader(objInputStream));

		try {
			while ((line = objInput.readLine()) != null) {
				Vector<String> objVetorTokens = new Vector<>();
				StudentEntry objStudent = new StudentEntry();

				StringTokenizer objToken = new StringTokenizer(line, "\t");
				int noOfTokens = objToken.countTokens();

				while (objToken.hasMoreTokens()) {
					String nextToken = objToken.nextToken();
					objVetorTokens.addElement(nextToken);
				}
				Vector<String> orderedPreferences = new Vector<String>();
				for (int i = 0; i < noOfTokens; i++) {
					switch (i) {
					case 0:
						objStudent.setStudentName(objVetorTokens.elementAt(i));
						break;
					case 1:
						objStudent.setIsPreAssigned(Boolean.parseBoolean(objVetorTokens.elementAt(i)));
						break;
					default:
						orderedPreferences.add(objVetorTokens.elementAt(i).intern());
						break;
					}
				}

				objStudent.setProjects(orderedPreferences);

				if (objStudent.getNumberOfStatedPreferences() == 1) {
					objStudent.preassignProject(objStudent.getOrderedPreferences().get(0));
				}

				this.setMinimumMaxPref(objStudent.getOrderedPreferences().size() > this.getMinimumMaxPref()
						? objStudent.getOrderedPreferences().size() : this.getMinimumMaxPref());

				getStudentList().addElement(objStudent);
				studentLookup.put(objStudent.getStudentName(), objStudent);
				objAllContent.addElement(objVetorTokens);
			}
			objInput.close();
		} catch (Exception e) {
			System.out.println("Error reading file!");
		}
		return objAllContent;
	}

	/**
	 * To get all student entries in the file *
	 * 
	 * @param fileName
	 */
	public Vector<StudentEntry> getAllStudentEntries() {
		return this.getStudentList();
	}

	/**
	 * To get entry of student *
	 * 
	 * @param studentName
	 */
	public StudentEntry getEntryFor(String studentName) {
		try {
			StudentEntry objStudent = studentLookup.get(studentName);
			return objStudent;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * To get a student randomly from student entries
	 * 
	 * @return
	 */
	public StudentEntry getRandomStudent() {
		Vector<String> keySet = new Vector<String>(studentLookup.keySet());
		return studentLookup.get(keySet.elementAt(ResourceAllocation.RND.nextInt(keySet.size())));
	}

	/**
	 * To get a preference randomly from all preferences
	 * 
	 * @return
	 */
	public String getRandomPreference() {
		StudentEntry randomStudent;
		String randomPreference = null;
		while (randomPreference == null) {
			randomStudent = getRandomStudent();
			if (!randomStudent.hasPreassignProject()) {
				randomPreference = randomStudent.getRandomPreference();
			}
		}
		return randomPreference;
	}

	/**
	 * To add extra project to each student entry
	 * 
	 * @param int
	 *            maxPrefs
	 * @return
	 */
	public void fillPreferencesOfAll(int maxPrefs) {
		for (StudentEntry student : getAllStudentEntries()) {
			if (!student.hasPreassignProject()) {
				while (student.getOrderedPreferences().size() != maxPrefs) {
					student.addProject(getRandomPreference());
				}
			}
		}
	}

	/**
	 * @return the studentData
	 */
	public Vector<Vector<String>> getStudentData() {
		return studentData;
	}

	/**
	 * @param studentData
	 *            the studentData to set
	 */
	public void setStudentData(Vector<Vector<String>> studentData) {
		this.studentData = studentData;
	}

	/**
	 * @return the studentList
	 */
	public Vector<StudentEntry> getStudentList() {
		return studentList;
	}

	/**
	 * @param studentList
	 *            the studentList to set
	 */
	public void setStudentList(Vector<StudentEntry> studentList) {
		this.studentList = studentList;
	}

	/**
	 * @return the minimumMaxPref
	 */
	public Integer getMinimumMaxPref() {
		return minimumMaxPref;
	}

	/**
	 * @param minimumMaxPref the minimumMaxPref to set
	 */
	private void setMinimumMaxPref(Integer minimumMaxPref) {
		this.minimumMaxPref = minimumMaxPref;
	}
}
