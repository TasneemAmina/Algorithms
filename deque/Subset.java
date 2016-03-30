import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

  public static void main(String[] args) {

    // read k
    int k = Integer.parseInt(args[0]);

    // create randomizedQueue class
    RandomizedQueue<String> q = new RandomizedQueue<String>();

    while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
      if (k != 0 && q.size() == k)
        q.dequeue();

      String item = StdIn.readString();
      if (k > 0)
        q.enqueue(item);
    }

    for (String str : q)
      StdOut.println(str);

  }

}
