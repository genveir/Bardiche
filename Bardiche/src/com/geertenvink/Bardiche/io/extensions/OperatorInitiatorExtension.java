package com.geertenvink.Bardiche.io.extensions;

import com.geertenvink.Bardiche.BardicheOperator;
import com.stephengware.java.planware.Operator;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.ParseException;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.SimpleExtension;
import com.stephengware.java.planware.io.pddl.sexp.Node;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.io.pddl.sexp.Symbol;
import com.stephengware.java.planware.logic.Term;

public class OperatorInitiatorExtension extends SimpleExtension<SExpression, Operator> {
	public static final String INITIATOR_IDENTIFIER = ":initiator";
	
	public OperatorInitiatorExtension() {
		super(Operator.class);
	}
	
	@Override
	public void parse(SExpression document, Parser<SExpression> parser) throws ParseException {
		Term initiator = BardicheOperator.NO_INITIATOR;
		
		Symbol initiatorSymbol = document.asList().findSymbol(INITIATOR_IDENTIFIER);
		if (initiatorSymbol != null) {
			Node initiatorNode = initiatorSymbol.requireNextSibling().asList().getChild(0);
			initiator = parser.parseOrFail(initiatorNode, Term.class);
		}
		System.out.println("initiator for " + document + " is " + initiator); // SCRIPTIE debug
		parser.define(Templates.BardicheOperatorTemplate.INITIATOR, initiator);
	}
	
	@Override
	public SExpression build(Operator object, SExpression document, Builder<SExpression> builder) throws BuildException {
		return document;
	}
}
