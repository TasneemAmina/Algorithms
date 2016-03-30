Algorithms part I
==================

# Week 1: percolation

**Percolation**. Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

**The model**. We model a percolation system using an N-by-N grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

<http://coursera.cs.princeton.edu/algs4/assignments/percolation.html>

# Week 2: Deque

**Dequeue**. A double-ended queue or deque is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure.

**Randomized queue**. A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure. 

<http://coursera.cs.princeton.edu/algs4/assignments/queues.html>

# Week 3: colinear
 
**The problem**. Given a set of N distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of the points.

**Brute force solution**. Program BruteCollinearPoints.java examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments.

**A faster, sorting-based solution**. Program FastCollinearPoints.java solves the problem much faster than the brute-force solution described above. Given a point p, the following method determines whether p participates in a set of 4 or more collinear points:

* Think of p as the origin.
* For each other point q, determine the slope it makes with p.
* Sort the points according to the slopes they makes with p.
* Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.

<http://coursera.cs.princeton.edu/algs4/assignments/collinear.html>

# Week 4: 8puzzle

**The problem**. The 8-puzzle problem is a puzzle invented and popularized by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square. Your goal is to rearrange the blocks so that they are in order, using as few moves as possible. You are permitted to slide blocks horizontally or vertically into the blank square. The following shows a sequence of legal moves from an initial board (left) to the goal board (right).

**Best-first search**. Program Solver.java illustrates a general artificial intelligence methodology known as the A* search algorithm using priority queues using Manhattan priority function.

<http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html>

# Week 5: KdTree 

**The problem**. Writing a data type to represent a set of points in the unit square using a 2d-tree to support efficient range search and nearest neighbor search. 2d-trees have numerous applications, ranging from classifying astronomical objects to computer animation to speeding up neural networks to mining data to image retrieval.

**Brute-force implementation** Program PointSET.java represents a set of points in the unit square using a red-black BST

**2d-tree implementation** Program KdTree.java uses a 2d-tree. A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence.

* Search and insert. The algorithms for search and insert are similar to those for BSTs, but at the root we use the x-coordinate (if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right); then at the next level, we use the y-coordinate (if the point to be inserted has a smaller y-coordinate than the point in the node, go left; otherwise go right); then at the next level the x-coordinate, and so forth.

* Range search. To find all points contained in a given query rectangle, start at the root and recursively search for points in both subtrees using the following pruning rule: if the query rectangle does not intersect the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). A subtree is searched only if it might contain a point contained in the query rectangle.

* Nearest neighbor search. To find a closest point to a given query point, start at the root and recursively search in both subtrees using the following pruning rule: if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). That is, a node is searched only if it might contain a point that is closer than the best one found so far. The effectiveness of the pruning rule depends on quickly finding a nearby point. To do this, organize your recursive method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.

<http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html>
