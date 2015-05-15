package com.geertenvink.Bardiche;

import com.stephengware.java.planware.IntentionalOperator;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.ParseException;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.SimpleExtension;
import com.stephengware.java.planware.io.pddl.intp.IntentionalOperatorExtension;
import com.stephengware.java.planware.io.pddl.sexp.List;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.logic.Expression;
import com.stephengware.java.planware.logic.Term;
import com.stephengware.java.planware.logic.Variable;
import com.geertenvink.Bardiche.Templates.BardicheOperatorTemplate;

public class BardicheOperatorExtension extends SimpleExtension<SExpression, BardicheOperator> {
	public BardicheOperatorExtension() {
		super(BardicheOperator.class);
	}
	
	@Override
	public void parse(SExpression document, Parser<SExpression> parser) throws ParseException {
		String name = parser.require(BardicheOperatorTemplate.NAME);
		Variable[] parameters = parser.require(BardicheOperatorTemplate.PARAMETERS);
		Expression precondition = parser.require(BardicheOperatorTemplate.PRECONDITION);
		Expression effect = parser.require(BardicheOperatorTemplate.EFFECT);
		Term[] agents = parser.require(BardicheOperatorTemplate.AGENTS);
		Term initiator = (agents.length == 0) ? null : agents[0];
		succeed(new BardicheOperator(name, parameters, precondition, effect, agents, initiator));
	}
	
	@Override
	public SExpression build(BardicheOperator operator, SExpression document, Builder<SExpression> builder) throws BuildException {
		buildAgents(operator, (List) document, builder);
		return document;
	}
	
	protected void buildAgents(IntentionalOperator operator, List document, Builder<SExpression> builder) throws BuildException {
		if(operator.agents.length == 0)
			return;
		document.addChild(IntentionalOperatorExtension.AGENTS_IDENTIFIER);
		List agentsList = new List();
		for(Term agent : operator.agents)
			agentsList.addChild(builder.build(agent));
		document.addChild(agentsList);
	}
}