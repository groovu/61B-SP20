import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class BSTStringSetTest  {
    // FIXME: Add your own tests for your BST StringSet

    @Test
    public void testNothing() {
        // FIXME: Delete this function and add your own tests
    }
    @Test
    public void testAlphabet() {
        BSTStringSet test = new BSTStringSet();
        test.put("A");
        test.put("B");
        test.put("C");
        ArrayList<String> woo = new ArrayList<String>(Arrays.asList("A", "B", "C"));
        assertEquals(woo, test.asList());
        //
        //Iterator<String> it = test.iterator();

        assertEquals(true, test.contains("A"));
        assertEquals(true, test.contains("B"));
        assertFalse(test.contains("Z"));
//        assertEquals("C", it.next());
//        assertEquals("A", it.next());
//        assertEquals("B", it.next());
//        assertEquals(false, test.contains("A"));
    }

    String ABC = "ABC";
}
