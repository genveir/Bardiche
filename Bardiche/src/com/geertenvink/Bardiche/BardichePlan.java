package com.geertenvink.Bardiche;

import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.logic.Constant;

public class BardichePlan extends GlaivePlan {
	public final Constant protagonist;
	public final BardicheProblem problem;
	
	public BardichePlan(GlaivePlan plan, ArgumentMap arguments) {
		super(plan);
		problem = arguments.get(Bardiche.PROBLEM);
		protagonist = problem.protagonist;
	}
}
