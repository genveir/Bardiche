package com.geertenvink.Bardiche;

import com.stephengware.java.cpocl.Utilities;
import com.stephengware.java.glaive.StepEvent;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.DocumentBuilder;
import com.stephengware.java.planware.io.Extension;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.pddl.sexp.FormatRule;
import com.stephengware.java.planware.io.pddl.sexp.List;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;

public class BardichePlanExtension implements Extension<SExpression, SExpression>, 
											DocumentBuilder<BardichePlan, SExpression> {
	
	@Override
	public void configure(Parser<SExpression> parser) {}
	
	@Override
	public void configure(Builder<SExpression> builder) {
		builder.overrideDocumentBuilder(BardichePlan.class, this);
	}
	
	@Override
	public SExpression build(BardichePlan plan, SExpression document, Builder<SExpression> builder) throws BuildException {
		document = new List("Bardiche Plan", new List(":protagonist", plan.problem.protagonist));
		((List) document).setFormatRule(FormatRule.PDDL_DOCUMENT);
		buildSteps(plan, (List) document, builder);
		return document;
	}
	
	protected void buildSteps(BardichePlan plan, List document, Builder<SExpression> builder) throws BuildException {
		List stepsList = new List(":steps");
		stepsList.setFormatRule(FormatRule.TWO_ELEMENTS_ON_FIRST_LINE_THEN_ONE_PER_LINE);
		for(StepEvent step : Utilities.getSteps(plan)){
			if(Utilities.isExecuted(step, plan))
				stepsList.addChild(builder.build(step.source));
		}
		document.addChild(stepsList);
	}
}
