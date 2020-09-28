import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class StackWithMax {
    Stack<Double> stack, maxElem;
    public StackWithMax() {
        stack = new Stack<>();  // for usual operations
        maxElem = new Stack<>();  // for storing max elements
    }

    public void push(double val) {
        stack.push(val);
        if (maxElem.isEmpty() || maxElem.peek() <= val)
            maxElem.push(val);
    }

    public double pop() {
        if (stack.isEmpty())
            throw new NoSuchElementException("stack underflow");
        double top = stack.peek();
        if (top == maxElem.peek())
            maxElem.pop();
        stack.pop();
        return top;
    }

    public double max() {
        if (maxElem.isEmpty())
            throw new NoSuchElementException("stack underflow");
        return maxElem.peek();
    }

    public static void main(String[] args) {
        StackWithMax stack = new StackWithMax();
        stack.push(2);
        stack.push(0);
        StdOut.println(stack.max() == 2);
        stack.push(3);
        StdOut.println(stack.max() == 3);
        StdOut.println(stack.pop() == 3);
        StdOut.println(stack.pop() == 0);
        StdOut.println(stack.pop() == 2);
    }
}
