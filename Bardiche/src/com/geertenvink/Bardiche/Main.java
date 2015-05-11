package com.geertenvink.Bardiche;

import java.io.IOException;
import java.io.PrintWriter;

import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.Plan;
import com.stephengware.java.planware.Result;
import com.stephengware.java.planware.Search;
import com.stephengware.java.planware.io.BuildException;

public class Main {
	public static void main(String[] args) {
		// Create an instance of Glaive
		Bardiche planner = new Bardiche();
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
		
		// set the domain and problem
		arguments.set(Bardiche.DOMAIN, domainName);
		arguments.set(Bardiche.PROBLEM, problemName);
		
		// generate a full plan
		Search search = planner.search(arguments);
		Result result = search.getNextPlan(arguments);
		
		if (result.getSuccess()) {
			System.out.println("solution found");
			GlaivePlan plan = (GlaivePlan) result.getPlan();
			
			BardichePlan bardichePlan = new BardichePlan(plan, arguments);
			
			System.out.println(bardichePlan);
			
			PrintWriter out = new PrintWriter(System.out);
			try {
				arguments.get(Bardiche.IO_MANAGER).write(bardichePlan, out);
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