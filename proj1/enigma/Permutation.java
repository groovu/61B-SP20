package enigma;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Cherish Truong
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cyc = new Cycle(cycles);
        _cycles = _cyc.getCyclesArray();
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        char ret = p;
        for (int i = 0; i < _cycles.length; i += 1) {
            for (int j = 0; j < _cycles[i].length(); j += 1) {
                if (_cycles[i].charAt(j) == p) {
                    if (j + 1 >= _cycles[i].length()) {
                        ret =  _cycles[i].charAt(0);
                    } else {
                        ret =  _cycles[i].charAt(j + 1);
                    }
                }
            }
        }
        return ret;
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char i = _alphabet.toChar(wrap(p));
        return _alphabet.toInt(permute(i));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        char ret = c;
        for (int i = 0; i < _cycles.length; i += 1) {
            for (int j = 0; j < _cycles[i].length(); j += 1) {
                if (_cycles[i].charAt(j) == c) {
                    if (j - 1 < 0) {
                        ret =  _cycles[i].charAt(_cycles[i].length() - 1);
                    } else {
                        ret =  _cycles[i].charAt(j - 1);
                    }
                }
            }
        }
        return ret;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char i = _alphabet.toChar(wrap(c));
        return _alphabet.toInt(invert(i));
    }


    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return the cycles of this Permutation. */
    String[] cycles() {
        return _cycles;
    }

    /** Return cycle object?  */
    Cycle cycleObj() {
        return _cyc;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycle of this permutation. */
    private String[] _cycles;

    /** Cycle object of this perm. */
    private Cycle _cyc;
}
