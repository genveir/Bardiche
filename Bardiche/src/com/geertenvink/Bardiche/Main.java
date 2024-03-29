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
		
		BardicheOutputList outputList = new BardicheOutputList();
		
		boolean complete = false;
		boolean buildingSuspense = true;
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
				
				GlaivePlan executedPlan = plan.getExecutedPlan();
				
				if (!complete || buildingSuspense) {
					
					if (!complete) {
						// pick an action, add it to the plan
						BardicheStep step = new BardicheStepPicker(arguments, executedPlan).pick();
							
						executedPlan.addStep(step);
					}
					State state = executedPlan.getCurrentState();
					
					BardicheProblem problem = arguments.get(Bardiche.PROBLEM);
					
					if (problem.goal.test(state)) complete = true;
					
					if (complete) {
						System.out.println("\nSuspense building complete\n");
						
						buildingSuspense = false;
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
				
				outputList.addSteps(executedPlan);
			}
		}
		
		System.out.println("\nStory complete\n");
		
		System.out.println("full story:");
		outputList.setProtagonist(arguments.get(Bardiche.PROBLEM).protagonist);
		IOHandler.print(arguments, outputList);
		
		IOHandler.close();
	}
}
