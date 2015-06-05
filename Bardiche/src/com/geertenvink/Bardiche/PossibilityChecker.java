package com.geertenvink.Bardiche;

import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.AxiomTree;
import com.stephengware.java.planware.State;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.ss.IntentionalStateSpace;
import com.stephengware.java.planware.IntentionalProblem;

public class PossibilityChecker {
	private static BardicheProblem problem;
	private static AxiomTree axiomTree;
	private static IntentionalStateSpace stateSpace;
	
	private static boolean initialized = false;
	
	public static void initialize(ArgumentMap arguments) {
		problem = arguments.get(Bardiche.PROBLEM);
		axiomTree = arguments.get(Bardiche.AXIOM_TREE);
		stateSpace = arguments.get(Bardiche.STATE_SPACE);
		
		initialized = true;
	}
	
	public static boolean test(Expression argument, State state) {
		if (!initialized)
			try {
				throw new InitializationException("PossibilityChecker used before initialization");
			} catch (InitializationException e) {
				e.printStackTrace();
				return false;
			}
		
		IntentionalProblem possibilityProblem = new IntentionalProblem(
				"possibilityCheckerProblem",
				problem.domain,
				problem.universe,
				state.toExpression(),
				argument);
		GlaivePlan possibilityPlan = new GlaivePlan("possibilityCheckerPlan", possibilityProblem, axiomTree);
		PossibilityEvaluator evaluator = new PossibilityEvaluator(stateSpace, argument);
		
		return evaluator.isPossible(possibilityPlan);
	}
}