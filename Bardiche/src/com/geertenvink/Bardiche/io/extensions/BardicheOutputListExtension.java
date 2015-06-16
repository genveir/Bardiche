package com.geertenvink.Bardiche.io.extensions;

import com.geertenvink.Bardiche.BardicheStep;
import com.geertenvink.Bardiche.BardicheOutputList;
import com.geertenvink.Bardiche.logic.InitializationException;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.Builder;
import com.stephengware.java.planware.io.DocumentBuilder;
import com.stephengware.java.planware.io.Extension;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.pddl.sexp.FormatRule;
import com.stephengware.java.planware.io.pddl.sexp.List;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.io.pddl.sexp.Symbol;

public class BardicheOutputListExtension implements Extension<SExpression, SExpression>, 
			DocumentBuilder<BardicheOutputList, SExpression> {
	
	@Override
	public void configure(Parser<SExpression> parser) {}
	
	@Override
	public void configure(Builder<SExpression> builder) {
		builder.overrideDocumentBuilder(BardicheOutputList.class, this);
	}
	
	@Override
	public SExpression build(BardicheOutputList outputList, SExpression document, Builder<SExpression> builder) throws BuildException {
		document = new List("Final Bardiche Story", new List(":protagonist", getProtagonist(outputList, builder)));
		((List) document).setFormatRule(FormatRule.PDDL_DOCUMENT);
		buildSteps(outputList, (List) document, builder);
		return document;
	}
	
	private SExpression getProtagonist(BardicheOutputList outputList, Builder<SExpression> builder) throws BuildException {
		try {
			return builder.build(outputList.getProtagonist());
		} catch (InitializationException e) {
			return new Symbol("unknown");
		}
	}
	
	protected void buildSteps(BardicheOutputList outputList, List document, Builder<SExpression> builder) throws BuildException {
		List stepsList = makeNewList(":steps");
		
		int stepCount = outputList.getStepsCount();
		
		for (int time = 1; time <= stepCount; time++) {
			addStep(stepsList, (BardicheStep) outputList.getStep(time).source, builder);
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
