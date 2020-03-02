package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author
 */
class FixedRotor extends Rotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is given by PERM. */
    FixedRotor(String name, Permutation perm) {
        super(name, perm);
    }

    // FIXME ?
    // Fixed rotor doesn't move.
    // Fixed rotor is super class for reflector.
    // But it's only 1, what's the point.
    // Do I need to fix the setting to 0?
}
