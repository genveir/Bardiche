package com.geertenvink.Bardiche;

import com.stephengware.java.planware.IntentionalDomain;
import com.stephengware.java.planware.IntentionalOperator;
import com.stephengware.java.planware.IntentionalProblem;
import com.stephengware.java.planware.IntentionalStep;
import com.stephengware.java.planware.Problem;
import com.stephengware.java.planware.Universe;
import com.stephengware.java.planware.logic.Constant;
import com.stephengware.java.planware.logic.Entity;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.Substitution;
import com.stephengware.java.planware.util.ImmutableArray;
import com.stephengware.java.planware.util.ImmutableSet;

public class BardicheProblem extends IntentionalProblem {
	public final String protagonist;
	
	public BardicheProblem(String name, IntentionalDomain domain,
			String protagonist, Universe universe, 
			Expression initialState, Expression goal) {
		super(name, domain, universe, initialState, goal);
		this.protagonist = protagonist;
	}
	
	@Override
	protected boolean testCompatibility(Problem other) {
		return other instanceof BardicheProblem;
	}
	
	@Override
	public BardicheProblem substitute(Substitution substitution){
		boolean different = false;
		IntentionalDomain domain = this.domain.substitute(substitution);
		if(domain != this.domain)
			different = true;
		Universe universe = this.universe.substitute(substitution);
		if(universe != this.universe)
			different = true;
		Expression initialState = this.initialState.substitute(substitution);
		if(initialState != this.initialState)
			different = true;
		Expression goal = this.goal.substitute(substitution);
		if(goal != this.goal)
			different = true;
		if(different)
			return new BardicheProblem(name, domain, protagonist, universe, initialState, goal);
		else
			return this;
	}
	
	@Override
	public IntentionalStep makeStep(String operatorName, Entity...arguments){
		IntentionalOperator operator = domain.getOperator(operatorName);
		Substitution substitution = getSubstitution(operator.parameters, arguments);
		Constant[] agents = new Constant[operator.agents.length];
		for(int i=0; i<agents.length; i++)
			agents[i] = substitution.substitute(operator.agents.get(i), Constant.class);
		return new IntentionalStep(operator, new ImmutableArray<Entity>(arguments), operator.precondition.substitute(substitution), operator.effect.substitute(substitution), new ImmutableSet<>(agents));
	}
}