import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;
import java.util.Iterator;

public class Solver 
{
    private int minMoves;
    private boolean solvableFlag;
    private Node finalBoard;
    private class Node implements Comparable<Node>
    {
      public int moves;
      public int manhattanValue;
      public Node predecessor;
      public Board board;
      
      public Node(Board b, Node prev, int moves)
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

    public Solver(Board initial)     
    {
      MinPQ<Node> PQ = new MinPQ<Node>();
      MinPQ<Node> PQtwin = new MinPQ<Node>();
      Node currentTwin = new Node(initial.twin(), null, 0);
      Node current = new Node(initial, null, 0);
      int movesCounter = 0, movesCounterTwin = 0;
      while (!current.board.isGoal() && !currentTwin.board.isGoal())
      {
        movesCounter++;
        movesCounterTwin++;
        for (Board b : current.board.neighbors())
          if (current.predecessor != null)
          {
            if(!b.equals(current.predecessor.board))
               PQ.insert(new Node(b, current, movesCounter)); 
          }
          else 
            PQ.insert(new Node(b, current, movesCounter)); 
        for (Board b : currentTwin.board.neighbors())
          if (currentTwin.predecessor != null)
          {
            if(!b.equals(currentTwin.predecessor.board))
              PQtwin.insert(new Node(b, currentTwin, movesCounterTwin)); 
          }
          else 
            PQtwin.insert(new Node(b, currentTwin, movesCounterTwin));          
        current = PQ.delMin();
        currentTwin = PQtwin.delMin();
        movesCounter = current.moves;
        movesCounterTwin = currentTwin.moves;
      }

      this.solvableFlag = current.board.isGoal();   
      if (this.solvableFlag)
      {
        this.minMoves = movesCounter;   
        this.finalBoard = current;
      }
    }
    
    public boolean isSolvable()            
    { 
      return this.solvableFlag;
    }

    public int moves()
    { 
      if(this.isSolvable())
        return this.minMoves; 
      else
        return -1;
    }      
              
    public Iterable<Board> solution()      
    {
      if (!this.isSolvable())
        return null;
      Stack<Board> sol = new Stack<Board>();
      Node current = this.finalBoard;
      while (current != null)
      {
        sol.push(current.board);
        current = current.predecessor;
      }
      return sol;
    }

    public static void main(String[] args) 
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
      if (!solver.isSolvable())
        StdOut.println("No solution possible");
      else 
      {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
          StdOut.println(board);
        StdOut.println("Minimum number of moves = " + solver.moves());
      }
    }
}