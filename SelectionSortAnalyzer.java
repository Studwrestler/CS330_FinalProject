/**
 * Analyzer class for the Selection Sort algorithm.
 * <p>
 * This class is responsible for:
 * <ul>
 *     <li>Running Selection Sort on different input sizes</li>
 *     <li>Measuring basic operation counts (comparisons + assignments)</li>
 *     <li>Measuring wall-clock time using {@code System.nanoTime()}</li>
 *     <li>Printing best, average, and worst-case results</li>
 * </ul>
 * It uses {@link DataGenerator} to generate test arrays.
 */
public class SelectionSortAnalyzer {

    /**
     * Executes an empirical study of Selection Sort for a single array size {@code n}.
     * <p>
     * For the given size, this method:
     * <ol>
     *     <li>Runs Selection Sort {@code runs} times on unique random arrays
     *         (average-case measurement).</li>
     *     <li>Runs Selection Sort on a pre-sorted array (best case).</li>
     *     <li>Runs Selection Sort on a reverse-sorted array (worst case).</li>
     * </ol>
     * It prints the number of operations and the wall-clock time for each case.
     *
     * @param n              the size of arrays to sort
     * @param runs           the number of random arrays to use for average-case
     * @param rangeMultiplier the multiplier used to determine the value range
     *                        ({@code maxValue = n * rangeMultiplier})
     */
    public static void analyze(int n, int runs, int rangeMultiplier) {
        int maxValue = n * rangeMultiplier;

        long totalOps = 0;
        long totalTime = 0;

        // Average case: run on 'runs' unique random arrays
        for (int i = 0; i < runs; i++) {
            int[] arr = DataGenerator.generateUniqueRandomArray(n, maxValue);

            long start = System.nanoTime();
            long ops = selectionSort(arr);
            long end = System.nanoTime();

            totalOps += ops;
            totalTime += (end - start);
        }

        long avgOps = totalOps / runs;
        long avgTime = totalTime / runs;

        // Base array for best- and worst-case construction
        int[] base = DataGenerator.generateUniqueRandomArray(n, maxValue);

        // Best case: already sorted
        int[] best = DataGenerator.createBestCase(base);
        long bestStart = System.nanoTime();
        long bestOps = selectionSort(best);
        long bestEnd = System.nanoTime();

        // Worst case: reverse-sorted
        int[] worst = DataGenerator.createWorstCase(base);
        long worstStart = System.nanoTime();
        long worstOps = selectionSort(worst);
        long worstEnd = System.nanoTime();

        System.out.println("Selection Sort (n = " + n + "):");
        System.out.printf("  Average case: ops = %d, time = %s%n",
                avgOps, formatTime(avgTime));
        System.out.printf("  Best case   : ops = %d, time = %s%n",
                bestOps, formatTime(bestEnd - bestStart));
        System.out.printf("  Worst case  : ops = %d, time = %s%n",
                worstOps, formatTime(worstEnd - worstStart));
    }

    /**
     * Implements the Selection Sort algorithm while counting basic operations.
     * <p>
     * The operation count is defined as:
     * <ul>
     *     <li>One operation per comparison {@code arr[j] < arr[minIndex]}</li>
     *     <li>Three operations per swap (three assignments)</li>
     * </ul>
     *
     * @param arr the array to be sorted in-place
     * @return the total number of basic operations performed
     */
    private static long selectionSort(int[] arr) {
        long comparisons = 0;
        long assignments = 0;

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Find index of minimum element in the unsorted portion
            for (int j = i + 1; j < n; j++) {
                comparisons++; // arr[j] < arr[minIndex]
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap minimum into correct position
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                assignments += 3;
            }
        }

        return comparisons + assignments;
    }

    /**
     * Convenience method used by the Part 2 demo to run Selection Sort
     * on a given array and return the operation count.
     *
     * @param arr the array to sort in-place
     * @return the number of basic operations performed
     */
    public static long demoSelectionSort(int[] arr) {
        return selectionSort(arr);
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

