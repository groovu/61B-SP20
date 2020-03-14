package enigma;
import static enigma.EnigmaException.*;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Cherish Truong
 */
class Alphabet {

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        int len = chars.length();
        _alphabet = new char[len];
        for (int i = 0; i < len; i += 1) {
            _alphabet[i] = chars.charAt(i);
        }
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _alphabet.length;
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        for (char i : _alphabet) {
            if (i == ch) {
                return true;
            }
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        if (size() <= index) {
            throw error("Error: Index is out of range.");
        } else if (index < 0) {
            throw error("Error: Index is less than 0.");
        }
        return _alphabet[index];
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        for (int i = 0; i < _alphabet.length; i += 1) {
            if (ch == _alphabet[i]) {
                return i;
            }
        }
        throw error("Character not in alphabet.");
    }
    @Override
    public String toString() {
        String print = new String(_alphabet);
        return print;
    }
    /** Array that contains alphabet. */
    private char[] _alphabet;
}
