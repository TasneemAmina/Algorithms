import edu.princeton.cs.algs4.Stack;;

public class Board {

  private final int[][] board; // a N-by-N array repr. board
  private final int     dim;   // board dimension

  /**
   * construct a board from an N-by-N array of blocks (where blocks[i][j] =
   * block in row i, column j)
   *
   * @param int[][]
   *          array
   */
  public Board(int[][] blocks) {

    // check if blocks is empty
    if (blocks == null)
      throw new java.lang.NullPointerException();

    // save board dimension
    dim = blocks.length;

    // initialize arrays
    board = new int[dim][dim];

    // copy blocks to board
    for (int i = 0; i < dim; i++)
      for (int j = 0; j < dim; j++)
        board[i][j] = blocks[i][j];
  }

  /**
   * Returns the board dimension N of an N-by-N array of blocks
   *
   * @return an integer representing the board dimension
   */
  public int dimension() {
    return dim;
  }

  /**
   * Hamming function returns the number of blocks in the wrong position
   *
   * @return an integer representing the sum of the number of blocks in the
   *         wrong position
   */
  public int hamming() {

    int result = 0;
    int n = 1;

    for (int i = 0; i < dim; i++)
      for (int j = 0; j < dim; j++)
        if (board[i][j] != n++ && board[i][j] != 0)
          result++;

    return result;
  }

  /**
   * Manhattan function returns the sum of the vertical and horizontal distance)
   * from the blocks to their goal positions
   *
   * @return an integer representing the sum of Manhattan distances
   */
  public int manhattan() {

    int result = 0;
    int value;
    int x, y;

    for (int i = 0; i < dim; i++)
      for (int j = 0; j < dim; j++) {
        value = board[i][j];
        if (value != 0) {
          x = (value - 1) / dim;
          y = (value - 1) - x * dim;
          result += Math.abs(x - i) + Math.abs(y - j);
        }
      }
    return result;
  }

  /**
   * isGoal checks if this board is the goal board
   *
   * @return a boolean representing if this board is the goal board
   */
  public boolean isGoal() // ?
  {
    int n = 1;
    for (int i = 0; i < dim; i++)
      for (int j = 0; j < dim; j++)
        if (board[i][j] != n++ && board[i][j] != 0)
          return false;

    return true;
  }

  /**
   * Function Twin creates a board that is obtained by exchanging a random pair
   * of blocks
   *
   * @return a twin Board
   */
  public Board twin() {
    Board newTwin = new Board(board);

    int row;
    // check for 0 in the first row
    if (board[0][0] == 0 || board[0][1] == 0)
      row = 1;
    else
      row = 0;

    newTwin.swap(row, 0, row, 1);

    return newTwin;

  }

  // swap the values of 2 tiles
  private void swap(int i1, int j1, int i2, int j2) {
    int temp = board[i1][j1];
    board[i1][j1] = board[i2][j2];
    board[i2][j2] = temp;
  }

  /**
   * Compares this Board to the specified Board
   *
   * @param y
   *          the other Board
   * @return <tt>true</tt> if this date equals <tt>other</tt>; <tt>false</tt>
   *         otherwise
   */
  public boolean equals(Object y) // does this board equal y?
  {

    if (y == this)
      return true;
    if (y == null)
      return false;
    if (y.getClass() != this.getClass())
      return false;

    // make a copy of board y
    Board that = (Board) y;

    // check dimensions
    if (this.dimension() != that.dimension())
      return false;

    // check values one by one
    for (int i = 0; i < dim; i++)
      for (int j = 0; j < dim; j++)
        if (!(board[i][j] == that.board[i][j]))
          return false;
    return true;

  }

  /**
   * Returns a string representation of dimension and the board
   *
   * @return a string of dimension and the board
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(dim + "\n");
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * Returns all neighboring boards
   *
   * @return an iterable bag class
   */
  public Iterable<Board> neighbors() {
    Stack<Board> stackBoards = new Stack<Board>();

    // find zero
    int i0 = 0, j0 = 0;
    for (int i = 0; i < dim; i++)
      for (int j = 0; j < dim; j++)
        if (board[i][j] == 0) {
          i0 = i;
          j0 = j;
        }

    // top tile
    if (i0 > 0) {
      Board newBoard = new Board(board);
      newBoard.swap(i0 - 1, j0, i0, j0);
      stackBoards.push(newBoard);
    }
    // bottom tile
    if (i0 < dim - 1) {
      Board newBoard = new Board(board);
      newBoard.swap(i0 + 1, j0, i0, j0);
      stackBoards.push(newBoard);
    }
    // left tile
    if (j0 > 0) {
      Board newBoard = new Board(board);
      newBoard.swap(i0, j0, i0, j0 - 1);
      stackBoards.push(newBoard);
    }
    // right tile
    if (j0 < dim - 1) {
      Board newBoard = new Board(board);
      newBoard.swap(i0, j0, i0, j0 + 1);
      stackBoards.push(newBoard);
    }

    return stackBoards;
  }

  // Test Unit
  public static void main(String[] args) {

    int[][] table1 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };

    int[][] table2 = { { 0, 2, 1 }, { 4, 1, 6 }, { 7, 5, 3 } };

    int[][] table3 = { { 3, 2, 1 }, { 4, 1, 6 }, { 7, 5, 0 } };

    Board board1 = new Board(table1);
    Board board2 = new Board(table1);
    Board board3 = new Board(table2);
    Board board4 = new Board(table3);

    System.out.println("Print table 1");
    System.out.print(board1);

    System.out.println("Print table 2");
    System.out.print(board2);

    System.out.println("Print table 3");
    System.out.print(board3);

    System.out.println("Print table 4");
    System.out.print(board4);

    System.out.println("Hamming function " + board1.hamming());
    System.out.println("Manhattan function " + board1.manhattan());

    System.out.println("Is goal ? " + board1.isGoal());

    System.out.println("Get a twin \n" + board1.twin());

    System.out.println("Com. same tables, diff objects \n" + board1.equals(board2));
    System.out.println("Compare the same objects \n" + board1.equals(board1));
    System.out.println("Compare diff objects \n" + board1.equals(board3));

    System.out.println("print neigbors table 1");
    for (Board b : board1.neighbors())
      System.out.println(b);

    System.out.println("print neigbors table 2");
    for (Board b : board3.neighbors())
      System.out.println(b);

    System.out.println("print neigbors table 3");
    for (Board b : board4.neighbors())
      System.out.println(b);
  }
}
