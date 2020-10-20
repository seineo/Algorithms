import edu.princeton.cs.algs4.StdOut;

// find dominant elements using three-way partitioning quick sort
public class ThreeWayQSDecimalDominant {
    public static void findDominant(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int lt = lo, gt = hi;
        Comparable pivot = a[lo];
        int i = lo;
        while (i <= gt) {
            if (a[i].compareTo(pivot) < 0) {
                exch(a, lt++, i++);
            } else if (a[i].compareTo(pivot) > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        // check whether pivot is dominant (appear more than n/10 times
        if (gt - lt + 1 > a.length / 10)
            StdOut.println(pivot);
        // recur in subproblems
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        /* n = 10, n / 10 = 1, so find the elements appear more than once
           the answer is 2, 4 and 6 */
        Integer[] a = {1, 3, 2, 4, 2, 4, 6, 2, 6, 8};
        findDominant(a);
    }
}
