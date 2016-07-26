/**
 * 
 */
package cs.resourceallocationapp.model;

/**
 * @author Rasanjana Nanayakkara - 14209670
 *
 */
public class CandidateAssignment {

	private StudentEntry studentEntry;
	private String assignedProject;
	private String prevProject;
	
	/**
	 * Constructor
	 */
	public CandidateAssignment() {
		setCandidate(new StudentEntry());
		assignedProject=null;
	}

	/**
	 * Constructor
	 */
	public CandidateAssignment(StudentEntry student) {
		this.studentEntry=student;
		randomizeAssignment();
	}
	
	/**
	 * @return the studentEntry
	 */
	public StudentEntry getCandidate() {
		return studentEntry;
	}

	/**
	 * @param studentEntry the studentEntry to set
	 */
	public void setCandidate(StudentEntry studentEntry) {
		this.studentEntry = studentEntry;
	}

	/**
	 * @return the assignedProject
	 */
	public String getAssignedProject() {
		return assignedProject;
	}

	/**
	 * @param project the assignedProject to set
	 */
	public void setProject(String project) {
		this.assignedProject = project;
	}

	/**
	 * assign project randomly
	 */
	public void randomizeAssignment(){
		this.prevProject = this.assignedProject;
		this.assignedProject=studentEntry.getRandomPreference().intern();
	}
	
	/**
	 * undo random project assignment
	 */
	public void undoChange()
	{
		if(this.prevProject !=null){
			this.assignedProject =this.prevProject;
		}
	}
	
	/*
	 * to get the energy of the assignment
	 */
	public int getEnergy(){
		int energy=(int) Math.pow(this.studentEntry.getRanking(this.assignedProject)+1, 2);
		return energy;
	}
}
