import java.util.Random;
import java.util.Arrays;

public class DataGenerator {

    private static final Random RANDOM = new Random();

    /**
     * Generates an array of n random integers in the range [0, maxValue).
     * Values are NOT guaranteed to be unique (by design).
     */
    public static int[] generateUniqueRandomArray(int n, int maxValue) {

        if (n > maxValue) {
            throw new IllegalArgumentException(
                "Cannot generate " + n + " unique values in range 0.." + (maxValue - 1)
            );
        }

        int[] pool = new int[maxValue];
        for (int i = 0; i < maxValue; i++) {
            pool[i] = i;
        }

        for (int i = maxValue - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            int temp = pool[i];
            pool[i] = pool[j];
            pool[j] = temp;
        }

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = pool[i];
        }

        return result;
    }



    /** Returns a sorted copy of the given array (best-case for many sorts). */
    public static int[] createBestCase(int[] base) {
        int[] copy = base.clone();
        Arrays.sort(copy);
        return copy;
    }

    /** Returns a reverse-sorted copy of the given array (worst-case pattern). */
    public static int[] createWorstCase(int[] base) {
        int[] copy = base.clone();
        Arrays.sort(copy);
        // reverse in-place
        for (int i = 0; i < copy.length / 2; i++) {
            int tmp = copy[i];
            copy[i] = copy[copy.length - 1 - i];
            copy[copy.length - 1 - i] = tmp;
        }
        return copy;
    }
}

