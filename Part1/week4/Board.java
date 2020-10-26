/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final char[] tiles;  // puzzle
    private final int size;  // puzzle size
    private static final int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new char[size * size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.tiles[transform(i, j)] = (char)tiles[i][j];
    }

    private Board(char[] tiles) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        size = (int)Math.sqrt(tiles.length);
    }

    // transform an one dimension index to a two dimension index
    private int transform(int i, int j) {
        return i * size + j;
    }

    // copy an array
    private char[] copy(char[] tiles) {
        char[] temp;
        temp = Arrays.copyOf(tiles, size * size);
        return temp;
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                str.append(String.format("%2d ", (int)tiles[transform(i, j)]));
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tiles[transform(i, j)] != 0 && tiles[transform(i, j)] != goalValue(i, j))
                    count++;
        return count;
    }

    // the goal value in (i, j)
    private int goalValue(int i, int j) {
        return (i * size + j + 1) % (size * size);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[transform(i, j)] == 0)
                    continue;;
                int[] index = goalIndex(tiles[transform(i, j)]);
                sum += Math.abs(index[0] - i) + Math.abs(index[1] - j);
            }
        }
        return sum;
    }

    // return index (i, j) of the given value
    private int[] goalIndex(int value) {
        int[] index = new int[2];  // first is i and second is j
        index[0] = (value - 1) / size;
        index[1] = (value - 1) % size;
        return index;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tiles[transform(i, j)] != goalValue(i, j))
                    return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)  return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.equals(tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        // find the blank
        int[] index = findBlank();
        // try four directions and exchange positions if possible
        for (int i = 0; i < 4; i++) {
            char[] temp = copy(tiles);
            int newX = index[0] + dir[i][0], newY = index[1] + dir[i][1];
            if (inBound(newX, newY)) {
                exch(temp, index[0], index[1], newX, newY);
                queue.enqueue(new Board(temp));
            }
        }
        return queue;
    }

    // find the blank
    private int[] findBlank() {
        int[] index = new int[2];  // first is i and second is j
        boolean found = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[transform(i, j)] == 0) {
                    index[0] = i;
                    index[1] = j;
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }
        return index;
    }

    // check whether the indexe is in bound
    private boolean inBound(int i, int j) {
        return i >= 0 && i < size && j >= 0 && j < size;
    }

    private void exch(char[] tiles, int i1, int j1, int i2, int j2) {
        char swap = tiles[transform(i1, j1)];
        tiles[transform(i1, j1)] = tiles[transform(i2, j2)];
        tiles[transform(i2, j2)] = swap;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int i1 = 0, j1 = 0;
        int i2, j2;
        char[] temp = copy(tiles);
        // seek two locations which are not blank
        while (tiles[transform(i1, j1)] == 0) {
            j1++;
            if (j1 == size) {
                j1 = 0;
                i1++;
            }
        }
        if (j1 == size - 1) {
            j2 = 0;
            i2 = i1 + 1;
        } else {
            j2 = j1 + 1;
            i2 = i1;
        }
        while (tiles[transform(i2, j2)] == 0) {
            j2++;
            if (j2 == size) {
                j2 = 0;
                i2++;
            }
        }
        // exchange them
        exch(temp, i1, j1, i2, j2);
        return new Board(temp);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board board = new Board(tiles);
        StdOut.println(board);
        StdOut.println("dimension: " + board.dimension());
        StdOut.println("isGoal? : " + board.isGoal());
        StdOut.println("hamming:" + board.hamming());
        StdOut.println("manhattan: " + board.manhattan());
        StdOut.println("twin: " + board.twin());
    }
}
