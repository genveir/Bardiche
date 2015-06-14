package com.geertenvink.Bardiche;

import com.geertenvink.Bardiche.io.IOHandler;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.State;

public class Main {
	public static void main(String[] args) {		
		// set domain and problem
		String domainName = "tests/door-domain-sc.pddl";
		String problemName = "tests/door-problem-sc.pddl";
		
		// get domain and problem from args if supplied
		if (args.length > 0) {
			 domainName = args[0];
		}
		if (args.length > 1) {
			problemName = args[1];
		}
		
		Object oDomain = domainName;
		Object oProblem = problemName;
		
		Bardiche planner = new Bardiche();
		ArgumentMap arguments = planner.makeArguments();
		
		boolean complete = false;
		boolean buildingTension = true;
		boolean goalChanged = true;
		while (!complete) {			
			arguments.set(Bardiche.DOMAIN, oDomain);
			arguments.set(Bardiche.PROBLEM, oProblem);
			
			// generate part of the narrative
			BardichePlan plan = planner.generate(arguments, goalChanged);
			if (goalChanged) goalChanged = false;
			
			if (plan == null) {
				System.out.println("unfortunately, Bardiche was unable to generate a story");
				break;
			}
			else {
				complete = plan.complete;
				
				// TODO maintain and update steps list
				
				if (!complete || buildingTension) {
					GlaivePlan executedPlan = plan.getExecutedPlan(arguments, false);
					
					StepsList.addSteps(executedPlan);
					if (!complete) {
						// pick an action, add it to the plan
						BardicheStep step = new BardicheStepPicker(arguments, executedPlan).pick();
							
						executedPlan.addStep(step);
						
						StepsList.addStep(step);
					}
					State state = executedPlan.getCurrentState();
					
					BardicheProblem problem = arguments.get(Bardiche.PROBLEM);
					
					if (problem.goal.test(state)) complete = true; 
					System.out.println(state);
					System.out.println(problem.goal + " = " + complete); //SCRIPTIE
					
					if (complete) {
						buildingTension = false;
						complete = false;
						problem.bardicheGoal.setFinalGoal(state);
						goalChanged = true;
					}
					
					BardicheProblem newProblem = new BardicheProblem(
							problem.name,
							problem.domain,
							problem.protagonist,
							problem.universe,
							state.toExpression(),
							problem.bardicheGoal);
					
					oProblem = newProblem;
				}
			}
		}
		
		System.out.println("full story:");
		IOHandler.print(arguments, new BardichePlan(arguments));
		
		IOHandler.close();
	}
}

/* SCRIPTIE ensure in goals dat laatste actie door protagonist wordt genomen, bijvoorbeeld door te zorgen dat er altijd
   een unieke intentie van de protagonist in de eindgoal zit. Waarschijnlijk is het een goed idee om te zorgen dat de
   goals van de user een subset zijn van de story goal */ 
