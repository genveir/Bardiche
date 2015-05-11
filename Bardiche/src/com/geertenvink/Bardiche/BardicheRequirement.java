package com.geertenvink.Bardiche;

import com.stephengware.java.planware.Domain;
import com.stephengware.java.planware.Problem;
import com.stephengware.java.planware.io.Extension;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.pddl.Requirement;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;

public class BardicheRequirement extends Requirement {
	private static final Extension<SExpression, SExpression> BARDICHE_PLAN_EXTENSION = new BardichePlanExtension();
	private static final Extension<SExpression, SExpression> BARDICHE_PROBLEM_EXTENSION = new BardicheProblemExtension();
	
	public static final Requirement BARDICHE = new BardicheRequirement();
	
	private BardicheRequirement(){
		super("bardiche", "interactive systems for Bardiche",
				BARDICHE_PROBLEM_EXTENSION,
				BARDICHE_PLAN_EXTENSION);
	}
	
	@Override
	public Iterable<Requirement> getPrerequisites() {
		// We can't run Bardiche without intentionality, so let's require it.
		/*return new ImmutableArray<Requirement>(new Requirement[]{
				IntentionalityRequirement.INTENTIONALITY
		});*/
		return NO_PREREQUISITES;
	}

	@Override
	protected void load(Parser<SExpression> parser){
		super.load(parser);
	}
	
	@Override
	public boolean isRequiredBy(Domain domain) {
		return true;
	}

	@Override
	public boolean isRequiredBy(Problem problem) {
		return true;
	}

}