package com.timwi.challenges.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.timwi.challenges.algorithms.BackToBasics;

public class BackToBasicsTests {

	@Test
	public void test() {
		
		List<String> circuit = Arrays.asList( new String[] {
				"123 -> x",
				"456 -> y",
				"x AND y -> d",
				"x OR y -> e",
				"x LSHIFT 2 -> f",
				"y RSHIFT 2 -> g",
				"NOT x -> h",
				"NOT y -> i"
		});
		Map<String, Integer> values = BackToBasics.answer( circuit, null );
		
		assertTrue( values.get("d").equals( 72 ) ); 
		assertTrue( values.get("e").equals( 507 ) );
		assertTrue( values.get("f").equals( 492 ) );
		assertTrue( values.get("g").equals( 114 ) );
		assertTrue( values.get("h").equals( 65412 ) );
		assertTrue( values.get("i").equals( 65079 ) );
		assertTrue( values.get("x").equals( 123 ) );
		assertTrue( values.get("y").equals( 456 ) );
		
	}
	
}
