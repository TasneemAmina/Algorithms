
/*
 * 05-02-2016
 * 
 * PercolationStats.java
 * 
 * Programming Assignment 1: Percolation
 * This data type estimates the percolation threshold using Monte Carlo 
 * simulation
 * 
 * Konstantinos Vantas: k_vantas@yahoo.gr
 * 
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  
  private int simRuns;
  private double[] simResults;

  // perform T independent experiments on an N-by-N grid
  public PercolationStats(int N, int T) {
    
    // check N and T
    if ((N <= 0) || (T <= 0))
      throw new java.lang.IllegalArgumentException();
    
    // compute sites
    int sites = N * N;
    
    // save number of runs and simulation results
    simRuns = T;
    simResults = new double[T];
    
    // perform T experiments
    for (int i = 0; i < T; i++)
      simResults[i] = monteCarlo(N) / ((double) sites);
    
  }

  // Monte Carlo simulation
  private int monteCarlo(int N) {

    // initialize values for index and count sites
    int i, j;
    int count = 0;

    // new percolation object
    Percolation perc = new Percolation(N);

    while (!perc.percolates()) {
      // Choose a site uniformly at random
      i = (int) StdRandom.uniform(1, N + 1);
      j = (int) StdRandom.uniform(1, N + 1);
      
      // count only if is random site is not open
      if (!perc.isOpen(i, j)) {
        perc.open(i, j);
        count++;
      }
    }
    // return the sites that are opened when the system percolates
    return count;
  }

  // sample mean of percolation threshold
  public double mean() {
    return (StdStats.mean(simResults));
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    if (simRuns == 1)
      return Double.NaN;
    else
    return (StdStats.stddev(simResults));
  }

  // low end point of 95% confidence interval
  public double confidenceLo() {
    return (mean() - (1.96 * stddev()) / Math.sqrt(simRuns)); 
  }

  // high end point of 95% confidence interval
  public double confidenceHi() {
    return (mean() + (1.96 * stddev()) / Math.sqrt(simRuns));
  }

  // test client
  public static void main(String[] args) {
    
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    
    PercolationStats ps = new PercolationStats(N, T);
    
    System.out.println("mean                    = " + ps.mean());
    System.out.println("stddev                  = " + ps.stddev());
    System.out.print("95% confidence interval = " + ps.confidenceLo());
    System.out.print(", " + ps.confidenceHi() + "\n");

  }

}
