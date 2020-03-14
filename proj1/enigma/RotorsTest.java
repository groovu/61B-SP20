package enigma;

import org.junit.Test;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

public class RotorsTest {
    @Test
    public void rotorInit() {
        Permutation p = new Permutation("", new Alphabet());
        Rotor aa = new Rotor("TEST", p);
        assertEquals("TEST", aa.name());
        Rotor[] bb = new Rotor[1];
    }
    Alphabet abc = new Alphabet();
    Rotor d = new Rotor("I", new Permutation(NAVALA.get("I"), abc));
    Rotor a = new MovingRotor("I", new Permutation(NAVALA.get("I"), abc), "Q");
    Rotor b = new FixedRotor("Beta", new Permutation(NAVALA.get("Beta"), abc));
    Rotor r = new Reflector("B", new Permutation(NAVALA.get("B"), abc));
    @Test
    public void rotateChecks() {
        assertEquals("Default should not rotate", false, d.rotates());
        assertEquals("Moving should rotate", true, a.rotates());
        assertEquals("Fixed should not rotate", false, b.rotates());
        assertEquals("Reflector should not rotate", false, r.rotates());
    }
    @Test
    public void reflectCheck() {
        assertEquals("Default should not reflect", false, d.reflecting());
        assertEquals("Moving should not reflect", false, a.reflecting());
        assertEquals("Fixed should not reflect", false, b.reflecting());
        assertEquals("Reflector should reflect", true, r.reflecting());
    }
    @Test
    public void advanceCheck() {
        assertEquals("Default setting is 0", 0, d.setting());
        assertEquals("Moving setting is 0", 0, a.setting());
        assertEquals("Fixed setting is 0", 0, b.setting());
        assertEquals("Reflector setting is 0", 0, r.setting());
        a.advance(); b.advance(); d.advance(); r.advance();
        assertEquals("Default does not advance.", 0, d.setting());
        assertEquals("Moving advances to 1", 1, a.setting());
        assertEquals("Fixed does not advance.", 0, b.setting());
        assertEquals("Reflector does not advance.", 0, r.setting());
    }
}
