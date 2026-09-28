import java.util.Arrays;

public class ClosestPairSolver {

    public long comparisons = 0;   // number of distance calculations
    public int maxDepth = 0;

    private Point[] buffer;
    private Point[] strip;

    // returns the smallest distance between two points
    // (if there are less than 2 points, returns infinity)
    public double solve(Point[] points) {
        comparisons = 0;
        maxDepth = 0;
        if (points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] p = points.clone();
        // sort by x
        Arrays.sort(p, (a, b) -> Double.compare(a.x, b.x));

        buffer = new Point[p.length];
        strip = new Point[p.length];
        return solve(p, 0, p.length - 1, 1);
    }

    private double solve(Point[] p, int left, int right, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }

        // small part - check all pairs
        if (right - left + 1 <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = left; i <= right; i++) {
                for (int j = i + 1; j <= right; j++) {
                    best = Math.min(best, distance(p[i], p[j]));
                }
            }
            sortByY(p, left, right);
            return best;
        }

        int mid = (left + right) / 2;
        double midX = p[mid].x;   // take it before the parts are sorted by y

        double dLeft = solve(p, left, mid, depth + 1);
        double dRight = solve(p, mid + 1, right, depth + 1);
        double d = Math.min(dLeft, dRight);

        // now p[left..right] is sorted by y (merge step)
        mergeByY(p, left, mid, right);

        // strip: points that are closer than d to the middle line
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(p[i].x - midX) < d) {
                strip[count] = p[i];
                count++;
            }
        }

        // check points in y order, only while y difference is less than d
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count && strip[j].y - strip[i].y < d; j++) {
                d = Math.min(d, distance(strip[i], strip[j]));
            }
        }

        return d;
    }

    private double distance(Point a, Point b) {
        comparisons++;
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void mergeByY(Point[] p, int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            buffer[i] = p[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (buffer[i].y <= buffer[j].y) {
                p[k] = buffer[i];
                i++;
            } else {
                p[k] = buffer[j];
                j++;
            }
            k++;
        }

        // rest of the left part
        while (i <= mid) {
            p[k] = buffer[i];
            i++;
            k++;
        }
        // rest of the right part is already on its place
    }

    private void sortByY(Point[] p, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Point key = p[i];
            int j = i - 1;
            while (j >= left && p[j].y > key.y) {
                p[j + 1] = p[j];
                j--;
            }
            p[j + 1] = key;
        }
    }

    // simple O(n^2) solution, used to check the fast one
    public static double bruteForce(Point[] points) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dx = points[i].x - points[j].x;
                double dy = points[i].y - points[j].y;
                best = Math.min(best, Math.sqrt(dx * dx + dy * dy));
            }
        }
        return best;
    }
}
