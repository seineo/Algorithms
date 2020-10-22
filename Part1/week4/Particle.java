import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;

public class Particle {
    private double rx, ry;  // position
    private double vx, vy;  // velocity
    private final double radius;  // radius
    private final double mass;  // mass
    private final Color color;  // color
    private int count;  // collision times

    // initialize the statue of a particle
    public Particle(double rx, double ry, double vx, double vy, double radius, double mass, Color color) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
        count = 0;
    }

    public Particle() {
        rx = StdRandom.uniform(0.0, 1.0);
        ry = StdRandom.uniform(0.0, 1.0);
        vx = StdRandom.uniform(-0.005, 0.005);
        vy = StdRandom.uniform(-0.005, 0.005);
        radius = 0.02;
        mass = 0.5;
        color = Color.BLACK;
    }

    // return the times of collisions
    public int count() {
        return count;
    }

    // change the position of particle after dt time
    public void move(double dt) {
        rx = rx + vx*dt;
        ry = ry + vy*dt;
    }

    // draw the particle with its own color
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx, ry, radius);
    }

    // predit time of collision with another particle
    public double timeToHit(Particle that) {
        if (this == that)
            return Double.POSITIVE_INFINITY;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double drx = that.rx - this.rx;
        double dry = that.ry - this.ry;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = drx*drx + dry*dry;
        double dvdr = dvx*drx + dvy*dry;
        double sigma = radius + that.radius;
        double d = dvdr*dvdr - dvdv * (drdr - sigma*sigma);
        if (dvdr >= 0 ||  d < 0)
            return Double.POSITIVE_INFINITY;
        else
            return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    // predit time of collision with vertical wall
    public double timeToHitVerticalWall() {
        if (vx == 0)
            return Double.POSITIVE_INFINITY;
        else if (vx > 0)  // expect to hit the right wall
            return (1 - radius - rx) / vx;
        else   // expect to hit the left wall
            return (rx - radius) / (-vx);
    }

    // predit time of collision with horizontal wall
    public double timeToHitHorizontalWall() {
        if (vy == 0)
            return Double.POSITIVE_INFINITY;
        else if (vy > 0)
            return (1 - radius - ry) / vy;
        else
            return (ry - radius) / (-vy);
    }

    // resolve collision with another particle
    public void bounceOff(Particle that) {
        // order matters, if this - that, things go wrong
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double drx = that.rx - this.rx;
        double dry = that.ry - this.ry;
        double dvdr = dvx*drx + dvy*dry;
        double sigma = radius + that.radius;
        double J = 2 * mass * that.mass * dvdr / (sigma * (mass + that.mass));
        double Jx = J * drx / sigma;
        double Jy = J * dry / sigma;
        vx += Jx / mass;
        vy += Jy / mass;
        that.vx -= Jx / that.mass;
        that.vy -= Jy / that.mass;
        count++;
        that.count++;

//        double dx  = that.rx - this.rx;
//        double dy  = that.ry - this.ry;
//        double dvx = that.vx - this.vx;
//        double dvy = that.vy - this.vy;
//        double dvdr = dx*dvx + dy*dvy;             // dv dot dr
//        double dist = this.radius + that.radius;   // distance between particle centers at collison
//
//        // magnitude of normal force
//        double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
//
//        // normal force, and in x and y directions
//        double fx = magnitude * dx / dist;
//        double fy = magnitude * dy / dist;
//
//        // update velocities according to normal force
//        this.vx += fx / this.mass;
//        this.vy += fy / this.mass;
//        that.vx -= fx / that.mass;
//        that.vy -= fy / that.mass;
//
//        // update collision counts
//        this.count++;
//        that.count++;
    }

    // resolve collision with vertical wall
    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    // resolve collision with horizontal wall
    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }
}
