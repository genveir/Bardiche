package com.geertenvink.Bardiche;

import java.util.ArrayList;
import java.util.Iterator;

import com.geertenvink.Bardiche.io.IOHandler;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.GlaiveRelaxedPlanBuilder;
import com.stephengware.java.glaive.GlaiveSearchNode;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.ig.IntentionGraph;
import com.stephengware.java.planware.logic.Constant;

public class BardicheStepPicker extends GlaiveRelaxedPlanBuilder {
	private final Constant protagonist;
	
	public BardicheStepPicker(ArgumentMap arguments, GlaivePlan executedPlan) {
		super(new IntentionGraph(arguments.get(Bardiche.STATE_SPACE)), arguments.get(Bardiche.PROBLEM).goal);
		this.protagonist = arguments.get(Bardiche.PROBLEM).protagonist;
		
		GlaiveSearchNode node = new GlaiveSearchNode(executedPlan, null);
		
		evaluate(node);		
	}
	
	@SuppressWarnings("unchecked")
	public BardicheStep pick() {
		Iterator<BardicheStep> steps = (Iterator<BardicheStep>) getNextSteps();
		ArrayList<BardicheStep> possibleActions = new ArrayList<BardicheStep>();
		while (steps.hasNext()) {
			BardicheStep step = steps.next();
			
			if (step.initiator == protagonist) {
				System.out.println(possibleActions.size() + ") " + step);
				possibleActions.add(step);
			}
		}
		
		int choice = -1;
		while (choice == -1) {
			choice = IOHandler.getInputInt(0, possibleActions.size() - 1);
		
			if (choice == -1) System.out.println("Illegal choice, please try again");
		}
		return possibleActions.get(choice);
	}
}
