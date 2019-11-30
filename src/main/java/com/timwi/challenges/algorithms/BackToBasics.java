package com.timwi.challenges.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BackToBasics {
	
	// The operators.
	private final static String NOT = "NOT"; 
	private final static String AND = "AND"; 
	private final static String OR = "OR";
	private final static String LSHIFT = "LSHIFT"; 
	private final static String RSHIFT = "RSHIFT";
	
	// Contains the wires.
	private static Map<String, Wire> wires = new HashMap<String, Wire>();
	
	/**
	 * 
	 * @param wire
	 * @return
	 */
	public static Map<String, Integer> answer ( List<String> lines, String wire ) {
		
		parseCircuit( lines );
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		if ( wire != null ) { result.put( wire, wires.get(wire).value ); }
		else {
			for ( Entry<String, Wire> entry : wires.entrySet() ) {
				result.put( entry.getKey(), entry.getValue().value );
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	public static void parseCircuit ( List<String> lines ) {
		
		// For each line in the list...
		for ( String line : lines ) {
			
			// ... Parses a wire's information.
			Wire w = parseLine( line );
			wires.put( w.name, w );
			
		}
		
		while ( true ) {
			Boolean hadToCompute = false;
			for ( Wire w : wires.values() ) {
				if ( !w.isComputed() ) {
					if ( w.canBeComputed() ) { 
						hadToCompute = true;
						w.compute();
					}
				}
			}
			if ( !hadToCompute ) { break; }
		}
		
	}
	
	/**
	 * Creates a wire from a line.
	 * @param line A line from which to extract a wire.
	 * @return a wire object from the line's information.
	 */
	public static Wire parseLine ( String line ) {
		
		Wire w = new Wire();
		
		// Splits the tokens of the line.
		String[] elems = line.split("->");
		String value = elems[0].trim(), wire = elems[1].trim();
		
		w.name = wire;
		
		// If it is a numeric value, takes it as such...
		try { 
			Integer v = Integer.parseInt(value);
			w.value = v;
		}
		// ... Otherwise, gets the operator, wires and such.
		catch ( Exception e ) {
			
			// If it contains a logical gate, it is an operation...
			for ( String gate : Arrays.asList( new String[]{ NOT, AND, OR, LSHIFT, RSHIFT } ) ) {
				if ( value.contains( gate ) ) {
					
					String[] wires = value.split(gate);

					w.operation = gate;
					
					String s1 = null, s2 = null;
					
					switch ( gate ) {
					case NOT:
						w.right = new Wire();
						s2 = wires[1].trim();
						if ( isNumeric(s2) ) { w.right.value = Integer.parseInt( s2 ); } else { w.right.name = s2; }
						break;
						
					case LSHIFT:
					case RSHIFT:
						w.left = new Wire();
						s1 = wires[0].trim();
						if ( isNumeric(s1) ) { w.left.value = Integer.parseInt( s1 ); } else { w.left.name = s1; }
						w.shift = Integer.parseInt( wires[1].trim() );
						break;
						
					case AND:
					case OR:
						w.left = new Wire();
						s1 = wires[0].trim();
						if ( isNumeric(s1) ) { w.left.value = Integer.parseInt( s1 ); } else { w.left.name = s1; }
						
						w.right = new Wire();
						s2 = wires[1].trim();
						if ( isNumeric(s2) ) { w.right.value = Integer.parseInt( s2 ); } else { w.right.name = s2; }
						
						break;
					}
					
					break;
				}
			}
			
			// ... Otherwise, it is an equivalence.
			if ( w.operation == null ) { w.left = new Wire( value ); }
			
		}

		return w;
		
	}
	
	/*********************************************************
	 *                         UTILS                         *
	 *********************************************************/
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private static Integer bitwiseNot ( Integer value ) {
		
		String binaryString = Integer.toBinaryString( value );
		while ( binaryString.length() < 16 ) { binaryString = "0" + binaryString; }
		
		String res = "";
		for ( char c : binaryString.toCharArray() ) {
			if ( c == '0' ) { res += '1'; }
			if ( c == '1' ) { res += '0'; }
		}

		Integer i = Integer.parseInt(res, 2);
		return i;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private static Boolean isNumeric ( String value ) {
		try { Integer.parseInt(value); return true; } catch ( Exception e ) { return false; } 
	}
	
	/*********************************************************
	 *                        CLASSES                        *
	 *********************************************************/
	
	/**
	 * Contains a wire's information.
	 */
	public static class Wire {
		
		String name;
		Wire left;
		Wire right;
		Integer value;
		String operation;
		Integer shift;
		
		public Wire () {}
		
		public Wire ( String name ) { this.name = name; }
		
		/**
		 * Computes the value
		 */
		public void compute () {
			
			getLeftAndRight();
			
			// If no operation is given, it is an equivalence...
			if ( operation == null ) { value = left.value; }

			// Otherwise, it is a computation.
			else {
				switch ( operation ) {
				case NOT:
					value = bitwiseNot( right.value );
					break;
				case LSHIFT:
					value = left.value << shift;
					break;
				case RSHIFT:
					value = left.value >> shift;
					break;
				case AND:
					value = left.value & right.value;
					break;
				case OR:
					value = left.value | right.value;
					break;
				}
			}

		}
		
		/**
		 * Indicates whether the wire's value is known.
		 * @return
		 */
		public Boolean isComputed () { return value != null; }
		
		/**
		 * Indicates whether the wire's value can be computed at this stage.
		 * @return
		 */
		public Boolean canBeComputed () {
			
			getLeftAndRight(); 
			
			// If no operation is given, it is an equivalence...
			if ( operation == null ) { return left.isComputed(); }

			// Otherwise, it is a computation.
			switch ( operation ) {
			
			case NOT:
				return right.isComputed();
			case LSHIFT:
			case RSHIFT:
				return left.isComputed();
			case AND:
			case OR:
				return left.isComputed() && right.isComputed();
			}
			
			return false;
		}
		
		/**
		 * Fills the left and right wires.
		 */
		private void getLeftAndRight () {
			if ( left != null && left.value == null && wires.containsKey( left.name ) ) { 
				left = wires.get( left.name ); 
			}
			if ( right != null && right.value == null && wires.containsKey( right.name ) ) { 
				right = wires.get( right.name ); 
			}
		}
		
	}

}
