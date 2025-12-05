import java.util.Random;
import java.util.Arrays;

/**
 * Utility class responsible for generating test data for the sorting experiments.
 * <p>
 * This class provides methods to generate:
 * <ul>
 *     <li>Unique random integer arrays (using Fisher–Yates shuffle)</li>
 *     <li>Best-case arrays (sorted ascending)</li>
 *     <li>Worst-case arrays (sorted descending)</li>
 * </ul>
 * All methods are static so the class can be used without instantiation.
 */
public class DataGenerator {

    /** Shared random number generator used by all methods. */
    private static final Random RANDOM = new Random();

    /**
     * Generates an array of {@code n} <b>unique</b> random integers
     * in the range {@code [0, maxValue)} using a Fisher–Yates shuffle.
     * <p>
     * The method first creates a pool of values {@code 0, 1, ..., maxValue-1},
     * randomly shuffles them in-place, and then copies the first {@code n}
     * values into the result array. This guarantees that all values in the
     * result are unique.
     *
     * @param n        the number of integers to generate
     * @param maxValue the upper bound (exclusive) for generated values;
     *                 must satisfy {@code n <= maxValue}
     * @return an array of length {@code n} containing unique random integers
     *         in the range {@code [0, maxValue)}
     * @throws IllegalArgumentException if {@code n > maxValue}
     */
    public static int[] generateUniqueRandomArray(int n, int maxValue) {
        if (n > maxValue) {
            throw new IllegalArgumentException(
                "Cannot generate " + n + " unique values in range 0.." + (maxValue - 1)
            );
        }

        // pool = [0, 1, 2, ..., maxValue-1]
        int[] pool = new int[maxValue];
        for (int i = 0; i < maxValue; i++) {
            pool[i] = i;
        }

        // Fisher–Yates shuffle on pool
        for (int i = maxValue - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1); // random index [0, i]
            int temp = pool[i];
            pool[i] = pool[j];
            pool[j] = temp;
        }

        // Take first n-shuffled values
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = pool[i];
        }

        return result;
    }

    /**
     * Produces a best-case input for many comparison-based sorts by returning
     * a sorted copy of the given array.
     *
     * @param base the original array (not modified)
     * @return a new array containing the elements of {@code base} sorted
     *         in non-decreasing order
     */
    public static int[] createBestCase(int[] base) {
        int[] copy = base.clone();
        Arrays.sort(copy);
        return copy;
    }

    /**
     * Produces a worst-case input for many comparison-based sorts by returning
     * a reverse-sorted copy of the given array.
     *
     * @param base the original array (not modified)
     * @return a new array containing the elements of {@code base} sorted
     *         in non-increasing (descending) order
     */
    public static int[] createWorstCase(int[] base) {
        int[] copy = base.clone();
        Arrays.sort(copy);

        // Reverse in-place
        for (int i = 0; i < copy.length / 2; i++) {
            int temp = copy[i];
            copy[i] = copy[copy.length - 1 - i];
            copy[copy.length - 1 - i] = temp;
        }
        return copy;
    }
}

