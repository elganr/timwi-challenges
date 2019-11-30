package com.timwi.challenges.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.timwi.challenges.algorithms.ReadingDigits;

public class ReadingDigitsTests {
	
	private final String CHALLENGE_INPUT = "1113122113";
	private final Integer CHALLENGE_TIMES = 40;
	
    @Test
    public void test() {
        assertNotNull( ReadingDigits.answer( CHALLENGE_INPUT, CHALLENGE_TIMES ) );
        
        assertEquals( ReadingDigits.answer("1", 1 ), "11" );
        assertEquals( ReadingDigits.answer("1", 2 ), "21" );
        assertEquals( ReadingDigits.answer("1", 3 ), "1211" );
        assertEquals( ReadingDigits.answer("1", 4 ), "111221" );
        assertEquals( ReadingDigits.answer("1", 5 ), "312211" );
    }

}
