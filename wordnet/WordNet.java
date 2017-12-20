import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.TreeSet;
public class WordNet {
  
  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms){
    In in;
    String line;
    String fields[];
    
    in = new In(synsets);
    line = in.readLine();
  
    int synset_id;    
    while(line != null){
      // example of a line:
      // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
      fields = line.split(",");
      assert(fields.length == 3);
      synset_id = Integer.parseInt(fields[0]);
      StdOut.printf("id = %d, nouns = %s, definition = %s\n", synset_id, fields[1], fields[2]);             
      line = in.readLine();
    } 
    
    
    in = new In(hypernyms);
    line = in.readLine();
  
     
    while(line != null){
      // example of a line:
      // 164,21012,56099
      fields = line.split(",");
      StdOut.printf("synsetid = %d, hypernyms: ", Integer.parseInt(fields[0]));
      for(int i = 1; i < fields.length; i++){
        StdOut.printf("%d ", Integer.parseInt(fields[i]));
      }
      StdOut.printf("\n");                
      line = in.readLine();
    } 
  }
  
  // returns all WordNet nouns
  public Iterable<String> nouns(){
    return new TreeSet<String>();
  }
  
  // is the word a WordNet noun?
  public boolean isNoun(String word){return false;}
  
  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB){return 0;}
  
  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB){return "";}
  
  // do unit testing of this class
  public static void main(String[] args){
    WordNet wn = new WordNet(args[0], args[1]);
    
  }
}
