package arrays;

import net.sf.saxon.style.XSLOutput;
import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    /** FIXME
     */
    @Test
    public void catenateTest() {
        int[] A = new int[]{1, 2};
        int[] B = new int[]{3, 4};
        int[] AB = new int[]{1, 2, 3, 4};
        int[] catenateAB = Arrays.catenate(A, B);
        assertArrayEquals(AB, catenateAB);
    }
    @Test
    public void removeTest() {
        int[] A = new int[]{1,2,3};
        int[] A1 = new int []{2,3};
        int[] A2 = new int []{1};
        int[] remove1from0 = Arrays.remove(A, 0, 1);
        int[] remove2from1 = Arrays.remove(A, 1, 2);
        assertArrayEquals(A1, remove1from0);
        assertArrayEquals(A2, remove2from1);
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
