package com.geertenvink.Bardiche;

import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.State;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.ss.IntentionalStateSpace;

public class PossibilityChecker {
	private static IntentionalStateSpace stateSpace;
	
	public static boolean initialized = false;
	
	public static void initialize(ArgumentMap arguments) {
		stateSpace = arguments.get(Bardiche.STATE_SPACE);
		
		BardicheProblem problem = arguments.get(Bardiche.PROBLEM);
		new PossibilityEvaluator(stateSpace, problem.goal);
		
		initialized = true;
	}
	
	public static boolean test(Expression argument, State state) throws InitializationException {
		if (!initialized) {
			throw new InitializationException("PossibilityChecker used before initialization");
		}

		PossibilityEvaluator evaluator = PossibilityEvaluator.instance;
		
		boolean result = evaluator.isPossible(state, argument);
		
		return result && !argument.test(state);
	}
}
