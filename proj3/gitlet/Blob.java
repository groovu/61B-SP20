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
        _bytes = Utils.readContents(input);
    }

    static byte[] _bytes;
    static String _string;
}
