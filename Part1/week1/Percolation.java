import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF objects, dupObjects;  // one-dimensional union find objects
    private final int gridSize;   // grid size N
    private int count;
    private boolean[][] opened;  // true -> open, false -> blocked. Initialized as false

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N is less than or equal to 0.");
        gridSize = N;
        count = 0;
        objects = new WeightedQuickUnionUF(N*N + 2);  // the last two are virtual nodes
        dupObjects = new WeightedQuickUnionUF(N*N + 1);  // the last is virtual-top node
        // link to the virtual-top
        for (int i = 0;i < N;i++) {
            objects.union(i, N*N);
            dupObjects.union(i, N*N);
        }
        //link to the virtual-bottom
        for (int i =N*N-N;i < N*N;i++) {
            objects.union(i, N*N + 1);
        }
        opened = new boolean[N][N];
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validate(i, j);
        if (isOpen(i, j))
            return;
        count++;
        opened[i-1][j-1] = true; // mark it as opened
        int p = map(i, j);
        // link to open neighbors of four directions
        int[] dir1 = {1, -1, 0, 0};
        int[] dir2 = {0, 0, 1, -1};
        for (int idx = 0; idx < 4; idx++) {
            int row = i + dir1[idx];
            int col = j + dir2[idx];
            if (inRange(row, col) && isOpen(row, col)) {
                int q = map(row, col);
                objects.union(p, q);
                dupObjects.union(p, q);
            }
        }
    }

    // check whether (i, j) is open
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return opened[i-1][j-1];
    }

    // check whether (i, j) is full
    public boolean isFull(int i, int j) {
        validate(i, j);
        int p = map(i, j);
        if (!isOpen(i, j))
            return false;
        else
            return dupObjects.find(p) == dupObjects.find(gridSize*gridSize);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // check whether the system percolates
    public boolean percolates() {
        if (gridSize == 1)
            return isOpen(1, 1);
        return objects.find(gridSize*gridSize) == objects.find(gridSize*gridSize+1);
    }

    /* map two-dimensional (row, column) pair to a one-dimensional union find objects,
       row first*/
    private int map(int i, int j) {
        validate(i, j);   // check bounds
        return (i-1) * gridSize + j-1;
    }

    // validate that (i, j) is a valid index
    private void validate(int i, int j) {
        if (i < 1 || i > gridSize)
            throw new IllegalArgumentException("row index " + i + " is not valid.");
        if (j < 1 || j > gridSize)
            throw new IllegalArgumentException("column index " + j + " is not valid.");
    }

    // check (row, col) whether in bounds
    private boolean inRange(int i, int j) {
        return (i >= 1 && i <= gridSize) && (j >= 1 && j <= gridSize);
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(2);
        test.open(1,2);
        test.open(2, 2);
        if (test.percolates()) {
            StdOut.println("counts: " + test.numberOfOpenSites());
            StdOut.println("percolates!");
        } else {
            StdOut.println("not percolate...");
        }
    }
}
