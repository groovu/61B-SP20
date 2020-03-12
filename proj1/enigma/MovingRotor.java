package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Cherish Truong
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
        set(0);
        // FIXME
    }
    @Override
    public boolean rotates() {
        return true;
    }

    @Override
    public boolean atNotch() {
        if (setting() == alphabet().toInt(_notches.charAt(0))) {
            return true;
        }
        return false; // place holder
    }
    // FIXME?

    @Override
    void advance() {
        set(permutation().wrap(setting() + 1));
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED
    private String _notches;

}
