package com.geertenvink.Bardiche;

import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.GlaiveRelaxedPlanBuilder;
import com.stephengware.java.glaive.GlaiveSearchNode;
import com.stephengware.java.planware.ig.IntentionGraph;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.ss.IntentionalStateSpace;

public class PossibilityEvaluator extends GlaiveRelaxedPlanBuilder {
	public PossibilityEvaluator(IntentionalStateSpace stateSpace, Expression goal) {
		super(new IntentionGraph(stateSpace), goal);
	}
	
	public boolean isPossible(GlaivePlan possibilityPlan) {
		GlaiveSearchNode node = new GlaiveSearchNode(possibilityPlan);
		
		evaluate(node);
		
		return node.isAuthorGoalAchievable();
	}
}
