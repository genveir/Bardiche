package com.geertenvink.Bardiche;

import com.stephengware.java.planware.AxiomTemplate;
import com.stephengware.java.planware.IntentionalDomain;
import com.stephengware.java.planware.IntentionalOperator;
import com.stephengware.java.planware.Universe;
import com.stephengware.java.planware.logic.Substitution;
import com.stephengware.java.planware.util.ImmutableSet;

public class BardicheDomain extends IntentionalDomain {
	
	public final ImmutableSet<BardicheOperator> operators;
	
	public BardicheDomain(String name, Universe universe, ImmutableSet<BardicheOperator> operators , ImmutableSet<AxiomTemplate> axioms) {
		super(name, universe, new ImmutableSet<IntentionalOperator>(operators.toArray()), axioms);
		this.operators = operators;
	}
	
	public BardicheDomain(String name, Universe universe, BardicheOperator[] operators, AxiomTemplate[] axioms){
		this(name, universe, new ImmutableSet<BardicheOperator>(operators), new ImmutableSet<AxiomTemplate>(axioms));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public BardicheDomain substitute(Substitution substitution){
		boolean different = false;
		Universe universe = this.universe.substitute(substitution);
		if(universe != this.universe)
			different = true;
		ImmutableSet<BardicheOperator> operators = (ImmutableSet<BardicheOperator>) substituteOperators(substitution);
		if(operators != this.operators)
			different = true;
		ImmutableSet<AxiomTemplate> axioms = (ImmutableSet<AxiomTemplate>) substituteAxioms(substitution);
		if(axioms != this.axioms)
			different = true;
		if(different)
			return new BardicheDomain(name, universe, operators, axioms);
		else
			return this;
	}
	
	@Override
	public BardicheOperator getOperator(String name){
		return (BardicheOperator) super.getOperator(name);
	}
}
