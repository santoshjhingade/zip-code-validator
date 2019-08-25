package com.san;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Application class to run ZipCodeInputSanitizer from the command prompt.
 * 
 * @author Santosh Jhingade
 */
public class ZipCodeInputSanitizerRunner {
	public static void main(String[] args) {
		
		// Create a Scanner object
        try (Scanner scanner = new Scanner(System.in)) {
 
            // Read values from Console
            System.out.print("Enter zipcode input ranges in the format \"1-2;4-5;7-9\": ");
            String zipCodeString = scanner.next();
            
            String[] zipCodeRanges = zipCodeString.split(";");
            
            List<Integer[]> zipCodeArrayList = new ArrayList<>();
            
            for(String range: zipCodeRanges) {
            	String[] zipCode = range.split("-");
            	zipCodeArrayList.add(new Integer[] { Integer.parseInt(zipCode[0]), Integer.parseInt(zipCode[1]) });
            }
             
            ZipCodeInputSanitizer.sanitize(zipCodeArrayList);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
	}

}
