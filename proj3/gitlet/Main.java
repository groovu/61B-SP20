package gitlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Cherish Truong
 */
public class Main {

    /** Current Working Directory. */
    private static final File CWD = new File(".");

    /** Get CWD.
     * @return File that points to CWD. */
    static File getCWD() {
        return CWD;
    }

    /** Hidden .gitlet Directory. */
    private static final File GD = Utils.join(CWD, ".gitlet");

    /** Objectrs directory, inside .gitlet. */
    private static final File OBJECTS = Utils.join(GD, "objects");

    /** Refs directory, inside .gitlet. */
    private static final File REFS = Utils.join(GD, "refs");

    /** Debug check. */
    private static final boolean DEBUG = true;

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException {
        if (DEBUG) {
            System.out.println("Debug flag is TRUE.");
        }
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        argsMax(args);
        if (args[0].equals("init")) {
            init(args);
        }
        if (!repoInitCheck()) {
            System.out.println("?Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        switch (args[0]) {
        case "add":
            add(args);
            break;
        case "commit":
            commit(args);
            break;
        case "rm":
            rm(args);
            break;
        case "log":
            log(args);
            break;
        case "global-log":
            globalLog(args);
            break;
        case "find":
            find(args);
            break;
        case "status":
            status(args);
            break;
        case "checkout":
            checkout(args);
            break;
        case "branch":
            branch(args);
            break;
        case "rm-branch":
            rmbranch(args);
            break;
        case "reset":
            reset(args);
            break;
        case "merge":
            merge(args);
            break;
        default:
            System.out.println("Command not implemented.");
            System.exit(0);
        }
    }
    /** Init.
     * @param args Args passed into command. */
    private static void init(String... args) throws FileNotFoundException {
        if (Utils.join(GD).exists()) {
            System.out.println("Gitlet version-control system already exists"
                    + " in the current directory.\"");
            System.exit(0);
        }
        if (!Utils.join(GD).exists()) {
            GD.mkdir();
            OBJECTS.mkdir();
            REFS.mkdir();
            System.out.println("Initialized empty Gitlet repository in "
                    + System.getProperty("user.dir") + "\\.gitlet\\");
            PrintWriter head = new PrintWriter(Utils.join(GD, "HEAD"));
            head.println("ref: refs/heads/master");
            head.close();
            Index index = new Index();
            Utils.writeObject(Utils.join(GD, "index"), index);
            System.exit(0);
        }
    }
    /** Add.
     * @param args Args passed into command. */
    private static void add(String... args) throws IOException {
        File add = Utils.join(CWD, args[1]);
        if (!add.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        } else if (add.exists()) {
            // make sure file is no longer staged for removal.
            Blob blobAdd = new Blob(add);
            String blobSha = blobAdd.sha();
            File blobObj = Utils.join(OBJECTS, blobSha);
            Utils.writeObject(blobObj, blobAdd);
            blobObj.createNewFile();
            // returns true, so somehow use index to change staging to be added?
        }
    }
    /** Commit.
     * @param args Args passed into command. */
    private static void commit(String... args) {
        System.out.println("Commit not implemented.");
        // If no files staged in index,
        // print "No changes added to the commit. And exit."
    }
    /** Remove.
     * @param args Args passed into command. */
    private static void rm(String... args) {
        System.out.println("rm not implemented.");
        // if file is not staged or tracked,
        // print "No reason to remove the file." and exit?
        // If file is staged in index, remove it from staging.
    }
    /** Log.
     * @param args Args passed into command. */
    private static void log(String... args) {
        System.out.println("log not implemented.");
        // Returns the log from current head commit to initial commit.
    }
    /** Global Log.
     * @param args Args passed into command. */
    private static void globalLog(String... args) {
        System.out.println("globa-log not implemented.");
        // returns all commits ever made, order does not matter.
    }
    /** Find commit.
     * @param args Args passed into command. */
    private static void find(String... args) {
        System.out.println("find not implemented.");
        // Finds commit message in global log and returns the commit ID.
        // If multiple keys have the same value,
        // return all of them on separate lines.
    }
    /** Returns status of repo.
    * @param args Args passed into command. */
    private static void status(String... args) {
        System.out.println("status not implemeneted.");
        // Displays branches, with current branch marked with *.
        // Template
        // check 61b site.
    }
    /** Checkouts ID.
     * @param args Args passed into command. */
    private static void checkout(String... args) {
    }
    /** Branches current repo.
     * @param args Args passed into command. */
    private static void branch(String... args) {
    }

    /** Removes branch.
     * @param args Args passed into command.*/
    private static void rmbranch(String... args) {
    }
    /** Resets ID.
     * @param args Args passed into command. */
    private static void reset(String... args) {
    }
    /** Merges two branches.
     * @param args Args passed into command. */
    private static void merge(String... args) {
    }
    /** Checks to see if repo is initialized.
     * @return True if repo is init, False otherwise. */
    private static boolean repoInitCheck() {
        if (!GD.exists()) {
            return false;
        }
        return true;
    }

    /** Run if operands are not correct. */
    private static void incOp() {
        System.out.println("Incorrect operands.");
        if (DEBUG) {
            Thread t = Thread.currentThread();
            Thread.dumpStack();
        }
        System.exit(0);
    }
    /** Checks if arguments are correct for commands.
     * @param args Arguments passed in.*/
    private static void argsMax(String... args) {
        int validArgs = 0;
        if (Arrays.asList("add", "commit", "rm", "find", "branch",
                "rm-branch", "reset", "merge").contains(args[0])) {
            validArgs = 2;
        } else if (Arrays.asList("status", "log", "global-log",
                "init").contains(args[0])) {
            validArgs = 1;
        } else if (args[0].equals("checkout")) {
            if (args.length > 1 && args.length < 5) {
                return;
            } else {
                incOp();
            }
        } else {
            System.out.println("No command with that name exists.");
            System.exit(0);
        }
        if (args.length != validArgs) {
            incOp();
        }
    }
}
