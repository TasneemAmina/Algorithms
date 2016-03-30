import java.util.Arrays;

/***********************************************************************************
 * Compilation: javac BruteCollinearPoints.java Execution: BruteCollinearPoints
 * 
 * 
 * This program examines 4 points at a time and checks whether they all lie on
 * the same line segment, returning all such line segments. To check whether the
 * 4 points p, q, r, and s are collinear, the program checks whether the three
 * slopes between p and q, between p and r, and between p and s are all equal.
 *
 **********************************************************************************/
public class BruteCollinearPoints {

  private int           numLines; // number of lines
  private LineSegment[] lines;    // array of linear segments

  /**
   * Finds all line segments containing 4 points in an array of points draw.
   *
   * @param points
   *          array
   */
  public BruteCollinearPoints(Point[] points) {

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
    // point
    Arrays.sort(copy);

    // the three slopes to compute
    double slope1, slope2, slope3;

    // outer loop to compute first slope
    for (int i = 0; i < n; i++) {

      // second slope loop
      for (int j = i + 1; j < n; j++) {
        // compute first slope
        slope1 = copy[i].slopeTo(copy[j]);

        // Throw an Exception if there is a repeated point
        if (slope1 == Double.NEGATIVE_INFINITY)
          throw new java.lang.IllegalArgumentException("Repeated point.");

        // third slope loop
        for (int k = j + 1; k < n; k++) {
          slope2 = copy[j].slopeTo(copy[k]);

          // if the first two slopes are equal compute the third one
          if (slope1 == slope2) {
            // third slope loop
            for (int l = k + 1; l < n; l++) {
              slope3 = copy[k].slopeTo(copy[l]);
              if (slope1 == slope3 && slope2 == slope3) {
                // double size of array if necessary
                if (numLines == lines.length)
                  resize(2 * lines.length);
                // add line segment with collinear points
                lines[numLines] = new LineSegment(copy[i], copy[l]);
                numLines++;
              }
            }
          }
        }
      }
    }
    // remove null entries in array
    resize(numLines);
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
