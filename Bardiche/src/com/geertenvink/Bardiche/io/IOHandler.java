package com.geertenvink.Bardiche.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.geertenvink.Bardiche.Bardiche;
import com.stephengware.java.planware.ArgumentMap;
import com.stephengware.java.planware.io.BuildException;

public class IOHandler {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void print(ArgumentMap arguments, Object toWrite) {
		try {
			PrintWriter out = new PrintWriter(System.out);
			arguments.get(Bardiche.IO_MANAGER).write(toWrite, out);
			out.flush();
		} catch (BuildException e) {
			System.out.println(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
	public static String getInputString() {
		try {
			String input = reader.readLine();
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getInputInt(int lowerBound, int upperBound) {
		int input = Integer.parseInt(getInputString());
		
		if (input >= lowerBound && input <= upperBound) return input;
		else return -1;
	}
	
	public static void close() {
		try {
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}