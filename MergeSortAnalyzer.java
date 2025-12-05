/**
 * Analyzer class for the Merge Sort algorithm.
 * <p>
 * This class:
 * <ul>
 *     <li>Runs Merge Sort on different array sizes</li>
 *     <li>Measures operation counts and wall-clock time</li>
 *     <li>Reports best, average, and worst-case behavior</li>
 * </ul>
 * It uses a classic array-based Merge Sort implementation with temporary
 * left/right subarrays for the merge step.
 */
public class MergeSortAnalyzer {

    /**
     * Executes an empirical analysis of Merge Sort for the given array size {@code n}.
     * <p>
     * For each size, this method:
     * <ol>
     *     <li>Runs Merge Sort {@code runs} times on unique random arrays
     *         (average-case measurement).</li>
     *     <li>Runs Merge Sort on a sorted array (best case).</li>
     *     <li>Runs Merge Sort on a reverse-sorted array (worst case).</li>
     * </ol>
     * It prints the number of operations and wall-clock time for each case.
     *
     * @param n               the array size to sort
     * @param runs            the number of random arrays for average-case testing
     * @param rangeMultiplier multiplier to determine maximum value
     *                        ({@code maxValue = n * rangeMultiplier})
     */
    public static void analyze(int n, int runs, int rangeMultiplier) {
        int maxValue = n * rangeMultiplier;

        long totalOps = 0;
        long totalTime = 0;

        // Average case: runs on random unique arrays
        for (int i = 0; i < runs; i++) {
            int[] arr = DataGenerator.generateUniqueRandomArray(n, maxValue);

            long start = System.nanoTime();
            long ops = mergeSort(arr, 0, arr.length - 1);
            long end = System.nanoTime();

            totalOps += ops;
            totalTime += (end - start);
        }

        long avgOps = totalOps / runs;
        long avgTime = totalTime / runs;

        // Base array for best- and worst-case construction
        int[] base = DataGenerator.generateUniqueRandomArray(n, maxValue);

        // Best case: sorted
        int[] best = DataGenerator.createBestCase(base);
        long bestStart = System.nanoTime();
        long bestOps = mergeSort(best, 0, best.length - 1);
        long bestEnd = System.nanoTime();

        // Worst case: reverse-sorted
        int[] worst = DataGenerator.createWorstCase(base);
        long worstStart = System.nanoTime();
        long worstOps = mergeSort(worst, 0, worst.length - 1);
        long worstEnd = System.nanoTime();

        System.out.println("Merge Sort (n = " + n + "):");
        System.out.printf("  Average case: ops = %d, time = %s%n",
                avgOps, formatTime(avgTime));
        System.out.printf("  Best case   : ops = %d, time = %s%n",
                bestOps, formatTime(bestEnd - bestStart));
        System.out.printf("  Worst case  : ops = %d, time = %s%n",
                worstOps, formatTime(worstEnd - worstStart));
    }

    /**
     * Recursive Merge Sort driver.
     * <p>
     * Sorts the portion of {@code arr} in the index range {@code [l, r]}
     * and accumulates a count of basic operations performed.
     *
     * @param arr the array to sort
     * @param l   the left index (inclusive)
     * @param r   the right index (inclusive)
     * @return the number of basic operations performed
     */
    private static long mergeSort(int[] arr, int l, int r) {
        if (l >= r) {
            return 0;
        }

        long ops = 0;
        int m = l + (r - l) / 2;

        ops += mergeSort(arr, l, m);
        ops += mergeSort(arr, m + 1, r);
        ops += merge(arr, l, m, r);

        return ops;
    }

    /**
     * Merges two sorted subarrays of {@code arr}:
     * <ul>
     *     <li>Left subarray:  {@code arr[l..m]}</li>
     *     <li>Right subarray: {@code arr[m+1..r]}</li>
     * </ul>
     * This method uses temporary arrays {@code L} and {@code R} and counts
     * both comparisons and assignments as basic operations.
     *
     * @param arr the original array containing two sorted halves
     * @param l   left index of the first subarray
     * @param m   ending index of the first subarray
     * @param r   ending index of the second subarray
     * @return the number of basic operations performed during the merge
     */
    private static long merge(int[] arr, int l, int m, int r) {
        long ops = 0;

        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy into L and R
        for (int i = 0; i < n1; i++) {
            L[i] = arr[l + i];
            ops++;
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[m + 1 + j];
            ops++;
        }

        int i = 0;
        int j = 0;
        int k = l;

        // Merge the two temporary arrays back into arr
        while (i < n1 && j < n2) {
            ops++; // comparison
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            ops++; // assignment
            k++;
        }

        // Copy any remaining elements of L[]
        while (i < n1) {
            ops++; // loop condition comparison
            arr[k] = L[i];
            ops++; // assignment
            i++;
            k++;
        }

        // Copy any remaining elements of R[]
        while (j < n2) {
            ops++; // loop condition comparison
            arr[k] = R[j];
            ops++; // assignment
            j++;
            k++;
        }

        return ops;
    }

    /**
     * Convenience method used by the Part 2 demo to run Merge Sort
     * on a given array and return the operation count.
     *
     * @param arr the array to sort in-place
     * @return the number of basic operations performed
     */
    public static long demoMergeSort(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        return mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * Utility to convert a time duration in nanoseconds to a human-readable string.
     *
     * @param nanos the time in nanoseconds
     * @return a formatted string with appropriate units (ns, µs, ms, or s)
     */
    private static String formatTime(long nanos) {
        if (nanos < 1_000) return nanos + " ns";
        if (nanos < 1_000_000) return String.format("%.3f µs", nanos / 1_000.0);
        if (nanos < 1_000_000_000) return String.format("%.3f ms", nanos / 1_000_000.0);
        return String.format("%.3f s", nanos / 1_000_000_000.0);
    }
}


