import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CountingInversions {
    int numOfInversions;
    public CountingInversions(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        numOfInversions = sort(a, aux, 0, a.length - 1);
    }

    public int get() {
        return numOfInversions;
    }

    public int sort(Comparable[] a, Comparable[] aux, int low, int high) {
        int count = 0;
        if (low < high) {
            int middle = low + (high - low) / 2;
            count += sort(a, aux, low, middle);
            count += sort(a, aux,middle + 1, high);
            count += merge(a, aux, low, middle, high);
        }
        return count;
    }

    public int merge(Comparable[] a, Comparable[] aux, int low, int middle, int high) {
        int i = low, j = middle + 1, count = 0;
        for (int k = low; k <= high; k++)
            aux[k] = a[k];
        for (int k = low; k <= high; k++) {
            if (i > middle)
                a[k] = aux[j++];
            else if (j > high)
                a[k] = aux[i++];
            else if(aux[i].compareTo(aux[j]) < 0)
                a[k] = aux[i++];
            else {
                a[k] = aux[j++];
                count += middle - i + 1;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Integer[] a = { 4, 2, 5, 3, 1};
        CountingInversions inversions = new CountingInversions(a);
        StdOut.println(inversions.get());
    }
}
