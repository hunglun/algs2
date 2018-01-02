import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;

public class BoggleSolver
{
  private TreeSet<String> dictset;
  private boolean marked[][];
  private int count;
  private StringBuilder word;
  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary){
    dictset = new TreeSet<String>();
    word = new StringBuilder();
    for(String word : dictionary){
      dictset.add(word);
    }
  }
  private class Pair{
    public int col,row;
    public Pair(int i, int j){
      col = i;
      row = j;
    }
  }
  
  private Iterable<Pair> adj(Pair pair){
    return new Stack<Pair>();
  }
  
  private void dfs(BoggleBoard board, int col, int row){
   marked[col][row] = true;
   count++;
   word.append(board.getLetter(row,col)); // String Builder TODO
   for(Pair pair : adj(new Pair(col,row))){
     if(!marked[pair.col][pair.row]) dfs(board,pair.col,pair.row);
   }
    
  }
  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board){
    marked = new boolean[board.cols()][board.rows()];
    for(int c = 0; c < board.cols(); c++){
      for(int r = 0; r < board.rows(); r++){
        dfs(board,c,r);
      }
    }
    return new Stack<String>();
  }
  
  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word){
    return 0;
    
  }
  
  public static void main(String[] args) {
    In in = new In(args[0]);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }
  
  
}
