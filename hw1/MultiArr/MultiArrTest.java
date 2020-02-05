import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    //@Test
    public void testPrintRowAndColumns() {
        MultiArr.printRowAndCol(new int[][] {{1,2},{3,4}});
        MultiArr.printRowAndCol(new int[][] {{1,2},{3,4,5}});
    }

    @Test
    public void testMaxValue() {
        //TODO: Your code here!
        assertEquals(4, MultiArr.maxValue(new int[][] {{1,2},{3,4}}));
        assertEquals(5, MultiArr.maxValue(new int[][] {{1,2},{3,4,5}}));
    }

    @Test
    public void testAllRowSums() {
        //TODO: Your code here!
        assertArrayEquals(new int[] {3,7,11}, MultiArr.allRowSums(new int[][] {{1, 2}, {3, 4}, {5,6}}));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
