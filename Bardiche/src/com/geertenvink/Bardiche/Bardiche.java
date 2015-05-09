package com.geertenvink.Bardiche;

import java.io.IOException;
import java.io.PrintWriter;

import com.stephengware.java.glaive.Glaive;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.Plan;
import com.stephengware.java.planware.Search;
import com.stephengware.java.planware.Result;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.pddl.PDDLManager;

public class Bardiche {

	public static void main(String[] args) {
		// Create an instance of Glaive
		Glaive planner = new Glaive();
		ArgumentMap arguments = planner.makeArguments();
		
		// parse domain and problem to be solved
		String domainName = "tests/door-domain.pddl";
		String problemName = "tests/door-problem.pddl";
		
		if (args.length > 0) {
			 domainName = args[0];
		}
		if (args.length > 1) {
			problemName = args[1];
		}
		
		// override the normal output to our special BardichePlan format
		PDDLManager manager = (PDDLManager) arguments.get(Glaive.IO_MANAGER);
		manager.install(new BardichePlanExtension());;
		
		// set the domain and problem
		arguments.set(Glaive.DOMAIN, domainName);
		arguments.set(Glaive.PROBLEM, problemName);
		
		// generate a full plan
		Search search = planner.search(arguments);
		Result result = search.getNextPlan(arguments);
		
		if (result.getSuccess()) {
			System.out.println("solution found");
			Plan plan = result.getPlan();
			
			System.out.println(plan);
			
			PrintWriter out = new PrintWriter(System.out);
			try {
				arguments.get(Glaive.IO_MANAGER).write(plan, out);
			} catch (BuildException be) {
				System.out.println("build exception:");
				System.out.println(be.getMessage());
				System.out.println(be.getStackTrace());
			} catch (IOException e) {
				System.out.println("IO exception:");
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
			}
			out.flush();
			System.out.println();
		} else {
			System.out.println("no succesful plan");
		}
		
		System.out.println("finished execution");
	}

}
