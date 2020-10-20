public class SelectionInTwoSortedArrays {
    public static Comparable getKthElement(Comparable[] arr1, Comparable[] arr2, int k) {
        int m = arr1.length, n = arr2.length;
        int index1 = 0, index2 = 0;
        while (true) {
            // corner cases
            if (index1 == m)
                return arr2[index2 + k - 1];
            if (index2 == n)
                return arr1[index1 + k - 1];
            if (k == 1)
                return min(arr1[index1], arr2[index2]);
            // compare the medians and then recur in a subproblem of roughly half the size
            int newIndex1 = Math.min(index1 + k/2 - 1, m - 1); // plus k/2 - 1 may overflow
            int newIndex2 = Math.min(index2 + k/2 - 1, n - 1); // plus k/2 - 1 may overflow
            if (arr1[newIndex1].compareTo(arr2[newIndex2]) <= 0) {
                k -= newIndex1 - index1 + 1;
                index1 = newIndex1 + 1;
            } else {
                k -= newIndex2 - index2 + 1;
                index2 = newIndex2 + 1;
            }
        }
    }

    private static Comparable min(Comparable a, Comparable b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    public static void main(String[] args) {
        Integer[] arr1 = {1, 4};
        Integer[] arr2 = {3, 5};
        Integer[] arr3 = {};
        Integer[] arr4 = {3, 4, 5, 7, 8};
        Integer[] arr5 = {2, 4, 6};
        System.out.println(getKthElement(arr1, arr2, 3).compareTo(4) == 0);
        System.out.println(getKthElement(arr3, arr4, 4).compareTo(7) == 0);
        System.out.println(getKthElement(arr4, arr5, 4).compareTo(4) == 0);
    }
}
