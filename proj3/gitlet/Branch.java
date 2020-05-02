package gitlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/** Branch file that keeps track of branches.
 * Branches point to commits.
 * @author Cherish Truong. */
public class Branch implements Serializable {
    /** Bootleg var that makes readObject work. */
    private static final long serialVersionUID = 555555555;

    /** Initial Branch constructor. */
    Branch(String masterSHA) {
        _branches = new HashMap<>();
        _branches.put("master", masterSHA);
        _currentBranch = "master";
    }
    /** Constructs branch file, to be stored. */
    Branch(String name, String cmt) {
        _name = name;
        _commit = cmt;
    }


    /** Returns name of branch.
     *
     * @return Name of branch.
     */
    String name() {
        return _name;
    }
    /** Returns name of commit.
     *
     * @return Name of commit.
     */
    String commitID() {
        return _commit;
    }

    /** Method to add to branch.
     *
     * @param name Name of the branch.
     * @param sha HEAD SHA at branch point.
     */
    void addBranch(String name, String sha) {
        if (_branches.containsKey(name)) {
            System.out.println("A branch with that name already exists.");
            return;
        } else {
            _branches.put(name, sha);
        }
    }
    /** Method to update branch pointer after commit. */
    void updateBranch(String sha) {
        _branches.put(_currentBranch, sha);
    }
    /** Method to remove branch. */
    void removeBranch(String name) {
        _branches.remove(name);
    }

    /** Returns current branch.  Error if head sha does not match.
     * I should just put head in here.
     * @return Name of current branch.
     */
    String currentBranch() {
        return _currentBranch;
    }

    /** Sets current branch to new branch.
     *
     * @param branchname Change to branch name.
     */
    void setBranch(String branchname) {
        _currentBranch = branchname;
    }
    /** Returns HashMap of branches.
     *
     * @return Hashmap of branches.
     */
    HashMap<String, String> branches() {
        return _branches;
    }

    /** Sorts branch keys for status.
     *
     * @return Sorted list of branch keys.
     */
    ArrayList<String> sortedBranchKeys() {
        ArrayList<String> sorted = new ArrayList<String>(_branches.keySet());
        Collections.sort(sorted);
        return sorted;
    }
    /** Name of branch. */
    private String _name;
    /** Name of commit. */
    private String _commit;
    /** Master list of branches; name to sha. */
    private HashMap<String, String> _branches;
    /** Current branch name. */
    private String _currentBranch;


}
