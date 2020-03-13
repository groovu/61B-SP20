package enigma;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.*;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Cherish Truong
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine enigma = readConfig();
        String readIn = _input.nextLine();
        while (_input.hasNext()) {
            if (readIn.charAt(0) != '*') {
                throw error("Input file does not start with *.");
            }
            String settings = readIn;
            setUp(enigma, settings);
            readIn = _input.next();
            //readIn = _input.nextLine(); //should be the first line after settings. *
            //while (readIn.charAt(0) != '*') {
            while (readIn.charAt(0) != '*') {
                String converted = "";
                //readIn = _input.nextLine();
//            if (readIn.charAt(0) == '*') {
//                throw error("Pass settings, but '*' found at beginning.");
//            }
                String readInput = "";
                Scanner readScan = new Scanner(readIn);

                while (readScan.hasNext() == true) {
                    String next = readScan.next();
                    readInput = readInput + next;
                }
                converted = converted + enigma.convert(readInput);
                System.out.println("Converted" + converted);
            }
        }
        // FIXME
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            //int numRotors; int numPawls;
            _alphabet = new Alphabet(_config.next());
            numRotors = _config.nextInt();
            numPawls = _config.nextInt();
            //HashMap<String, Rotor> availRotors = new HashMap<>();
            availRotors = new ArrayList<>();
            namePass = "";
            //System.out.println(_config.hasNext());
            while (_config.hasNext() == true) {
                Rotor addRot = readRotor();
                availRotors.add(addRot);
                System.out.println("Rotor added to avail" + addRot.name());
                //availRotors.put(addRot.name(), addRot);
            }
            return new Machine(_alphabet, numRotors, numPawls, availRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        String name = "";
        String notches = "";
        String notchesonly = "";
        String perms = "";
        String read = "";
        try {
            if (namePass == "") {
                name = _config.next();
            } else {
                name = namePass;
            }
            notches = _config.next();
            notchesonly = notches.substring(1); //Q
            read = _config.next();
            while (read.charAt(0) == '(') {
//                System.out.println(read);
//                read = _config.next();
//                System.out.println(read);
//                if (read.charAt(0) != '(') {
//                    throw error("'(' missing from next read segment.)" + read);
//                }
                perms = perms + read;
                if (_config.hasNext() == false) {
                    break;
                }
                read = _config.next();

            }
            namePass = read;
            Permutation toPerm = new Permutation(perms, _alphabet);
            if (notches.charAt(0) == 'M') {
                return new MovingRotor(name, toPerm, notchesonly);
            } else if (notches.charAt(0) == 'N') {
                return new FixedRotor(name, toPerm);
            } else if (notches.charAt(0) == 'R') {
                return new Reflector(name, toPerm);
            } else {
                throw error("Incorrect Rotor type M/N/R" + notches);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description" + read);
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        Scanner scanset = new Scanner(settings);
        String setread = scanset.next(); //star
        String[] insertRotors = new String[numRotors];
        if (settings.charAt(0) != '*') {
            throw error("Settings does not start with *.");
        }
        int i = 0;
        while (i < numRotors) {
            setread = scanset.next();
            insertRotors[i] = setread;
            i += 1;
        } // sanity check
        String rotSettings = scanset.next(); // AXLE
        if (availRotors.contains(rotSettings) == true) {
            throw error("This read is a rotor, not the rotor settings.");
        }
        String plugboard = "";
        while (scanset.hasNext()) {
            plugboard = plugboard + scanset.next();
        }
        M.setPlugboard(new Permutation(plugboard, _alphabet));
        M.insertRotors(insertRotors);
        M.setRotors(rotSettings);
    }


    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        // FIXME
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** Number of rotors from settings*/
    private int numRotors;

    /** Number of pawls from settings*/
    private int numPawls;

    /** Collection of available Rotors*/
    private Collection<Rotor> availRotors;

    /** Name of rotor */
    private String namePass;
}
