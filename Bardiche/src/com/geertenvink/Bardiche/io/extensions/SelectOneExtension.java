package com.geertenvink.Bardiche.io.extensions;

import com.geertenvink.Bardiche.logic.SelectOne;
import com.stephengware.java.planware.io.pddl.BooleanExpressionExtension;
import com.stephengware.java.planware.logic.Expression;

public class SelectOneExtension extends BooleanExpressionExtension<SelectOne, Expression> {
	
	public static final String SELECT_ONE_OPERATOR = "select";
	
	public SelectOneExtension() {
		super(SelectOne.class, SELECT_ONE_OPERATOR, Expression.class, 2, -1);
	}
	
	@Override
	protected SelectOne construct(Expression[] arguments) {
		return new SelectOne(arguments);
	}
}
