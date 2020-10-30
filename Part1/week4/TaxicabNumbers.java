import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class TaxicabNumbers {
    private MinPQ<Taxicab> pq;
    private int n;

    private class Taxicab implements Comparable<Taxicab> {
        private int num1;
        private int num2;
        private long taxicab;

        public Taxicab(int num1, int num2, long taxicab) {
            this.num1 = num1;
            this.num2 = num2;
            this.taxicab = taxicab;
        }

        @Override
        public int compareTo(Taxicab that) {
            if (taxicab < that.taxicab)
                return -1;
            else if (taxicab > that.taxicab)
                return 1;
            else
                return 0;
        }
    }

    public TaxicabNumbers(int n) {
        pq = new MinPQ<>();
        this.n = n;
    }

    public void show() {
        // insert diagonal elements first
        for (int i = 1; i < n; i++)
            pq.insert(new Taxicab(i, i, i*i*i*2));
        /* pop current min element and compare it to the previous one, if equal,
         *  then it's a taxicab number, then (whether equal or not)put the item
         *  to the right of current min element to min heap */
        Taxicab prev = new Taxicab(0, 0, 0);
        while (!pq.isEmpty()) {
            Taxicab cur = pq.delMin();
            if (cur.compareTo(prev) == 0) {
                StdOut.print(cur.taxicab + " = " + cur.num1 + "^3 + " + cur.num2 + "^3");
                StdOut.println(" = " + prev.num1 + "^3 + " + prev.num2 + "^3");
            }
            prev = cur;
            if (cur.num2 < n - 1) {
                int next = cur.num2 + 1;
                long taxicab = (long) Math.pow(cur.num1, 3) + (long) Math.pow(next, 3);
                pq.insert(new Taxicab(cur.num1, next, taxicab));
            }
        }
    }

    public static void main(String[] args) {
        int n = 500;
        TaxicabNumbers taxicab = new TaxicabNumbers(n);
        taxicab.show();
    }
}
