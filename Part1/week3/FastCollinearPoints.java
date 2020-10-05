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

public class FastCollinearPoints {
    private final int lineNum;
    private final LineSegment[] lineSegs;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        int num = 0;
        ArrayList<LineSegment> segs = new ArrayList<>();

        // check argument
        if (points == null)
            throw new IllegalArgumentException();
        Point[] copy = Arrays.copyOf(points, points.length);
        Point[] temp = Arrays.copyOf(points, points.length);
        checkNullPoints(copy);
        Arrays.sort(copy);
        checkRepeatedPoints(copy);

        // traverse the points array and sort it by slopeOrder each time
        for (int i = 0; i < copy.length; i++) {
            Arrays.sort(temp, copy[i].slopeOrder());
            Point origin = temp[0];
            ArrayList<Point> linkedLine = new ArrayList<>();
            for (int j = 1; j < copy.length - 1; j++) {
                int count = 1;
                linkedLine.clear();
                linkedLine.add(origin);
                while (j + 1 < copy.length && origin.slopeTo(temp[j]) == origin.slopeTo(temp[j+1])) {
                    count++;
                    linkedLine.add(temp[j]);
                    j++;
                }
                linkedLine.add(temp[j]);
                if (count >= 3) {  // at least 4 points in a line
                    Point[] arrayLine = linkedLine.toArray(new Point[count + 1]);
                    Arrays.sort(arrayLine); // sort to find the min point
                    // if the min point is exactly the origin, then this line is not duplicate
                    if (arrayLine[0].compareTo(origin) == 0) {
                        num++;
                        segs.add(new LineSegment(arrayLine[0], arrayLine[arrayLine.length - 1]));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}