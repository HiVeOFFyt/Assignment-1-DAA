import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] original = {5, 2, 9, 1, 5, 6, 0, 3, 8, 7, 4, 11, 15, 13, 12, 14, 10, 20, 18, 19, 17, 16};

        int[] a = original.clone();
        MergeSorter mergeSorter = new MergeSorter();
        mergeSorter.sort(a);
        System.out.println("MergeSort: " + Arrays.toString(a));
        System.out.println("comparisons = " + mergeSorter.comparisons + ", max depth = " + mergeSorter.maxDepth);

        int[] b = original.clone();
        QuickSorter quickSorter = new QuickSorter();
        quickSorter.sort(b);
        System.out.println("QuickSort: " + Arrays.toString(b));
        System.out.println("comparisons = " + quickSorter.comparisons + ", max depth = " + quickSorter.maxDepth);
    }
}
