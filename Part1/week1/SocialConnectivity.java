import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SocialConnectivity {
    private int[] parent;
    private int[] sz;
    private int count;

    public SocialConnectivity(int N) {
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
        if (sz[pRoot] < sz[qRoot]) {
            parent[pRoot] = qRoot;
            sz[qRoot] = sz[qRoot] + sz[pRoot];
        } else {
            parent[qRoot] = pRoot;
            sz[pRoot] = sz[pRoot] + sz[qRoot];
        }
        count--;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int count() {
        return count;
    }

    private int root(int i) {
        while (i != parent[i]) {
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    public static void main(String[] args) {
        int N = 5;  // number of members
        SocialConnectivity sc = new SocialConnectivity(N);
        while (!StdIn.isEmpty()) {
            // p and q are [0, N-1]
            int time = StdIn.readInt();
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!sc.connected(p, q))
                sc.union(p, q);
            if (sc.count() == 1) {
                StdOut.println("At " + time + ", all members are connected.");
                break;
            }
        }
        if (sc.count() != 1)
            StdOut.println("Not all members are connected.");
    }
}
