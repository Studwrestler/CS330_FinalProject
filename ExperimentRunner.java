import java.util.Arrays;

public class ExperimentRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 50000};
        int runs = 10;
        int rangeMultiplier = 4;

        System.out.println("==============================================================");
        System.out.println("           CS-330 Sorting Algorithm Analysis");
        System.out.println("Algorithms: UnqiueNumberGenerator (O(n)) Selection Sort (O(n^2)) and Merge Sort (O(n log n))");
        System.out.println("Runs per average-case test: " + runs);
        System.out.println("==============================================================\n");

        runPartTwoDemo();
        
        for (int n : sizes) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Array Size: n = " + n);
            System.out.println("--------------------------------------------------------------");

            long genStart = System.nanoTime();
            int[] sample = DataGenerator.generateUniqueRandomArray(n, n * rangeMultiplier);
            long genEnd = System.nanoTime();
            long genTime = genEnd - genStart;

            System.out.println("Random Number Generator:");
            System.out.println("  Time to generate array of size " + n + ": " + formatTime(genTime));
            
            // Run Selection Sort analysis
            SelectionSortAnalyzer.analyze(n, runs, rangeMultiplier);
            System.out.println(); // spacing

            // Run Merge Sort analysis
            MergeSortAnalyzer.analyze(n, runs, rangeMultiplier);
            System.out.println(); // spacing
        }

        System.out.println("==============================================================");
        System.out.println("                 Experiment Complete");
        System.out.println("==============================================================");
    }
    private static void runPartTwoDemo() {
        System.out.println();
        System.out.println("********************** PART 2 DEMO ***************************");
        System.out.println("Random array of 25 integers in [0, 99], then Selection Sort and Merge Sort");
        System.out.println("**************************************************************");

        int n = 25;
        int maxValue = 100; // numbers between 0 and 99

        // Measure efficiency (time) of the random number generator
        long genStart = System.nanoTime();
        int[] original = DataGenerator.generateUniqueRandomArray(n, maxValue);
        long genEnd = System.nanoTime();
        long genTime = genEnd - genStart;

        System.out.println("Original random array (n = 25, values 0–99):");
        System.out.println("  " + Arrays.toString(original));
        System.out.println();

        // Make copies so we can sort independently
        int[] selectionArray = original.clone();
        int[] mergeArray = original.clone();

        // Selection Sort demo
        long selOps = SelectionSortAnalyzer.demoSelectionSort(selectionArray);
        System.out.println("After Selection Sort:");
        System.out.println("  " + Arrays.toString(selectionArray));
        System.out.println("  Selection Sort operations (comparisons + assignments): " + selOps);
        System.out.println();

        // Merge Sort demo
        long mergeOps = MergeSortAnalyzer.demoMergeSort(mergeArray);
        System.out.println("After Merge Sort:");
        System.out.println("  " + Arrays.toString(mergeArray));
        System.out.println("  Merge Sort operations (comparisons + assignments): " + mergeOps);
        System.out.println();

        // Show random generator efficiency
        System.out.println("Random number generator efficiency (n = 25):");
        System.out.println("  Wall time: " + formatTime(genTime));
        System.out.println("**************************************************************");
        System.out.println();
    }

    /** Local helper just for printing time in a readable way for Part 2. */
    private static String formatTime(long nanos) {
        if (nanos < 1_000) {
            return nanos + " ns";
        } else if (nanos < 1_000_000) {
            double micros = nanos / 1_000.0;
            return String.format("%.3f µs", micros);
        } else if (nanos < 1_000_000_000) {
            double millis = nanos / 1_000_000.0;
            return String.format("%.3f ms", millis);
        } else {
            double seconds = nanos / 1_000_000_000.0;
            return String.format("%.3f s", seconds);
        }
    }
}
