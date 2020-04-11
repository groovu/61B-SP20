import java.util.Arrays;

/**
 * Note that every sorting algorithm takes in an argument k. The sorting 
 * algorithm should sort the array from index 0 to k. This argument could
 * be useful for some of your sorts.
 *
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Counting Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 *
 * References: GeeksForGeeks/StackOverflow.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int temp, next;
            for (int i = 0; i < k; i += 1) {
                next = i;
                while (next > 0 && array[next-1] > array[next]) {
                    temp = array[next-1];
                    array[next-1] = array[next];
                    array[next] = temp;
                    next -= 1;
                    if (next < 0) {
                        break;
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int min;
            for (int i = 0; i < k; i += 1) {
                min = i;
                for (int j = i; j < k; j += 1) {
                    if (array[j] < array[min]) {
                        min = j;
                    }
                }
                if (min != i) {
                    int temp = array[i];
                    array[i] = array[min];
                    array[min] = temp;
                }
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
            sort2(array, 0, k-1);

        }
        public void sort2(int[] array, int l, int r) {
            if (l < r) {
                int m = (l + r) / 2;
                sort2(array, l, m);
                sort2(array, m + 1, r);
                merge(array, l, m, r);
            }
        }
        public void merge(int[] array, int l, int m, int r) {
            int[] left = new int[m-l+1];
            for (int i = 0; i < m-l+1; i += 1) {
                left[i] = array[l+i];
            }
            int[] right = new int[r-m];
            for (int i = 0; i < r - m; i += 1) {
                right[i] = array[m + 1 + i];
            }
            int i = 0;
            int j = 0;
            int k = l;
            while (i < m - l + 1 && j < r - m) {
                if (left[i] <= right[j]) {
                    array[k] = left[i];
                    i += 1;
                } else {
                    array[k] = right[j];
                    j += 1;
                }
                k += 1;
            }
            while (i < m - l + 1) {
                array[k] = left[i];
                i += 1;
                k += 1;
            }
            while (j < r - m) {
                array[k] = right[j];
                j += 1;
                k += 1;
            }
        }
        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Counting Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class CountingSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Counting Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
            int n = a.length;
            int R = 256;
            for (int i = k - 1; i < n; i += 1) {

            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
