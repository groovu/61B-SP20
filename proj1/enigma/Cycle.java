package enigma;
import java.util.Arrays;

import static enigma.EnigmaException.*;

/** A Cycle of permutations.
 * Provides a mapping for characters based on a cycle.
 * @author Cherish Truong
 */
public class Cycle {
    /** Initialize Cycle using input strings.
     * @param chars String of characters for your perm. */
    Cycle(String chars) {
        String cleanup = chars;
        cleanup = cleanup.replace(")", " ");
        cleanup = cleanup.replace("(", " ");
        cleanup = cleanup.trim().replaceAll(" +", " ");
        if (!unique(cleanup)) {
            throw error("Cycle is not unique");
        } else {
            _cyclesarray = cleanup.split(" ");
        }
    }

    /** Method checks if cycle string is unique.  IF it is not unique,
     * that means there are letters that map
     * to multiple letters.  Which is not good.
     * Citation: geeksforgeeks "Determine-String-Unique-Characters"
     * @param strin Input string.
     * @return boolean True if the cycle string is unique.
     */
    boolean unique(String strin) {
        String str = strin.replace(" ", "");
        if (str.length() > 256) {
            return false;
        }
        boolean[] chars = new boolean[256];
        Arrays.fill(chars, false);

        for (int i = 0; i < str.length(); i += 1) {
            int index = (int) str.charAt(i);
            if (chars[index]) {
                return false;
            }
            chars[index] = true;
        }
        return true;
    }
     /** Returns String array of cycles. */
    final String[] getCyclesArray() {
        return _cyclesarray;
    }

    @Override
    public String toString() {
        for (String i : _cyclesarray) {
            System.out.print("(" + i + ")");
        }
        return "";
    }
    /** Cycles in an array. */
    private String[] _cyclesarray;
}
