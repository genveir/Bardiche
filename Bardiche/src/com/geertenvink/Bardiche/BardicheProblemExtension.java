package com.geertenvink.Bardiche;

import com.geertenvink.Bardiche.Templates.BardicheProblemTemplate;
import com.geertenvink.Bardiche.Templates.ProtagonistTemplate;
import com.stephengware.java.planware.Domain;
import com.stephengware.java.planware.IntentionalDomain;
import com.stephengware.java.planware.Universe;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.ParseException;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.SimpleExtension;
import com.stephengware.java.planware.io.Templates;
import com.stephengware.java.planware.io.pddl.sexp.List;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.logic.Expression;

public class BardicheProblemExtension extends SimpleExtension<SExpression, BardicheProblem> {
	public static final String PROTAGONIST_IDENTIFIER = ":protagonist";
	
	public BardicheProblemExtension() {
		super(BardicheProblem.class);
	}

	@Override
	public void parse(SExpression document, Parser<SExpression> parser)
			throws ParseException {
		String name = parser.require(BardicheProblemTemplate.NAME);
		Domain domain = parser.require(Templates.ProblemTemplate.DOMAIN);
		String protagonist = parseProtagonist(document.asList(), parser);
		Universe universe = parser.require(BardicheProblemTemplate.UNIVERSE);
		Expression initialState = parser.require(BardicheProblemTemplate.INITIAL_STATE);
		Expression goal = parser.require(BardicheProblemTemplate.GOAL);
		succeed(new BardicheProblem(name, (IntentionalDomain) domain, protagonist, universe, initialState, goal));
	}
	
	protected String parseProtagonist(List document, Parser<SExpression> parser) throws ParseException {
		String protagonist = document.requireListStartingWith(PROTAGONIST_IDENTIFIER).asList(2).getChild(1).asSymbol().content;
		parser.define(ProtagonistTemplate.PROTAGONIST, protagonist);
		return protagonist;
	}
	
	@Override
	public SExpression build(BardicheProblem object, SExpression document,
			Builder<SExpression> builder) throws BuildException {
		
		return document;
	}
	
}