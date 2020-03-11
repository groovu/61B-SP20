package enigma;

import org.junit.Test;
import ucb.junit.textui;

/** The suite of all JUnit tests for the enigma package.
 *  @author
 */
public class UnitTest {

    @Test
    public static void alphabet() {
        Alphabet a = new Alphabet();
        System.out.println(a);
    }

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(CycleTest.class,
                                        PermutationTest.class,
                                      MovingRotorTest.class,
                                        AlphabetTest.class));
    }

}


