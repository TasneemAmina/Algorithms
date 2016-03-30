
/*
 * 05-02-2016
 *
 * Percolation.java
 *
 * Programming Assignment 1: Percolation This data type models a percolation
 * system using Weighted Quick Union
 *
 * Konstantinos Vantas k_vantas@yahoo.gr
 *
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

  private boolean[] grid;                   // grid vector
  private int count;                        // number of components for QU
  private int dim;                          // dimension of grid
  private WeightedQuickUnionUF quickUnion;  // Weighted Quick Union Find Object
  private int topSite;                      // Virtual top site index
  private boolean sysPerc;                  // system percolates

  // create a NxN grid all sites blocked
  public Percolation(int N)
  {
    if (N < 1)
      throw new java.lang.IndexOutOfBoundsException("N must be > 0");

    // init system
    sysPerc = false;
    // compute number of sites
    count = N * N + 1;
    dim = N;
    // create a new QUF object with indexes from 0 to N^2 + 1
    quickUnion = new WeightedQuickUnionUF(count);
    // set top sites
    topSite = 0;
    // create an new vector with all sites = False
    grid = new boolean[count];

  }

  // throw an exception if i and j are outside [1,N]
  private void checkIJ(int i, int j)
  {
    if ((i < 1) || (i > dim) || (j < 1) || (j > dim))
      throw new java.lang.IndexOutOfBoundsException("i, j must be in [1,N]");
  }

  // map from grid to vector
  private int mapGrid(int i, int j)
  {
    return (i + (j - 1) * dim);
  }

  // is any site on the bottom connected with the top site
  private void bottomFilled()
  {
    for (int j = 0; j < dim; j++)
    {
      if (isFull(dim, j))
      {
        sysPerc = true;
        break;
      }
    }
  
  }

  // open site (row i, column j) if it is not open already
  public void open(int i, int j)
  {
    // open site
    if (!isOpen(i, j))
    {
      grid[mapGrid(i, j)] = true;
      bottomFilled();
    }
    else
      return;

    // convert grid to index
    int index = mapGrid(i, j);

    // link the site to its open neighbors
    // left
    if (j > 1 && isOpen(i, j - 1))
      quickUnion.union(index, mapGrid(i, j - 1));
    // right
    if (j < dim && isOpen(i, j + 1))
      quickUnion.union(index, mapGrid(i, j + 1));
    // top
    if (i == 1)
      quickUnion.union(index, topSite);
    else if (isOpen(i - 1, j))
      quickUnion.union(index, mapGrid(i - 1, j));
    // bottom
    if (!(i == dim) && isOpen(i + 1, j))
      quickUnion.union(index, mapGrid(i + 1, j));
  }

  // is site (row i, column j) open?
  public boolean isOpen(int i, int j)
  {
    checkIJ(i, j);
    return grid[mapGrid(i, j)];
  }

  // is site (row i, column j) full?
  public boolean isFull(int i, int j)
  {
    checkIJ(i, j);
    return (quickUnion.connected(mapGrid(i, j), topSite));
  }

  // does the system percolate?
  public boolean percolates()
  {
    return sysPerc;
  }

  // test client
  public static void main(String[] args)
  {

    int N = 3;

    System.out.println("Create a percolation object");
    Percolation p1 = new Percolation(N);

    System.out.println("Open site 1, 1");
    p1.open(1, 1);

    System.out.println("Open site 2, 1");
    p1.open(2, 1);

    System.out.println("Check if 2, 1 is full");
    System.out.println(p1.isFull(2, 1));

    System.out.println("Check if p1 percolates");
    System.out.println(p1.percolates());

    System.out.println("Open site 3, 1");
    p1.open(3, 1);

    System.out.println("Check if 3, 1 is full");
    System.out.println(p1.isFull(3, 1));

    System.out.println("Check if p1 percolates");
    System.out.println(p1.percolates());
  }
}
