import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/***********************************************************************************
 * The immutable data type Solver is used to solve the 8-puzzle problem (and its
 * natural generalizations) using the A* search algorithm.
 **********************************************************************************/
public class Solver {

  private SearchNode finalNode;

  // search node of the game
  private class SearchNode implements Comparable<SearchNode> {

    private Board      board;
    private SearchNode previous;
    private int        moves;
    private int        cost;

    private SearchNode(Board board, SearchNode previous, String priority) {

      this.board = board;
      this.previous = previous;

      // compute moves
      if (previous == null)
        this.moves = 0;
      else
        this.moves = previous.moves++;

      // calculate priority function
      if (priority.equals("Manhattan"))
        this.cost = board.manhattan() + this.moves;
      else
        this.cost = board.hamming() + this.moves;
    }

    // implement compareTo based on the priority function cost
    public int compareTo(SearchNode that) {
      if (this.cost == that.cost)
        return 0;
      else if (this.cost < that.cost)
        return -1;
      else
        return 1;
    }

    // check if this board is the final board
    public boolean isGoal() {
      return this.board.isGoal();
    }

  }

  /**
   * Finds a solution to the initial board using the A* algorithm
   *
   */
  public Solver(Board initial) {

    String priority = "Manhattan";

    // insert the initial search node and a twin
    SearchNode init = new SearchNode(initial, null, priority);
    SearchNode twin = new SearchNode(initial.twin(), null, priority);

    MinPQ<SearchNode> queuInit = new MinPQ<SearchNode>();
    MinPQ<SearchNode> queuTwin = new MinPQ<SearchNode>();

    // loop while the first and the twin search nodes are not final
    while (!init.isGoal() && !twin.isGoal()) {

      // find initial and twin search nodes neighbors
      Iterable<Board> initNeighbors = init.board.neighbors();
      Iterable<Board> twinNeighbors = twin.board.neighbors();

      // loop through initial search board neighbors
      for (Board b1 : initNeighbors)
        if (b1 != null && (init.previous == null || !b1.equals(init.previous.board)))
          queuInit.insert(new SearchNode(b1, init, priority));
      // loop through twin search board neighbors
      for (Board b2 : twinNeighbors)
        if (b2 != null && (twin.previous == null || !b2.equals(twin.previous.board)))
          queuTwin.insert(new SearchNode(b2, twin, priority));

      // remove the node with the smallest priority queues and update with these
      // values initial and twin search nodes
      init = queuInit.delMin();
      twin = queuTwin.delMin();
    }

    if (init.isGoal())
      finalNode = init;
    else
      finalNode = null;

  }

  /**
   * isSolvable finds if the initial board is solvable
   *
   * @return a true if is solvable
   */
  public boolean isSolvable() {
    return !(finalNode == null);
  }

  /**
   * moves returns the minimum number of moves to solve initial board; -1 if
   * unsolvable
   *
   * @return an int representing the minimum moves
   */
  public int moves() {
    if (finalNode == null)
      return -1;
    else
      return finalNode.moves;
  }

  /**
   * solution returns the sequence of boards in a shortest solvable solution.
   * Returns null if unsolvable
   *
   * @return the sequence of boards in a shortest solvable solution
   */
  public Iterable<Board> solution()

  {
    if (!isSolvable())
      return null;

    Queue<Board> queue = new Queue<Board>();
    solutionOrder(queue, finalNode);
    return queue;
  }

  // chase the pointers all the way back to the initial search node
  private void solutionOrder(Queue<Board> queu, SearchNode node) {
    if (node == null)
      return;

    solutionOrder(queu, node.previous);
    queu.enqueue(node.board);

  }

  /**
   * solve a slider puzzle using a file input of the puzzle null if unsolvable
   *
   * @return print solution to the standard output
   */
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

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
