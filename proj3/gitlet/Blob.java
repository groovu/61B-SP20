package gitlet;

import java.io.File;
import java.io.Serializable;
import static gitlet.Main.*;
import static gitlet.Utils.*;


public class Blob implements Serializable {
    /** Blob constructor.  Give it a file, get a blob back ./
     * @param
     */
    Blob(File input) {
        _contents = Utils.readContents(input);
        _sha = Utils.sha1(_contents);
    }

    public String sha() {
        return _sha;
    }

    /** Contents of file, stored in Blob. */
    static byte[] _contents;

    /** Name of file, stored in Blob. */
    static String _name;

    /** SHA1 of file */
    static String _sha;
}
