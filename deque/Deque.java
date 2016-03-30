
/****************************************************************************
 * 
 * Programming Assignment 2: Dequeue 12/02/2016
 *
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/**
 * The <tt>DeQueue</tt> class represents is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the
 * data structure.
 * 
 * @author Konstantinos Vantas k_vantas@yahoo.gr
 */
public class Deque<Item> implements Iterable<Item> {

  private int N;         // number of elements on queue
  private Node first;    // beginning of queue
  private Node last;     // end of queue

  // helper linked list class
  private class Node {
    private Item item;
    private Node next;
    private Node previous;
  }

  /**
   * Initializes an empty queue.
   */
  public Deque() {
    first = null;
    last = null;
    N = 0;
  }

  /**
   * Returns true if this queue is empty.
   *
   * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
   */
  public boolean isEmpty() {
    return (first == null || last == null);
  }

  /**
   * Returns the number of items in this queue.
   *
   * @return the number of items in this queue
   */
  public int size() {
    return N;
  }

  /*
   * check if an item is null and throw exception
   */
  private void checkItem(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
  }

  /**
   * Adds an item to the front of the queue
   *
   * @param an item
   * @throws java.lang.NullPointerException if item is null
   */
  public void addFirst(Item item) {
    // check for null item
    checkItem(item);

    // save old first node
    Node oldfirst = first;

    // create new first node
    first = new Node();
    first.item = item;
    first.next = oldfirst;

    // check if queue is empty
    if (isEmpty())
      last = first;
    else
      oldfirst.previous = first;

    // increment N
    N++;
  }

  /**
   * Adds an item to the end of the queue
   *
   * @param an item
   * @throws java.lang.NullPointerException if item is null
   */
  public void addLast(Item item) {
    // check for null item
    checkItem(item);

    // save old last node
    Node oldlast = last;

    // create new last node
    last = new Node();
    last.item = item;
    last.next = null;

    // check if queue is empty and set first or old last next node
    if (isEmpty())
      first = last;
    else {
      last.previous = oldlast;
      oldlast.next = last;
    }
    // increment N
    N++;
  }

  /**
   * Remove and return the item from the front
   *
   * @throws java.lang.NoSuchElementException if queue is empty
   * @return first item in queue
   */
  public Item removeFirst() {
    if (isEmpty())
      throw new NoSuchElementException("Queue underflow");

    // save first item to pop
    Item item = first.item;

    // change first item to second
    first = first.next;

    // decrement N
    N--;

    // clear first and last if queue is empty
    if (isEmpty()) {
      first = null;
      last = null;
    } else
      first.previous = null;

    return item;
  }

  /**
   * Remove and return the item from the front
   *
   * @throws java.lang.NoSuchElementException if queue is empty
   * @return first item in queue
   */
  public Item removeLast() {
    if (isEmpty())
      throw new NoSuchElementException("Queue underflow");

    // save first item to pop
    Item item = last.item;

    // change first item to second
    last = last.previous;

    // decrement N
    N--;

    // clear first and last if queue is empty
    if (isEmpty()) {
      first = null;
      last = null;
    } else
      last.next = null;

    return item;
  }

  /**
   * Returns an iterator that iterates over the items in this queue in FIFO order.
   * @return an iterator that iterates over the items in this queue in FIFO order
   */
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  // unit testing
  public static void main(String[] args) {

    // initialize a deque class
    Deque<String> q = new Deque<String>();

    q.addFirst("b");
    q.addFirst("a");
    q.addLast("c");
    q.addLast("d");
    q.removeFirst();
    q.removeLast();

    StdOut.println("(" + q.size() + " left on queue)");

    // print using iterators
    for (String s : q)
      StdOut.println(s);

  }
}
