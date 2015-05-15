package com.geertenvink.Bardiche;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.stephengware.java.glaive.Glaive;
import com.stephengware.java.glaive.GlaivePlan;
import com.stephengware.java.planware.Argument;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.Result;
import com.stephengware.java.planware.Search;
import com.stephengware.java.planware.io.BuildException;
import com.stephengware.java.planware.io.pddl.PDDLManager;

/** Bardiche is an extension of Glaive which ensures a protagonist will be read from
  * the problem file, and that the I/O extensions required to input such problem files and
  * to output plans in the special Bardiche format will be installed.
  */ 
public class Bardiche extends Glaive {
	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static final Argument<BardicheProblem> PROBLEM = new Argument.ParsedArgument<BardicheProblem>(
			Glaive.PROBLEM.name, Glaive.PROBLEM.abbreviation, BardicheProblem.class) {
		
		@Override
		protected BardicheProblem getValue(Object object, ArgumentMap arguments){
			// make sure the domain is parsed before the problem 			
			arguments.get(DOMAIN);
			return super.getValue(object, arguments);
		}
	};
	
	public static final Argument<BardicheDomain> DOMAIN = new Argument.ParsedArgument<BardicheDomain>(
			Glaive.DOMAIN.name, Glaive.DOMAIN.abbreviation, BardicheDomain.class);
	
	public ArgumentMap makeArguments() {
		ArgumentMap arguments = super.makeArguments();
		PDDLManager io = (PDDLManager) arguments.get(IO_MANAGER);
		io.install(BardicheRequirement.BARDICHE);
		io.install(new BardichePlanExtension());
		
		return arguments;
	}
	
	public void generate(ArgumentMap arguments) {
		arguments.get(PROBLEM);
		
		Search search = search(arguments);
		Result result = search.getNextPlan(arguments);
		
		BardichePlan bardichePlan = null;
		int numSteps = 0;
		do {			
			if (result.getSuccess()) {
				GlaivePlan plan = (GlaivePlan) result.getPlan();
				
				bardichePlan = new BardichePlan(plan, arguments, numSteps);
				
				printPlan(arguments, bardichePlan);
				
				if (bardichePlan.complete) {
					System.out.println("the end");
				}
				else {
					ArrayList<BardicheStep> executedSteps = bardichePlan.getExecutedSteps();
					
					numSteps = executedSteps.size() - 1;
					
					BardicheStep lastStep = executedSteps.get(numSteps);
					if (lastStep.initiator == bardichePlan.protagonist) {
						System.out.println("take action (" + lastStep + ")? (y/n)");
						int nextAction = getNextAction(lastStep);
						if (nextAction == -1) {
							numSteps++;
							continue;
						}
					}
					else {
						System.out.println("allow action (" + lastStep + ")? (y/n)");
						int nextAction = getNextAction(lastStep);
						if (nextAction == -1) {
							numSteps++;
							continue;
						}
					}
					System.out.println("continue");
					break;
				}
			}
		} while (!bardichePlan.complete);
	
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int getNextAction(BardicheStep lastStep) {
		try {
			switch(getInputString()) {
				case "y":
					return -1;
				default:
					System.out.println("determine new action");
					return 0;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private void printPlan(ArgumentMap arguments, BardichePlan bardichePlan) {
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
	}
	
	private String getInputString() throws IOException {
		String input = reader.readLine();
		
		return input;
	}
}
