import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandamizedHeap<Key> implements Iterable<Key> {
    private Key[] pq;
    private int n;

    public RandamizedHeap(int capacity) {
        pq = (Key[]) new Object[capacity + 1];
        n = 0;
    }

    public RandamizedHeap() {
        this(1);
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void insert(Key x) {
        if (n == pq.length - 1)
            resize(2 * pq.length);
        pq[++n] = x;
        swim(n);
    }

    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("heap is empty");
        return pq[1];
    }

    public Key delMax() {
        if (isEmpty())
            throw new NoSuchElementException("heap is empty");
        Key max = pq[1];
        exch(1, n--);
        pq[n + 1] = null; // avoid loitering
        sink(1);
        if (n > 0 && n == (pq.length - 1) / 4)
            resize(pq.length / 2);
        return max;
    }

    public Key sample() {
        if (isEmpty())
            throw new NoSuchElementException("heap is empty");
        int index = StdRandom.uniform(1, n + 1);
        return pq[index];
    }

    public Key delRandom() {
        if (isEmpty())
            throw new NoSuchElementException("heap is empty");
        int index = StdRandom.uniform(1, n + 1);
        Key sample = pq[index];
        exch(index, n--);
        pq[n + 1] = null; // avoid loitering
        if (index != n + 1) {   // if index equals n+1, swim will throw exception
            sink(index);
            swim(index);
        }
        if (n > 0 && n == (pq.length - 1) / 4)
            resize(pq.length / 2);
        return sample;
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        private  RandamizedHeap<Key> copy;

        public HeapIterator() {
            copy = new RandamizedHeap<>(n);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy.delRandom();
        }
    }

    private void resize(int newSize) {
        Key[] copy = (Key[]) new Object[newSize];
        for (int i = 0; i <= n; i++)
            copy[i] = pq[i];
        pq = copy;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1))
                j = j + 1;
            if (!less(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean less(int i, int j) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
    }

    public static void main(String[] args) {
        Integer[] arr = {2, 1, 3, 5, 4};
        RandamizedHeap<Integer> pq = new RandamizedHeap<>();
        for (int i = 0; i < arr.length; i++)
            pq.insert(arr[i]);
        for (Integer i : pq)
            StdOut.println(i);
    }
}
