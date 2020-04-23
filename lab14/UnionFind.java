
/** Disjoint sets of contiguous integers that allows (a) finding whether
 *  two integers are in the same set and (b) unioning two sets together.  
 *  At any given time, for a structure partitioning the integers 1 to N, 
 *  into sets, each set is represented by a unique member of that
 *  set, called its representative.
 *  @author
 */
public class UnionFind {

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        uf = new int[N + 1];
        sizes = new int[N + 1];
        for (int i = 0; i <= N; i += 1) {
            uf[i] = i;
            sizes[i] = 1;
        }
        // FIXME
    }

    /** Return the representative of the set currently containing V.
     *  Assumes V is contained in one of the sets.  */
    public int find(int v) {
        int found = 0;
        if (uf[v] != v) {
            found = find(uf[v]);
        } else {
            found = uf[v];
        }
        return found;  // FIXME
        //return uf[v];
    }

    /** Return true iff U and V are in the same set. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single set, returning its representative. */
    public int union(int u, int v) {
        int parU = find(u);
        int parV = find(v);
        uf[parU] = parV;
        return parV;
    }
//        if (samePartition(u, v)) {
//            return find(u);
//        } else {
//            int repU = find(u);
//            int uCount = counter(repU);
////            for (int i = 0; i < uf.length; i += 1) {
////                if (uf[i] == repU) {
////                    uCount += 1;
////                }
////            }
//            int repV = find(v);
//            int vCount = counter(repV);
////            for (int i = 0; i < uf.length; i += 1) {
////                if (uf[i] == repV) {
////                    vCount += 1;
////                }
////            }
//            if (uCount > vCount) {
//                uf[v] = repU;
//                return repU;
//            } else if (vCount > uCount) {
//                uf[u] = repV;
//                return repV;
//            } else {
//                uf[v] = repU;
//                return repU;
//            }
//        }
//    }
    public int counter(int v) {
        int counter = 1;
        for (int i = 0; i < uf.length; i += 1) {
            if (uf[i] == v && i != v) {
                counter += 1;
                counter += counter(i);
            }
        }
        return counter;
    }

    public void size(int v) {
        int intV = find(v);

    }

    // FIXME
    /** Union Find Structure. */
    int[] uf;
    int[] sizes;
}
