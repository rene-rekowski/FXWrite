package test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import service.Caesar;

public class CaesarTest {
	
	@Test
	void testCaesarEncodeDecode() {
	    String original = "Hallo Welt";
	    String encoded = Caesar.encode(original, 3);
	    String decoded = Caesar.encode(encoded, 26 - 3);
	    assertEquals(original, decoded);
	}
	
	@Test
	void testNonLettersRemainUnchanged() {
	    String text = "1234 !?.,";
	    String encoded = Caesar.encode(text, 5);
	    assertEquals(text, encoded);
	}

	@Test
	void testLargeShiftWrapsAround() {
	    String text = "A";
	    String encoded = Caesar.encode(text, 52); 
	    assertEquals("A", encoded);
	}

	@Test
	void testLowerAndUpperCaseShift() {
	    String text = "AzZ";
	    String encoded = Caesar.encode(text, 1);
	    assertEquals("BaA", encoded);
	}


}
