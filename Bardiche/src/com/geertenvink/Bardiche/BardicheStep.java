package com.geertenvink.Bardiche;

import com.stephengware.java.planware.IntentionalStep;
import com.stephengware.java.planware.logic.Constant;
import com.stephengware.java.planware.logic.Entity;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.util.ImmutableArray;
import com.stephengware.java.planware.util.ImmutableSet;

public class BardicheStep extends IntentionalStep {
	public final Constant initiator;
	
	public BardicheStep(BardicheOperator operator,
			ImmutableArray<Entity> arguments, Expression precondition,
			Expression effect, ImmutableSet<Constant> agents, Constant initiator) {
		super(operator, arguments, precondition, effect, agents);
		this.initiator = initiator;
	}
}
