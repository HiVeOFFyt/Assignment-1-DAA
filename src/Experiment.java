import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {

    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final String[] TYPES = {"random", "sorted", "reverse", "duplicates"};
    private static final int RUNS = 5;

    private Random random = new Random(42);
    private FileWriter writer;

    public void runAll(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        writer = new FileWriter(file);
        writer.write("algorithm,inputType,n,timeMs,maxDepth,comparisons\n");

        for (int n : SIZES) {
            for (String type : TYPES) {
                System.out.println("n = " + n + ", input = " + type);
                int[] data = makeArray(n, type);
                runMergeSort(data, type);
                runQuickSort(data, type);
                runSelect(data, type);
                runClosestPair(n, type);
            }
        }

        writer.close();
        System.out.println("Results saved to " + fileName);
    }

    private int[] makeArray(int n, String type) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (type.equals("random")) {
                a[i] = random.nextInt();
            } else if (type.equals("sorted")) {
                a[i] = i;
            } else if (type.equals("reverse")) {
                a[i] = n - i;
            } else {
                a[i] = random.nextInt(10); // many duplicates
            }
        }
        return a;
    }

    private Point[] makePoints(int n, String type) {
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            double x;
            double y;
            if (type.equals("random")) {
                x = random.nextDouble() * 1000000;
                y = random.nextDouble() * 1000000;
            } else if (type.equals("sorted")) {
                x = i;
                y = random.nextDouble() * 1000000;
            } else if (type.equals("reverse")) {
                x = n - i;
                y = random.nextDouble() * 1000000;
            } else {
                x = random.nextInt(10);
                y = random.nextInt(10);
            }
            points[i] = new Point(x, y);
        }
        return points;
    }

    private void runMergeSort(int[] data, String type) throws IOException {
        MergeSorter sorter = new MergeSorter();
        sorter.sort(data.clone()); // warm up

        long total = 0;
        for (int r = 0; r < RUNS; r++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            sorter.sort(copy);
            total += System.nanoTime() - start;
        }
        save("MergeSort", type, data.length, total, sorter.maxDepth, sorter.comparisons);
    }

    private void runQuickSort(int[] data, String type) throws IOException {
        QuickSorter sorter = new QuickSorter();
        sorter.sort(data.clone()); // warm up

        long total = 0;
        for (int r = 0; r < RUNS; r++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            sorter.sort(copy);
            total += System.nanoTime() - start;
        }
        save("QuickSort", type, data.length, total, sorter.maxDepth, sorter.comparisons);
    }

    private void runSelect(int[] data, String type) throws IOException {
        DeterministicSelector selector = new DeterministicSelector();
        int k = data.length / 2; // median
        selector.select(data.clone(), k); // warm up

        long total = 0;
        for (int r = 0; r < RUNS; r++) {
            int[] copy = data.clone();
            long start = System.nanoTime();
            selector.select(copy, k);
            total += System.nanoTime() - start;
        }
        save("Select", type, data.length, total, selector.maxDepth, selector.comparisons);
    }

    private void runClosestPair(int n, String type) throws IOException {
        Point[] points = makePoints(n, type);
        ClosestPairSolver solver = new ClosestPairSolver();
        solver.solve(points); // warm up

        long total = 0;
        for (int r = 0; r < RUNS; r++) {
            long start = System.nanoTime();
            solver.solve(points);
            total += System.nanoTime() - start;
        }
        // for closest pair "comparisons" = number of distance calculations
        save("ClosestPair", type, n, total, solver.maxDepth, solver.comparisons);
    }

    private void save(String algorithm, String type, int n, long totalNanos,
                      int maxDepth, long comparisons) throws IOException {
        // average time of one run in milliseconds (3 digits after the dot)
        double timeMs = Math.round(totalNanos / RUNS / 1000.0) / 1000.0;
        writer.write(algorithm + "," + type + "," + n + "," + timeMs + ","
                + maxDepth + "," + comparisons + "\n");
    }
}
