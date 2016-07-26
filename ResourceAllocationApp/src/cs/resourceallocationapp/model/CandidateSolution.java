/**
 * 
 */
package cs.resourceallocationapp.model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import cs.resourceallocationapp.app.ResourceAllocation;

/**
 * @author Rasanjana Nanayakkara - 14209670
 *
 */
public class CandidateSolution {
	private Hashtable<String,CandidateAssignment> solution;
	private final int penalty=10;
	/**
	 * Default Constructor
	 */
	public CandidateSolution() {
		this.setSolution(new Hashtable<String, CandidateAssignment>());
	}

	/**
	 * Parameterized Constructor
	 */
	public CandidateSolution(PreferenceTable prefs) {
		this.setSolution(new Hashtable<String, CandidateAssignment>());
		for(StudentEntry student :prefs.getAllStudentEntries()) {
			getSolution().put(student.getStudentName(), new CandidateAssignment(student));
		}	
	}
	
	/**
	 * @param sname (student name) get assignment for a student
	 */
	public CandidateAssignment getAssignmentFor(String sname){
		return getSolution().get(sname);
	}
	
	/**
	 * @randomly pick an assignment
	 */
	public CandidateAssignment getRandomAssignment(){
		Vector<String> keySet = new Vector<String>(getSolution().keySet());
		return getSolution().get(keySet.elementAt(ResourceAllocation.RND.nextInt(keySet.size())));
	}
	
	/*
	 * to get the energy of the solution
	 */
	public int getEnergy(){
		/*int totalEnergy=0,totalPenalty=0;
		String assignedProject;
		CandidateAssignment canAssignment;
		
	    Set<CandidateAssignment> candidateAssignments = (Set<CandidateAssignment>) table.values();
	    Iterator<CandidateAssignment> itrAssignments = candidateAssignments.iterator();
	    
	    while (itrAssignments.hasNext()) {
	    	canAssignment =itrAssignments.next();
	    	totalEnergy+=canAssignment.getEnergy();
	    	assignedProject=canAssignment.getAssignedProject();		    	
	    	//check if project exists and put to hash table
	    	if(!solutionProjects.containsKey(assignedProject)){
	    		solutionProjects.put(assignedProject, assignedProject);
	    	}else{
	    		totalPenalty+=penalty;
	    	}
	    } 
	    return totalEnergy-totalPenalty;*/
		int energy = 0, counter = 0;
		Iterator<String> it = getSolution().keySet().iterator();
		Hashtable<String, Integer> collisionCounter = new Hashtable<String, Integer>();
		
		while (it.hasNext()) {
			String sname = it.next();
			energy += getSolution().get(sname).getEnergy();
			if (!collisionCounter.containsKey(getSolution().get(sname).getAssignedProject()))  {
				collisionCounter.put(getSolution().get(sname).getAssignedProject(), 1);
			} else {
				counter++;
			}
        }
		energy += (counter*penalty);
		return energy;
	}
	
	/*
	 * to get the fitness of the solution
	 */
	public int getFitness(){
		return -(this.getEnergy());
	}

	/**
	 * @return the solution
	 */
	public Hashtable<String,CandidateAssignment> getSolution() {
		return solution;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(Hashtable<String,CandidateAssignment> solution) {
		this.solution = solution;
	}
}
