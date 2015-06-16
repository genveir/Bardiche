package com.geertenvink.Bardiche;

import java.util.LinkedList;
import java.util.List;

import com.stephengware.java.planware.FormatException;
import com.stephengware.java.planware.NonDeterministicException;
import com.stephengware.java.planware.State;
import com.stephengware.java.planware.logic.Conjunction;
import com.stephengware.java.planware.logic.Disjunction;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.NAryBooleanExpression;
import com.stephengware.java.planware.logic.Substitution;

public class SelectOne extends NAryBooleanExpression {
	protected static final String SELECT_ONE_PREDICATE = "select";
	
	public SelectOne(Expression...arguments) {
		super(SELECT_ONE_PREDICATE, arguments);
		if (arguments.length < 2)
			throw new FormatException(getDescription(), "2 or more arguments required.");
	}
	
	public SelectOne(List<? extends Expression> arguments) {
		this(arguments.toArray(new Expression[arguments.size()]));
	}
	
	@Override
	public Expression substitute(Substitution substitution) {
		Expression substituted = substitution.substitute(this, Expression.class);
		if (this != substituted)
			return substituted;
		LinkedList<Expression> substitutedArguments = new LinkedList<>();
		boolean different = false;
		for (Expression argument : arguments) {
			Expression transformedArgument = argument.substitute(substitution);

			if (argument != transformedArgument)
				different = true;
			substitutedArguments.add(transformedArgument);
		}
		
		if (!different) return this;
		
		if (substitutedArguments.size() == 0)
			return Expression.TRUE.substitute(substitution);
		else if (substitutedArguments.size() == 1)
			return substitutedArguments.get(0);
		else return new SelectOne(substitutedArguments).substitute(substitution);
	}
	
	@Override
	public boolean test(State state) {
		boolean result = arguments.get(0).test(state);
		for (int i = 1; i < arguments.length; i++) {
			boolean value = arguments.get(i).test(state); 
			if (result && value) {
				result = false;
				break;
			}
			else result = result || value;
		}
		
		return result;
	}
	
	// I don't quite know what this does, but the impose on disjunctions throws a "non deterministic
	// exception", and a select one is not deterministic, so I'll set this to false.
	@Override
	public boolean isImposable() {
		return false;
	}
	
	@Override
	public boolean impose(State state) {
		throw new NonDeterministicException(this);
	}
	
	@Override
	public Expression negate() {
		// TODO very ugly, should build a MoreThanOne expression if I have time
		return toCNF().negate();
	}
	
	@Override
	public Expression toCNF() {
		Conjunction allArguments = new Conjunction(arguments.toArray());
		
		LinkedList<Expression> pairsList = new LinkedList<>();
		for (int n = 0; n < arguments.size(); n++) {
			for (int i = n + 1; i < arguments.size(); i++) {
				Disjunction disjunct = new Disjunction(arguments.get(n).negate(), arguments.get(i).negate()); 
				pairsList.add(disjunct);
			}
		}
		Expression allPairs;
		if (pairsList.size() == 1) allPairs = pairsList.get(0); 
		else allPairs = new Conjunction(pairsList);
		
		return new Conjunction(allArguments, allPairs);
	}
	
	@Override
	public Expression toDNF() {
		// TODO very ugly
		return toCNF().toDNF();
	}
	
	public String getDescription() {
		return "exclusive disjunction";
	}
}
