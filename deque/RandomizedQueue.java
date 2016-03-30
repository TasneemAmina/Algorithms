
/****************************************************************************
 * 
 * Programming Assignment 2: RandomizedQueue 12/02/2016
 *
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * The <tt>RandomizedQueue</tt> class is similar to a stack or queue, except
 * that the item removed is chosen uniformly at random from items in the data
 * structure.
 * 
 * @author Konstantinos Vantas k_vantas@yahoo.gr
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] a; // array elements
  private int    N; // number of elements on queue

  /**
   * Construct an empty randomized queue
   */
  public RandomizedQueue() {
    a = (Item[]) new Object[2];
    N = 0;
  }

  /**
   * Is this queue empty?
   * 
   * @return true if this queue is empty; false otherwise
   */
  public boolean isEmpty() {
    return N == 0;
  }

  /**
   * Returns the number of items in this queue.
   * 
   * @return the number of items in this queue
   */
  public int size() {
    return N;
  }

  // resize the underlying array holding the elements
  private void resize(int capacity) {
    assert capacity >= N;
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < N; i++) {
      temp[i] = a[i];
    }
    a = temp;
  }

  /*
   * check if an item is null and throw exception
   */
  private void checkItem(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
  }

  /**
   * Adds the item to this queue.
   * 
   * @param item
   *          the item to add
   */
  public void enqueue(Item item) {
    // check for null item
    checkItem(item);

    // double size of array if necessary
    if (N == a.length)
      resize(2 * a.length);

    // add item
    a[N++] = item;

  }

  /**
   * Removes and returns a random item on this queue.
   * 
   * @return the item on this queue that was least recently added
   * @throws java.util.NoSuchElementException
   *           if this queue is empty
   */
  public Item dequeue() {
    if (isEmpty())
      throw new NoSuchElementException("Queue underflow");

    // replace last item with a random one
    int Index = StdRandom.uniform(0, N);
    Item item = a[Index];
    a[Index] = a[N - 1];
    a[N - 1] = item;

    // to avoid loitering
    a[N - 1] = null;
    N--;

    // shrink size of array if necessary
    if (N > 0 && N == a.length / 4)
      resize(a.length / 2);

    return item;

  }

  // return (but do not remove) a random item
  public Item sample() {
    if (isEmpty())
      throw new NoSuchElementException("Stack underflow");
    return a[StdRandom.uniform(0, N)];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomArrayIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  private class RandomArrayIterator implements Iterator<Item> {
    private int i = 0;

    public boolean hasNext() {
      return i < N;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      // create a shuffle random index array
      if (i == 0)
        StdRandom.shuffle(a);

      if (!hasNext())
        throw new NoSuchElementException();

      Item item = a[i % a.length];
      i++;
      return item;
    }
  }

  /**
   * Unit tests the <tt>RandomizedQueue</tt> data type.
   */
  public static void main(String[] args) {
    RandomizedQueue<String> s = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {

      String item = StdIn.readString();

      if (!item.equals("-"))
        s.enqueue(item);
      else if (!s.isEmpty())
        StdOut.print(s.dequeue() + " ");
    }
    StdOut.println("(" + s.size() + " left on stack)");

    // print using iterators
    StdOut.println("First run of iterator");
    for (String str : s)
      StdOut.print(str);

  }

}
