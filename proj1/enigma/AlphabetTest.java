package enigma;
import net.sf.saxon.style.XSLOutput;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

public class AlphabetTest {
    @Test
    public void alphabetTest() {
        Alphabet a = new Alphabet();
        Alphabet b = new Alphabet("1A2B");
    }
}
