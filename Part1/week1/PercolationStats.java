import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double sample_mean, sample_std;
    private final int times;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T is less than or equal to 0.");
        }
        times = T;
        double[] x = new double[T];  // the fraction of open sits
        // perform T times experiments
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);
            int openCount = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openCount++;
                }
            }
            x[i] = (double)openCount / (N*N);
        }
        sample_mean = StdStats.mean(x);
        sample_std = StdStats.stddev(x);
    }

    // sample mean of percolation threshold
    public double mean() {
        return sample_mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sample_std;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        double mu = mean();
        double sigma = stddev();
        return mu - 1.96*sigma / Math.sqrt(times);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        double mu = mean();
        double sigma = stddev();
        return mu + 1.96*sigma / Math.sqrt(times);
    }
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("the length of args is not 2.");
        }
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);  // if T == 1, stddev() return NaN
        StdOut.println("mean() = " + percStats.mean());
        StdOut.println("stddev() = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
    }
}
