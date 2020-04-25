package gitlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

    /** Get gitlet dir.
     *
     * @return Gitlet directory.
     */
    static File getGD() {
        return GD;
    }

    /** Objects directory, inside .gitlet. */
    private static File od = Utils.join(GD, "objects");

    /** Get Objects directory.
     * @return Returns objects directory.*/
    static File getOd() {
        return od;
    }

    /** Refs directory, inside .gitlet. */
    private static File _refs = Utils.join(GD, "refs");

    /** Index file that tracks the working directory. */
    private static File _index = Utils.join(GD, "index");

    /** Head file thats keeps track of current head. */
    private static File _head = Utils.join(GD, "HEAD");

    /** Global log file. */
    private static File _glog = Utils.join(GD, "global-log");

    /** Debug check. */
    private static final boolean DEBUG = false;

    /** Debug flag access for other classes.
     * @return Debug flag on or off. */
    static boolean debug() {
        return DEBUG;
    }


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
        case "db":
            db(args);
            break;
        default:
            System.out.println("Command not implemented.");
            System.exit(0);
        }
    }
    private static void db(String... args) {
        Index ind = Utils.readObject(_index, gitlet.Index.class);

        if (args[1].equals("blobs")) {
            System.out.println(ind.blobs().toString());
        }
         else if (args[1].equals("remove")) {
            System.out.println(ind.removal().toString());
        }
        else if (args[1].equals("stage")) {
            System.out.println(ind.staged().toString());
        } else {
            System.out.println("blobs, remove, stage");
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
            od.mkdir();
            _refs.mkdir();
            _index.createNewFile();
            _head.createNewFile();
            _glog.createNewFile();
            initPers();
            System.exit(0);
        }
    }
    /** Persistence Initializer. Initailizes INDEX, HEAD, and GLOG. */
    private static void initPers() throws FileNotFoundException {
        Index initInd = new Index();
        Commit initCommit = new Commit(initInd, 0);
        initInd.setLog(initCommit.logs());
        Utils.writeObject(_index, initInd);
        Utils.writeContents(_head, initCommit.sha());
        File icommit = Utils.join(od, initCommit.sha());
        Utils.writeObject(icommit, initCommit);
        globalLogAdd(initCommit);
    }
    /** Method that updates the global log; updated after every commit.
     * @param cmt Metadata from commit to be added to global log.*/
    private static void globalLogAdd(Commit cmt) throws FileNotFoundException {
        String readIn = Utils.readContentsAsString(_glog);
        String write = "===\n" + "commit " + cmt.sha()
                + " \nDate: " + cmt.time() + "\n" + cmt.msg();
        if (readIn.isEmpty()) {
            readIn = readIn.concat(write);
        } else {
            readIn = readIn.concat("\n");
            readIn = readIn.concat(write);
        }
        readIn = readIn.concat("\n");
        Utils.writeContents(_glog, readIn);
    }
    /** Method to update index log.  Logs are stored in commits.  */
    private static void logAdd() { }
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
            File blobObj = Utils.join(od, blobAdd.sha());
            Index ind = Utils.readObject(_index, gitlet.Index.class);
            //ind.removeRemove(args[1]);
            if (!blobObj.exists()) {
                Utils.writeObject(blobObj, blobAdd);
                blobObj.createNewFile();
            } else if (blobObj.exists() && ind.blobs().containsKey(args[1])) {
                if (blobAdd.sha().equals(ind.blobs().get(args[1]))
                        && !ind.removal().contains(args[1])) {
                    if (DEBUG) {
                        System.out.println("File is unchanged in blobs.");
                    }
                    System.exit(0);
                }
            }
            ind.removeRemove(args[1]);
            ind.put(blobAdd, args[1]);
            Utils.writeObject(_index, ind);


        }
    }
    /** Commit.
     * @param args Args passed into command. */
    private static void commit(String... args) throws FileNotFoundException {
        if (args[1].equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        Index i = Utils.readObject(_index, gitlet.Index.class);
        if (i.staged().isEmpty() && i.removal().isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        Commit cmt = new Commit(i, args);
        if (DEBUG) {
            System.out.println("Commit: index has this blob.");
            System.out.println(i.blobs().toString());
            System.out.println("Commit: new commit has this blob.  Should be "
                    + "same.");
            System.out.println(cmt.blobs().toString());
        }
        File cmtFile = Utils.join(od, cmt.sha());
        Utils.writeObject(cmtFile, cmt);

        i.clearStage();
        i.setParent(cmt.sha());

        Utils.writeObject(_index, i);

        globalLogAdd(cmt);

        Utils.writeContents(_head, cmt.sha());
    }

    /** Remove.
     * @param args Args passed into command. */
    private static void rm(String... args) {
        Index ind = Utils.readObject(_index, gitlet.Index.class);
        if (!ind.staged().containsKey(args[1]) && !ind.blobs().containsKey(args[1])) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        } else {
            if (ind.removal().contains(args[1])) {
                System.exit(0);
            }
            if (ind.staged().containsKey(args[1])
                    && !ind.blobs().containsKey(args[1])) {
                ind.removeStage(args);
            } else if (!ind.staged().containsKey(args[1])
                    && ind.blobs().containsKey(args[1])) {
                ind.removeDelete(args);
                Utils.join(CWD, args[1]).delete();
            } else if (ind.staged().containsKey(args[1])
            && ind.blobs().containsKey(args[1])) {
                System.out.println("?File is staged and in commit.");
            }
            //Utils.writeObject(_index, ind);
        }
        Utils.writeObject(_index, ind);
        // if staged but not in blob; remove from stage, do not delete.
        // if staged and in blob, remove from stage, prep for remove, delete
        // if file is not staged or tracked,
        // print "No reason to remove the file." and exit?
        // If file is staged in index, remove it from staging.
    }
    /** Log.
     * @param args Args passed into command. */
    private static void log(String... args) {
        Index ind = Utils.readObject(_index, gitlet.Index.class);
        int size = ind.log().size();
        for (int i = size - 1; i >= 0; i -= 1) {
            System.out.println(ind.log().get(i));
        }
    }
    /** Global Log.
     * @param args Args passed into command. */
    private static void globalLog(String... args) {
        String readIn = Utils.readContentsAsString(_glog);
        System.out.println(readIn);
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
        List<String> dirFiles = Utils.plainFilenamesIn(CWD);
        Index i = Utils.readObject(_index, gitlet.Index.class);
        List<String> staged = new ArrayList<String>(i.staged().keySet());
        //HashMap<String, String> staged = i.staged();

        System.out.println("=== Branches ===");
        // Display first branch with *.
        System.out.print("*");
        System.out.println("master");

        System.out.println("\n=== Staged Files ===");
        for (String s : staged) {
            System.out.println(s);
        }

        System.out.println("\n=== Removed Files ===");
        for (String s : i.removal()) {
            System.out.println(s);
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");
        for (Map.Entry<String, String> file: i.blobs().entrySet()) {
            File load1 = Utils.join(od, file.getValue());
            Blob load = Utils.readObject(load1, gitlet.Blob.class);
            File dir1 = Utils.join(CWD,file.getValue());
            Blob dir;
            if (!dir1.exists()) {
                continue;
            } else {
                dir = new Blob(Utils.join(CWD, file.getKey()));
            }
            if (!load1.exists()) {
                continue;
            } else if (!load.sha().equals(dir.sha())) {
                System.out.println(file.getKey());
            }

        }

        System.out.println("\n=== Untracked Files ===");
        for (String s : dirFiles) {
            if (i.blobs().containsKey(s) || i.staged().containsKey(s)) {
                continue;
            } else {
                System.out.println(s);
            }
        }
        System.out.println("");

    }
    /** Checkouts ID.
     * @param args Args passed into command. */
    private static void checkout(String... args) {
        if (args.length == 3 && args[1].equals("--")) {
            String head = Utils.readContentsAsString(_head);
            File headFile = Utils.join(od, head);
            Commit headCommit = Utils.readObject(headFile, gitlet.Commit.class);
            HashMap<String, String> blobs = headCommit.blobs();
            if (!blobs.containsKey(args[2])) {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
            String checkoutSha = blobs.get(args[2]);
            // for another version.
//            File cwdCheck = Utils.join(CWD, args[2]);
//            if (cwdCheck.exists()) {
//                Blob check = new Blob(cwdCheck);
//                if (check.sha().equals(checkoutSha)) {
//                    System.out.println();
//                }
//            }
            File checkoutFile = Utils.join(od, checkoutSha);
            Blob checkOutBlob = Utils.readObject(checkoutFile, Blob.class);
            File checkOutFinal = Utils.join(CWD, args[2]);
            Utils.writeContents(checkOutFinal, (Object) checkOutBlob.contents());
            //Utils.writeObject(checkOutFinal, checkOutBlob);
        }
        else if (args.length == 4 && args[2].equals("--")) {
            String commitID = args[1];
            File commitFile = Utils.join(od, commitID);
            if (!commitFile.exists()) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }
            Commit loadcommit = Utils.readObject(commitFile,
                    gitlet.Commit.class);
            HashMap<String, String> blobs = loadcommit.blobs();
            if (!blobs.containsKey(args[3])) {
                System.out.println("File does not exists in that commit.");
                System.exit(0);
            }
            String checkoutSha = blobs.get(args[3]);
            File checkoutFile = Utils.join(od, checkoutSha);
            Blob checkOutBlob = Utils.readObject(checkoutFile, Blob.class);
            File checkOutFinal = Utils.join(CWD, args[3]);
            Utils.writeContents(checkOutFinal, (Object) checkOutBlob.contents());
        }
    }
    /** Branches current repo.
     * @param args Args passed into command. */
    private static void branch(String... args) {
    }
    private static void branchInit() {

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
        }else if (args[0].equals("db")) {
            return;
        } else {
            System.out.println("No command with that name exists.");
            System.exit(0);
        }
        if (args.length != validArgs) {
            incOp();
        }
    }
}

