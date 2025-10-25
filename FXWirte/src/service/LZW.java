package service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * lzw endoer and decoder for unicode text
 * 
 * @author rene-rekowski
 * @version 1.0
 */
public class LZW {

	/**
	 * encode a string in lzw
	 * 
	 * @param input clean code
	 * @return encodeString
	 */
	public static String encode(String input) {
		if (input == null || input.isEmpty()) {
			return "";
		}

		// init dictioniary with all unicode-chars
		// TODO: modular?
		Map<String, Integer> dictionary = new HashMap<>();
		int dictSize = 0;
		for (int i = 0; i <= Character.MAX_VALUE; i++) {
			dictionary.put(String.valueOf((char) i), dictSize++);
		}

		StringBuilder result = new StringBuilder();
		String currentString = "";

		for (char nextChar : input.toCharArray()) {
			String newString = currentString + nextChar;
			if (dictionary.containsKey(newString)) {
				currentString = newString;
			} else {
				result.append(dictionary.get(currentString)).append(",");
				dictionary.put(newString, dictSize++);
				currentString = String.valueOf(nextChar);
			}
		}

		if (!currentString.isEmpty()) {
			result.append(dictionary.get(currentString));
		}

		//remove if there is an comma at the end
		if (result.length() > 0 && result.charAt(result.length() - 1) == ',') {
			result.deleteCharAt(result.length() - 1);
		}

		return result.toString();
	}

	/**
	 * decode a encode-lzw-string
	 * 
	 * @param input
	 * @return encodeString
	 */
	public static String decode(String input) {
		if (input == null || input.isEmpty()) {
			return "";
		}

		// init dictioniary with all unicode-chars
		Map<Integer, String> dictionary = new HashMap<>();
		int dictSize = 0;
		for (int i = 0; i <= Character.MAX_VALUE; i++) {
			dictionary.put(dictSize++, String.valueOf((char) i));
		}

		// remove every line break
		String cleanInput = input.replaceAll("\\r\\n|\\r|\\n", "");
		// check if somestihng writen after delete line break
		if (cleanInput.isEmpty()) {
			return "";
		}
		List<Integer> codes = Arrays.stream(cleanInput.split(",")).map(String::trim).map(Integer::parseInt)
				.collect(Collectors.toList());
		if (codes.isEmpty()) {
			return "";
		}

		String previousString = dictionary.get(codes.get(0));
		StringBuilder result = new StringBuilder(previousString);

		for (int i = 1; i < codes.size(); i++) {
			int newCode = codes.get(i);
			String newString;

			if (dictionary.containsKey(newCode)) {
				newString = dictionary.get(newCode);
			} else {
				newString = previousString + previousString.charAt(0);
			}

			result.append(newString);
			dictionary.put(dictSize++, previousString + newString.charAt(0));
			previousString = newString;
		}

		return result.toString();
	}

}
