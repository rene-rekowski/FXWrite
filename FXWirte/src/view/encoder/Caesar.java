package view.encoder;

/**
 *	encode a string in ceaser code
 */
public class Caesar {

    /**
     * encode a string in ceaser. 
     * for decoding shift in the other direktion
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
