import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class DutchNationalFlag {
    // use integer to represent color
    public static final int RED = 0;
    public static final int WHITE = 1;
    public static final int BLUE = 2;
    private static int[] a;

    public DutchNationalFlag(int[] in) {
        a = in;
    }

    // sort using 3-way partitioning quick sort
    private static void sort() {
        StdRandom.shuffle(a);
        int i = 0;
        int lt = 0, gt = a.length - 1;
        while (i <= gt) {
            int color_i = color(i);
            if (color_i < WHITE)
                swap(lt++, i++);
            else if (color_i > WHITE)
                swap(i, gt--);
            else
                i++;
        }
    }

    private static int color(int i) {
        return a[i];
    }

    private static void swap(int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void show() {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == RED)
                StdOut.println("RED");
            else if (a[i] == WHITE)
                StdOut.println("WHITE");
            else
                StdOut.println("BLUE");
        }
    }

    public static void main(String[] args) {
        int n = 12;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = StdRandom.uniform(3);
        DutchNationalFlag flag = new DutchNationalFlag(arr);
        flag.sort();
        flag.show();
    }
}
