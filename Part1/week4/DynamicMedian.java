import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class DynamicMedian<Key extends Comparable<Key>> {
    private MinPQ<Key> minPQ;
    private MaxPQ<Key> maxPQ;

    // create a median heap
    public DynamicMedian() {
        minPQ = new MinPQ<>();
        maxPQ = new MaxPQ<>();
    }

    // insert a key
    public void insert(Key x) {
        if (maxPQ.isEmpty())
            maxPQ.insert(x);
        else if (minPQ.isEmpty())
            minPQ.insert(x);
        else {
            if (x.compareTo(maxPQ.max()) > 0) {
                minPQ.insert(x);
            } else {
                maxPQ.insert(x);
            }
        }
        adjust();
    }

    // return median
    public Key median() {
        checkSizes();
        if (minPQ.isEmpty())
            return maxPQ.max();
        else if (maxPQ.isEmpty())
            return minPQ.min();
        else {
            if (maxPQ.size() == minPQ.size())  {
                return (maxPQ.max().compareTo(minPQ.min()) < 0 ? maxPQ.max() : minPQ.min());
            } else {
                if (maxPQ.size() > minPQ.size())
                    return maxPQ.max();
                else
                    return minPQ.min();
            }
        }
    }

    // return median and delete it from the heap
    public Key delMedian() {
        checkSizes();
        Key median;
        if (minPQ.isEmpty())
            median =  maxPQ.delMax();
        else if (maxPQ.isEmpty())
            median = minPQ.delMin();
        else {
            if (maxPQ.size() == minPQ.size()) {
                median = (maxPQ.max().compareTo(minPQ.min()) < 0 ? maxPQ.delMax() : minPQ.delMin());
            } else {
                if (maxPQ.size() > minPQ.size())
                    median = maxPQ.delMax();
                else
                    median = minPQ.delMin();
            }
        }
        adjust();
        return median;
    }

    // check whether both heaps are empty
    private void checkSizes() {
        if (minPQ.isEmpty() && maxPQ.isEmpty())
            throw new UnsupportedOperationException();
    }

    // adjust the sizes of two heaps to avoid unbalanced situation
    private void adjust() {
        while (minPQ.size() - maxPQ.size() > 1) {
            maxPQ.insert(minPQ.delMin());
        }
        while (maxPQ.size() - minPQ.size() > 1) {
            minPQ.insert(maxPQ.delMax());
        }
    }

    public static void main(String[] args) {
        DynamicMedian heap1 = new DynamicMedian();
        int[] arr1 = {1, 4, 3, 2, 5};
        for (int i = 0; i < arr1.length; i++)
            heap1.insert(arr1[i]);
        StdOut.println(heap1.delMedian());  // 3
        StdOut.println(heap1.delMedian());  // 2
        StdOut.println(heap1.median());  // 4
    }
}
