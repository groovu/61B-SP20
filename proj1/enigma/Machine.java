package enigma;

import java.util.*;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Cherish Truong
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  AVAILROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls, Collection<Rotor> availRotors) {
        _numRotors = numRotors;
        _alphabet = alpha;
        _numPawls = pawls;
        _availRotors = new HashMap<>();
        for (Rotor r : availRotors) {
            _availRotors.put(r.name().toUpperCase(), r);
        }
        _rotors = new LinkedHashMap<>();
        _plug = new Permutation("", alpha);
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        _rotors.clear();
        if (_availRotors.get(rotors[0]).reflecting() == false) {
            throw error ("First rotor is not reflector.");
        }
        for (String r : rotors) {
            if (_availRotors.containsKey(r) == false) {
                throw error("Rotor does not exist in available Rotors.");
            }
            _rotors.put(r.toUpperCase(), _availRotors.get(r.toUpperCase()));
        }
        int pawls = 0;
        for (Map.Entry<String, Rotor> r : _rotors.entrySet()) {
            if (r.getValue().rotates()) {
                pawls += 1;
            }
        }
        if (pawls != numPawls()) {
            throw error ("Pawl count does not match machine setting.");
        }
        if (_rotors.size() != _numRotors) {
            throw error("Number of rotors does not match.");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != _rotors.size()) {
            throw error ("Number of settings do not match number of rotors.");
        }
        for (int i = 0; i < numRotors() - 1; i += 1) {
            if (_alphabet.contains(setting.charAt(i)) == false) {
                throw error("Setting is not in alphabet");
            }
        }
        Set<String> rotors = _rotors.keySet();
        int i = 0;
        for(String r : rotors) {
            _rotors.get(r).set(setting.charAt(i));
            i += 1;
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plug = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        return 0; // FIXME
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        return ""; // FIXME
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotors in machine. */
    private int _numRotors;

    /** Number of pawls in machine. */
    private int _numPawls;

    /** HashMap of all Rotors. */
    private HashMap<String, Rotor> _availRotors;

    /** LinkedHashMap of Rotors used by Machine */
    private LinkedHashMap<String, Rotor> _rotors;

    /** Plugboard of machine */
    private Permutation _plug;

    // FIXME: ADDITIONAL FIELDS HERE, IF NEEDED.
}
