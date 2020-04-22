package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/** Index file that acts as the working directory of current
 * gitlet instance.  When you commit, you are committing the
 * contents of index.  Index should be saved as a persistent
 * file on disk.
 * @author Cherish Truong*/
public class Index implements Serializable {
    private static final File INDEX = Utils.join(Main.getGD(), "index");

    /** Construct index from commit. */
    Index(Commit cmt) {
        _blobs = cmt.blobs();
        _parent = cmt.parent();
        //System.out.println("index file created.");
    }
    /** Initial Index constructor. */
    Index() {
        _blobs = new HashMap<String, String>();
        _parent = null;
    }
    void put(Blob b, String... args) {
        _blobs.put(b.sha(), args[1]);
    }
    String parent() {
        return _parent;
    }
    HashMap<String, String> blobs() {
        return _blobs;
    }

    /** SHA1 of current index.  When a commit is pulled,
     * this SHA1 should match the commits, until a change is made. */
    String _commit;

    /** HashMap that maps blob shas to file names. */
    private static HashMap<String, String> _blobs;
    /** Parent commit of index. */
    private static String _parent;
}
