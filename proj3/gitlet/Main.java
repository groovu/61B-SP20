package gitlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    /** Branches file. */
    private static File _branchList = Utils.join(GD, "branches");

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

    /** Method used to debug.
     *
     * @param args Debug commands.
     */
    private static void db(String... args) {
        Index ind = Utils.readObject(_index, gitlet.Index.class);

        if (args[1].equals("blobs")) {
            System.out.println(ind.blobs().toString());
        } else if (args[1].equals("remove")) {
            System.out.println(ind.removal().toString());
        } else if (args[1].equals("stage")) {
            System.out.println(ind.staged().toString());
        } else if (args[1].equals("plog")) {
            if (args.length == 2) {
                System.out.println(ind.parentLog().toString());
            } else if (args.length == 3) {
                String[] a = {"1", args[2]};
                findLCA(a);
            }
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
            _branchList.createNewFile();
            initPers();
            System.exit(0);
        }
    }
    /** Persistence Initializer. Initailizes INDEX, HEAD, and GLOG. */
    private static void initPers() throws FileNotFoundException {
        Index initInd = new Index();
        Commit initCommit = new Commit(initInd, 0);
        Branch initBranch = new Branch(initCommit.sha());
        initInd.setLog(initCommit.logs());

        initInd.setParentLog(initCommit.parentLog());

        initInd.setMergeLog(initCommit.mergeLog());

        Utils.writeObject(_index, initInd);
        Utils.writeContents(_head, initCommit.sha());
        Utils.writeObject(_branchList, initBranch);
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
            Blob blobAdd = new Blob(add);
            File blobObj = Utils.join(od, blobAdd.sha());
            Index ind = Utils.readObject(_index, gitlet.Index.class);
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

        i.setParentLog(cmt.parentLog());

        i.setMergeLog(cmt.mergeLog());

        Utils.writeObject(_index, i);

        globalLogAdd(cmt);
        Branch branchList = Utils.readObject(_branchList, gitlet.Branch.class);
        branchList.updateBranch(cmt.sha());
        Utils.writeObject(_branchList, branchList);
        Utils.writeContents(_head, cmt.sha());
    }

    /** Remove.
     * @param args Args passed into command. */
    private static void rm(String... args) {
        Index ind = Utils.readObject(_index, gitlet.Index.class);
        if (!ind.staged().containsKey(args[1])
                && !ind.blobs().containsKey(args[1])) {
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
        }
        Utils.writeObject(_index, ind);
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
    }
    /** Find commit.
     * @param args Args passed into command. */
    private static void find(String... args) throws FileNotFoundException {
        Scanner read = new Scanner(_glog);
        String l1 = null;
        String l2 = null;
        String l3 = null;
        boolean found = false;
        while (read.hasNext()) {
            l1 = l2;
            l2 = l3;
            l3 = read.nextLine();
            if (l3.equals(args[1])) {
                System.out.println(l1.replaceAll("commit ", ""));
                found = true;
            }
        }
        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }
    /** Returns status of repo.  This should just read from index,
     * As all changes are made to index after each command.
    * @param args Args passed into command. */
    private static void status(String... args) {
        Index i = Utils.readObject(_index, gitlet.Index.class);

        System.out.println("=== Branches ===");
        Branch branches = Utils.readObject(_branchList, gitlet.Branch.class);

        ArrayList<String> branchList = branches.sortedBranchKeys();
        System.out.print("*");
        System.out.println(branches.currentBranch());
        for (String s : branchList) {
            if (s.equals(branches.currentBranch())) {
                continue;
            } else {
                System.out.println(s);
            }
        }
        System.out.println("\n=== Staged Files ===");
        ArrayList<String> staged = new ArrayList<String>(i.staged().keySet());
        Collections.sort(staged);
        for (String s : staged) {
            System.out.println(s);
        }

        System.out.println("\n=== Removed Files ===");
        ArrayList<String> sortedRemoval = i.removal();
        Collections.sort(sortedRemoval);
        for (String s : sortedRemoval) {
            System.out.println(s);
        }
        ArrayList<String> dirFiles
                = new ArrayList<String>(Utils.plainFilenamesIn(CWD));
        ArrayList<String> mods = statusMods(i, staged, dirFiles);
        Collections.sort(mods);
        for (String s : mods) {
            System.out.println(s);
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
    /** Status continued.
     *
     * @param i Passed in params.
     * @param staged Passed in params.
     * @param dirFiles Passed in params.
     * @return List to be printed by status.
     */
    private static ArrayList<String> statusMods(Index i,
                                                ArrayList<String> staged,
                                                ArrayList<String> dirFiles) {
        System.out.println("\n=== Modifications Not Staged For Commit ===");
        Collections.sort(dirFiles);
        ArrayList<String> mods = new ArrayList<>();
        for (String s : dirFiles) {
            if (i.blobs().containsKey(s) && !i.staged().containsKey(s)) {
                Blob shaCheck = new Blob(Utils.join(CWD, s));
                if (!i.blobs().get(s).equals(shaCheck.sha())) {
                    mods.add(s + " (modified)");
                }
            }
        }
        for (String s : staged) {
            File shaFile = Utils.join(CWD, s);
            File objFile = Utils.join(od, i.staged().get(s));
            if (!shaFile.exists()) {
                mods.add(s + " (deleted)");
            } else {
                Blob shaCheck = new Blob(shaFile);
                Blob objSha = Utils.readObject(objFile, gitlet.Blob.class);
                if (!objSha.sha().equals(shaCheck.sha())) {
                    mods.add(s + " (modified)");
                }
            }
        }
        for (String s : i.blobs().keySet()) {
            if (!i.removal().contains(s)) {
                File delChk = Utils.join(CWD, s);
                if (!delChk.exists()) {
                    mods.add(s + " (deleted)");
                }
            }
        }
        return mods;
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
            File checkoutFile = Utils.join(od, checkoutSha);
            Blob checkOutBlob = Utils.readObject(checkoutFile, Blob.class);
            File checkOutFinal = Utils.join(CWD, args[2]);
            Utils.writeContents(checkOutFinal, checkOutBlob.contents());
        } else if (args.length == 4 && args[2].equals("--")) {
            String commitID = args[1];
            if (commitID.length() < 4 * 10) {
                commitID = fullID(commitID);
            }
            File commitFile = Utils.join(od, commitID);
            if (!commitFile.exists()) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }
            Commit loadcommit = Utils.readObject(commitFile,
                    gitlet.Commit.class);
            HashMap<String, String> blobs = loadcommit.blobs();
            if (args[3].equals("ALL")) {
                for (Map.Entry<String, String> s : blobs.entrySet()) {
                    File f = Utils.join(od, s.getValue());
                    Blob b = Utils.readObject(f, gitlet.Blob.class);
                    File w = Utils.join(CWD, s.getKey());
                    Utils.writeContents(w, b.contents());
                }
            } else if (!blobs.containsKey(args[3])) {
                System.out.println("File does not exists in that commit.");
                System.exit(0);
            } else {
                String checkoutSha = blobs.get(args[3]);
                File checkoutFile = Utils.join(od, checkoutSha);
                Blob checkOutBlob = Utils.readObject(checkoutFile, Blob.class);
                File checkOutFinal = Utils.join(CWD, args[3]);
                Utils.writeContents(checkOutFinal,
                        (Object) checkOutBlob.contents());
            }
        } else if (args.length == 2) {
            checkout2(args);
        } else {
            incOp();
        }
    }

    /** Checkout continued.
     *
     * @param args Passed on from checkout.
     */
    private static void checkout2(String... args) {
        Index ind = Utils.readObject(_index, gitlet.Index.class);
        Branch branchList = Utils.readObject(_branchList, gitlet.Branch.class);
        String checkoutSha = args[1];
        if (checkoutSha.length() != 4 * 10) {
            if (!branchList.branches().containsKey(args[1])) {
                System.out.println("No such branch exists.");
                System.exit(0);
            } else if (branchList.currentBranch().equals(args[1])) {
                System.out.println("No need to checkout the current branch.");
                System.exit(0);
            }
            checkoutSha = branchList.branches().get(args[1]);
        }
        Commit ckoCommit = Utils.readObject(Utils.join(od, checkoutSha),
                gitlet.Commit.class);
        ArrayList<String> dirFiles
                = new ArrayList<String>(Utils.plainFilenamesIn(CWD));
        for (String s : dirFiles) {
            if (!ind.blobs().containsKey(s)
                    && ckoCommit.blobs().containsKey(s)) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it, or add and commit it first.");
                System.exit(0);
            } else if (!ckoCommit.blobs().containsKey(s)
                    && ind.blobs().containsKey(s) && !args[0].equals("merge")) {
                File f = Utils.join(CWD, s);
                f.delete();
            }
        }
        for (String s : ckoCommit.blobs().keySet()) {
            String[] pargs = {"checkout", checkoutSha, "--", s};
            checkout(pargs);
        }
        ind.writeLog(ckoCommit.logs());
        ind.setParentLog(ckoCommit.parentLog());

        ind.setMergeLog(ckoCommit.mergeLog());

        ind.clearStage();
        ind.setBlobs(ckoCommit.blobs());
        if (args[1].length() != 4 * 10) {
            branchList.setBranch(args[1]);
        } else if (args[1].length() == 4 * 10) {
            branchList.updateBranch(args[1]);
        }
        Utils.writeObject(_branchList, branchList);
        Utils.writeContents(_head, checkoutSha);
        Utils.writeObject(_index, ind);
    }

    /** Find full commit ID from short commit ID.
     *
     * @param shID passed in short ID.
     * @return Long ID.
     */
    private static String fullID(String shID) {
        String commitID = shID;
        ArrayList<String> pID =
                new ArrayList<String>(Utils.plainFilenamesIn(od));
        for (String s : pID) {
            if (s.indexOf(shID) == 0) {
                commitID = s;
                break;
            }
        }
        return commitID;
    }
    /** Branches current repo.
     * @param args Args passed into command. */
    private static void branch(String... args) throws IOException {
        String head = Utils.readContentsAsString(_head);
        Branch branchlist = Utils.readObject(_branchList, gitlet.Branch.class);
        branchlist.addBranch(args[1], head);
        Utils.writeObject(_branchList, branchlist);
    }


    /** Removes branch.
     * @param args Args passed into command.*/
    private static void rmbranch(String... args) {
        Branch branchlist = Utils.readObject(_branchList, gitlet.Branch.class);
        if (!branchlist.branches().containsKey(args[1])) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        } else if (branchlist.currentBranch().equals(args[1])) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        } else {
            branchlist.removeBranch(args[1]);
        }
        Utils.writeObject(_branchList, branchlist);
    }
    /** Resets ID.
     * @param args Args passed into command. */
    private static void reset(String... args) {
        String commitID = args[1];
        if (commitID.length() < 4 * 10) {
            commitID = fullID(commitID);
        }
        File commitFile = Utils.join(od, commitID);
        if (!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        String[] a = {"checkout", commitID};
        checkout(a);
    }
    /** Merges two branches.
     * @param args Args passed into command. */
    private static void merge(String... args) throws IOException {
        boolean conflict = false;
        mergeErr(args);
        String lcaSha = findLCA(args);
        Branch branchList = Utils.readObject(_branchList, gitlet.Branch.class);
        Index ind = Utils.readObject(_index, gitlet.Index.class);
        String givenSha = branchList.branches().get(args[1]);
        String curSha = branchList.branches().get(branchList.currentBranch());
        specialMergeCheck(lcaSha, givenSha, curSha);
        Commit lcaCommit = Utils.readObject(Utils.join(od, lcaSha),
                gitlet.Commit.class);
        Commit givCommit = Utils.readObject(Utils.join(od, givenSha),
                gitlet.Commit.class);
        HashMap<String, String> lcaBlob = lcaCommit.blobs();
        HashMap<String, String> givBlob = givCommit.blobs();
        HashMap<String, String> curBlob = ind.blobs();
        for (String file : lcaBlob.keySet()) {
            String lcaVal = lcaBlob.get(file);
            String givVal = givBlob.get(file);
            String curVal = curBlob.get(file);
            if (curVal == null && (givVal == null || lcaVal.equals(givVal))) {
                File lcaFile = Utils.join(CWD, file);
                if (lcaFile.exists()) {
                    String[] s = {"rm", file};
                    rm(s);
                } else {
                    continue;
                }
            }
            if (curVal.equals(givVal)) {
                continue;
            }
            if (lcaVal.equals(curVal) && givVal == null) {
                String[] r = {"rm", file};
                rm(r);
            }
            if (lcaVal.equals(curVal) && !lcaVal.equals(givVal)
                    && givVal != null) {
                String[] c = {"merge", givenSha, "--", file};
                checkout(c);
                String[] a = {"add", file};
                add(a);
            }
            if (!lcaVal.equals(curVal) && !curVal.equals(givVal)) {
                System.out.println("");
            }
        }
        for (String file : givBlob.keySet()) {
            String lcaVal = lcaBlob.get(file);
            String curVal = curBlob.get(file);
            if (lcaVal == null && curVal == null) {
                String[] c = {"merge", givenSha, "--", file};
                checkout(c);
                String[] a = {"add", file};
                add(a);
            }
        }
        String msg = "Merged " + args[1] + " into " + branchList.currentBranch()
                + ".";
        String[] mergeArgs = {"merge", msg, curSha, givenSha};
        commit(mergeArgs);
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
        }
    }

    /** Method that checks for special merge conditions.
     *
     * @param lca Sha of least common ancestor.
     * @param given Sha of given branch.
     * @param curr Sha of current branch.
     */
    private static void specialMergeCheck(String lca, String given,
                                            String curr) {
        if (lca.equals(given)) {
            System.out.println("Given branch is an ancestor of the current "
                    + "branch.");
            System.exit(0);
        } else if (lca.equals(curr)) {
            String[] a = {"checkout", given};
            checkout(a);
            System.out.println("Current branch fast-forwarded.");
            System.exit(0);
        }
    }

    /** Find LCA between current branch and given branch.
     *
     * @param args Given branch.
     * @return String if LCA.
     */
    private static String findLCA(String... args) {
        String lca = "";
        boolean mergeShared = false;
        Branch branchList = Utils.readObject(_branchList, gitlet.Branch.class);
        String givenBranch = branchList.branches().get(args[1]);
        File givenFile = Utils.join(od, givenBranch);
        Commit givenCommit = Utils.readObject(givenFile, gitlet.Commit.class);
        List<String> givenPLog = givenCommit.parentLog();

        Index ind = Utils.readObject(_index, gitlet.Index.class);
        List<String> currPLog = ind.parentLog();

        List<String> mergeLog = ind.mergeLog();
//        if (mergeLog.size() != 0) {



        int minSize = Math.min(currPLog.size(), givenPLog.size());
        for (int i = 0; i < minSize; i += 1) {
            String currParent = currPLog.get(i);
            String givenParent = givenPLog.get(i);
            if (currParent.equals(givenParent)) {
                lca = currParent;
            } else {
                break;
            }
        }
        return lca;
    }
    /** Error check for merge.
     *
     * @param args Arguments passed from merge.
     */
    private static void mergeErr(String... args) {
        Branch branchList = Utils.readObject(_branchList, gitlet.Branch.class);
        String current = branchList.currentBranch();
        Index ind = Utils.readObject(_index, gitlet.Index.class);
        if (!ind.staged().isEmpty() || !ind.removal().isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        } else if (current.equals(args[1])) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        } else if (!branchList.branches().containsKey(args[1])) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        } else {
            File cmtF = Utils.join(od, branchList.branches().get(args[1]));
            Commit cmt = Utils.readObject(cmtF, gitlet.Commit.class);
            boolean unt = untracked(ind, cmt);
            if (unt) {
                System.out.println("There is an untracked file in the way; "
                        + "delete it, or add and commit it first.");
                System.exit(0);
            }
        }
    }

    /** Untracked check.
     *
     * @param ind Index.
     * @param cmt Commit.
     * @return True if there exists untracked file.
     */
    private static boolean untracked(Index ind, Commit cmt) {
        boolean untracked = false;
        ArrayList<String> dirFiles
                = new ArrayList<String>(Utils.plainFilenamesIn(CWD));
        for (String s : dirFiles) {
            if (!ind.blobs().containsKey(s)
                    && cmt.blobs().containsKey(s)) {
                untracked = true;
            }
        }
        return untracked;
    }
    /** Checks to see if repo is initialized.
     * @return True if repo is init, False otherwise. */
    private static boolean repoInitCheck() {
        return GD.exists();
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
        } else if (args[0].equals("db")) {
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

