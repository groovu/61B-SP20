import static org.junit.Assert.*;
import org.junit.Test;

/** Tips for writing tests.
 * 1. Write the test; run the test (should fail)
 * 2. Write the code
 * 3. Run the tests, it should pass.  Otherwise, return to 2.
 * 4. Refactor/improve the code, the output should still be the same
 * You refactor for readability/efficiency.
 *
 * Writing tests first is a good method because it checks to see if
 * you understand what needs to be written.  But what happens if you
 * start off with a bad idea of what a function is supposed to do?
 * Could be screwing yourself.  Try to incorporate the given testings
 * from assignments.  Or ask others about what is being specified.
 * Don't start coding until you understand what is supposed to be output!
 *
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
        assertEquals(12.544,
                CompoundInterest.futureValue(10,12, 2022),
                tolerance);
        assertEquals(259.37,
                CompoundInterest.futureValue(100, 10, 2030),
                tolerance);

    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(295712.29,
                CompoundInterest.futureValueReal(
                        1000000, 0,
                        2060, 3), tolerance);
        assertEquals(11.8026496,
                CompoundInterest.futureValueReal(
                        10, 12,
                        2022, 3), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550,
                CompoundInterest.totalSavings(5000, 2022, 10),
                tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(14936.375,
                CompoundInterest.totalSavingsReal(
                        5000, 2022, 10, 5), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
