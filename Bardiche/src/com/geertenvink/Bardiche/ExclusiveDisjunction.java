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
import com.stephengware.java.planware.logic.Negation;
import com.stephengware.java.planware.logic.Substitution;

public class ExclusiveDisjunction extends NAryBooleanExpression {
	protected static final String EXCLUSIVE_DISJUNCTION_PREDICATE = "xor";
	
	public ExclusiveDisjunction(Expression...arguments) {
		super(EXCLUSIVE_DISJUNCTION_PREDICATE, flatten(arguments));
		if (arguments.length < 2)
			throw new FormatException(getDescription(), "2 or more arguments required.");
	}
	
	private static final Expression[] flatten(Expression[] arguments) {
		LinkedList<Expression> flat = new LinkedList<>();
		for (Expression argument : arguments) {
			if (argument instanceof ExclusiveDisjunction) {
				for (Expression innerArgument : ((ExclusiveDisjunction) argument).arguments)
					flat.add(innerArgument);
			}
			else flat.add(argument);
		}
		return flat.toArray(new Expression[flat.size()]);
	}
	
	public ExclusiveDisjunction(List<? extends Expression> arguments) {
		this(arguments.toArray(new Expression[arguments.size()]));
	}
	
	@Override
	public Expression substitute(Substitution substitution) {
		Expression lastTruthValue = null;
		
		Expression substituted = substitution.substitute(this, Expression.class);
		if (this != substituted)
			return substituted;
		LinkedList<Expression> substitutedArguments = new LinkedList<>();
		boolean different = false;
		for (Expression argument : arguments) {
			Expression transformedArgument = argument.substitute(substitution);
			// if the transformed argument is a truth value, xor it with the stored
			// truth value, or just store it if no such argument exists.
			if (transformedArgument == Expression.TRUE ||
					transformedArgument == Expression.FALSE) {
				if (lastTruthValue == null) {
					lastTruthValue = Expression.TRUE;
				}
				else if (lastTruthValue == transformedArgument) {
					lastTruthValue = Expression.FALSE;
				}
				else lastTruthValue = Expression.TRUE;
				
				different = true;
				continue;
			}
			if (argument != transformedArgument)
				different = true;
			substitutedArguments.add(transformedArgument);
		}
		
		// add any stored truth value to the argument list
		if (lastTruthValue != null) {
			substitutedArguments.add(lastTruthValue);
		}
		
		if (!different) return this;
		
		if (substitutedArguments.size() == 0)
			return Expression.TRUE.substitute(substitution);
		else if (substitutedArguments.size() == 1)
			return substitutedArguments.get(0);
		else return new ExclusiveDisjunction(substitutedArguments).substitute(substitution);
	}
	
	@Override
	public boolean test(State state) {
		boolean result = arguments.get(0).test(state);
		for (int i = 1; i < arguments.length; i++)
			result = arguments.get(i).test(state) ^ result;
		
		return result;
	}
	
	// I don't quite know what this does, but the impose on disjunctions throws a "non deterministic
	// exception", and an xor is not deterministic, so I'll set this to false.
	@Override
	public boolean isImposable() {
		return false;
	}
	
	@Override
	public boolean impose(State state) {
		throw new NonDeterministicException(this);
	}
	
	@Override
	public ExclusiveDisjunction negate() {
		Expression[] newArguments = new Expression[arguments.length];
		newArguments[0] = arguments.get(0).negate();
		for (int i = 1; i < arguments.length; i++) {
			newArguments[i] = arguments.get(i);
		}
		return new ExclusiveDisjunction(newArguments);
	}
	
	@Override
	public Expression toCNF() {
		return asConjunction().toCNF();
	}
	
	@Override
	public Expression toDNF() {
		return asConjunction().toDNF();
	}
	
	private Expression asConjunction() {
		LinkedList<Expression> args = (LinkedList<Expression>) arguments.toList();
		
		return asConjunction(args, null);
	}
	
	// a xor b = (a v b) /\ -(a /\ b)
	private Expression asConjunction(LinkedList<Expression> arguments, Expression conjunction) {
		if (arguments.size() == 0) return conjunction;
		
		Expression nextArgument = arguments.removeFirst();
		if (conjunction == null) return (asConjunction(arguments, nextArgument));
		Expression[] currentAndNext = new Expression[] {nextArgument, conjunction};
		
		Disjunction firstArgument = new Disjunction(currentAndNext);
		Negation secondArgument = new Negation(new Conjunction(currentAndNext));
		return asConjunction(arguments, new Conjunction(new Expression[] {firstArgument, secondArgument}));
	}
	
	public String getDescription() {
		return "exclusive disjunction";
	}
}
