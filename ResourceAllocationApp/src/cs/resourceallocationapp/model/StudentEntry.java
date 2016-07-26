/**
 * 
 */
package cs.resourceallocationapp.model;

import java.util.Vector;

import cs.resourceallocationapp.app.ResourceAllocation;

/**
 * @author Rasanjana Nanayakkara - 14209670
 * Represents Students	
 */
public class StudentEntry {

	private String studentName;
	private Boolean isPreAssigned;
	private Vector<String> orderedPreferences;
	private String preAssignedProject;
	private int originalNoOfProject;
	//private Random objRandom = new Random();
	
	/**
	 * Constructor for class StudentEntry
	 */
	public StudentEntry() {
		this.studentName=null;
		this.isPreAssigned=false;
		this.orderedPreferences = new Vector<String>();
		this.setPreAssignedProject(null);
		this.setOriginalNoOfProject(0);
	}
	/**
	 * Parameterized Constructor for class StudentEntry
	 */
	public StudentEntry(String studentName) {
		this.studentName=studentName;
		this.isPreAssigned=false;
		this.orderedPreferences =new Vector<String>();
	}
	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the isPreAssigned
	 */
	public Boolean getIsPreAssigned() {
		return isPreAssigned;
	}	
	/**
	 * @param isPreAssigned the isPreAssigned to set
	 */
	public void setIsPreAssigned(Boolean isPreAssigned) {
		this.isPreAssigned = isPreAssigned;
	}	
	/**
	 * @return the projects
	 */
	public Vector<String> getOrderedPreferences() {
		return orderedPreferences;
	}	
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Vector<String> orderedPreferences) {
		this.orderedPreferences = orderedPreferences;
		this.originalNoOfProject=orderedPreferences.size();
	}	
	/**
	 * records internally that preference has been pre-assigned
	 * when only project is available
	**/
	public void preassignProject(String pname){
		this.setPreAssignedProject(pname);
			this.isPreAssigned = true;		
	}	
	/**
	 * To check whether a project is pre assigned
	 * @return boolean
	 */
	public Boolean hasPreassignProject(){
		return this.getIsPreAssigned();
	}	
	/**
	 * Get Number Of Stated Preferences
	 */
	public int getNumberOfStatedPreferences(){
		return this.originalNoOfProject;
	}	
	/**
	 * insert new preference to list
	 * @return boolean
	 */
	public void addProject(String projectName){
		this.orderedPreferences.add(projectName.intern());
	}
	/**
	 * @param originalNoOfProject the originalNoOfProject to set
	 */
	public  void setOriginalNoOfProject(int originalNoOfProject) {
		this.originalNoOfProject = originalNoOfProject;
	}
	
	public String getRandomPreference(){
		return orderedPreferences.get(ResourceAllocation.RND.nextInt(orderedPreferences.size()));
	}
	
	/**
	 * checks whether he preference is already in the list
	 * @param preference - name of the preference
	 */
	public boolean	hasPreference(String preference) {
		return orderedPreferences.contains(preference.intern());
	}
	
	/**
	 * to get ranking of a given preference
	 * @param preference - name of the preference
	 * @return int - ranking
	 */
	public int getRanking(String preference){
		if(hasPreassignProject()){
			return 0;
		}else if(!orderedPreferences.contains(preference.intern())){
			return -1;
		}else{
			return orderedPreferences.indexOf(preference.intern());
		}
	}
	/**
	 * @return the preAssignedProject
	 */
	public String getPreAssignedProject() {
		return preAssignedProject;
	}
	/**
	 * @param preAssignedProject the preAssignedProject to set
	 */
	public void setPreAssignedProject(String preAssignedProject) {
		this.preAssignedProject = preAssignedProject;
	}
}
