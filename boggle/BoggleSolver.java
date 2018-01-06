import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.TrieST;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.TreeSet;

public class BoggleSolver
{
 // private TreeSet<String> dictset;
  private StringBuilder word;
  private boolean marked[][];

  private TreeSet<String> allValidWords;
  private TrieST<Integer> st;
  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary){
    if(dictionary == null) throw new IllegalArgumentException("error");
    
    int count=0;
    //dictset = new TreeSet<String>();
    allValidWords = new TreeSet<String>();
    
    st = new TrieST<Integer>();
    for(int i=0; i< dictionary.length; i++){
      if (dictionary[i].indexOf("QU") != -1)
        dictionary[i] = dictionary[i].replaceAll("QU","Q");
    }
    for(String w : dictionary){
    
      //dictset.add(word);
      st.put(w,count++);
    }
  }
  private class Pair{
    public int col,row;
    public Pair(int i, int j){
      row = i;
      col = j;
    }
  }
  
  private Bag<Pair> adj(BoggleBoard board, Pair pair){
    Bag<Pair> bag = new Bag<Pair>();
    //StdOut.printf("Adjacent cells to %d, %d\n",pair.row,pair.col);
    // if it is the virtual start node
    if ((pair.row == -1) && (pair.col == -1)){
      for(int j=0;j<board.cols(); j++){
        for(int i=0;i<board.rows(); i++){
        bag.add(new Pair(i,j));
        }
      }
      return bag;
    }
    for(int i=pair.col-1; i<pair.col+2; i++){
      for(int j=pair.row-1; j<pair.row + 2; j++){
        if( i<0 || j<0 || i>board.cols()-1 || j>board.rows()-1 || (i==pair.col && j==pair.row)) {
         // StdOut.printf("Out of bound:%d, %d\n",j,i); 
          continue;
        }else{
          //StdOut.printf("%d, %d\n",j,i);
          if(!marked[j][i]) {
            bag.add(new Pair(j,i));
          }
        }
      }
    }
      
    return bag;
  }

  

  private boolean isValid(String w){
    return w.length()>2 && st.contains(w);
  }
  
  private boolean isPrefixInDictionary(String w){
    for(String s : st.keysWithPrefix(w)) {
      return true;
    }
    return false;
  }
                                       
                                 
  private void dfs(BoggleBoard board, int row, int col){
    
    Bag<Pair> nbs = adj(board, new Pair(row,col));
    String wordstring = word.toString();
    if(isValid(wordstring)) allValidWords.add(wordstring.replaceAll("Q","QU"));

    if(!isPrefixInDictionary(wordstring)) return; // important optimisation happens here.
    
    for(Pair pair : nbs){      
      word.append(board.getLetter(pair.row,pair.col));
      marked[pair.row][pair.col] = true;
      dfs(board,pair.row,pair.col);
      word.deleteCharAt(word.length()-1);
      marked[pair.row][pair.col] = false;  
    }
    
    
  }
  
  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board){
    if(board == null) throw new IllegalArgumentException("error");
    marked = new boolean[board.rows()][board.cols()];
    word = new StringBuilder();
    
    dfs(board,-1,-1);
    
    return allValidWords;
  }
  
  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word){
    if(word == null) throw new IllegalArgumentException("error");
    int length = word.length();
    //length += word.split("Q").length - 1;
      if (length == 3) return 1;
      if (length == 4) return 1;
      if (length == 5) return 2;
      if (length == 6) return 3;
      if (length == 7) return 5;
      if (length >= 8) return 11;
      assert(false); // shouldn't be here.
      return 0;
  }
  
  public static void main(String[] args) {
    In in = new In(args[0]);
    Stopwatch timer = new Stopwatch();
    
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(args[1]);
    int score = 0;
    StdOut.println(board);
    for (String word : solver.getAllValidWords(board)) {
      StdOut.println(word);
      score += solver.scoreOf(word);
    }
    StdOut.println("time elapsed:" + timer.elapsedTime());
    StdOut.println("Score = " + score);
  }
  

  
  
}
