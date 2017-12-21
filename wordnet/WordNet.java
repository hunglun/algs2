import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.TreeSet;
import java.util.Stack;

public class WordNet {
  private TreeSet<Synset> synsetsTree;
  ST<Synset, Integer> st2;
  Digraph digraph;
  SAP sap;
  Synset a[];
  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms){
    In in;
    synsetsTree = new TreeSet<Synset>();
    String line;
    String fields[];
    ST<Integer, Synset> st = new ST<Integer, Synset>();
    st2 = new ST<Synset, Integer>();
   
    in = new In(synsets);
    line = in.readLine();
    int table_id = 0;
  
    int synset_id;    
    while(line != null){
      // example of a line:
      // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
      fields = line.split(",");
      assert(fields.length == 3);
      synset_id = Integer.parseInt(fields[0]);
      StdOut.printf("id = %d, nouns = %s, definition = %s\n", synset_id, fields[1], fields[2]);             
      line = in.readLine();
      Synset synset = new Synset(synset_id,fields[1].split(" "),fields[2]);
      Synset synset2 = new Synset(table_id,fields[1].split(" "),fields[2]);
      synsetsTree.add(synset);
      st.put(synset_id, synset2);
      st2.put(synset2, table_id);
      
      table_id++;
       // create a BST from synsets.txt, making isNoun return in logarithmic time
    } 
    a = new Synset[synsetsTree.size()];
    for(Synset synset : synsetsTree){
      //a[synset.table_id] = synset2;
    }
    //in.close();
    
    in = new In(hypernyms);
    line = in.readLine();
    // create a digraph based on hypernyms.txt and pass G to SAP class in distance and sap methods.
    digraph = new Digraph(synsetsTree.size());
    while(line != null){
      // example of a line:
      // 164,21012,56099
      fields = line.split(",");
      StdOut.printf("synsetid = %d, hypernyms: ", Integer.parseInt(fields[0]));
      for(int i = 1; i < fields.length; i++){
        StdOut.printf("%d ", Integer.parseInt(fields[i]));
        int v = st.get(Integer.parseInt(fields[0])).id;
        int w = st.get(Integer.parseInt(fields[i])).id;
        digraph.addEdge(v,w);
      }
      StdOut.printf("\n");                
      line = in.readLine();
      
    } 
    
    sap = new SAP(digraph);
  }
  private class Synset implements Comparable<Synset>{
    public final int id;
    public final String nouns[];
    private String definition;

    public Synset(int id, String nouns[], String definition){
      this.id = id;
      this.nouns = nouns;
      this.definition = definition;
    }
    public int compareTo(Synset that) {
      for(String thisNoun : nouns){
        for(String thatNoun : that.nouns){
          if (thisNoun.compareTo(thatNoun) == 0) return 0;
        }
      }
      
      return 1; // don't care about other cases.
    }

  }
  // returns all WordNet nouns
  public Iterable<String> nouns(){
    Stack<String> stack = new Stack<String>();
    for(Synset s : synsetsTree){
      for(String noun : s.nouns){
        stack.push(noun);
      }
    }
    return stack;
  }
  
  // is the word a WordNet noun?
  public boolean isNoun(String word){
    for(String n: nouns()){
      if (n.equals(word)){
        return true;
      }
    }
    return false;
  }
  
  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB){
    if (isNoun(nounA) == false) return -1;
    if (isNoun(nounB) == false) return -1;
    String _nounA[] = new String[1];
    _nounA[0] = nounA;
    String _nounB[] = new String[1];
    _nounB[0] = nounB;
    
    int table_ida = st2.get(new Synset(1, _nounA, ""));
    int table_idb = st2.get(new Synset(1, _nounB, ""));
    return sap.length(table_ida, table_idb);
  }
  
  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB){
    if (isNoun(nounA) == false) throw new IllegalArgumentException("not a wordnet noun");
    if (isNoun(nounB) == false) throw new IllegalArgumentException("not a wordnet noun");
    String _nounA[] = new String[1];
    _nounA[0] = nounA;
    String _nounB[] = new String[1];
    _nounB[0] = nounB;
    
    int table_ida = st2.get(new Synset(1, _nounA, ""));
    int table_idb = st2.get(new Synset(1, _nounB, ""));
    
    int ancestor = sap.ancestor(table_ida, table_idb);
    return "";
  }
  
  // do unit testing of this class
  public static void main(String[] args){
    WordNet wn = new WordNet(args[0], args[1]);
    assert(wn.isNoun("a"));
    for(String n : wn.nouns()){
      StdOut.printf("%s\n", n);
    }
    
    SAP sap = new SAP(wn.digraph);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
