import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class ConvexHull {
    private final Point2D[] points;
    private final Stack<Point2D> stack;

    public ConvexHull(Point2D[] points) {
        if (points.length <= 2)
            throw new IllegalArgumentException("the array of points is too short.");
        this.points = Arrays.copyOf(points, points.length);
        stack = new Stack<>();
    }

    public boolean isFound() {
        Arrays.sort(points);   // sort the points by y-coordinate
        Point2D a = points[0]; // the smallest point with y-coordinate
        Arrays.sort(points, a.polarAngleOrder());  // sort points by polar angle with a
        Point2D b = points[1];
        stack.push(a);
        stack.push(b);
        int c_index = 2;
        // consider points in order; discard unless it create a ccw turn
        while (c_index < points.length) {
            Point2D c = points[c_index];
            if (Point2D.ccw(a, b, c) > 0) {
                stack.push(c);
                a = b;
                b = c;
                c_index++;
            } else {
                stack.pop();
                b = stack.pop();
                if (stack.isEmpty())
                    return false;
                a = stack.peek();
                stack.push(b);
            }
        }
        return true;
    }

    // print the convex hull
    public void show() {
        for (Point2D point : stack)
            StdOut.println(point.toString());
    }

    public static void main(String[] args) {
        // read points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point2D(x, y);
        }
        ConvexHull ch = new ConvexHull(points);
        if (ch.isFound()) {
            ch.show();
        } else {
            StdOut.println("not found");
        }
    }
}
