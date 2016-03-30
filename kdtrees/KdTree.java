import java.awt.Color;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/*
 * A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is
 * to build a BST with points in the nodes, using the x- and y-coordinates of
 * the points as keys in strictly alternating sequence.
 */
public class KdTree {
  
  private Node    root;                        // root of the 2dTree
  private int     n;                           // size of the 2dTree
  private Point2D champion = null;             // nearest neighbor
  private double  minDist  = Double.MAX_VALUE; // minimum distance
  
  // kDtree helper node data type
  private class Node {
    private Point2D point;       // key
    private boolean useX;        // use x coordinate to insert
    private RectHV  rect;        // node rectangle
    private Node    left, right; // links to left and right subtrees
    
    public Node(Point2D point, boolean useX, RectHV rect) {
      this.point = point;
      this.useX = useX;
      this.rect = rect;
      this.left = null;
      this.right = null;
    }
  }
  
  /**
   * Initializes an empty KdTree.
   */
  public KdTree() {
    n = 0;
    root = null;
  }
  
  /**
   * Returns true if this KdTree is empty.
   * 
   * @return <tt>true</tt> if this KdTree is empty; <tt>false</tt> otherwise
   */
  public boolean isEmpty() {
    return (root == null);
  }
  
  /**
   * Returns the number of points in this KdTree.
   * 
   * @return the number of points in this KdTree
   */
  public int size() {
    return n;
  }
  
  // throws an exception if a point is null
  private void checkPoint(Point2D p) {
    if (p == null)
      throw new java.lang.NullPointerException("Null point");
  }
  
  // throws an exception if a rectangle is null
  private void checkRect(RectHV rect) {
    if (rect == null)
      throw new java.lang.NullPointerException("Null rectangle");
  }
  
  /**
   * Inserts the specified point into the KdTree, overwriting the old if it is
   * not already in the KdTree.
   *
   * @param p
   *          the point
   * @throws NullPointerException
   *           if <tt>p</tt> is <tt>null</tt>
   */
  public void insert(Point2D p) {
    checkPoint(p);
    root = put(root, p);
    root.rect = new RectHV(0, 0, 1, 1);
  }
  
  // compares a point and a node based on useX
  private int compareNP(Node node, Point2D p) {
    double dx = node.point.x() - p.x();
    double dy = node.point.y() - p.y();
    
    if (dx == 0 && dy == 0)
      return 0;
    
    if (node.useX && dx > 0)
      return 1;
    
    if (!node.useX && dy > 0)
      return 1;
    
    return -1;
  }
  
  // recursively find position for point p
  private Node put(Node x, Point2D p) {
    
    if (x == null) {
      n++;
      return new Node(p, true, null);
    }
    
    int cmp = compareNP(x, p);
    
    if (cmp == 0)
      return x;
    else if (cmp > 0) {
      x.left = put(x.left, p);
      x.left.useX = !x.useX;
      if (x.useX)
        x.left.rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
            x.point.x(), x.rect.ymax());
      else
        x.left.rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
            x.rect.xmax(), x.point.y());
    } else {
      x.right = put(x.right, p);
      x.right.useX = !x.useX;
      if (x.useX)
        x.right.rect = new RectHV(x.point.x(), x.rect.ymin(), x.rect.xmax(),
            x.rect.ymax());
      else
        x.right.rect = new RectHV(x.rect.xmin(), x.point.y(),
            x.rect.xmax(), x.rect.ymax());
      ;
    }
    return x;
  }
  
  /**
   * Returns true if this KdTree contains the point p.
   * 
   * @param p
   *          the point
   * @return <tt>true</tt> if this KdTree icontains the point p.y;
   *         <tt>false</tt> otherwise
   */
  public boolean contains(Point2D p) {
    checkPoint(p);
    return search(root, p);
  }
  
  // search recursively for point p
  private boolean search(Node x, Point2D p) {
    if (x == null)
      return false;
    
    // compare new node created from p with root
    int cmp = compareNP(x, p);
    if (cmp > 0)
      return search(x.left, p);
    else if (cmp < 0)
      return search(x.right, p);
    else
      return true;
  }
  
  private Iterable<Node> levelOrder() {
    Queue<Node> nodes = new Queue<Node>();
    Queue<Node> queue = new Queue<Node>();
    queue.enqueue(root);
    
    while (!queue.isEmpty()) {
      Node x = queue.dequeue();
      if (x == null)
        continue;
      nodes.enqueue(x);
      queue.enqueue(x.left);
      queue.enqueue(x.right);
    }
    return nodes;
  }
  
  /**
   * Draws all points to standard draw
   * 
   * @return void
   */
  public void draw() {
    for (Node node : levelOrder()) {
      StdDraw.setPenColor(Color.BLACK);
      StdDraw.setPenRadius(.01);
      node.point.draw();
      if (node.useX) {
        StdDraw.setPenRadius(.005);
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(),
            node.rect.ymax());
      } else {
        StdDraw.setPenRadius(.005);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(),
            node.point.y());
      }
    }
  }
  
  /**
   * Returns all points in the kDTree inside a query rectangle as an
   * <tt>Iterable</tt>. To iterate over all of the points in the rectangle
   * <tt>rect</tt>, use the foreach notation:
   * <tt>for (Point2D p : kd.range(rect))</tt>.
   *
   * @param rect
   *          the rectangle query
   * @return all keys in a rectangle
   */
  public Iterable<Point2D> range(RectHV rect) {
    
    checkRect(rect);
    
    Bag<Point2D> nodes = new Bag<Point2D>();
    Queue<Node> queue = new Queue<Node>();
    queue.enqueue(root);
    
    while (!queue.isEmpty()) {
      Node x = queue.dequeue();
      if (x == null)
        continue;
      if (rect.intersects(x.rect)) {
        if (rect.contains(x.point))
          nodes.add(x.point);
        queue.enqueue(x.left);
        queue.enqueue(x.right);
      }
    }
    return nodes;
  }
  
  /**
   * Returns the nearest point in the kDTree given a query point.
   *
   * @param query
   *          the point query
   * @return all keys in a rectangle
   */
  public Point2D nearest(Point2D query) {
    
    checkPoint(query);
    
    // set root as champion
    if (root == null)
      return null;
    champion = root.point;
    minDist = query.distanceSquaredTo(champion);
    
    // find nearest recursively
    nearest(root, query);
    return champion;
  }
  
  // recursively searches for the nearest point
  private void nearest(Node node, Point2D query) {
    
    // check if node is a new champion
    double dist = node.point.distanceSquaredTo(query);
    if (dist < minDist) {
      champion = node.point;
      minDist = dist;
    }
    
    // both nodes exist
    if (node.left != null && node.right != null) {
      
      // compute distances to query point
      double left = node.left.rect.distanceSquaredTo(query);
      double right = node.right.rect.distanceSquaredTo(query);
      
      // alternate searching based on smaller query distances
      if (left < right) {
        nearest(node.left, query);
        // prune right node if distance is less than minimum
        if (right < minDist)
          nearest(node.right, query);
      } else {
        nearest(node.right, query);
        // prune left node if distance is less than minimum
        if (left < minDist)
          nearest(node.left, query);
      }
      return;
    }
    
    // left node exists
    if (node.left != null) {
      // prune left node if distance is less than minimum
      if (node.left.rect.distanceSquaredTo(query) < minDist) {
        nearest(node.left, query);
      }
    }
    
    // right node exists
    if (node.right != null) {
      // prune right node if distance is less than minimum
      if (node.right.rect.distanceSquaredTo(query) < minDist) {
        nearest(node.right, query);
      }
    }
    return;
  }
  
  public static void main(String[] args) {
    
    KdTree kd = new KdTree();
    
    kd.insert(new Point2D(0.7, 0.2));
    kd.insert(new Point2D(0.7, 0.2));
    kd.insert(new Point2D(0.5, 0.4));
    kd.insert(new Point2D(0.2, 0.3));
    kd.insert(new Point2D(0.4, 0.7));
    kd.insert(new Point2D(0.9, 0.6));
    
    System.out.println("tree size = " + kd.size());
    
    RectHV rect = new RectHV(0, 0, 0.5, 0.5);
    // StdDraw.setPenColor(StdDraw.BLACK);
    // StdDraw.setPenRadius();
    // rect.draw();
    
    System.out.println("\n Points in rectangle [0, 0, 0.5, 0.5]");
    for (Point2D p : kd.range(rect))
      System.out.println(p);
    
    kd.draw();
    StdDraw.show();
    
    System.out.println("\n Nearest Point (1., 1.)");
    System.out.println(kd.nearest(new Point2D(1., 1.)));
    
  }
  
}
