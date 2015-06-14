package com.geertenvink.Bardiche;

import java.util.LinkedList;

import com.stephengware.java.cpocl.Utilities;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.StepEvent;

public class StepsList {
	public static LinkedList<BardicheStep> steps = new LinkedList<BardicheStep>();
	
	public static void addStep(BardicheStep step) {
		System.out.println(step); //SCRIPTIE
		
		steps.add(step);
	}
	
	public static void addSteps(GlaivePlan plan) {
		System.out.println("adding steps");
		for (StepEvent step : Utilities.getSteps(plan)) {
			addStep((BardicheStep) step.source);
		}
	}
}
