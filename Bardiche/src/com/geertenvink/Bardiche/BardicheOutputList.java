package com.geertenvink.Bardiche;

import java.util.LinkedList;

import com.geertenvink.Bardiche.logic.InitializationException;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.StepEvent;
import com.stephengware.java.planware.logic.Constant;

public class BardicheOutputList {
	private final LinkedList<StepEvent> steps = new LinkedList<StepEvent>();
	
	private Constant protagonist;
	
	public void setProtagonist(Constant protagonist) {
		this.protagonist = protagonist;
	}
	
	public Constant getProtagonist() throws InitializationException {
		if (protagonist == null) 
			throw new InitializationException("Protagonist has not been set");
		
		return protagonist;
	}
	
	public void addStep(StepEvent step) {
		steps.add(step);
	}
	
	public void addSteps(GlaivePlan plan) {
		for (int time = 1; time <= plan.getCurrentTime(); time++) {
			addStep(plan.getStep(time));
		}
	}
	
	public int getStepsCount() {
		return steps.size();
	}
	
	public StepEvent getStep(int time) {
		return steps.get(time -1);
	}
}
