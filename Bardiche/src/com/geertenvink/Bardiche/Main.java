package com.geertenvink.Bardiche;

import com.geertenvink.Bardiche.io.IOHandler;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.logic.Expression;

public class Main {
	public static void main(String[] args) {		
		// set domain and problem
		String domainName = "tests/dungeon-domain.pddl";
		String problemName = "tests/dungeon-simple-problem.pddl";
		
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
			
			if (plan == null) {
				System.out.println("unfortunately, Bardiche was unable to generate a story");
				break;
			}
			else {
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
		}
		
		IOHandler.close();
	}
}

/* SCRIPTIE ensure in goals dat laatste actie door protagonist wordt genomen, bijvoorbeeld door te zorgen dat er altijd
   een unieke intentie van de protagonist in de eindgoal zit. Waarschijnlijk is het een goed idee om te zorgen dat de
   goals van de user een subset zijn van de story goal */ 

// SCRIPTIE lelijke shit dat Glaive niet test of expressions goed getyped zijn in problem

// SCRIPTIE disjunctie van goals in story goal doen, en selectief opties afsluiten tijdens verhaal. Completeness is wanneer
// een van de goals gebeurd is, en de andere niet meer possible zijn.

// SCRIPTIE tweeplaatsige conjunctie van disjuncties doen. "Goede aflopen" en "Slechte aflopen". Die twee disjuncties
// moeten ook éénplaatsig kunnen zijn (en dan dus eigenlijk geen disjunctie).

// SCRIPTIE possibility moet dan wel duidelijk zijn, je wilt niet dat hij een jaar denkt over een oplossing die niet kan

// SCRIPTIE genereren van opties voor goals heb je een semantiek van je verhaal voor nodig, dat ligt buiten de scope van
// dit project, dus doen we niet. Vandaar goals met de hand.
