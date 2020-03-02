package enigma;
import java.util.Arrays;

/** A Cycle of permutations.  Provides a mapping for characters based on a cycle.
 * @author Cherish Truong
 */
public class Cycle {
    Cycle(String chars) {
//        //if (unique(chars) == false) {
//        //return error;
//        //}
//        cyclecount = 0;
//        //for (char i : chars) {
//        for (int i = 0; i < chars.length(); i += 1) {
//            if (chars.charAt(i) == '(') {
//                cyclecount++;
//            }
//        }
//        _cycles = new char[cyclecount][];
//        //_cycles[0][0] = 1;
        //TODO: check if cycle is valid  (contins "(" and ")"
        String cleanup = chars;
        cleanup = cleanup.replace(")", " ");
        cleanup = cleanup.replace("(", " ");
        cleanup = cleanup.trim().replaceAll(" +", " ");
        if (unique(cleanup) == false) {
            System.out.println("Cycle is not unique, will error.");  //FIXME: How do I error out here.
        } else {
            _cyclesarray = cleanup.split(" ");
        }
        //System.out.println(cleanup[0].charAt(i));
    }

    /** Method checks if cycle string is unique.  IF it is not unique, that means there are letters that map
     * to multiple letters.  Which is not good.
     * Citation: geeksforgeeks "Determine-String-Unique-Characters"
     * @param strin
     * @return True if the cycle string is unique.
     */
    boolean unique(String strin) {
        String str = strin.replace(" ", "");
        if (str.length() > 256) return false;
        boolean[] chars = new boolean[256];
        Arrays.fill(chars, false);

        for (int i = 0; i < str.length(); i++) {
            int index = (int)str.charAt(i);
            if (chars[index])
                return false;
            chars[index] = true;
        }
        return true;
    }

    final String[] get_cyclesarray() {
        return _cyclesarray;
    }

    @Override
    public String toString() {
        for (String i : _cyclesarray) {
            System.out.print("("+ i +")");
        }
        return "";
    }
    private String[] _cyclesarray;
}