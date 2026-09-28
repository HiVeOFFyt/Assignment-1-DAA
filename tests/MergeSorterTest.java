import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSorterTest {

    // sort with MergeSorter and compare with Arrays.sort
    private void check(int[] a) {
        int[] expected = a.clone();
        Arrays.sort(expected);
        new MergeSorter().sort(a);
        assertArrayEquals(expected, a);
    }

    @Test
    void emptyArray() {
        check(new int[0]);
    }

    @Test
    void oneElement() {
        check(new int[]{42});
    }

    @Test
    void randomArrays() {
        Random rnd = new Random(1);
        int[] sizes = {2, 5, 16, 17, 100, 1000, 10000};
        for (int n : sizes) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt();
            }
            check(a);
        }
    }

    @Test
    void sortedArray() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        check(a);
    }

    @Test
    void reverseSortedArray() {
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
        check(a);
    }

    @Test
    void duplicates() {
        Random rnd = new Random(2);
        int[] a = new int[5000];
        for (int i = 0; i < a.length; i++) {
            a[i] = rnd.nextInt(5);
        }
        check(a);
    }
}
