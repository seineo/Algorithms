import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CanonicalElement {
    private int[] parent;
    private int[] sz;
    private int count;

    public CanonicalElement(int N) {
        parent = new int[N];
        sz = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            sz[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot)
            return;
        // the root is the max element in the set
        if (pRoot > qRoot)
            parent[qRoot] = pRoot;
        else
            parent[pRoot] = qRoot;
        count--;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int count() {
        return count;
    }

    public int find(int i) {
        return root(i);
    }

    private int root(int i) {
        while (i != parent[i]) {
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    public static void main(String[] args) {
        int N = 5;
        CanonicalElement ce = new CanonicalElement(N);
        while (!StdIn.isEmpty()) {
            // p and q are [0, N-1]
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!ce.connected(p, q))
                ce.union(p, q);
            StdOut.println(ce.find(p));
        }
    }
}
