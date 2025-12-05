public class MergeSortAnalyzer {

    public static void analyze(int n, int runs, int rangeMultiplier) {
        int maxValue = n * rangeMultiplier;

        long totalOps = 0;
        long totalTime = 0;

        // ---------------------------
        // Average case over 10 random runs
        // ---------------------------
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

        // ---------------------------
        // Best and Worst Cases
        // ---------------------------
        int[] base = DataGenerator.generateUniqueRandomArray(n, maxValue);

        // Best case
        int[] best = DataGenerator.createBestCase(base);
        long bestStart = System.nanoTime();
        long bestOps = mergeSort(best, 0, best.length - 1);
        long bestEnd = System.nanoTime();

        // Worst case
        int[] worst = DataGenerator.createWorstCase(base);
        long worstStart = System.nanoTime();
        long worstOps = mergeSort(worst, 0, worst.length - 1);
        long worstEnd = System.nanoTime();

        // ---------------------------
        // Output Results
        // ---------------------------
        System.out.println("Merge Sort (n = " + n + "):");
        System.out.printf("  Average case: ops = %d, time = %s%n",
                avgOps, formatTime(avgTime));
        System.out.printf("  Best case   : ops = %d, time = %s%n",
                bestOps, formatTime(bestEnd - bestStart));
        System.out.printf("  Worst case  : ops = %d, time = %s%n",
                worstOps, formatTime(worstEnd - worstStart));
    }

    // --------------------------------------------------
    //  Merge Sort Implementation (Based on GeeksforGeeks)
    //  with added operation counting.
    // --------------------------------------------------
    private static long mergeSort(int[] arr, int l, int r) {
        if (l >= r) return 0;

        long ops = 0;
        int m = l + (r - l) / 2;

        ops += mergeSort(arr, l, m);
        ops += mergeSort(arr, m + 1, r);
        ops += merge(arr, l, m, r);

        return ops;
    }

    private static long merge(int[] arr, int l, int m, int r) {
        long ops = 0;

        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy into temp arrays
        for (int i = 0; i < n1; i++) {
            L[i] = arr[l + i];
            ops++; // assignment
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[m + 1 + j];
            ops++; // assignment
        }

        int i = 0, j = 0, k = l;

        // Merge L[] and R[]
        while (i < n1 && j < n2) {
            ops++; // comparison L[i] <= R[j]
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            ops++; // assignment to arr[k]
            k++;
        }

        // Remaining L[]
        while (i < n1) {
            ops++; // comparison in loop
            arr[k] = L[i];
            ops++; // assignment
            i++;
            k++;
        }

        // Remaining R[]
        while (j < n2) {
            ops++; // comparison
            arr[k] = R[j];
            ops++; // assignment
            j++;
            k++;
        }

        return ops;
    }

    private static String formatTime(long nanos) {
        if (nanos < 1_000) return nanos + " ns";
        if (nanos < 1_000_000) return (nanos / 1_000.0) + " µs";
        if (nanos < 1_000_000_000) return (nanos / 1_000_000.0) + " ms";
        return (nanos / 1_000_000_000.0) + " s";
    }
    /**
     * Public helper for Part 2 demo:
     * sorts the given array in-place using Merge Sort
     * and returns the number of basic operations performed.
     */
    public static long demoMergeSort(int[] arr) {
        if (arr.length == 0) return 0;
        return mergeSort(arr, 0, arr.length - 1);
    }

}

