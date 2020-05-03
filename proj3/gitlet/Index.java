package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/** Index file that acts as the working directory of current
 * gitlet instance.  When you commit, you are committing the
 * contents of index.  Index should be saved as a persistent
 * file on disk.
 * @author Cherish Truong*/
public class Index implements Serializable {
    /** Bootleg var that makes readObject work. */
    private static final long serialVersionUID = 999999999;

    /** Construct index from commit.
     * @param cmt Commit file to be loaded into index. */
    Index(Commit cmt) {
        _blobs = cmt.blobs();
        _parent = cmt.parent();
        clearStage();
    }
    /** Initial Index constructor. */
    Index() {
        _blobs = new HashMap<>();
        _parent = null;
        clearStage();
    }
    /**
    /** Method that adds blobs to _blobs and _staged.
     * @param b Blobs to be added.
     * @param args File name of blobs. */
    void put(Blob b, String args) {
        if (_blobs.containsKey(args)) {
            if (_blobs.get(args).equals(b.sha())) {
                return;
            }
        }
        _staged.put(args, b.sha());
        if (Main.debug()) {
            System.out.println("debug: Added " + args);
            System.out.println("debug: " + _blobs);
        }
    }

    /** Method that removes file from staging area.
     *
     * @param args to be removed from staging.
     */
    void removeStage(String... args) {
        _staged.remove(args[1]);
    }

    /** Method for adding file for removal at commit.
     *
     * @param args to be staged for deletion.
     */
    void removeDelete(String... args) {
        _removal.add(args[1]);
    }

    /** Method for removing Strings from removal list.
     *
     * @param args to be removed from removal.
     */
    void removeRemove(String args) {
        _removal.remove(args);
    }

    /** Clears the staging area of index after being loaded. */
    void clearStage() {
        _staged = new HashMap<>();
        _removal = new ArrayList<>();
    }
    /** Method that returns parent of index. */
    String parent() {
        return _parent;
    }

    /** Method for setting parent of index.
     * @param sha SHA of parent. */
    void setParent(String sha) {
        _parent = sha;
    }

    /** Returns current logs.
     *
     * @return Returns list of logs.
     */
    List<String> log() {
        return _logs;
    }

    /** Write to log after checkout.
     *
     * @param log Logs from checkout commit.
     */
    void writeLog(List<String> log) {
        _logs = log;
    }

    /** Set logs from init commit.  Should never happen again.
     * @param log log copied from init commit to be stored in index. */
    void setLog(List<String> log) {
        _logs = log;
    }

    /** Sets parent log from init commit.
     *
     * @param pLog Parent log from commit.
     */
    void setParentLog(List<String> pLog) {
        _parentLog = pLog;
    }

    /** Gets parent log of index.
     *
     * @return Returns the current parentlog of index.
     */
    List<String> parentLog() {
        return _parentLog;
    }
    /** Method that returns list of staged files.
     *
     * @return Returns list of staged files.
     */
    HashMap<String, String> staged() {
        return _staged;
    }

    /** Method that returns list of files prepped for removal.
     * @return List of files prepped for removal.
     */
    ArrayList<String> removal() {
        return _removal;
    }

    /** Method that returns blobs in index.
     *
     * @return Returns Map of file names to blobs.
     */
    HashMap<String, String> blobs() {
        return _blobs;
    }

    /** Method to set index blobs after checkout.
     *
     * @param b blob to be set in index.
     */
    void setBlobs(HashMap<String, String> b) {
        _blobs = b;
    }


    /** SHA1 of current index.  When a commit is pulled,
     * this SHA1 should match the commits, until a change is made. */
    private String _commit;
    /** Branch of current index. */
    private String[] _branch;

    /** HashMap that maps blob shas to file names. */
    private HashMap<String, String> _blobs;
    /** Parent commit of index. */
    private String _parent;
    /** List of Strings that contain staged files for addition. */
    private HashMap<String, String> _staged;
    /** List of files names that are staged for removal. */
    private ArrayList<String> _removal;
    /** Logs stored in a list. */
    private List<String> _logs;
    /** Parent log stored in a list. */
    private List<String> _parentLog;
}
