/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        int i = 1;
        // use reservoir sampling
        // 1. enqueue the first k elements
        for (int j = 0; j < k ; j++) {
            queue.enqueue(StdIn.readString());
        }
        // 2. for the nth element, it has the possibility of k/n to replace
        //    one of the element in queue
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            int random = StdRandom.uniform(0, k + i);
            if (random < k) {
                queue.dequeue();
                queue.enqueue(str);
            }
            i++;
        }
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }
}

