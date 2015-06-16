package com.geertenvink.Bardiche;

import com.geertenvink.Bardiche.io.IOHandler;
import com.geertenvink.Bardiche.io.extensions.BardicheOutputListExtension;
import com.geertenvink.Bardiche.io.extensions.BardichePlanExtension;
import com.geertenvink.Bardiche.io.extensions.BardicheRequirement;
import com.geertenvink.Bardiche.logic.PossibilityChecker;
import com.stephengware.java.glaive.Glaive;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.Argument;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.Result;
import com.stephengware.java.planware.Search;
import com.stephengware.java.planware.io.pddl.PDDLManager;

/** Bardiche is an extension of Glaive which ensures a protagonist will be read from
  * the problem file, and that the I/O extensions required to input such problem files and
  * to output plans in the special Bardiche format will be installed.
  */ 
public class Bardiche extends Glaive {
	public static final Argument<BardicheProblem> PROBLEM = new Argument.ParsedArgument<BardicheProblem>(
			Glaive.PROBLEM.name, Glaive.PROBLEM.abbreviation, BardicheProblem.class) {
		
		@Override
		protected BardicheProblem getValue(Object object, ArgumentMap arguments){
			// make sure the domain is parsed before the problem 			
			arguments.get(DOMAIN);
			return super.getValue(object, arguments);
		}
	};
	
	public static final Argument<BardicheDomain> DOMAIN = new Argument.ParsedArgument<BardicheDomain>(
			Glaive.DOMAIN.name, Glaive.DOMAIN.abbreviation, BardicheDomain.class);
	
	@Override
	public ArgumentMap makeArguments() {
		ArgumentMap arguments = super.makeArguments();
		PDDLManager io = (PDDLManager) arguments.get(IO_MANAGER);
		io.install(BardicheRequirement.BARDICHE);
		io.install(new BardichePlanExtension());
		io.install(new BardicheOutputListExtension());
		
		return arguments;
	}
	
	public BardichePlan generate(ArgumentMap arguments, boolean goalChanged) {		
		// we need to parse the problem before Glaive does so, or it will
		// attempt to parse it as an IntentionalProblem.
		arguments.get(PROBLEM);
		
		if (goalChanged) PossibilityChecker.initialize(arguments);
		
		Search search = search(arguments);
		
		Result result = search.getNextPlan(arguments);
		
		BardichePlan bardichePlan = null;
		
		if (result.getSuccess()) {
			boolean doContinue = false;
			
			GlaivePlan plan = (GlaivePlan) result.getPlan();
			
			do {
				bardichePlan = new BardichePlan(plan, arguments);
				
				IOHandler.print(arguments, bardichePlan);
				if (bardichePlan.complete) break;
				
				BardicheStep suggestedStep = bardichePlan.getSuggestedStep();
				boolean protagonistIsInitiator = (suggestedStep.initiator == bardichePlan.protagonist);
				
				doContinue = queryUserToContinue(suggestedStep, protagonistIsInitiator);
				if (doContinue) suggestedStep.approve();
			} while (doContinue);
		}
		
		return bardichePlan;
	}
	
	private boolean queryUserToContinue(BardicheStep lastStep, boolean protagonistIsInitiator) {
		if (protagonistIsInitiator)
			System.out.print("take");
		else System.out.print("allow");
		System.out.println(" action (" + lastStep + ")? (y/n)");
		
		switch(IOHandler.getInputString()) {
			case "y":
				return true;
			default:
				return false;
		}
	}
}
