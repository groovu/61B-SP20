import static org.junit.Assert.*;
import org.junit.Test;

/** Tips for writing tests.
 * 1. Write the test; run the test (should fail)
 * 2. Write the code
 * 3. Run the tests, it should pass.  Otherwise, return to 2.
 * 4. Refactor/improve the code, the output should still be the same
 * You refactor for readability/efficiency.
 */
public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.
        assertEquals(0, 0); */
        assertEquals(1, CompoundInterest.numYears(2021));
        assertEquals(1000, CompoundInterest.numYears(3020));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
