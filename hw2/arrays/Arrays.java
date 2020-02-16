package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        /* *Replace this body with the solution. */
        int lengthA = A.length;
        int lengthB = B.length;
        int[] combined = new int[lengthA+lengthB];

        for (int i = 0; i < lengthA; i += 1) {
            combined[i] = A[i];
        }
        for (int i = 0; i < lengthB; i += 1) {
            combined[i + lengthA] = B[i];
        }
        return combined;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        /* *Replace this body with the solution. */
        if (A.length == 0){
            return A;
        } else {
            int[] removed = new int[A.length - len];
            System.arraycopy(A, 0, removed, 0, start);
            System.arraycopy(A, start + len, removed, start, A.length - (len + start));
            return removed;
        }
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */
        return null;
    }
}
