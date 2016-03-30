import java.util.Arrays;

/***********************************************************************************
 * Compilation: javac BruteCollinearPoints.java Execution: BruteCollinearPoints
 * 
 * 
 * This program examines 4 points at a time and checks whether they all lie on
 * the same line segment, returning all such line segments. To check whether the
 * 4 points p, q, r, and s are collinear, this program checks whether the three
 * slopes between p and q, between p and r, and between p and s are all equal.
 *
 **********************************************************************************/

public class FastCollinearPoints {

  private int           numLines; // number of lines
  private LineSegment[] lines;    // array of linear segments

  /**
   * Finds all line segments containing 4 points in an array of points draw.
   *
   * @param points
   *          array
   */
  public FastCollinearPoints(Point[] points) {

    // initialize variables
    numLines = 0;
    lines = new LineSegment[1];

    // Throw an Exception if the argument to the constructor is null
    if (points == null)
      throw new java.lang.NullPointerException("Empty array");

    // get the length of the array
    int n = points.length;

    // create a copy of points array
    Point[] copy = new Point[points.length];
    for (int i = 0; i < n; i++) {
      // Throw an Exception if any point in the array is null
      if (points[i] == null)
        throw new java.lang.NullPointerException("Null point");
      // copy points
      copy[i] = points[i];
    }

    // sort array of points, this way the calculations start from the lower left
    Arrays.sort(copy);

    // check points for repeated values
    checkPoints(copy);

    // create a copy of the point array
    Point[] temp = Arrays.copyOf(copy, n);

    // outer loop to set origin point p
    for (int i = 0; i < n; i++) {

      Point origin = copy[i];

      // exit method if n < 3
      if (n - i < 3)
        break;

      // For each other point sort these points with the slope it makes with p
      Arrays.sort(temp, origin.slopeOrder());

      int counter = 1; // counter of equal slopes
      double slope, nextSlope; // the slopes calculated in loop
      Point min = origin;
      Point max = origin;

      // compute first slope
      slope = origin.slopeTo(temp[1]);
      for (int j = 2; j < n; j++) {

        // compute next slope
        nextSlope = origin.slopeTo(temp[j]);

        if (slope == nextSlope) {
          // update values for collinear points
          counter++;
          min = (min.compareTo(temp[j]) > 0) ? temp[j] : min;
          min = (min.compareTo(temp[j - 1]) > 0) ? temp[j - 1] : min;
          max = (max.compareTo(temp[j]) < 0) ? temp[j] : max;
          max = (max.compareTo(temp[j - 1]) < 0) ? temp[j - 1] : max;
        } else {
          // check if a unique line found previously
          if (counter >= 3 && min == origin)
            addLine(origin, max);

          // reset values for collinear points
          counter = 1;
          min = origin;
          max = origin;
        }
        slope = nextSlope;
      }

      // check the last run for a unique line
      if (counter >= 3 && min == origin)
        addLine(origin, max);

    }

    // remove null entries in array
    resize(numLines);
  }

  private void addLine(Point p1, Point p2) {
    // check if the lines array have space
    if (numLines == lines.length)
      resize(2 * lines.length);

    // add line segment with collinear points
    lines[numLines] = new LineSegment(p1, p2);
    numLines++;

  }

  private void checkPoints(Point[] points) {
    for (int i = 1; i < points.length; i++) {
      if (points[i - 1].compareTo(points[i]) == 0)
        throw new java.lang.IllegalArgumentException("Repeated point.");
    }
  }

  // resize the underlying array holding the elements
  private void resize(int capacity) {
    LineSegment[] temp = new LineSegment[capacity];
    for (int i = 0; i < numLines; i++) {
      temp[i] = lines[i];
    }
    lines = temp;
  }

  // the number of line segments
  public int numberOfSegments() {
    return numLines;
  }

  // the line segments
  public LineSegment[] segments() {
    LineSegment[] l = new LineSegment[lines.length];

    for (int i = 0; i < lines.length; i++)
      l[i] = lines[i];
    return l;
  }

}
