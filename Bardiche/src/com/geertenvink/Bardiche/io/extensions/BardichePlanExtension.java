package com.geertenvink.Bardiche.io.extensions;

import java.util.ArrayList;

import com.geertenvink.Bardiche.BardichePlan;
import com.geertenvink.Bardiche.BardicheStep;
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
		document = new List("Bardiche Progress", new List(":protagonist", plan.protagonist.name));
		((List) document).setFormatRule(FormatRule.PDDL_DOCUMENT);
		buildSteps(plan, (List) document, builder);
		return document;
	}
	
	protected void buildSteps(BardichePlan plan, List document, Builder<SExpression> builder) throws BuildException {
		List stepsList = makeNewList(":steps");
		ArrayList<BardicheStep> steps = plan.executedSteps;
		
		for (int n = 0; n < steps.size(); n++) {
			if (!plan.complete && n == steps.size() - 1) {
				document.addChild(stepsList);
				stepsList = makeNewList(":suggested");
			}
			
			addStep(stepsList, steps.get(n), builder);
		}
		document.addChild(stepsList);
	}
	
	private List makeNewList(String title) throws BuildException {
		List stepsList = new List(title);
		stepsList.setFormatRule(FormatRule.TWO_ELEMENTS_ON_FIRST_LINE_THEN_ONE_PER_LINE);
		return stepsList;
	}
	
	private void addStep(List stepsList, BardicheStep step, Builder<SExpression> builder) throws BuildException {
		Object initiator = "-";
		if (step.initiator != null) initiator = builder.build(step.initiator);
		stepsList.addChild(new List(initiator, builder.build(step)));
	}
}
