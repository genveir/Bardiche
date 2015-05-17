package com.geertenvink.Bardiche;

import com.geertenvink.Bardiche.io.IOHandler;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.logic.Expression;

public class Main {
	public static void main(String[] args) {		
		// set domain and problem
		String domainName = "tests/door-domain.pddl";
		String problemName = "tests/door-problem.pddl";
		
		// get domain and problem from args if supplied
		if (args.length > 0) {
			 domainName = args[0];
		}
		if (args.length > 1) {
			problemName = args[1];
		}
		
		Object oDomain = domainName;
		Object oProblem = problemName;
		
		boolean complete = false;
		while (!complete) {
			Bardiche planner = new Bardiche();
			ArgumentMap arguments = planner.makeArguments();
			
			arguments.set(Bardiche.DOMAIN, oDomain);
			arguments.set(Bardiche.PROBLEM, oProblem);
			
			// generate part of the narrative
			BardichePlan plan = planner.generate(arguments);
			complete = plan.complete;
			
			// TODO maintain and update steps list
			
			if (!complete) {
				GlaivePlan executedPlan = plan.getExecutedPlan(arguments, false);
				
				// pick an action, add it to the plan
				BardicheStep step = new BardicheStepPicker(arguments, executedPlan).pick();
				
				executedPlan.addStep(step);

				Expression state = executedPlan.getCurrentState().toExpression();
			
				BardicheProblem problem = arguments.get(Bardiche.PROBLEM);
				BardicheProblem newProblem = new BardicheProblem(
						problem.name,
						problem.domain,
						problem.protagonist,
						problem.universe,
						state,
						problem.goal);
				
				oDomain = problem.domain;
				oProblem = newProblem;
			}
		}
		
		IOHandler.close();
	}
}
