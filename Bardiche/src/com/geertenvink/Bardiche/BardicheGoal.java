package com.geertenvink.Bardiche;

import java.util.ArrayList;

import com.stephengware.java.planware.logic.Disjunction;
import com.stephengware.java.planware.logic.Expression;

public class BardicheGoal {
	private ArrayList<Expression> positiveGoals = new ArrayList<Expression>();
	private ArrayList<Expression> negativeGoals = new ArrayList<Expression>();

	public BardicheGoal(ExclusiveDisjunction goal) {
		Disjunction positiveGoalDisjunct = (Disjunction) goal.arguments.get(0);
		Disjunction negativeGoalDisjunct = (Disjunction) goal.arguments.get(1);
		
		addArgumentsToArrayList(positiveGoalDisjunct, positiveGoals);
		addArgumentsToArrayList(negativeGoalDisjunct, negativeGoals);
	}
	
	private void addArgumentsToArrayList(Disjunction disjunct, ArrayList<Expression> list) {
		for (Expression argument : disjunct.arguments) {
			list.add(argument);
		}
	}
	
	public Expression getGoal() {
		return null;
	}
}
