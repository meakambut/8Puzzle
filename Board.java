import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.lang.Math;
import java.util.Stack;
import java.util.Iterator;

public final class Board 
{
    private final int[][] board;
    private Stack<Board> neighborsList = new Stack<Board>();

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

    public Board(int[][] blocks)          
    {
      if (blocks == null) throw new java.lang.IllegalArgumentException();
      int n = blocks.length;
      board = new int[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          board[i][j] = blocks[i][j];
      
    }

    public int dimension()  
    {
      return board.length;
    }              

    public int hamming()  
    {
      int value = 0;
      int n = this.dimension();
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (this.board[i][j] != i*n + j + 1)
            value++;
      return --value;
    }            
       
    public int manhattan()        
    {
      int value = 0;
      int n = this.dimension();
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (this.board[i][j] != 0)
            value += Math.abs(i - (this.board[i][j]-1)/n) + Math.abs(j - (this.board[i][j]-1)%n);
      return value;
    }      

    public boolean isGoal()  
    {
      int n = this.dimension();
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (!(i == n-1 && j == n-1))
            if (board[i][j] != i*n + j + 1)
              return false;
      return true;
    }               

    public Board twin()
    {
      int n = this.dimension();
      int[][] twinBoard = new int[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          twinBoard[i][j] = this.board[i][j];
      twinBoard[0][0] = this.board[0][1];
      twinBoard[0][1] = this.board[0][0];
      return new Board(twinBoard);
    }                    

    public boolean equals(Object y)  
    {
      if (y == this) return true;
      if (y == null) return false;
      if (y.getClass() != this.getClass()) return false;
      Board yBoard = (Board) y;
      if (yBoard.dimension() != this.dimension()) return false;
      int n = this.dimension();
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (yBoard.board[i][j] != this.board[i][j])
            return false;
      return true;
    }       

    public Iterator<Board> neighbors()
    {
      int n = this.dimension();
      int[][] neighboringBoard = new int[n][n];

      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          neighboringBoard[i][j] = this.board[i][j];
      outerloop:
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
        {
          if (this.board[i][j] == 0)
            {
              if (i > 0)
              {
                neighboringBoard[i-1][j] = this.board[i][j];
                neighboringBoard[i][j] = this.board[i-1][j]; 
                neighborsList.push(new Board(neighboringBoard));
                neighboringBoard[i-1][j] = this.board[i-1][j];
                neighboringBoard[i][j] = this.board[i][j]; 
              }
              
              if (i < n - 1)
              {
                neighboringBoard[i+1][j] = this.board[i][j];
                neighboringBoard[i][j] = this.board[i+1][j]; 
                neighborsList.push(new Board(neighboringBoard));
                neighboringBoard[i+1][j] = this.board[i+1][j];
                neighboringBoard[i][j] = this.board[i][j]; 
              }

              if (j > 0)
              {
                neighboringBoard[i][j-1] = this.board[i][j];
                neighboringBoard[i][j] = this.board[i][j-1]; 
                neighborsList.push(new Board(neighboringBoard)); 
                neighboringBoard[i][j-1] = this.board[i][j-1];
                neighboringBoard[i][j] = this.board[i][j]; 
              }

              if (j < n - 1)
              {
                neighboringBoard[i][j+1] = this.board[i][j];
                neighboringBoard[i][j] = this.board[i][j+1]; 
                neighborsList.push(new Board(neighboringBoard));
                neighboringBoard[i][j+1] = this.board[i][j+1];
                neighboringBoard[i][j] = this.board[i][j]; 
              }
              break outerloop;
            }  
        }
      return new ListNeighbors();
    }

    private class ListNeighbors implements Iterator<Board>    
    {        
      public void remove() {}
      public boolean hasNext() { return !neighborsList.isEmpty(); }                    
      public Board next() { return neighborsList.pop(); }    
    }

    public String toString() 
    {     
      int n = this.dimension();
      StringBuilder s = new StringBuilder();
      s.append(n + "\n");
      for (int i = 0; i < n; i++) 
      {
        for (int j = 0; j < n; j++) 
          s.append(String.format("%2d ", this.board[i][j]));
        s.append("\n");
      }
      return s.toString();
    }

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
      System.out.println("hamming = " + initial.hamming());
      System.out.println("manhattan = " + initial.manhattan());
      System.out.println("is the board the goal board: " + initial.isGoal());
      System.out.println("twin board: ");
      initial.twin().show();
      System.out.println("equals: " + initial.equals(initial.twin()));
      Iterator<Board> it = initial.neighbors();
    
      while (it.hasNext())
        System.out.println(it.next().toString());
    
    }
}