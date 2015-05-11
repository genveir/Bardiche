package com.geertenvink.Bardiche;

import com.stephengware.java.planware.IntentionalDomain;
import com.stephengware.java.planware.Problem;
import com.stephengware.java.planware.io.Field;

public class Templates {
	public static abstract class BardicheProblemTemplate extends com.stephengware.java.planware.io.Templates.ProblemTemplate{
		public static final Field<BardicheProblem, IntentionalDomain> DOMAIN = new Field<>(BardicheProblem.class, "domain", IntentionalDomain.class);
	
	}
	
	public static abstract class ProtagonistTemplate {
		
		public static final Field<Problem, String> PROTAGONIST = new Field<>(Problem.class, "protagonist", String.class);
	}
}