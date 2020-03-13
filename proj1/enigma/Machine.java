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
        _rotors = new LinkedList<>();
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
        //_rotors.clear();
        if (_availRotors.get(rotors[0]).reflecting() == false) {
            throw error ("First rotor is not reflector.");
        }
        if (_availRotors.get(rotors[rotors.length - 1]).rotates() == false) {
            throw error ("Last rotor does not rotate.");
        }
        for (String r : rotors) {
            if (_availRotors.containsKey(r.toUpperCase()) == false) {
                throw error("Trying to add rotor that does not exist." + r);
            }
            _rotors.add(_availRotors.get(r.toUpperCase()));
        }
        int pawls = 0;
//        for (Map.Entry<String, Rotor> r : _rotors.entrySet()) {
//            if (r.getValue().rotates()) {
//                pawls += 1;
//            }
//        }
        for (Rotor r: _rotors) {
            if (r.rotates()) {
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
        if (setting.length() != _rotors.size() - 1) {
            throw error ("Number of settings do not match number of rotors.");
        }
        for (int i = 0; i < numRotors() - 1; i += 1) {
            if (_alphabet.contains(setting.charAt(i)) == false) {
                throw error("Setting is not in alphabet");
            }
        }
//        Set<String> rotors = _rotors.keySet();
//        int i = 0;
//        for(String r : rotors) {
//            _rotors.get(r).set(setting.charAt(i));
//            i += 1;
//        }
        int i = -1;
        for (Rotor r : _rotors) {
            if (i == -1) {
                r.set(0);
                i += 1;
            } else {
                r.set(setting.charAt(i));
                i += 1;
            }
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
        //forward pass (going right to left)
        char x = _alphabet.toChar(c);
        if (_alphabet.contains(x) == false) {
            throw error("Input int c is not in machine's alphabet.");
        }
        int first = _rotors.size() - 1;
        boolean advanced = true;
        boolean atNotch = _rotors.get(first).atNotch();
        Rotor currentRot;
        currentRot = _rotors.get(first);
        currentRot.advance();
        for (int i = first - 1; i >= first - _numPawls; i -= 1) {
            Rotor nextRot = _rotors.get(i);
            if ((atNotch && advanced) || (advanced && nextRot.atNotch())) {
                nextRot.advance();
                advanced = true;
                atNotch = nextRot.atNotch();
            } else {
                advanced = false;
                atNotch = nextRot.atNotch();
            }
        }
        c = _plug.permute(c);
        for (int i = first; i >= 0; i -= 1) {
            Rotor nextRot = _rotors.get(i);
            c = nextRot.convertForward(c);
        }
        //backwards
        for (int i = 1; i < first + 1; i += 1) {
            Rotor nextRot = _rotors.get(i);
            c = nextRot.convertBackward(c);
        }
        return c; //
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String str = "";
        for (int i = 0; i < msg.length(); i += 1) {
            char c = msg.charAt(i);
            int x = _alphabet.toInt(c);
            int convertX = convert(x);
            char convertXtoChar = _alphabet.toChar(convertX);
            str = str + convertXtoChar;
        }
        return str;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotors in machine. */
    private int _numRotors;

    /** Number of pawls in machine. */
    private int _numPawls;

    /** HashMap of all Rotors. */
    private HashMap<String, Rotor> _availRotors;

    /** LinkedList of Rotors used by Machine */
    private LinkedList<Rotor> _rotors;

    /** Plugboard of machine */
    private Permutation _plug;

    // FIXME: ADDITIONAL FIELDS HERE, IF NEEDED.
}
