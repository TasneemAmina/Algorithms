import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

  private SET<Point2D> set;

  // construct an empty set of points
  public PointSET() {
    set = new SET<Point2D>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return set.isEmpty();
  }

  // number of points in the set
  public int size() {
    return set.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    checkPoint(p);
    if (!set.contains(p))
      set.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    checkPoint(p);
    return set.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    for (Point2D p : set)
      p.draw();
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {

    checkRect(rect);

    Bag<Point2D> bag = new Bag<Point2D>();

    for (Point2D p : set)
      if (rect.contains(p))
        bag.add(p);

    return bag;

  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {

    checkPoint(p);

    if (set == null)
      return null;

    Point2D near = null;

    double minDist = Double.MAX_VALUE;
    double Dist = minDist;

    for (Point2D point : set) {
      Dist = p.distanceTo(point);
      if (Dist < minDist) {
        near = point;
        minDist = Dist;
      }
    }
    return near;
  }

  private void checkPoint(Point2D p) {
    if (p == null)
      throw new java.lang.NullPointerException("Null point");
  }

  private void checkRect(RectHV rect) {
    if (rect == null)
      throw new java.lang.NullPointerException("Null rectangle");
  }

  /*
   * unit testing of the methods public static void main(String[] args) {
   * 
   * PointSET pointSet = new PointSET();
   * 
   * String inFile = args[0];
   * 
   * In in = new In(inFile); while (!in.isEmpty()) {
   * 
   * double x = in.readDouble(); double y = in.readDouble();
   * 
   * Point2D point = new Point2D(x, y); pointSet.insert(point);
   * 
   * StdOut.println("point:" + point); StdOut.println("nearest: " +
   * pointSet.nearest(point)); } pointSet.draw(); }
   */
}