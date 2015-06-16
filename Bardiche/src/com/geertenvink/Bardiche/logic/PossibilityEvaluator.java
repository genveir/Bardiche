package com.geertenvink.Bardiche.logic;

import com.stephengware.java.glaive.GlaiveRelaxedPlanBuilder;
import com.stephengware.java.planware.State;
import com.stephengware.java.planware.ig.IntentionGraph;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.ss.IntentionalStateSpace;

public class PossibilityEvaluator extends GlaiveRelaxedPlanBuilder {
	public static PossibilityEvaluator instance;
	
	public PossibilityEvaluator(IntentionalStateSpace stateSpace, Expression goal) {
		super(new IntentionGraph(stateSpace), goal);
		
		instance = this;
	}
	
	public boolean isPossible(State state, Expression goal) {
		initialize(state);
		return satisfy(goal);
	}
}
