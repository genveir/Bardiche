package com.geertenvink.Bardiche.io.extensions;

import java.util.LinkedList;

import com.geertenvink.Bardiche.BardicheDomain;
import com.geertenvink.Bardiche.BardicheGoal;
import com.geertenvink.Bardiche.BardicheProblem;
import com.geertenvink.Bardiche.io.extensions.Templates.BardicheProblemTemplate;
import com.stephengware.java.planware.Domain;
import com.stephengware.java.planware.Universe;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.ParseException;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.SimpleExtension;
import com.stephengware.java.planware.io.pddl.PDDLManager;
import com.stephengware.java.planware.io.pddl.sexp.List;
import com.stephengware.java.planware.io.pddl.sexp.Node;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.io.pddl.sexp.Symbol;
import com.stephengware.java.planware.io.pddl.intp.Templates;
import com.stephengware.java.planware.logic.Constant;
import com.stephengware.java.planware.logic.Expression;

public class BardicheProblemExtension extends SimpleExtension<SExpression, BardicheProblem> {
	public static final String PROTAGONIST_IDENTIFIER = ":protagonist";
	public static final String BARDICHEGOAL_IDENTIFIER = ":bardichegoal";
	public static final String GOOD_ENDING_IDENTIFIER = "good";
	public static final String BAD_ENDING_IDENTIFIER = "bad";
	
	public BardicheProblemExtension() {
		super(BardicheProblem.class);
	}

	@Override
	public void parse(SExpression document, Parser<SExpression> parser)
			throws ParseException {
		String name = parser.require(BardicheProblemTemplate.NAME);
		Domain domain = parser.require(Templates.IntentionalProblemTemplate.DOMAIN);
		if (domain instanceof BardicheDomain)
			parser.define(BardicheProblemTemplate.DOMAIN, (BardicheDomain) domain);
		else
			fail();	
		Constant protagonist = parseProtagonist(document.asList(), parser);
		Universe universe = parser.require(BardicheProblemTemplate.UNIVERSE);
		Expression initialState = parser.require(BardicheProblemTemplate.INITIAL_STATE);
		BardicheGoal goal = parseGoal(document.asList(), parser);
		succeed(new BardicheProblem(name, (BardicheDomain) domain, protagonist, universe, initialState, goal));
	}
	
	protected BardicheGoal parseGoal(List document, Parser<SExpression> parser) throws ParseException {
		List endings = document.requireListStartingWith(BARDICHEGOAL_IDENTIFIER);

		LinkedList<Expression> goodEndings = parseEndings(endings, GOOD_ENDING_IDENTIFIER, parser);
		LinkedList<Expression> badEndings = parseEndings(endings, BAD_ENDING_IDENTIFIER, parser);
		
		return new BardicheGoal(goodEndings, badEndings);
	}
	
	private LinkedList<Expression> parseEndings(List endings, String identifier, Parser<SExpression> parser)
			throws ParseException {
		List endingsByIdentifier = endings.requireListStartingWith(identifier);
		
		LinkedList<Expression> result = new LinkedList<>();
		for (Node ending : endingsByIdentifier) {
			if (ending instanceof Symbol && ((Symbol) ending).content.equals(identifier)) continue;
			result.add(PDDLManager.parseGoal(ending, parser));
		}
		if (result.size() == 0)
			throw new ParseException(endings, "problem must have at least one ending of type " + identifier);
		
		return result;
	}
	
	protected Constant parseProtagonist(List document, Parser<SExpression> parser) throws ParseException {
		Node node = document.requireListStartingWith(PROTAGONIST_IDENTIFIER).asList(2).getChild(1);
		Constant protagonist = parser.parseOrFail(node, Constant.class);
		parser.define(BardicheProblemTemplate.PROTAGONIST, protagonist);
		return protagonist;
	}
	
	@Override
	public SExpression build(BardicheProblem object, SExpression document,
			Builder<SExpression> builder) throws BuildException {
		
		return document;
	}
	
}
