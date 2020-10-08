/* shuffle a singly linked list by picking item uniformly
 when merging two sublists */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ShuffleList<Item extends Comparable<Item>> implements Iterable<Item> {
    private Node first;

    private class Node {
        private Item item;
        private Node next;
    }

    // initialize the list
    public ShuffleList() {
        first = null;
    }

    // check whether list is empty
    public boolean isEmpty() {
        return first == null;
    }

    // add an element in the front
    public void add(Item item) {
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        first = newFirst;
    }

    // sort the list using mergesort
    public void mergeSort() {
        if (isEmpty() || first.next == null)
            return;
        first = sort(first);
    }

    @Override
    public Iterator iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator {
        Node current;

        public ListIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /*
        sort the list.
        We should return the head node since we change the node pointers
        when merging two sublists.

     */
    private Node sort(Node low) {
        if (low.next == null)
            return low;
        Node mid = findMiddle(low);
        Node secondLow = mid.next;
        mid.next = null;  // split the list into two sublists
        low = sort(low);
        secondLow = sort(secondLow);
        return merge(low, secondLow);
    }

    // find the middle node of the list
    private Node findMiddle(Node low) {
        Node slow = low, fast = low;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    // merge two sub lists
    private Node merge(Node low, Node secondLow) {
        Node pre = null, head = null;
        Node i = low, j = secondLow;
        while (i != null || j != null) {
            Node min;
            if (i == null) {
                min = j;
                j = j.next;
            } else if (j == null) {
                min = i;
                i = i.next;
            } else {  // pick item uniformly
                int choice = StdRandom.uniform(2);
                // if choice equals 0, choose i, else choose j
                if (choice == 0) {
                    min = i;
                    i = i.next;
                } else {
                    min = j;
                    j = j.next;
                }
            }
            if (pre == null) {
                pre = min;
                head = pre;
            } else {
                pre.next = min;
                pre = min;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ShuffleList<Integer> list = new ShuffleList<>();
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(3);
        list.add(5);
        list.mergeSort();
        for (int i : list)
            StdOut.println(i);
    }
}