package gitlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

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

    /** Get gitlet dir. */
    static File getGD() { return GD; }

    /** Objectrs directory, inside .gitlet. */
    private static File OD = Utils.join(GD, "objects");

    /** Get Objects directory. */
    static File getOD() { return OD; }

    /** Refs directory, inside .gitlet. */
    private static File REFS = Utils.join(GD, "refs");

    /** Index file that tracks the working directory. */
    private static File INDEX = Utils.join(GD, "index");

    /** Head file thats keeps track of current head. */
    private static File HEAD = Utils.join(GD, "HEAD");

    /** Global log file. */
    private static File GLOG = Utils.join(GD, "global-log");

    /** Debug check. */
    private static final boolean DEBUG = true;
    /** Debug flag access for other classes. */
    static boolean debug() { return DEBUG; }


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
            System.out.println("Not in an initialized Gitlet directory.");
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
    private static void init(String... args) throws IOException {
        if (Utils.join(GD).exists()) {
            System.out.println("Gitlet version-control system already exists"
                    + " in the current directory.");
            System.exit(0);
        }
        if (!Utils.join(GD).exists()) {
            GD.mkdir();
            OD.mkdir();
            REFS.mkdir();
            INDEX.createNewFile();
            HEAD.createNewFile();
            GLOG.createNewFile();
            initPers();
            //indexInit();
            //initCommitHead();
            System.exit(0);
        }
    }
    /** Persistence Initializer. Initailizes INDEX, HEAD, and GLOG. */
    private static void initPers() throws FileNotFoundException {
        Index initInd = new Index();
        Utils.writeObject(INDEX, initInd);
        Index i = Utils.readObject(INDEX, Index.class);
        Commit initial = new Commit(i, 0);
        Utils.writeContents(HEAD, initial.sha());
        globalLogAdd(initial);
    }
//    /** Initialize Index for persistence. */
//    private static void indexInit() {
//        Index initial = new Index();
//        Utils.writeObject(INDEX, initial);
//    }
    /** Method that updates the global log; updated after every commit.
     * @param cmt Metadata from commit to be added to global log.*/
    private static void globalLogAdd(Commit cmt) throws FileNotFoundException {
        String readIn = Utils.readContentsAsString(GLOG);
        String write = "===\n" + "commit " + cmt.sha()
                + " \nDate: " + cmt.time() + "\n" + cmt.msg();
        if (readIn.isEmpty()) {
            readIn = readIn.concat(write);
        } else {
            readIn = readIn.concat("\n");
            readIn = readIn.concat(write);
        }
        Utils.writeContents(GLOG, readIn);
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
            File blobObj = Utils.join(OD, blobAdd.sha());
            Index ind = Utils.readObject(INDEX, gitlet.Index.class);
            if (!blobObj.exists()) {
                Utils.writeObject(blobObj, blobAdd);
                blobObj.createNewFile();
            } else if (blobObj.exists() && ind.blobs().containsKey(args[1])) {
                if (blobAdd.sha().equals(ind.blobs().get(args[1]))) {
                    if (DEBUG) {
                        System.out.println("File is unchanged in blobs.");
                    }
                    System.exit(0);
                }
            }
            ind.put(blobAdd, args);
            Utils.writeObject(INDEX, ind);
        }
    }
    /** Commit.
     * @param args Args passed into command. */
    private static void commit(String... args) {
        // If no files staged in index,
        // print "No changes added to the commit. And exit."
        // from index, grab all tracked blobs.
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
    /** Returns status of repo.  This should just read from index,
     * As all changes are made to index after each command.
    * @param args Args passed into command. */
    private static void status(String... args) {
        // Displays branches, with current branch marked with *.
        // Template
        // check 61b site.
        List<String> dirFiles = Utils.plainFilenamesIn(CWD);
        Index i = Utils.readObject(INDEX, gitlet.Index.class);
        List<String> staged = i.staged();



        System.out.println("=== Branches ===");

        System.out.println("\n=== Staged Files ===");
        for (String s : staged) {
            System.out.println(s);
        }

        System.out.println("\n=== Removed Files ===");

        System.out.println("\n=== Modifications Not Staged For Commit ===");

        System.out.println("\n=== Untracked Files ===");
        for (String s : dirFiles) {
            System.out.println(s);
        }




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
    /** Index that contains current working dir. */
    private static Index _index;
}

