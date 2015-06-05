package com.geertenvink.Bardiche;

import java.util.LinkedList;
import java.util.List;

import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.Substitution;
import com.stephengware.java.planware.util.ImmutableArray;

public class BardicheGoal {
	protected ImmutableArray<Expression> goodEndings;
	protected ImmutableArray<Expression> badEndings;

	protected Expression goal;
	
	protected BardicheGoal(Expression[] goodEndings, Expression[] badEndings) {
		this.goodEndings = new ImmutableArray<Expression>(goodEndings);
		this.badEndings = new ImmutableArray<Expression>(badEndings);
		
		generateGoal();
	}
	
	public BardicheGoal(List<Expression> goodEndings, List<Expression> badEndings) {
		this(goodEndings.toArray(new Expression[goodEndings.size()]), 
		     badEndings.toArray(new Expression[badEndings.size()]));
	}
	
	private void generateGoal() {
		goal = goodEndings.get(0); // SCRIPTIE
	}
	
	public BardicheGoal substitute(Substitution substitution) {
		ImmutableArray<Expression> goodEndings = substitute(this.goodEndings, substitution);
		ImmutableArray<Expression> badEndings = substitute(this.badEndings, substitution);
		
		if (goodEndings == this.goodEndings && badEndings == this.badEndings) 
			return this;
		else 
			return new BardicheGoal(goodEndings.toList(), badEndings.toList());
	}
	
	private ImmutableArray<Expression> substitute(ImmutableArray<Expression> endings, Substitution substitution) {
		boolean different = false;
		
		LinkedList<Expression> substitutedEndings = new LinkedList<>();
		for (Expression ending : endings) {
			Expression substitutedEnding = ending.substitute(substitution);
			if (ending != substitutedEnding) different = true;
			substitutedEndings.add(substitutedEnding);
		}
		if (different) {
			Expression[] substitutedEndingsAsArray = substitutedEndings.toArray(new Expression[substitutedEndings.size()]);  
			return new ImmutableArray<Expression>(substitutedEndingsAsArray);
		}
		else 
			return endings;
	}
	
	public Expression getGoal() {
		return goal;
	}
}
