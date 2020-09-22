import java.util.Arrays;

public class Bitonic {
    // find the index of max element in the array
    public static int findIndexOfMax(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while (true) {
            if (start == end)
                return start;
            int mid = (start + end) / 2;
            if (arr[mid] > arr[mid+1])
                end = mid;
            else
                start = mid + 1;
        }
    }

    // check whether the element a is in the array using binary search
    public static boolean binarySearch(int[] arr, int a) {
        int start = 0;
        int end = arr.length - 1;
        while(start <= end) {
            int mid = (start + end) / 2;
            if (arr[mid] == a)
                return true;
            else if (arr[mid] > a)
                end = mid - 1;
            else
                start = mid + 1;
        }
        return false;
    }
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 5, 4, 0};
        int idx = findIndexOfMax(arr);
        int target = 2;
        if (binarySearch(Arrays.copyOfRange(arr, 0, idx), target) ||
                binarySearch(Arrays.copyOfRange(arr, idx, arr.length), target)) {
            System.out.println("Element " + target + " exists.");
        } else {
            System.out.println("Element " + target + " doesn't exist.");
        }
    }
}
