/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

// use linked list
public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first, last;
    private class Node {
        private Item item;
        private Node next, prev;
    }

    // construct an empty deque
    public Deque() {
        first = last = null;
        n = 0;
    }

    // check whether deque is empty
    public boolean isEmpty() {
        return n == 0;
    }

    // return the numebr of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the argument is null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty())
            last = first;
        else
            oldFirst.prev = first;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("the argument is null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("deque underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("deque underflow");
        Item item = last.item;
        n--;
        if (isEmpty())
            first = last = null;
        else {
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node current;

        public LinkedIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);
        for (Integer item : deque)
            StdOut.print(item + " ");
        StdOut.println();
        StdOut.println(deque.size() == 4);
        StdOut.println(deque.removeFirst() == 1);
        StdOut.println(deque.removeLast() == 4);
        StdOut.println(deque.removeLast() == 3);
        StdOut.println(deque.removeFirst() == 2);
        StdOut.println(deque.isEmpty());
    }
}
