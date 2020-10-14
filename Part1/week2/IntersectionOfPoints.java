import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class IntersectionOfPoints {
    private ArrayList<Point> list;
    public IntersectionOfPoints(Point[] p1, Point[] p2) {
        list = new ArrayList<>();
        if (p1 == null || p2 == null) {
            return;
        }
        Arrays.sort(p1);
        Arrays.sort(p2);
        int index1 = 0, index2 = 0;
        while (index1 < p1.length && index2 < p2.length) {
            if (p1[index1].compareTo(p2[index2]) < 0)
                index1++;
            else if (p1[index1].compareTo(p2[index2]) > 0)
                index2++;
            else {
                list.add(p1[index1]);
                index1++;
                index2++;
            }
        }
    }

    public ArrayList<Point> get() {
        return list;
    }

    public static void main(String[] args) {
        Point[] p1 = new Point[2];
        Point[] p2 = new Point[3];
        p1[0] = new Point(0, 0);
        p1[1] = new Point(0, 1);
        p2[0] = new Point(0, 1);
        p2[1] = new Point(1, 2);
        p2[2] = new Point(0, 0);
        IntersectionOfPoints intersection = new IntersectionOfPoints(p1, p2);
        ArrayList<Point> points = intersection.get();
        for (Point point : points)
            StdOut.println(point.toString());
    }
}
