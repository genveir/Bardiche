package com.geertenvink.Bardiche;

import com.stephengware.java.planware.IntentionalProblem;
import com.stephengware.java.planware.Problem;
import com.stephengware.java.planware.Universe;
import com.stephengware.java.planware.logic.Constant;
import com.stephengware.java.planware.logic.Entity;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.Substitution;
import com.stephengware.java.planware.util.ImmutableArray;
import com.stephengware.java.planware.util.ImmutableSet;

public class BardicheProblem extends IntentionalProblem {
	public final Constant protagonist;
	public final BardicheDomain domain;
	
	public BardicheProblem(String name, BardicheDomain domain,
			Constant protagonist, Universe universe, 
			Expression initialState, Expression goal) {
		super(name, domain, universe, initialState, goal);
		this.protagonist = protagonist;
		this.domain = domain;
	}
	
	@Override
	protected boolean testCompatibility(Problem other) {
		return other instanceof BardicheProblem;
	}
	
	@Override
	public BardicheProblem substitute(Substitution substitution){
		boolean different = false;
		BardicheDomain domain = this.domain.substitute(substitution);
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
	public BardicheStep makeStep(String operatorName, Entity...arguments){
		BardicheOperator operator = domain.getOperator(operatorName);
		Substitution substitution = getSubstitution(operator.parameters, arguments);
		Constant[] agents = new Constant[operator.agents.length];
		Constant initiator = null;
		for(int i=0; i<agents.length; i++) {
			agents[i] = substitution.substitute(operator.agents.get(i), Constant.class);
			if (operator.agents.get(i).equals(operator.initiator)) initiator = agents[i]; 
		}
		return new BardicheStep(operator, new ImmutableArray<Entity>(arguments), operator.precondition.substitute(substitution), 
				operator.effect.substitute(substitution), new ImmutableSet<>(agents), initiator);
	}
}
