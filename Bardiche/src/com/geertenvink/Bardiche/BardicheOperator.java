package com.geertenvink.Bardiche;

import com.stephengware.java.planware.IntentionalOperator;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.Term;
import com.stephengware.java.planware.logic.Variable;
import com.stephengware.java.planware.util.ImmutableArray;
import com.stephengware.java.planware.util.ImmutableSet;

public class BardicheOperator extends IntentionalOperator {
	public final Term initiator;
	
	public BardicheOperator(String name, ImmutableArray<Variable> parameters, Expression precondition, Expression effect, 
			ImmutableSet<Term> agents, Term initiator) {
		super(name, parameters, precondition, effect, agents);
		this.initiator = initiator;
	}

	public BardicheOperator(String name, Variable[] parameters,
			Expression precondition, Expression effect, Term[] agents,
			Term initiator) {
		this(name,  new ImmutableArray<Variable>(parameters),precondition, effect, new ImmutableSet<Term>(agents), initiator);
	}
}