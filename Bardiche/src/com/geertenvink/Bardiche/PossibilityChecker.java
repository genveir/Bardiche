package com.geertenvink.Bardiche;

import com.stephengware.java.planware.State;
import com.stephengware.java.planware.logic.*;

public class PossibilityChecker {
	public static boolean isPossible(Expression subGoal, State state) {
		// an expression not caught by a more specific method is an unknown.
		// we will return false unless the expression evaluates to true, 
		// since in that case it's possible.
		return subGoal.test(state);
	}
	
	public static boolean isPossible(Predication subGoal, State state) {
		// the only predicates allowed in bardiche goals are goals that
		// are irreversible and reachable. So any predicate in a goal
		// is possible.
		
		// SCRIPTIE eigenlijk zou er gekeken moeten worden of er een
		// agent is die het ook echt kan doen. Nu moeten we forcen dat
		// de state bereikbaar is.
		return true;
	}
	
	public static boolean isPossible(Conjunction subGoal, State state) {
		// a conjunction is possible if each of its arguments is
		// possible.
		for (Expression argument : subGoal.arguments) {
			if (!isPossible(argument, state)) return false;
		}
		return true;
	}
	
	public static boolean isPossible(Disjunction subGoal, State state) {
		// a disjunction is possible if one of its arguments is possible.
		for (Expression argument : subGoal.arguments) {
			if (isPossible(argument, state)) return true;
		}
		return false;
	}
	
	public static boolean isPossible(Negation subGoal, State state) {
		// SCRIPTIE fixen
		return !subGoal.argument.test(state);
	}
}
