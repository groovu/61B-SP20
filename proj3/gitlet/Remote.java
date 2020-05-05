package gitlet;

import java.io.Serializable;
import java.util.HashMap;

/** Class that represents remote related stuff.
 * @author Cherish Truong*/
public class Remote implements Serializable {
    /** Bootleg var that makes readObject work. */
    private static final long serialVersionUID = 1212121213;
    /** Init remote constructor. */
    Remote() {
        _repos = new HashMap<>();
    }

    /** Method to add repos.
     *
     * @param name of Repo.
     * @param loc location of repo.
     */
    void put(String name, String loc) {
        if (_repos.containsKey(name)) {
            System.out.println("A remote with that name already exists.");
            System.exit(0);
        } else {
            _repos.put(name, loc);
        }
    }

    /** Method to remove repo.
     * @param name Name of repo to be removed.
     */
    void remove(String name) {
        if (!_repos.containsKey(name)) {
            System.out.println("A remote with that name does not exist.");
            System.exit(0);
        } else {
            _repos.remove(name);
        }
    }

    /** Method to get map of repos.
     *
     * @return Map of repos.
     */
    HashMap<String, String> repos() {
        return _repos;
    }
    /** Map of repos.*/
    private HashMap<String, String> _repos;
}
