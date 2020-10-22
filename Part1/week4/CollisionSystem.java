import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;
import java.util.Arrays;

public class CollisionSystem {
    private MinPQ<Event> pq;  // the priority queues
    private double t;  // simulation clock time
    private final Particle[] particles; // the array of particles
    private static final double CLOCK_TO_REDRAW = 0.5;  // clock time for redrawing

    private class Event implements Comparable<Event> {
        private final double time; // time of event
        private final Particle a, b;  // particles involved in event
        private final int countA, countB;  // collision counts for a and b

        // create an event
        public Event(double t, Particle a, Particle b) {
            time = t;
            this.a = a;
            this.b = b;
            countA = a == null ? -1 : a.count();
            countB = b == null ? -1 : b.count();
        }
        @Override
        public int compareTo(Event that) {
            return Double.compare(time, that.time);
        }

        /* check whether the event is valid now.
           It invalids if the collision times have changed. */
        public boolean isValid() {
            if (a != null && countA != a.count())
                return false;
            if (b != null && countB != b.count())
                return false;
            return true;
        }
    }

    public CollisionSystem(Particle[] particles) {
        this.particles = Arrays.copyOf(particles, particles.length);
        t = 0;
    }

    /* predict events to happen. Add to PQ all particle-wall
       and particle-particle collisions involving this particle */
    public void predict(Particle a, double timeLimit) {
        if (a == null)
            return;
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= timeLimit) {
                pq.insert(new Event(t + dt, a, particles[i]));
            }
        }
        double dtVertical = a.timeToHitVerticalWall();
        double dtHorizontal = a.timeToHitHorizontalWall();
        if (t + dtVertical <= timeLimit)
            pq.insert(new Event(t + dtVertical, a, null));
        if (t + dtHorizontal <= timeLimit)
            pq.insert(new Event(t + dtHorizontal, null, a));
    }

    // redraw the particles
    public void redraw(double timeLimit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++)
            particles[i].draw();
        StdDraw.show();
        StdDraw.pause(20);
        StdDraw.enableDoubleBuffering();
        // insert next redraw event
        if (t + CLOCK_TO_REDRAW <= timeLimit)
            pq.insert(new Event(t + CLOCK_TO_REDRAW, null, null));
    }

    // simulate the model
    public void simulate(double timeLimit) {
        // initialize PQ
        pq = new MinPQ<>();
        for (int i = 0; i < particles.length; i++)
            predict(particles[i], timeLimit);
        pq.insert(new Event(0, null, null));  // event to redraw
        while (!pq.isEmpty()) {
            Event event = pq.delMin();
            if (!event.isValid())
                continue;
            for (int i = 0; i < particles.length; i++)
                particles[i].move(event.time - t);
            t = event.time;
            Particle a = event.a;
            Particle b = event.b;
            if (a != null && b != null)
                a.bounceOff(b);
            else if (a != null && b == null)
                a.bounceOffVerticalWall();
            else if (a == null && b != null)
                b.bounceOffHorizontalWall();
            else
                redraw(timeLimit);
            predict(a, timeLimit);
            predict(b, timeLimit);
        }
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        Particle[] particles = new Particle[N];
        for (int i = 0; i < N; i++) {
            double rx = StdIn.readDouble();
            double ry = StdIn.readDouble();
            double vx = StdIn.readDouble();
            double vy = StdIn.readDouble();
            double radius = StdIn.readDouble();
            double mass = StdIn.readDouble();
            int r = StdIn.readInt();
            int g = StdIn.readInt();
            int b = StdIn.readInt();
            particles[i] = new Particle(rx, ry, vx, vy, radius, mass, new Color(r, g, b));
        }
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(Double.POSITIVE_INFINITY);
    }
}
