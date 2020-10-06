import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import static java.lang.Math.*;

public class Point2D implements Comparable<Point2D> {
    private final double x;
    private final double y;
    private final static double ERROR = 1e-6;

    // initialize the point
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // check whether a->b-> is a counterclockwise turn
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0)   // clockwise;
            return -1;
        else if (area2 > 0)  // counter-clockwise
            return 1;
        else   // collinear
            return 0;
    }

    // compare two points by y-coordinate, breaking tie by x-coordinate
    public int compareTo(Point2D that) {
        double y_error = that.y - y;
        if (y_error > ERROR)
            return -1;
        else if (y_error < -ERROR)
            return 1;
        else {  // same y-coordinates
            double x_error = that.x - x;
            if (x_error > ERROR)
                return -1;
            else if (x_error < -ERROR)
                return 1;
            else
                return 0;
        }
    }

    // Compute the polar angle a point make with this point
    public double polarAngleTo(Point2D that) {
        double x_error = abs(that.x - x);
        double y_error = abs(that.y - y);
        if (x_error < ERROR && y_error < ERROR) // same point
            return Double.NEGATIVE_INFINITY;
        else if (x_error < ERROR)   // vertical
            return 90;
        else  // normal
            return atan((that.y - y) / (that.x - x)) * 180 / PI;
    }

    // print pretty
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    // Compare two points by the slope they make with this point
    public Comparator<Point2D> polarAngleOrder() {
        return new PolarAngleOrder();
    }

    private class PolarAngleOrder implements Comparator<Point2D> {
        public int compare(Point2D p1, Point2D p2) {
            double angle1 = polarAngleTo(p1);
            double angle2 = polarAngleTo(p2);
            double angle_error = angle1 - angle2;
            if (angle_error > ERROR)
                return 1;
            else if (angle_error < -ERROR)
                return -1;
            else
                return 0;
        }
    }

    public static void main(String[] args) {
        Point2D p0 = new Point2D(0, 0);
        Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(2, 2);
        Point2D p3 = new Point2D(1, 2);
        Point2D[] points = {p0, p1, p2, p3};

        StdOut.println(p0.toString());
        StdOut.println(p1.toString());
        StdOut.println(p2.toString());

        StdOut.println(Point2D.ccw(p0, p1, p2) == 0);
        StdOut.println(Point2D.ccw(p0, p1, p3) > 0);

        StdOut.println(p0.polarAngleTo(p1) == p0.polarAngleTo(p2));
        StdOut.println(p0.polarAngleTo(p3) > p0.polarAngleTo(p1));

        StdOut.println(p1.compareTo(p3) < 0);
        StdOut.println(p2.compareTo(p3) > 0);

        Arrays.sort(points, p0.polarAngleOrder());
        for (int i = 0; i < points.length; i++)
            StdOut.println(points[i].toString());
    }
}
