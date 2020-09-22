import java.util.Arrays;
import java.util.HashMap;

public class ThreeSUM {
    // count the number of all combinations and print them
    public static int countNum(int[] arr, int target) {
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        // sort the array
        Arrays.sort(arr);
        // put each element into hashmap. val -> key, index -> val
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        // for each element in the array, solve the two-sum problem using hashmap
        for (int i = 0; i < arr.length; i++) {
            int tmp_target = target - arr[i];
            for (int j = i+1; j < arr.length; j++) {
                Integer index = map.get(tmp_target - arr[j]);
                if (index != null && index > i && index > j) {
                    count++;
                    System.out.println(arr[i] + " + " + arr[j] + " + " + arr[index] + " = " + target);
                }
            }
        }
        return count;
    }
    public static void main(String[] args) {
        int[] arr = { 30, -40, -20, -10, 40, 0, 10, 5 };
        int target = 0;
        System.out.println(countNum(arr, target));
    }
}
