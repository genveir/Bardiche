package com.geertenvink.Bardiche;

import com.stephengware.java.planware.Domain;
import com.stephengware.java.planware.Problem;
import com.stephengware.java.planware.io.Extension;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.pddl.Requirement;
import com.stephengware.java.planware.io.pddl.intp.IntentionalityRequirement;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.util.ImmutableArray;

public class BardicheRequirement extends Requirement {
	private static final Extension<SExpression, SExpression> BARDICHE_PLAN_EXTENSION = new BardichePlanExtension();
	private static final Extension<SExpression, SExpression> BARDICHE_PROBLEM_EXTENSION = new BardicheProblemExtension();
	private static final Extension<SExpression, SExpression> BARDICHE_DOMAIN_EXTENSION = new BardicheDomainExtension();
	private static final Extension<SExpression, SExpression> BARDICHE_OPERATOR_EXTENSION = new BardicheOperatorExtension();
	
	public static final Requirement BARDICHE = new BardicheRequirement();
	
	private BardicheRequirement(){
		super("bardiche", "interactive systems for Bardiche",
				BARDICHE_OPERATOR_EXTENSION,
				BARDICHE_PROBLEM_EXTENSION,
				BARDICHE_DOMAIN_EXTENSION,
				BARDICHE_PLAN_EXTENSION);
	}
	
	@Override
	public Iterable<Requirement> getPrerequisites() {
		// We can't run Bardiche without intentionality, so let's require it.
		return new ImmutableArray<Requirement>(new Requirement[]{
				IntentionalityRequirement.INTENTIONALITY
		});
	}

	@Override
	protected void load(Parser<SExpression> parser){
		super.load(parser);
	}
	
	// in any domain or problem where bardiche is a requirement, we want to use
	// the bardiche problems and plans.
	@Override
	public boolean isRequiredBy(Domain domain) {
		return true;
	}

	@Override
	public boolean isRequiredBy(Problem problem) {
		return true;
	}

}
