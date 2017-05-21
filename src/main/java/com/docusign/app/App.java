package main.java.com.docusign.app;
/*
 * File Name : GettingReady.java
 */

import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 * Generates and displays commands needed to get ready before going out of
 * the house. It contains methods that create the appropriate Dress object 
 * for the day using the input provided. The Dress (HotDress or ColdDress)
 * object is used to create a string that will later be displayed. The 
 * string will contain commands that are appropriate for the conditions 
 * on that day and the input commands.
 * @author Kushal
 *
 */
class App {
    
	/**
	 * The main method for the GettingReady program.
	 * Takes the input command numbers to create a suitable Dress object 
	 * and compute the output string using the Dress object's methods, 
	 * before outputting it.
	 * @param args Not used
	 */
	public static void main(String[] args) {
		String[] inArr, numList = {};
	    HotDress hd;
	    ColdDress cd;		
		String inStr, outStr;
		Scanner s = new Scanner(System.in);
		inStr = s.nextLine();
		
		inArr = inStr.split(" ", 2);
        s.close();
		
        /* Print fail and return if input is one word - no
         * commands provided */
        if (inArr.length <= 1) {
            System.out.println("fail");
            return;
        }            
        
        /* The part of input consisting of the commands */
        numList = inArr[1].split(", ");		
              
        /* String commands converted to Integers */
		ArrayList<Integer> numbers = convertToInt(numList);	
		
		/* Create HotDress object and make output string 
		 * when it's HOT */
		if (inArr[0].toUpperCase().equals("HOT")) {			
			hd = new HotDress(numbers);			
			outStr = hd.makeString();
		}
		
		/* Create ColdDress object and make output string 
		 * when it's COLD */
		else if (inArr[0].toUpperCase().equals("COLD")) {
			cd = new ColdDress(numbers);			
			outStr = cd.makeString();
		}
		
		/* Invalid input */
		else {
			System.out.println("fail");
			return;
		}
		 
		System.out.println(outStr);
		
	}
	
	/**
	 * Transforms the array of strings into an Integer ArrayList.
	 * @param  s the array of strings containing input command numbers.
	 * @return outArray the output ArrayList that has commands in the 
	 * 		   form of Integers.
	 */
	private static ArrayList<Integer> convertToInt(String[] s) {
		ArrayList<Integer> outArray = new ArrayList<Integer>();
		
		/* last is used when fail should be added at end */
		int length, last = 0;
		length = s.length;
		
		/* More than 8 commands were input, so consider only first 8
		 * and indicate a fail at the end
		 */
		if (length > 8) {
		    length = 8;	
		    last = -1;
		}
		for (int i = 0; i < length; i++) {
		    try {
		        outArray.add(Integer.parseInt(s[i]));
		    }
		    
		    /* The input commands were not numerical characters */
		    catch (NumberFormatException e) {
		        outArray.add(-1);
		        return outArray;
		    }
		}
		
		/* More than 8 commands input, so 
		 * fail after 8 commands
		 */
		if (last != 0)
		    outArray.add(last);
		return outArray;
	}
}


