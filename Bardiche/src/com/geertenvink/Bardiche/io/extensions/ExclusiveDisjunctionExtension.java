package com.geertenvink.Bardiche.io.extensions;

import com.geertenvink.Bardiche.ExclusiveDisjunction;
import com.stephengware.java.planware.io.pddl.BooleanExpressionExtension;
import com.stephengware.java.planware.logic.Expression;

public class ExclusiveDisjunctionExtension extends BooleanExpressionExtension<ExclusiveDisjunction, Expression> {
	
	public static final String EXCLUSIVE_DISJUNCTION_OPERATOR = "xor";
	
	public ExclusiveDisjunctionExtension() {
		super(ExclusiveDisjunction.class, EXCLUSIVE_DISJUNCTION_OPERATOR, Expression.class, 2, -1);
	}
	
	@Override
	protected ExclusiveDisjunction construct(Expression[] arguments) {
		return new ExclusiveDisjunction(arguments);
	}
}
