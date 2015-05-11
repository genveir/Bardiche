package com.geertenvink.Bardiche;

import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;

public class BardichePlan extends GlaivePlan {
	public final String protagonist;
	public final BardicheProblem problem;
	
	public BardichePlan(GlaivePlan plan, ArgumentMap arguments) {
		super(plan);
		problem = arguments.get(Bardiche.PROBLEM);
		protagonist = problem.protagonist;
	}
}
