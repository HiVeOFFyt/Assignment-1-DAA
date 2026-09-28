import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        int[] original = {5, 2, 9, 1, 5, 6, 0, 3, 8, 7, 4, 11, 15, 13, 12, 14, 10, 20, 18, 19, 17, 16};

        // MergeSort
        int[] a = original.clone();
        MergeSorter mergeSorter = new MergeSorter();
        mergeSorter.sort(a);
        System.out.println("MergeSort: " + Arrays.toString(a));
        System.out.println("comparisons = " + mergeSorter.comparisons + ", max depth = " + mergeSorter.maxDepth);

        // QuickSort
        int[] b = original.clone();
        QuickSorter quickSorter = new QuickSorter();
        quickSorter.sort(b);
        System.out.println("QuickSort: " + Arrays.toString(b));
        System.out.println("comparisons = " + quickSorter.comparisons + ", max depth = " + quickSorter.maxDepth);

        // Deterministic Select
        int k = 10;
        int[] c = original.clone();
        DeterministicSelector selector = new DeterministicSelector();
        int result = selector.select(c, k);
        System.out.println("Select k = " + k + ": " + result);
        System.out.println("comparisons = " + selector.comparisons + ", max depth = " + selector.maxDepth);

        // Closest Pair
        Point[] points = {
                new Point(2, 3), new Point(12, 30), new Point(40, 50),
                new Point(5, 1), new Point(12, 10), new Point(3, 4)
        };
        ClosestPairSolver solver = new ClosestPairSolver();
        double distance = solver.solve(points);
        System.out.println("Closest pair distance: " + distance);
        System.out.println("distance calculations = " + solver.comparisons + ", max depth = " + solver.maxDepth);

        // Experiments (writes results/results.csv)
        System.out.println();
        System.out.println("Running experiments...");
        Experiment experiment = new Experiment();
        experiment.runAll("results/results.csv");
    }
}
