package enigma;
import net.sf.saxon.style.XSLOutput;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;
public class CycleTest {
    @Test
    public void uniqueTest() {
        String a = "ABCDEFG";
        String c = "ABCDEFGG";
        Cycle b = new Cycle(a);
        assertEquals(false, b.unique(c));
        assertEquals(true, b.unique(a));
    }
    @Test
    public void cycleMaker() {
        String a = "(ABC)(DEF) (KIL)   ";
        Cycle cyc = new Cycle(a);
        System.out.println(cyc);
    }
}
