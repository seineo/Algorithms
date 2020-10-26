/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<Node> pq;  // priority queue
    private Stack<Board> sequence;  // the sequence of solution
    private boolean solvable = false;   // whether it's solvable
    private int moveNumber = 0;   // the number of moves

    // search node for priority queue
    private class Node implements Comparable<Node> {
        private Board board;  // current board
        private int moveNum;  //  number of moves made to reach the board
        private Node pre;    // previous search node
        private int manhattan;  // manhattan distance from goal
        private boolean isTwin; // whether it's twin

        public Node(Board board, int moveNum, Node pre, boolean isTwin) {
            this.board = board;
            this.moveNum = moveNum;
            this.pre = pre;
            manhattan = board.manhattan();
            this.isTwin = isTwin;
        }

        public int compareTo(Node that) {
            int prior = manhattan + moveNum;
            int thatPrior = that.manhattan+ that.moveNum;
            return prior - thatPrior;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("illegal argument");
        /* run the A* algorithm on two puzzle instances—one with the initial board
        and one with the initial board modified by swapping a pair of tiles in lockstep */
        pq = new MinPQ<>();
        pq.insert(new Node(initial, 0, null, false));
        Board twinBoard = initial.twin();
        pq.insert(new Node(twinBoard, 0, null, true));
        while (true) {
            if (pq.min().board.isGoal())
                break;
            seekGoal(pq);
        }
        if (!pq.min().isTwin) {
            solvable = true;
            moveNumber = pq.min().moveNum;
            sequence = new Stack<>();
            Node goal = pq.delMin();
            while (goal != null) {
                sequence.push(goal.board);
                goal = goal.pre;
            }
        } else {
            solvable = false;
        }
    }

    // search for the goal
    private void seekGoal(MinPQ<Node> pq) {
        Node node = pq.delMin();
        for (Board i : node.board.neighbors()) {
            if (node.pre == null || !i.equals(node.pre.board)) {
                pq.insert(new Node(i, node.moveNum + 1, node, node.isTwin));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? moveNumber : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solvable ? sequence : null;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}