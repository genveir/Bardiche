package com.geertenvink.Bardiche;

import java.util.ArrayList;

import com.stephengware.java.cpocl.Utilities;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.StepEvent;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.logic.Constant;

public class BardichePlan extends GlaivePlan {
	public final Constant protagonist;
	
	public final boolean complete;
	private final int completedSteps;
	public final ArrayList<BardicheStep> executedSteps;
	public final BardicheStep lastStep;
	
	public BardichePlan(GlaivePlan plan, ArgumentMap arguments, int completedSteps) {
		super(plan.name, arguments.get(Bardiche.PROBLEM), arguments.get(Bardiche.AXIOM_TREE));
		setSteps(plan);
		protagonist = arguments.get(Bardiche.PROBLEM).protagonist;
		
		this.completedSteps = completedSteps;
		executedSteps = getExecutedSteps();
		lastStep = executedSteps.get(executedSteps.size() -1);
		complete = getComplete(arguments);
	}
	
	private boolean getComplete(ArgumentMap arguments) {
		GlaivePlan executedPlan = getExecutedPlan(arguments, !lastStep.agents.contains(protagonist));
		
		// SCRIPTIE moet in twee stappen. Plannen naar spanning, dan naar einde.
		return executedPlan.getCurrentState().isTrue(arguments.get(Bardiche.PROBLEM).goal);
	}
	
	public GlaivePlan getExecutedPlan(ArgumentMap arguments, boolean getSuggestion) {
		int suggestionModifier = (getSuggestion) ? 0 : -1;
		int planSize = executedSteps.size() + suggestionModifier;
		
		if (planSize < completedSteps) planSize = completedSteps;
		
		GlaivePlan executedPlan = new GlaivePlan(name, problem, arguments.get(Bardiche.AXIOM_TREE));
		
		for(int numSteps = 0; numSteps < planSize; numSteps++) {
			BardicheStep step = executedSteps.get(numSteps);
			executedPlan.addStep(step);
		}
		
		return executedPlan;
	}
	
	private void setSteps(GlaivePlan plan) {
		for (StepEvent step : Utilities.getSteps(plan)) {
			if (Utilities.isExecuted(step, plan)) addStep(step.source);
		}
	}
	
	private ArrayList<BardicheStep> getExecutedSteps() {
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
