package service;

/**
 *	encode a string in caeser code
 */
public class Caesar {

    /**
     * encode a string in caeser. 
     * for decoding shift in the other direction!
     * 
     * @param input clean code
     * @param shift num of shift the text
     * @return the encoded text
     */
    public static String encode(String input, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                result.append((char) ((c - base + shift + 26) % 26 + base));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
    
}
