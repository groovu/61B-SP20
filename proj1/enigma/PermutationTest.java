package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /****** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i),
                    e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /****** TESTS ******/

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }
    @Test
    public void debugCheckIdTransform() {
        perm = new Permutation("", UPPER);
        String fromAlpha = UPPER_STRING;
        String toAlpha = UPPER_STRING;
        char c = fromAlpha.charAt(0);
        char e = toAlpha.charAt(0);
        int ci = alpha.indexOf(c);
        int ei = alpha.indexOf(e);
        int cp = perm.permute(ci);
        System.out.println(cp);
        System.out.println(ci);
    }

    @Test
    public void permuteTest() {
        Permutation a = new Permutation("(ABC)(EFG)", new Alphabet());
        System.out.println(a.cycleObj());
        assertEquals('B', a.permute('A'));
        assertEquals('E', a.permute('G'));
    }
    @Test
    public void multiplePermuteTest() {
        Permutation a = new Permutation("(AB)(CD)", new Alphabet());
        Permutation b = new Permutation("(BE)", new Alphabet());
        assertEquals('E', b.permute(a.permute('A')));
    }
    @Test
    public void permuteNonAlpha() {
        Permutation a = new Permutation("(!@)(#$)(*8A)",
                new Alphabet("!@#$%^&*()ABCDEFG"));
        assertEquals('$', a.permute('#'));

    }

}
