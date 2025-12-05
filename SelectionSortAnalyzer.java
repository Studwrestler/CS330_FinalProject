public class SelectionSortAnalyzer {

    /**
     * Runs Selection Sort on:
     *  - multiple random arrays (average case),
     *  - one sorted array (best case),
     *  - one reverse-sorted array (worst case),
     * and prints operations and wall time.
     */
    public static void analyze(int n, int runs, int rangeMultiplier) {
        int maxValue = n * rangeMultiplier;

        long totalOps = 0;
        long totalTime = 0;

        // Average case: run on 'runs' random arrays
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

        // Use a base random array to derive best & worst cases
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
     * Standard Selection Sort.
     * Returns: total basic operations = comparisons + assignments (for swaps).
     */
    private static long selectionSort(int[] arr) {
        long comparisons = 0;
        long assignments = 0;

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Find index of minimum in the unsorted part
            for (int j = i + 1; j < n; j++) {
                comparisons++; // arr[j] < arr[minIndex]
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap into position i if needed
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                assignments += 3; // three writes
            }
        }

        return comparisons + assignments;
    }
    private static String formatTime(long nanos) {
        if (nanos < 1_000) return nanos + " ns";
        if (nanos < 1_000_000) return (nanos / 1_000.0) + " µs";
        if (nanos < 1_000_000_000) return (nanos / 1_000_000.0) + " ms";
        return (nanos / 1_000_000_000.0) + " s";
    }
    public static long demoSelectionSort(int[] arr) {
        return selectionSort(arr);  // calls the existing private method
    }
}
