import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueWithTwoStacks<Item> {
    private Stack<Item> in, out;

    public QueueWithTwoStacks() {
        in = new Stack<>();  // for enqueue
        out = new Stack<>();  // for dequeue
    }

    public void enqueue(Item item) {
        in.push(item);
    }

    public Item dequeue() {
        if (!out.isEmpty()) {
            Item item = out.pop();
            return item;
        } else {
            if (in.isEmpty())
                throw new NoSuchElementException("Queue underflow");
            // reverse order to get the first element
            while (!in.isEmpty())
                out.push(in.pop());
            Item item = out.pop();
            return item;
        }
    }

    public static void main(String[] args) {
        QueueWithTwoStacks<String> queue = new QueueWithTwoStacks<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(queue.dequeue() + " ");
            else
                queue.enqueue(s);
        }
    }
}
