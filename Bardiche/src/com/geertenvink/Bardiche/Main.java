package com.geertenvink.Bardiche;

import com.stephengware.java.planware.ArgumentMap;

public class Main {
	public static void main(String[] args) {
		// Create an instance of Bardiche
		Bardiche planner = new Bardiche();
		ArgumentMap arguments = planner.makeArguments();
		
		// set domain and problem
		String domainName = "tests/door-domain.pddl";
		String problemName = "tests/door-problem.pddl";
		
		// get domain and problem from args if supplied
		if (args.length > 0) {
			 domainName = args[0];
		}
		if (args.length > 1) {
			problemName = args[1];
		}
		
		// set the domain and problem in the argument map
		arguments.set(Bardiche.DOMAIN, domainName);
		arguments.set(Bardiche.PROBLEM, problemName);
		
		// generate a full narrative
		planner.generate(arguments);
	}
}
