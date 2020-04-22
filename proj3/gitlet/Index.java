package gitlet;

import java.io.File;
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
    private static final long serialVerisonUID = 999999999;
    private static final File INDEX = Utils.join(Main.getGD(), "index");

    /** Construct index from commit. */
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

    /** Method that adds blobs to _blobs and _staged.
     * @param b Blobs to be added.
     * @param args File name of blobs.
     */
    void put(Blob b, String... args) {
        _blobs.put(args[1], b.sha());
        _staged.add(args[1]);
        if (Main.debug()) {
            System.out.println("debug: Added " + args[1]);
            System.out.println("debug: " + _blobs);
        }
    }
    private void clearStage() {
        _staged = new ArrayList<>();
        _removal = new ArrayList<>();
    }
    /** Method that returns parent of index. */
    String parent() {
        return _parent;
    }
    /** Method for setting parent of index. */
    void setParent(String sha) {
        _parent = sha;
    }
    /** Returns current logs. */
    List<String > log() {
        return _logs;
    }
    /** Set logs from init commit.  Should never happen again. */
    void setLog(List<String> log) {
        _logs = log;
    }
    /** Method that returns list of staged files. */
    List<String> staged() {
        return _staged;
    }
    /** Method that returns blobs in index. */
    HashMap<String, String> blobs() {
        return _blobs;
    }

    /** SHA1 of current index.  When a commit is pulled,
     * this SHA1 should match the commits, until a change is made. */
    String _commit;

    /** HashMap that maps blob shas to file names. */
    private HashMap<String, String> _blobs;
    /** Parent commit of index. */
    private static String _parent;
    /** List of Strings that contain staged files for addition. */
    private List<String> _staged;
    /** List of files names that are staged for removal. */
    private List<String> _removal;
    /** Logs stored in a list. */
    private List<String> _logs;
}
