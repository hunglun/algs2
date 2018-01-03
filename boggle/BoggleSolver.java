import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import java.util.TreeSet;

public class BoggleSolver
{
  private TreeSet<String> dictset;
  private boolean marked[][];
  private int count;
  private StringBuilder word;
  private Bag<String> allValidWords;
  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary){
    dictset = new TreeSet<String>();
    allValidWords = new Bag<String>();
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
  
  private Iterable<Pair> adj(BoggleBoard board, Pair pair){
    Bag<Pair> bag = new Bag<Pair>();
    //StdOut.printf("Adjacent cells to %d, %d\n",pair.row,pair.col);
    for(int i=pair.col-1; i<pair.col+2; i++){
      for(int j=pair.row-1; j<pair.row + 2; j++){
        if( i<0 || j<0 || i>board.cols()-1 || j>board.rows()-1 || (i==pair.col && j==pair.row)) {
         // StdOut.printf("Out of bound:%d, %d\n",j,i);        
        }else{
          //StdOut.printf("%d, %d\n",j,i);
          bag.add(new Pair(i,j));
        }
      }
    }
      
    return bag;
  }
  private void clear_mark(){
    for(int i=0; i< marked.length; i++){
      for(int j=0; j< marked[0].length; j++){
        marked[i][j] = false;
      }
    }
  }
  private void dfs(BoggleBoard board, int col, int row){
   marked[col][row] = true;
   //StdOut.printf("%c ", board.getLetter(row,col));
   int orig_length = word.length();
   word.append(board.getLetter(row,col)); 
   
   if (word.length() == 3) {
      allValidWords.add(word.toString());
      word.deleteCharAt(word.length()-1); //TODO cache word.size()
    //  word.deleteCharAt(word.length()-1); //TODO cache word.size()
      //word = new StringBuilder();
      marked[col][row] = false;
      return;
   }
   
   
   for(Pair pair : adj(board, new Pair(col,row))){
     if(!marked[pair.col][pair.row]) {
       dfs(board,pair.col,pair.row);
     }
   }
  // word.delete(orig_length, word.length()-1);
   //marked[col][row] = false;
  
    
   
   //word = new StringBuilder();
  }
  
  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board){
    marked = new boolean[board.cols()][board.rows()];
    word = new StringBuilder();
    dfs(board,0,0);
    /*
     * 
    for(int c = 0; c < board.cols(); c++){
      for(int r = 0; r < board.rows(); r++){
        dfs(board,c,r);
        clear_mark();
      }
    }
    */
    return allValidWords;
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
    StdOut.println(board);
    int score = 0;
    int _count = 0;
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(_count++ + ":"+word);
      score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
  }
  
  
}
