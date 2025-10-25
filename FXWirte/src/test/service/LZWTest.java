package test.service;

import org.junit.jupiter.api.Test;

import service.LZW;

import static org.junit.jupiter.api.Assertions.*;

public class LZWTest {

	@Test
	public void testEncodeDecode() {
		String original = "ABABABABABABABAB";
		String encoded = LZW.encode(original);
		String decoded = LZW.decode(encoded);

		System.out.println("Original: " + original);
		System.out.println("Encoded : " + encoded);
		System.out.println("Decoded : " + decoded);

		assertEquals(original, decoded, "Decoded text should match original");
	}

	@Test
	public void testEmptyString() {
		String original = "";
		String encoded = LZW.encode(original);
		String decoded = LZW.decode(encoded);

		assertEquals(original, decoded, "Empty string should encode/decode correctly");
	}

	@Test
	public void testLongText() {
		String original = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
		String encoded = LZW.encode(original);
		String decoded = LZW.decode(encoded);

		assertEquals(original, decoded, "Decoded long text should match original");
	}

	@Test
	public void testAllAscii() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 128; i++) {
			sb.append((char) i);
		}
		String original = sb.toString();
		String encoded = LZW.encode(original);
		String decoded = LZW.decode(encoded);

		assertEquals(original, decoded, "Decoded ASCII range should match original");
	}
	
	@Test
	void testNullInput() {
	    assertEquals("", LZW.encode(null));
	    assertEquals("", LZW.decode(null));
	}
	
	@Test
	void testRepeatingCharacters() {
	    String text = "AAAAAA";
	    String encoded = LZW.encode(text);
	    String decoded = LZW.decode(encoded);
	    assertEquals(text, decoded);
	}

	@Test
	void testUnicodeCharacters() {
	    String text = "äöüß漢字";
	    String encoded = LZW.encode(text);
	    String decoded = LZW.decode(encoded);
	    assertEquals(text, decoded);
	}


}
