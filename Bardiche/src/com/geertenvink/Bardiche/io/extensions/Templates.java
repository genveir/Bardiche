package com.geertenvink.Bardiche.io.extensions;

import com.geertenvink.Bardiche.BardicheDomain;
import com.geertenvink.Bardiche.BardicheOperator;
import com.geertenvink.Bardiche.BardicheProblem;
import com.geertenvink.Bardiche.logic.Possibility;
import com.stephengware.java.planware.io.Field;
import com.stephengware.java.planware.logic.Constant;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.Term;

public class Templates {
	public static abstract class BardicheProblemTemplate extends com.stephengware.java.planware.io.pddl.intp.Templates.IntentionalProblemTemplate {
		public static final Field<BardicheProblem, BardicheDomain> DOMAIN = new Field<>(BardicheProblem.class, "domain", BardicheDomain.class);
		public static final Field<BardicheProblem, Constant> PROTAGONIST = new Field<>(BardicheProblem.class, "protagonist", Constant.class);
	}
	
	public static abstract class BardicheDomainTemplate extends com.stephengware.java.planware.io.pddl.intp.Templates.IntentionalDomainTemplate {
		public static final Field<BardicheDomain, BardicheOperator[]> OPERATORS = new Field<>(BardicheDomain.class, "operators", BardicheOperator[].class);
	}
	
	public static abstract class BardicheOperatorTemplate extends com.stephengware.java.planware.io.pddl.intp.Templates.IntentionalOperatorTemplate {
		public static final Field<BardicheOperator, Term> INITIATOR = new Field<>(BardicheOperator.class, "initiator", Term.class);
	}
	
	public static abstract class PossibilityTemplate {
		public static final Field<Possibility, Expression> ARGUMENT = new Field<>(Possibility.class, "argument", Expression.class);
	}
}
