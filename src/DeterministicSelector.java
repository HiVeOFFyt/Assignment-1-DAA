public class DeterministicSelector {

    public long comparisons = 0;
    public int maxDepth = 0;

    // returns the k-th smallest element (k starts from 0)
    // note: the array is changed (partitioned in place)
    public int select(int[] a, int k) {
        comparisons = 0;
        maxDepth = 0;
        if (a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("empty array or wrong k");
        }
        return select(a, 0, a.length - 1, k, 1);
    }

    private int select(int[] a, int left, int right, int k, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }

        // small part - just sort it
        if (right - left + 1 <= 5) {
            insertionSort(a, left, right);
            return a[k];
        }

        int pivot = medianOfMedians(a, left, right, depth);

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

        // go only to the part where k is
        if (k < lt) {
            return select(a, left, lt - 1, k, depth + 1);
        } else if (k <= gt) {
            return a[k];
        } else {
            return select(a, gt + 1, right, k, depth + 1);
        }
    }

    private int medianOfMedians(int[] a, int left, int right, int depth) {
        int n = right - left + 1;
        int groups = (n + 4) / 5;

        for (int g = 0; g < groups; g++) {
            int groupLeft = left + g * 5;
            int groupRight = groupLeft + 4;
            if (groupRight > right) {
                groupRight = right;
            }
            insertionSort(a, groupLeft, groupRight);
            int median = groupLeft + (groupRight - groupLeft) / 2;
            // move the median of this group to the beginning
            swap(a, left + g, median);
        }

        // medians are now in a[left .. left + groups - 1]
        // find the median of them (recursive call)
        int mid = left + (groups - 1) / 2;
        return select(a, left, left + groups - 1, mid, depth + 1);
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

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
