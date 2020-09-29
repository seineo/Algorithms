/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// use resizing array
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
        n = 0;
    }

    // check whether randomized queue is empty
    public boolean isEmpty() {
        return n == 0;
    }

    // return the numebr of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the argument is null");
        if (n == arr.length)
            resize(2 * arr.length);
        arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("randomized queue underflow");
        int index = StdRandom.uniform(0, n);
        Item item = arr[index];
        arr[index] = arr[n-1];
        arr[n-1] = null;  // avoid loitering
        n--;
        if (n > 0 && n == arr.length / 4)
            resize(arr.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("randomized queue underflow");
        int index = StdRandom.uniform(0, n);
        return arr[index];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i;
        private int[] order;

        public ArrayIterator() {
            i = 0;
            order = new int[n];
            for (int j = 0; j < n; j++)
                order[j] = j;
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return i != n;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = arr[order[i++]];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = arr[i];
        arr = copy;
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; i++)
            queue.enqueue(i);
        for (int i = 0; i < 5; i++)
            StdOut.println(queue.dequeue());
        StdOut.println(queue.isEmpty());
        queue.enqueue(1);
        StdOut.println(queue.size() == 1);
        StdOut.println(queue.sample() == 1);
        queue.dequeue();
        for (int i = 0; i < 5; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}