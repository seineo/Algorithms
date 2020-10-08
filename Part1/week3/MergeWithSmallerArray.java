import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class MergeWithSmallerArray {
    public MergeWithSmallerArray(Comparable[] a) {
        // check argument
        if (a == null || !isEven(a.length))
            throw new IllegalArgumentException();
        // copy the first half of array
        int halfSize = a.length / 2;
        int i = 0, j = halfSize;
        Comparable[] copy = Arrays.copyOf(a, halfSize);
        // rearrange the first half of array
        for (int k = 0; k < halfSize; k++) {
            if (copy[i].compareTo(a[j]) < 0)
                a[k] = copy[i++];
            else
                a[k] = a[j++];
        }
        int k = 0;
        // copy and rearrange athe left unsorted elements
        while (i < halfSize && j < a.length) {
            if (copy[i].compareTo(a[j]) < 0)
                copy[k++] = copy[i++];
            else
                copy[k++] = a[j++];
        }
        while (j < a.length) {
            copy[k++] = a[j++];
        }
        // put the second sorted half back
        for (int m = 0; m < halfSize; m++)
            a[m + halfSize] = copy[m];
    }

    // check whether the size of array is even
    private boolean isEven(int length) {
        return length % 2 == 0;
    }

    public static void main(String[] args) {
        Integer[] a = {1, 2, 4, 6, 3, 4, 5, 8};
        Integer[] b = {1, 2, 3, 4, 5, 6, 7, 8};
        Integer[] c = {5, 6, 7, 8, 1, 2, 3, 4};
        new MergeWithSmallerArray(a);
        new MergeWithSmallerArray(b);
        new MergeWithSmallerArray(c);
        for (int i : a)
            StdOut.print(i + " ");
        StdOut.println();
        for (int i : b)
            StdOut.print(i + " ");
        StdOut.println();
        for (int i : c)
            StdOut.print(i + " ");
        StdOut.println();
    }
}