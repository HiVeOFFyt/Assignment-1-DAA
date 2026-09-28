import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosestPairSolverTest {

    // compare the fast solution with brute force
    private void check(Point[] points) {
        double expected = ClosestPairSolver.bruteForce(points);
        double result = new ClosestPairSolver().solve(points);
        assertEquals(expected, result, 1e-9);
    }

    @Test
    void oneAndTwoPoints() {
        check(new Point[]{new Point(1, 1)});
        check(new Point[]{new Point(0, 0), new Point(3, 4)});
    }

    @Test
    void randomTests() {
        Random rnd = new Random(1);
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(1999) + 2;
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }
            check(points);
        }
    }

    @Test
    void duplicatePoints() {
        Random rnd = new Random(2);
        for (int t = 0; t < 50; t++) {
            int n = rnd.nextInt(500) + 2;
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point(rnd.nextInt(10), rnd.nextInt(10));
            }
            check(points);
        }
    }

    @Test
    void samePointTwice() {
        Point[] points = {new Point(5, 5), new Point(1, 2), new Point(5, 5), new Point(9, 9)};
        assertEquals(0.0, new ClosestPairSolver().solve(points), 1e-9);
    }

    @Test
    void sameX() {
        Random rnd = new Random(3);
        Point[] points = new Point[300];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(7, rnd.nextDouble() * 1000);
        }
        check(points);
    }
}
