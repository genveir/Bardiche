package com.geertenvink.Bardiche;

import com.stephengware.java.glaive.Glaive;
import com.stephengware.java.planware.Argument;
import com.stephengware.java.planware.ArgumentMap;

/** Bardiche is an extension of Glaive which ensures a protagonist will be read from
  * the problem file, and that the I/O extensions required to input such problem files and
  * to output plans in the special Bardiche format will be installed.
  */ 
public class Bardiche extends Glaive {
	
	public static final Argument<BardicheProblem> PROBLEM = new Argument.ParsedArgument<BardicheProblem>(
			Glaive.PROBLEM.name, Glaive.PROBLEM.abbreviation, BardicheProblem.class) {
		
		@Override
		protected BardicheProblem getValue(Object object, ArgumentMap arguments){
			// make sure the domain is parsed before the problem 			
			arguments.get(DOMAIN);
			return super.getValue(object, arguments);
		}
	};
	
	public Bardiche() {
		super();
		this.io.install(BardicheRequirement.BARDICHE);
		this.io.install(new BardichePlanExtension());
	}
}
