package enigma;

import net.sf.saxon.style.XSLOutput;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;

import static enigma.TestUtils.*;

public class MachineTest {
    @Test
//    public void newMachine() {
//        NAVALA a =
//        Machine a = new Machine(new Alphabet(), 4, 3, NAVALA.get("I"));
//    }

    public void printTest() {
        System.out.println(NAVALA);
        HashMap<String, Integer> happy = new HashMap<String, Integer>();
        happy.put("a", 10);
        happy.put("b", 3);
        happy.put("c", 88);
        System.out.println(happy);
        System.out.println(NAVALA.get("II"));
    }


}
