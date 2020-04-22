package gitlet;

import java.io.File;
import java.io.Serializable;
import static gitlet.Main.*;
import static gitlet.Utils.*;

/** Class the represents the contents of a file.
 * @author Cherish Truong*/
public class Blob implements Serializable {
    /** Bootleg var that makes readObject work. */
    private static final long serialVersionUID = 111111111;
    /** Blob constructor.  Give it a file, get a blob back ./
     * @param input File to be turned into contents.
     */
    Blob(File input) {
        _contents = Utils.readContents(input);
        _sha = Utils.sha1(_contents);
    }
    /** Returns the sha of the Blob. */
    String sha() {
        return _sha;
    }

    /** Contents of file, stored in Blob. */
    private static byte[] _contents;

    /** Name of file, stored in Blob. */
    private static String _name;

    /** SHA1 of file. */
    private static String _sha;
}
