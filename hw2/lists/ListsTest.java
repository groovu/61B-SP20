package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author FIXME
 */

public class ListsTest {
    /** FIXME((1, 3, 7), (5), (4, 6, 9, 10), (10, 11))
     */
    @Test
    public void naturalRunsTests(){
        IntListList a = IntListList.list(new int[][]{{1,3,7},{5},{4,6,8,10},{10,12}});
        IntList b = IntList.list(new int[] {1,3,7,5,4,6,8,10,10,12});
        IntListList c = IntListList.list(new int[][]{{1,2,4},{3}});
        IntList d = IntList.list(new int[]{1,2,4,3});
        System.out.println(c);
        System.out.println(d);
        assertTrue(c.equals(Lists.naturalRuns(d)));
        System.out.println(d);
//        System.out.println(a);
//        System.out.println(b);
//        Lists.naturalRuns(b);
//        System.out.println(b);
        assertTrue(a.equals(Lists.naturalRuns(b)));
    }
    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.
    @Test
    public void printTest(){
        IntList a = IntList.list(new int[] {1,2,3});
        System.out.println(a.head);
        System.out.println(a.tail.head);
        IntListList b = IntListList.list(new int[][] {{1},{2},{3}});
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
