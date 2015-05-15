package com.geertenvink.Bardiche;

import java.util.ArrayList;

import com.stephengware.java.cpocl.Utilities;
import com.stephengware.java.glaive.AgentGoal;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.StepEvent;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.logic.Constant;

public class BardichePlan extends GlaivePlan {
	public final Constant protagonist;
	public final BardicheProblem problem;
	public final boolean complete;
	private final int completedSteps;
	private final ArrayList<BardicheStep> executedSteps;
	
	public BardichePlan(GlaivePlan plan, ArgumentMap arguments, int completedSteps) {
		super(plan);
		problem = arguments.get(Bardiche.PROBLEM);
		protagonist = problem.protagonist;
		this.completedSteps = completedSteps;
		executedSteps = getExecutedSteps();
		complete = getComplete(arguments);
	}
	
	private boolean getComplete(ArgumentMap arguments) {
		GlaivePlan executedPlan = new GlaivePlan(name, problem, arguments.get(Bardiche.AXIOM_TREE));
		
		for(int numSteps = 0; numSteps < executedSteps.size(); numSteps++) {
			if (numSteps != completedSteps) {
				BardicheStep step = executedSteps.get(numSteps);
				executedPlan.addStep(step);
			}
		}
		
		for(AgentGoal goal : executedPlan.agentGoals()) {
			if (goal.agent == protagonist && !executedPlan.getCurrentState().isTrue(goal.goal)) {
				
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<BardicheStep> getExecutedSteps() {
		int numSteps = 0;
		ArrayList<BardicheStep> steps = new ArrayList<BardicheStep>();
		
		for(StepEvent step : Utilities.getSteps(this)){
			BardicheStep bardicheStep = (BardicheStep) step.source;

			if (!Utilities.isExecuted(step, this)) {
				continue;
			} else {
				numSteps++;
			}
			
			if (numSteps <= completedSteps || !step.source.agents.contains(protagonist)) {
				steps.add(bardicheStep);
			}
			else {
				steps.add(bardicheStep);
				break;
			}
		}
		
		return steps;
	}
}
