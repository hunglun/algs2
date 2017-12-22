import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.TreeSet;
import java.util.Stack;

public class WordNet {
  private TreeSet<Synset> synsetsTree;
  private ST<String, Integer> st2;
  private Digraph digraph;
  private SAP sap;
  private Synset a[];
  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms){
    In in;
    synsetsTree = new TreeSet<Synset>();
    String line;
    String fields[];
    ST<Integer, Synset> st = new ST<Integer, Synset>();
    st2 = new ST<String, Integer>();
   
    in = new In(synsets);
    line = in.readLine();
    int table_id = 0;
  
    int synset_id;    
    while(line != null){
      // example of a line:
      // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
      fields = line.split(",");
     
      synset_id = Integer.parseInt(fields[0]);
     // StdOut.printf("id = %d, nouns = %s, definition = %s\n", synset_id, fields[1], fields[2]);             
      line = in.readLine();
      Synset synset = new Synset(synset_id,fields[1].split(" "),fields[2],table_id);
      synsetsTree.add(synset);
      st.put(synset_id, synset);
      String[] _noun = new String[1];
    
      for(String noun : fields[1].split(" "))
        st2.put(noun, table_id);
      
      table_id++;
       // create a BST from synsets.txt, making isNoun return in logarithmic time
    } 
    int size = synsetsTree.size();
    assert(table_id == synsetsTree.size());
    a = new Synset[synsetsTree.size()];
    for(Synset synset : synsetsTree){
      a[synset.table_id] = synset;
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
   //   StdOut.printf("synsetid = %d, hypernyms: ", Integer.parseInt(fields[0]));
      for(int i = 1; i < fields.length; i++){
     //   StdOut.printf("%d ", Integer.parseInt(fields[i]));
        int v = st.get(Integer.parseInt(fields[0])).id;
        int w = st.get(Integer.parseInt(fields[i])).id;
        digraph.addEdge(v,w);
      }
     // StdOut.printf("\n");                
      line = in.readLine();
      
    } 
    
    sap = new SAP(digraph);
  }
  private class Synset implements Comparable<Synset>{
    public final int id;
    public final String nouns[];
    public final int table_id;
    private String definition;

    public Synset(int id, String nouns[], String definition, int table_id){
      this.id = id;
      this.nouns = nouns;
      this.definition = definition;
      this.table_id = table_id;
    }
    public int compareTo(Synset that) {
      
      if( this.id > that.id ) return 1;
      if( this.id < that.id ) return -1;
      return 0;
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
 
    Object table_ida = st2.get(nounA);
    Object table_idb = st2.get(nounB);
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    return sap.length((Integer)table_ida, (Integer)table_idb);
  }
  
  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB){
    Object table_ida = st2.get(nounA);
    Object table_idb = st2.get(nounB);
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    
    int ancestor = sap.ancestor((Integer)table_ida, (Integer)table_idb);
    if (ancestor == -1) throw new IllegalArgumentException("no common ancestor found!");
    String result = "";
    for(String n : a[ancestor].nouns){
      result += n;
    }
    return result;
  }

  // do unit testing of this class
  public static void main(String[] args){
    WordNet wn = new WordNet(args[0], args[1]);
    SAP sap = new SAP(wn.digraph);
  /*
    assert(wn.isNoun("a"));
    for(String n : wn.nouns()){
      StdOut.printf("%s\n", n);
    }
    
    SAP sap = new SAP(wn.digraph);
    
    int length   = sap.length(0, 1);
    int ancestor = sap.ancestor(0, 1);     
    StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    
    length   = sap.length(0, 0);
    ancestor = sap.ancestor(0, 0);     
    StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    
    assert(wn.size() == 3);
    String m = "a";
    String n = "b";
    String ancestor_name = "";
    int distance   = wn.distance(m, n);
    ancestor_name = wn.sap(m, n);
    
    StdOut.printf("distance = %d, ancestor = %s\n", distance, ancestor_name);
*/   
    while (!StdIn.isEmpty()) {
//      int v = StdIn.readInt();
//      int w = StdIn.readInt();
//      int length   = sap.length(v, w);
//      int ancestor = sap.ancestor(v, w);     
//      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//      
      String m = StdIn.readString();
      String n = StdIn.readString();
      int distance   = wn.distance(m, n);
      String ancestor_name = wn.sap(m, n);
      
      StdOut.printf("length = %d, ancestor = %s\n", distance, ancestor_name);
      
    }
  }
}
