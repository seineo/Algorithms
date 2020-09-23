import edu.princeton.cs.algs4.StdOut;

public class SuccessorWithDelete {
    private boolean[] removed;
    private CanonicalElement uf;
    private int n;
    public SuccessorWithDelete(int N) {
        n = N;
        removed = new boolean[N];
        uf =  new CanonicalElement(N);
    }

    public void remove(int i) {
        removed[i] = true;
        if (i > 0 && removed[i - 1])
            uf.union(i, i-1);
        if (i < n-1 && removed[i + 1])
            uf.union(i, i+1);
    }

    public int successor(int i) {
        if (!removed[i])
            return i;
        else {
            int max = uf.find(i);
            if (max == n - 1) {
                StdOut.println("there is no successor for " + i);
                return -1;
            }
            return max + 1;
        }
    }

    public static void main(String[] args) {
        SuccessorWithDelete swd = new SuccessorWithDelete(10);
        swd.remove(2);
        StdOut.println(swd.successor(2) == 3);
        swd.remove(3);
        StdOut.println(swd.successor(2) == 4);
        StdOut.println(swd.successor(5) == 5);
        swd.remove(8);
        StdOut.println(swd.successor(8) == 9);
        swd.remove(9);
        StdOut.println(swd.successor(8) == -1);
    }
}
