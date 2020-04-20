package gitlet;

import java.io.Serializable;

/** Class the represents a Commit.
 * @author Cherish Truong
 */
public class Commit implements Serializable {
    /** Commit constructor.
     * @param sha1 1
     * @param tree 1
     * @param parent 1
     * @param author 1
     * @param committer 1
     * @param msg 1*/
    Commit(String sha1, String tree, String parent,
           String author, String committer, String msg) {
        _tree = tree;
        _parent = parent;
        _author = author;
        _committer = committer;
        _msg = msg;
        _sha1 = sha1;
    }
    /** Returns tree that commit points to. */
    String tree() {
        return _tree;
    }
    /** Returns the parent of the commit. */
    String parent() {
        return _parent;
    }
    /** Returns the author of the commit. */
    String author() {
        return _author;
    }
    /** Returns the commiter of the commit. */
    String committer() {
        return _committer;
    }
    /** Returns the sha1 of the commit. */
    String sha() {
        return _sha1;
    }
    /** Returns the commit msg. */
    String msg() {
        return _msg;
    }
    /** The tree the commit points to. */
    private static String _tree;
    /** Parent of commit.*/
    private static String _parent;
    /** Msg of commit. */
    private static String _msg;
    /** Author of commit. */
    private static String _author;
    /** Committer of commit. */
    private static String _committer;
    /** SHA1 of commit. */
    private static String _sha1;

}
