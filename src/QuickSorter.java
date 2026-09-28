import java.util.Random;

public class QuickSorter {

    private Random random = new Random();
    public long comparisons = 0;
    public int maxDepth = 0;

    public void sort(int[] a) {
        comparisons = 0;
        maxDepth = 0;
        if (a.length < 2) {
            return;
        }
        quickSort(a, 0, a.length - 1, 1);
    }

    private void quickSort(int[] a, int left, int right, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }

        while (left < right) {
            // random pivot
            int pivot = a[left + random.nextInt(right - left + 1)];

            // partition in place into 3 parts: < pivot, == pivot, > pivot
            int lt = left;
            int i = left;
            int gt = right;
            while (i <= gt) {
                comparisons++;
                if (a[i] < pivot) {
                    swap(a, lt, i);
                    lt++;
                    i++;
                } else if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }

            // recursion for the smaller part, loop for the bigger part
            if (lt - left < right - gt) {
                quickSort(a, left, lt - 1, depth + 1);
                left = gt + 1;
            } else {
                quickSort(a, gt + 1, right, depth + 1);
                right = lt - 1;
            }
        }
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
