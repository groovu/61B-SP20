package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/** Class the represents a Commit.
 * @author Cherish Truong
 */
public class Commit implements Serializable {
    /** Bootleg var that makes readObject work. */
    private static final long serialVersionUID = 222222222;
    /** Commit constructor.
     * @param blobs HashMap that maps blobs to file names.
     * @param parent 1
     * @param msg 1 */
    Commit(HashMap<String, String> blobs, String parent, String msg) {
        SimpleDateFormat time = new SimpleDateFormat("EE LLL d HH:mm:ss yyyy "
                + "Z");
        _time = time.format(new Date());
        _blobs = blobs;
        _parent = parent;
        _msg = msg;
        _sha1 = Utils.sha1(_time + _parent + _msg + _blobs.toString());
        File commit = Utils.join(Main.getOd(), _sha1);
        Utils.writeObject(commit, this);
    }

    /** Initial Commit when git is run.
     * @param ind Index passed in.
     * @param z Dummy variable to force initial commit.
     */
    Commit(Index ind, int z) {
        SimpleDateFormat time = new SimpleDateFormat("EE LLL d HH:mm:ss yyyy "
                + "Z");
        _time = time.format(new Date(0));
        String time2 = "Thu Jan 1 00:00:00 1970 -0000";
        _parent = ind.parent();
        _msg = "initial commit";
        _blobs = ind.blobs();
        _sha1 = Utils.sha1(_time + _parent + _msg + _blobs);
        _logs = new ArrayList<>();
        _logs.add("===\n" + "commit " + _sha1
                + " \nDate: " + _time + "\n" + _msg + "\n");
    }

    /** Commit constructor using Index.
     * @param ind Index passed in.
     * @param args File name passed in from user.
     */
    Commit(Index ind, String[] args) {
        SimpleDateFormat time = new SimpleDateFormat("EE LLL d HH:mm:ss yyyy "
                + "Z");
        _time = time.format(new Date());
        _blobs = ind.blobs();
        _parent = ind.parent();
        _msg = args[1];
        _sha1 = Utils.sha1(_time + _parent + _msg + _blobs.toString());
        _logs = ind.log();
        _logs.add("===\n" + "commit " + _sha1
                + " \nDate: " + _time + "\n" + _msg + "\n");
    }
    /** Returns logs of commit. */
    List<String> logs() {
        return _logs;
    }
    /** Returns hashmap that maps Blob SHAs to File names. */
    HashMap<String, String> blobs() {
        return _blobs;
    }
    /** Returns the parent of the commit. */
    String parent() {
        return _parent;
    }
    /** Returns the sha1 of the commit. */
    String sha() {
        return _sha1;
    }
    /** Returns the commit msg. */
    String msg() {
        return _msg;
    }
    /** Returns commit time. */
    String time() {
        return _time;
    }
    /** HashMap that maps blob shas to file names. */
    private HashMap<String, String> _blobs;
    /** Parent of commit.*/
    private String _parent;
    /** Msg of commit. */
    private String _msg;
    /** SHA1 of commit. */
    private String _sha1;
    /** Commit time. */
    private String _time;
    /** Logs stored in a list. */
    private List<String> _logs;

}
