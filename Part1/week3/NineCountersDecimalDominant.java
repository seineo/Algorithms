import edu.princeton.cs.algs4.Count;
import edu.princeton.cs.algs4.StdOut;

// find dominant elements using a variant of Boyer-Moore majority vote algorithm
public class NineCountersDecimalDominant {
    private static class Counter {
        private Comparable elem;
        private int count;

        public Counter(Comparable e, int c) {
            elem = e;
            count = c;
        }
    }
    private static Counter[] counter;

    public static void findDominant(Comparable[] a) {
        // initialize counter
        counter = new Counter[9];
        for (int i = 0; i < 9; i++)
            counter[i] = new Counter(null, 0);
        // go through the array and update counter
        for (int i = 0;i < a.length; i++) {
            int index = isCounted(a[i]);
            if (index >= 0) {  // counted
                counter[index].count++;
            } else {
                add(a[i]);
            }
        }
        // check whether any element appears more than n/10 times
        for (int i = 0; i < 9; i++) {
            if (counter[i].elem != null) {
                // reinitialize counter
                counter[i].count = 0;
                // go through the array and update counter
                for (int j = 0; j < a.length; j++)
                    if (a[j].compareTo(counter[i].elem) == 0)
                        counter[i].count++;
                if (counter[i].count > a.length/10)
                    StdOut.println(counter[i].elem);
            }
         }
    }

    // if a is in counter return its index, otherwise return -1
    private static int isCounted(Comparable a) {
        for (int i = 0; i < 9; i++) {
            if (counter[i].elem != null && a.compareTo(counter[i].elem) == 0) {
                return i;
            }
        }
        return -1;
    }

    /*
        add the element to counter. If counter is not full, just add it and set its count 1,
        otherwise decrement every element's count in counter until there is entry for the new element
        and then add it and set its count 1.
     */
    private static void add(Comparable a) {
        // counter is not full
        for (int i = 0; i < 9; i++) {
            if (counter[i].count == 0) {
                counter[i].elem = a;
                counter[i].count = 1;
                return;
            }
        }
        // full, then decrement every element's count in counter until there is entry for the new element
        while (true) {
            for (int i = 0; i < 9;i++) {
                if (--counter[i].count == 0) {
                    counter[i].elem = a;
                    counter[i].count = 1;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        /* n = 10, n / 10 = 1, so find the elements appear more than once
           the answer is 2, 4 and 6 */
        Integer[] a = {1, 3, 2, 4, 2, 4, 6, 2, 6, 8};
        findDominant(a);
    }
}
