import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;

public class Solver 
{
    private int moves = 0;
    private Board predecessor = null;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
      MinPQ<Board> PQ = new MinPQ<Board>();
      Iterator<Board> it = initial.neighbors();

      PQ.insert(initial);    
      while (it.hasNext())
        PQ.insert(it.next());
    }
    //public boolean isSolvable()            // is the initial board solvable?
    //public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    //public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    //public static void main(String[] args) // solve a slider puzzle (given below)
}