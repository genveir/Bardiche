package com.geertenvink.Bardiche;

import com.stephengware.java.glaive.DefaultGlaiveSearch;
import com.stephengware.java.glaive.Glaive;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.glaive.GlaiveRelaxedPlanBuilder;
import com.stephengware.java.glaive.GlaiveSearchNode;
import com.stephengware.java.glaive.PartialPlan;
import com.stephengware.java.planware.Argument;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.AxiomTree;
import com.stephengware.java.planware.IntentionalProblem;
import com.stephengware.java.planware.Search;
import com.stephengware.java.planware.ig.IntentionGraph;
import com.stephengware.java.planware.search.HeuristicSearch;
import com.stephengware.java.planware.ss.IntentionalStateSpace;

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
	
	public Bardiche() {
		super();
		this.io.install(BardicheRequirement.BARDICHE);
		this.io.install(new BardichePlanExtension());
	}
	
	public ArgumentMap makeArguments(){
		ArgumentMap arguments = super.makeArguments();
		
		return arguments;
	}
	
	@Override
	public Search search(ArgumentMap arguments) {
		BardicheProblem problem = arguments.get(PROBLEM);
		
		return super.search(arguments);
		
		/*boolean debug = arguments.get(DEBUG);
		IntentionalStateSpace space = arguments.get(STATE_SPACE);
		AxiomTree axiomTree = arguments.get(AXIOM_TREE);
		PartialPlan remaining = arguments.get(PARTIAL_PLAN);
		
		GlaivePlan rootPlan = new BardichePlan(problem.name + "-solution", problem, axiomTree);
		GlaiveSearchNode rootNode = new GlaiveSearchNode(rootPlan, remaining);
		GlaiveRelaxedPlanBuilder relaxedPlanner = new GlaiveRelaxedPlanBuilder(new IntentionGraph(space), problem.getGoal());
		relaxedPlanner.evaluate(rootNode);
		HeuristicSearch<GlaiveSearchNode> strategy = new HeuristicSearch<GlaiveSearchNode>(GLAIVE_HEURISTIC, GLAIVE_TIE_BREAKER, rootNode, debug);
		
		return new DefaultGlaiveSearch(strategy, relaxedPlanner, problem.getGoal());*/
	}
}
