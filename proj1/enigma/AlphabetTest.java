package enigma;

import org.junit.Test;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

public class AlphabetTest {
    @Test
    public void alphabetTest() {
        Alphabet a = new Alphabet();
        Alphabet b = new Alphabet("1A2B");
        String checkb = b.toString();
        String checkb2 = "1A2B";
        assertEquals(checkb, checkb2);
    }
    @Test
    public void nonAlphabet() {
        Alphabet a = new Alphabet("$3!*A");
        String alpha = a.toString();
        assertEquals("$3!*A", alpha);
    }
}
