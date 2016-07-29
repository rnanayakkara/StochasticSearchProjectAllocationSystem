# StochasticSearchProjectAllocationSystem
01. Overview
	-This system is used to distribute a set of resources (preferences or projects) among a set of intelligent 
	agents(students) in order to respect the preferences of the agents and to maximize some measure of global utility.

02. File List
Packages
cs.resourceallocationapp.app
cs.resourceallocationapp.model
cs.resourceallocationapp.utility
	
Files
ResourceAllocation.java
ResourceAllocationForm.java
CandidateAssignment.java
CandidateSolution.java
PreferenceTable.java
StudentEntry.java
FileTypeFilter.java
ResourceAllocationTableRender.java
	
03. Algorithms Used
	---------------------------------------
	Simulated Annealing (SA)
	---------------------------------------
	Implemented using an energy function (class CandidateSolution) that reflects the inadequacy of any mapping, 
	so that SA can gradually reduce this energy and improve the mapping as much as it can.
	Energy will be a function of overall disappointment.

	---------------------------------------
	Genetic Algorithm (GA)
	---------------------------------------
	Implemented using a fitness (class CandidateSolution)  function that reflects the adequacy of a solution. 
	Fitness will be inversely related to overall disappointment.

04. Program Design
	-Have used seperate packages to organize classes belonging to the same category or providing similar functionality.
	-Error handling to make life easier for the user.
	-Important information provided for user.
		- Total number of students and valid mappings.
		- Highlight invalid mappings in table.
		- Display enery and fitness of the solution.
	
05. Result
	-Provides the best valid mapping.
	-Tip : 
		Low energy 	-> Increase quality of solution.
		High fitness 	-> Increase quality of solution.
