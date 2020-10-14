import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Permutation {
    public static void perm(int[] arr1, int[] arr2) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        if (Arrays.equals(arr1, arr2)) {
            StdOut.println("yes");
        } else {
            StdOut.println("no");
        }
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 3, 2};
        int[] arr2= {2, 3, 1};
        Permutation.perm(arr1, arr2);
    }
}
