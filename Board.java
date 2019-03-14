import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {
    private int[][] board;

    private void show()    
    {
      int n = board.length;
      for (int i = 0; i < n; i++)
      {
        for (int j = 0; j < n; j++)
          System.out.print(board[i][j] + " ");
        System.out.println();
      }
    }

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
      if (blocks == null) throw new java.lang.IllegalArgumentException();
      int n = blocks.length;
      board = new int[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          board[i][j] = blocks[i][j];
    }                                       // (where blocks[i][j] = block in row i, column j)
    public int dimension()  
    {
      return 0;
    }               // board dimension n
    public int hamming()  
    {
      return 0;
    }                   // number of blocks out of place
    public int manhattan()        
    {
      return 0;
    }       // sum of Manhattan distances between blocks and goal
    public boolean isGoal()  
    {
      return false;
    }                // is this board the goal board?
    /*public Board twin()
    {
      return null;
    }*/                      // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)  
    {
      return false;
    }        // does this board equal y?
    //public Iterable<Board> neighbors()     // all neighboring boards
    //public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args)
    {
      // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);
    initial.show();
    } // unit tests (not graded)
}