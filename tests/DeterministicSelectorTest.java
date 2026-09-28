import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeterministicSelectorTest {

    // compare select with Arrays.sort(a)[k]
    private void check(int[] a, int k) {
        int[] sorted = a.clone();
        Arrays.sort(sorted);
        int result = new DeterministicSelector().select(a, k);
        assertEquals(sorted[k], result);
    }

    @Test
    void oneElement() {
        check(new int[]{42}, 0);
    }

    @Test
    void randomTests() {
        Random rnd = new Random(1);
        for (int t = 0; t < 200; t++) {
            int n = rnd.nextInt(500) + 1;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(2000) - 1000;
            }
            check(a, rnd.nextInt(n));
        }
    }

    @Test
    void duplicates() {
        Random rnd = new Random(2);
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(500) + 1;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(5);
            }
            check(a, rnd.nextInt(n));
        }
    }

    @Test
    void sortedAndReverse() {
        int n = 1000;
        int[] sorted = new int[n];
        int[] reverse = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = i;
            reverse[i] = n - i;
        }
        check(sorted, 0);
        check(sorted, 500);
        check(sorted, n - 1);
        check(reverse, 0);
        check(reverse, 500);
        check(reverse, n - 1);
    }
}
