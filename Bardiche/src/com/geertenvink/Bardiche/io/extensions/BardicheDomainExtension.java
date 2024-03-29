package com.geertenvink.Bardiche.io.extensions;

import com.stephengware.java.planware.AxiomTemplate;
import com.stephengware.java.planware.IntentionalOperator;
import com.stephengware.java.planware.Universe;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.ParseException;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.SimpleExtension;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.io.pddl.intp.Templates;
import com.geertenvink.Bardiche.BardicheDomain;
import com.geertenvink.Bardiche.BardicheOperator;
import com.geertenvink.Bardiche.io.extensions.Templates.BardicheDomainTemplate;

public class BardicheDomainExtension extends SimpleExtension<SExpression, BardicheDomain> {
	public BardicheDomainExtension() {
		super(BardicheDomain.class);
	}
	
	public void parse(SExpression document, Parser<SExpression> parser)
			throws ParseException {
		String name = parser.require(BardicheDomainTemplate.NAME);
		Universe universe = parser.require(BardicheDomainTemplate.UNIVERSE);
		IntentionalOperator[] operators = parser.require(Templates.IntentionalDomainTemplate.OPERATORS);
		BardicheOperator[] bardicheOperators = parseOperators(operators);
		parser.define(BardicheDomainTemplate.OPERATORS, bardicheOperators);
		AxiomTemplate[] axioms = parser.require(BardicheDomainTemplate.AXIOMS);
		succeed(new BardicheDomain(name, universe, bardicheOperators, axioms));
	}
	
	private BardicheOperator[] parseOperators(IntentionalOperator[] operators) {
		BardicheOperator[] bardicheOperators = new BardicheOperator[operators.length];
		for(int i=0; i < operators.length; i++) {
			if (operators[i] instanceof BardicheOperator)
				bardicheOperators[i] = (BardicheOperator) operators[i];
			else
				fail();
		}
		
		return bardicheOperators;
	}
	
	@Override
	public SExpression build(BardicheDomain object, SExpression document, Builder<SExpression> builder) throws BuildException {
		return document;
	}
}
