/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final int lineNum;
    private final LineSegment[] lineSegs;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        int num = 0;
        ArrayList<LineSegment> segs = new ArrayList<>();

        // check argument
        if (points == null)
            throw new IllegalArgumentException();
        Point[] copy = Arrays.copyOf(points, points.length);
        checkNullPoints(copy);
        Arrays.sort(copy);
        checkRepeatedPoints(copy);

        // brute force
        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++){
                for (int m = j + 1; m < copy.length; m++) {
                    for (int n = m + 1; n < copy.length; n++) {
                        double s1 = copy[i].slopeTo(copy[j]);
                        double s2 = copy[i].slopeTo(copy[m]);
                        double s3 = copy[i].slopeTo(copy[n]);
                        if (s1 == s2 && s1 == s3) {
                            num++;
                            segs.add(new LineSegment(copy[i], copy[n]));
                        }
                    }
                }
            }
        }
        lineNum = num;
        lineSegs = segs.toArray(new LineSegment[num]);
    }

    // check whether there is a null point
    private void checkNullPoints(Point[] points) {
        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException();
    }

    // check whether there are repeated points
    private void checkRepeatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineNum;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegs, lineNum);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
