public class MergeSorter {

    private static final int CUTOFF = 16;

    private int[] buffer;
    public long comparisons = 0;
    public int maxDepth = 0;

    public void sort(int[] a) {
        comparisons = 0;
        maxDepth = 0;
        if (a.length < 2) {
            return;
        }
        buffer = new int[a.length];
        mergeSort(a, 0, a.length - 1, 1);
    }

    private void mergeSort(int[] a, int left, int right, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }

        // small part - use insertion sort
        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right);
            return;
        }

        int mid = (left + right) / 2;
        mergeSort(a, left, mid, depth + 1);
        mergeSort(a, mid + 1, right, depth + 1);
        merge(a, left, mid, right);
    }

    private void merge(int[] a, int left, int mid, int right) {
        // copy to buffer
        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            comparisons++;
            if (buffer[i] <= buffer[j]) {
                a[k] = buffer[i];
                i++;
            } else {
                a[k] = buffer[j];
                j++;
            }
            k++;
        }

        // rest of the left part
        while (i <= mid) {
            a[k] = buffer[i];
            i++;
            k++;
        }
        // rest of the right part is already on its place
    }

    private void insertionSort(int[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}
