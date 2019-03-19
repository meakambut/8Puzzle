import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;

public class Solver 
{
    private int minMoves;
    private class Node implements Comparable<Node>
    {
      public int moves;
      public int manhattanValue;
      public Board predecessor;
      public Board board;
      
      public Node(Board b, Board prev, int moves)
      {  
        this.board = b; 
        this.predecessor = prev;
        this.manhattanValue = this.board.manhattan();
        this.moves = moves;
      }
 
      public int compareTo(Node b)   
      { 
        if (this.manhattanValue + this.moves < b.manhattanValue + b.moves)
          return -1; 
        else     
          if (this.manhattanValue + this.moves > b.manhattanValue + b.moves)   
            return +1;    
          else 
            if (this.moves > b.moves)
              return -1;
            else
              if (this.moves < b.moves)
                return +1;
              else
                return 0;   
      } 
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
      MinPQ<Node> PQ = new MinPQ<Node>();
 
      Node current = new Node(initial, null, 0);
      int movesCounter = 0;
      while (!current.board.isGoal())
      {
        movesCounter++;
        for (Board b : current.board.neighbors())
          if(!b.equals(current.predecessor))
            PQ.insert(new Node(b, current.board, movesCounter));          
        current = PQ.delMin();
        movesCounter = current.moves;
      }
      this.minMoves = movesCounter;
      System.out.println("goal board: \n" + current.board.toString());
      System.out.println("moves made: " + movesCounter); 
      
    }
    //public boolean isSolvable()            // is the initial board solvable?
    public int moves()
    { return this.minMoves; }                     // min number of moves to solve initial board; -1 if unsolvable
    //public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);
      System.out.println("initial: \n" + initial.toString());
      Solver solver = new Solver(initial);
    }
}