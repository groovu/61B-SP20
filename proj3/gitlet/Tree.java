package gitlet;

import java.io.Serializable;
import java.util.HashMap;

/** Class that represents Tree.  Trees point to blobs.
 * @author Cherish Truong*/
public class Tree implements Serializable {
    /** Tree constructor.
     *
     * @param sha1 SHA1 of tree.
     * @param treeMap Mapping of trees in Tree.
     * @param blobNameMap Mapping of names to Blobs in Tree.
     */
    Tree(String sha1, HashMap<String, Tree> treeMap,
         HashMap<String, String> blobNameMap) {
        _sha1 = sha1;
        _treeMap = treeMap;
        _blobNameMap = blobNameMap;
    }

    /** Returns SHA1 of tree.
     * @return SHA1.
     */
    String sha() {
        return _sha1;
    }

    /** Returns treeMap of Tree.
     * @return HashMap of treeMap.*/
    HashMap<String, Tree> treeMap() {
        return _treeMap;
    }

    /** Returns file names for mapped blobs.
     * @return HashMap of file names of blobs. */
    HashMap<String, String> fileNameMap() {
        return _blobNameMap;
    }
    /** Reads the mappings.  Useful for cat file print. */
    void read() {
        _blobNameMap.forEach((k, v) ->
                System.out.println("blob " + k + " " + v));
        _treeMap.forEach((k, v) ->
                System.out.println("tree " + k + " " + v));
    }
    /** SHA1. */
    private static String _sha1;
    /** treeMap. */
    private static HashMap<String, Tree> _treeMap;
    /** nameMap. */
    private static HashMap<String, String> _blobNameMap;
}
