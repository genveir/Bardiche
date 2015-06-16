package com.geertenvink.Bardiche.io.extensions;

import com.geertenvink.Bardiche.logic.Possibility;
import com.stephengware.java.planware.io.ParseException;
import com.stephengware.java.planware.io.Parser;
import com.stephengware.java.planware.io.pddl.ModalExpressionExtension;
import com.stephengware.java.planware.io.pddl.sexp.SExpression;
import com.stephengware.java.planware.logic.Entity;
import com.stephengware.java.planware.logic.Expression;

public class PossibilityExtension extends ModalExpressionExtension<Possibility> {

	public static final String POSSIBILITY_MODALITY = "possible";

	public PossibilityExtension() {
		super(Possibility.class, POSSIBILITY_MODALITY, Expression.class);
	}
	
	@Override
	public void parse(SExpression document, Parser<SExpression> parser) throws ParseException {
		Possibility possibility = parseModalExpression(document, parser);
		parser.define(Templates.PossibilityTemplate.ARGUMENT, possibility.argument);
		succeed(possibility);
	}
	
	@Override
	protected Possibility construct(Entity[] arguments) {
		return new Possibility((Expression) arguments[0]);
	}
}
