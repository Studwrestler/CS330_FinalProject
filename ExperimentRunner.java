import java.util.Arrays;

/**
 * Entry point for the CS-330 final project experiments.
 * <p>
 * This class coordinates:
 * <ul>
 *     <li>The main empirical analysis for multiple array sizes</li>
 *     <li>Timing of the random data generator</li>
 *     <li>A small Part 2 demonstration on a 25-element array</li>
 * </ul>
 */
public class ExperimentRunner {

    /**
     * Main method. Runs the full experiment for several array sizes and then
     * executes a small demonstration on a 25-element array.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 50000};
        int runs = 10;
        int rangeMultiplier = 4;

        System.out.println("==============================================================");
        System.out.println("           CS-330 Sorting Algorithm Analysis");
        System.out.println("Algorithms: Selection Sort (O(n^2)) and Merge Sort (O(n log n))");
        System.out.println("Runs per average-case test: " + runs);
        System.out.println("==============================================================\n");

        // PART 1 — Main experiment
        for (int n : sizes) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Array Size: n = " + n);
            System.out.println("--------------------------------------------------------------");

            int maxValue = n * rangeMultiplier;

            // Measure data generation time for a single unique array of this size
            long genStart = System.nanoTime();
            int[] sample = DataGenerator.generateUniqueRandomArray(n, maxValue);
            long genEnd = System.nanoTime();
            long genTime = genEnd - genStart;

            System.out.println("Random Number Generator (unique values):");
            System.out.println("  Time to generate array of size " + n + ": " + formatTime(genTime));
            System.out.println("  Theoretical complexity: O(n)");
            System.out.println();

            // Analyze Selection Sort
            SelectionSortAnalyzer.analyze(n, runs, rangeMultiplier);
            System.out.println();

            // Analyze Merge Sort
            MergeSortAnalyzer.analyze(n, runs, rangeMultiplier);
            System.out.println();
        }

        // PART 2 — Small demonstration with 25 elements in [0, 99]
        runPartTwoDemo();

        System.out.println("==============================================================");
        System.out.println("                 Experiment Complete");
        System.out.println("==============================================================");
    }

    /**
     * Part 2 demonstration:
     * <ul>
     *     <li>Generates a unique random array of 25 integers in [0, 99]</li>
     *     <li>Displays the original array</li>
     *     <li>Sorts the array using Selection Sort and Merge Sort</li>
     *     <li>Prints the sorted arrays and the corresponding operation counts</li>
     *     <li>Reports the time taken by the generator</li>
     * </ul>
     */
    private static void runPartTwoDemo() {
        System.out.println();
        System.out.println("********************** PART 2 DEMO ***************************");
        System.out.println("Random UNIQUE array of 25 integers in [0, 99]");
        System.out.println("**************************************************************");

        int n = 25;
        int maxValue = 100; // values 0–99

        long genStart = System.nanoTime();
        int[] original = DataGenerator.generateUniqueRandomArray(n, maxValue);
        long genEnd = System.nanoTime();
        long genTime = genEnd - genStart;

        System.out.println("Original unique random array:");
        System.out.println("  " + Arrays.toString(original));
        System.out.println();

        int[] selectionArray = original.clone();
        int[] mergeArray = original.clone();

        long selOps = SelectionSortAnalyzer.demoSelectionSort(selectionArray);
        System.out.println("After Selection Sort:");
        System.out.println("  " + Arrays.toString(selectionArray));
        System.out.println("  Operations: " + selOps);
        System.out.println();

        long mergeOps = MergeSortAnalyzer.demoMergeSort(mergeArray);
        System.out.println("After Merge Sort:");
        System.out.println("  " + Arrays.toString(mergeArray));
        System.out.println("  Operations: " + mergeOps);
        System.out.println();

        System.out.println("Random number generator time for n = 25: " + formatTime(genTime));
        System.out.println("**************************************************************");
        System.out.println();
    }

    /**
     * Formats a duration given in nanoseconds into a human-readable string.
     *
     * @param nanos time duration in nanoseconds
     * @return formatted string with appropriate units
     */
    private static String formatTime(long nanos) {
        if (nanos < 1_000) return nanos + " ns";
        if (nanos < 1_000_000) return String.format("%.3f µs", nanos / 1_000.0);
        if (nanos < 1_000_000_000) return String.format("%.3f ms", nanos / 1_000_000.0);
        return String.format("%.3f s", nanos / 1_000_000_000.0);
    }
}
