package gitlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Cherish Truong
 */
public class Main {

    /**
     * Current Working Directory.
     */
    static final File CWD = new File(".");

    /** Get CWD */
    public static File getCWD() {
        return CWD;
    }

    /** Hidden .gitlet Directory. */
    static final File GD = Utils.join(CWD, ".gitlet");

    /** Objectrs directory, inside .gitlet. */
    static final File OBJECTS = Utils.join(GD,"objects");

    /** Refs directory, inside .gitlet. */
    static final File REFS = Utils.join(GD, "refs");

    /** HEAD file? */

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            exitWithError("Must have at least one argument");
        }
        if (args[0].equals("init")) {
            init(args);
        }
        if (!repoInitCheck()) {
            exitWithError("fatal: not a gitlet repository (or any of the parent directors): .gitlet");
        }
        switch (args[0]) {
        }

    }
    private static void init(String[] args) throws FileNotFoundException {
        if (!Utils.join(GD).exists()) {
            GD.mkdir();
            OBJECTS.mkdir();
            REFS.mkdir();
            System.out.println("Initialized empty Gitlet repository in " + System.getProperty("user.dir") + "\\.gitlet\\");
            String test = "test.  holding spot for HEAD";
            PrintWriter head = new PrintWriter(".gitlet/HEAD");
            head.println("ref: refs/heads/master");
            head.close();
            //String test = "test.  holding spot for HEAD";
            //Utils.writeObject(Utils.join(GD,"HEAD"), test);
            System.exit(0);
        }
    }
    private static boolean repoInitCheck() {
        if (!GD.exists()) {
            return false;
        }
        return true;
    }

    private static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(-1);
    }

}
