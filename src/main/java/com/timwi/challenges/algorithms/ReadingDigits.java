package com.timwi.challenges.algorithms;

public class ReadingDigits {
	
	/**
	 * 
	 * @param input
	 * @param times
	 * @return
	 */
	public static String answer ( String input, Integer times ) {
		
		if ( input == null || input.isEmpty() || input.isBlank() ) { return null; }
		
		if ( times == null || times <= 0 ) { return input; }
		
		String result = input;
		Integer n = times;
		
		while ( n > 0 ) {
			String newResult = "";
			Character currentChar = null;
			Integer count = 0;
			
			char[] digits = result.toCharArray(); 
			for ( int i = 0; i < digits.length; i++  ) {
				
				char c = digits[i];
				
				if ( !Character.isDigit(c) ) { return null; } 
				
				if ( currentChar == null ) { currentChar = c; }
				
				// Increments if the current character is the same as the previous one...
				if ( currentChar.equals( c ) ){ count++; } 
				// ... Otherwise, appends it to the result and switches to a new character.
				else {
					newResult += ( count + "" + currentChar );
					currentChar = c;
					count = 1;
				}
				
				// If this is the end of the input, appends it.
				if ( i == digits.length - 1 ) { newResult += ( count + "" + currentChar ); }
			}
			
			result = newResult;
			n--;
		}
		
		System.out.println("Result for input " + input + " read " + times + " times is " + result.length() + " characters long.");
		
		return result;
	}

}
