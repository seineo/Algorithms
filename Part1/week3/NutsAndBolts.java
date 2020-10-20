import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class NutsAndBolts {
    private static Comparable[] nuts, bolts;

    public static void sort(Comparable[] n, Comparable[] b) {
        // don't change the original arrays
        nuts = Arrays.copyOf(n, n.length);
        bolts = Arrays.copyOf(b, b.length);
        sort(nuts, 0, nuts.length - 1, bolts, 0, bolts.length - 1);
    }

    /* partition nuts by an element in bolts and then partition bolts by the corresponding
       element in nuts, then recur it for subproblems */
    private static void sort(Comparable[] nuts, int nLo, int nHi, Comparable[] bolts, int bLo, int bHi) {
        if (nLo < nHi && bLo < bHi) {
            int nPivot = partition(nuts, nLo, nHi, bolts[bLo]);
            int bPivot = partition(bolts, bLo, bHi, nuts[nPivot]);
            sort(nuts, nLo, nPivot - 1, bolts, bLo, bPivot - 1);
            sort(nuts, nPivot + 1, nHi, bolts, bPivot + 1, bHi);
        }
    }

    private static int partition(Comparable[] arr, int lo, int hi, Comparable pVal) {
        // find the corresponding value and swap it to arr[lo]
        for (int i = lo; i <= hi; i++)
            if (arr[i].compareTo(pVal) == 0)
                exch(arr, lo, i);
        int i = lo, j = hi + 1;
        while (true) {
            while (less(arr[++i], pVal))
                if (i == hi)
                    break;
            while (less(pVal, arr[--j]))
                if (j == lo)
                    break;
            if (j <= i)
                break;
            exch(arr, i, j);
        }
        exch(arr, lo, j);
        return j;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] arr, int i, int j) {
        Comparable swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    public static void show() {
        for (Comparable i : nuts)
            StdOut.print(i + " ");
        StdOut.println();
        for (Comparable i : bolts)
            StdOut.print(i + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        Integer[] nuts = {1, 3, 4, 2, 5};
        Integer[] bolts = {5, 3, 4, 1, 2};
        sort(nuts, bolts);
        show();
    }
}
