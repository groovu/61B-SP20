import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        //TODO: Your code here!
        assertEquals(4, MultiArr.maxValue(new int[][] {{1,2},{3,4}}));
//        System.out.println(twoD_arr[0][1]);
    }

    @Test
    public void testAllRowSums() {
        //TODO: Your code here!
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
