import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import java.util.TreeSet;
import java.util.Stack;

public class WordNet {
  private Bag<Synset> synsetbag;
  private ST<String, Bag<Integer>> noun_2_tableid_symtable;
  private Digraph digraph;
  private SAP sap;
  private Synset a[];
  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms){
    In in;
    String line;
    String fields[];
    ST<Integer, Synset> id_2_synset_symtable = new ST<Integer, Synset>();
    int synset_id;
    int table_id = 0;

    noun_2_tableid_symtable = new ST<String, Bag<Integer>>();
    synsetbag = new Bag<Synset>(); 
    in = new In(synsets);
    line = in.readLine();
    while(line != null){
      // example of a line:
      // 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
      fields = line.split(",");
      synset_id = Integer.parseInt(fields[0]);
     // StdOut.printf("id = %d, nouns = %s, definition = %s\n", synset_id, fields[1], fields[2]);             
      line = in.readLine();
      Synset synset = new Synset(synset_id,fields[1].split(" "),fields[2],table_id);
      synsetbag.add(synset);
      id_2_synset_symtable.put(synset_id, synset);
      Bag<Integer> tablelist;
      for(String noun : fields[1].split(" ")){
        tablelist = noun_2_tableid_symtable.get(noun);
        if (tablelist == null){
          tablelist = new Bag<Integer>();
        }
        tablelist.add(table_id);
        noun_2_tableid_symtable.put(noun, tablelist);
      }
      
      table_id++; // replace table id with synset id
       // create a BST from synsets.txt, making isNoun return in logarithmic time
    } 
    int size = synsetbag.size();
    assert(table_id == synsetbag.size());
    a = new Synset[synsetbag.size()];
    for(Synset synset : synsetbag){
      a[synset.table_id] = synset;
    }
    //in.close();
    
    in = new In(hypernyms);
    line = in.readLine();
    // create a digraph based on hypernyms.txt and pass G to SAP class in distance and sap methods.
    digraph = new Digraph(synsetbag.size());
    while(line != null){
      // example of a line:
      // 164,21012,56099
      fields = line.split(",");
   //   StdOut.printf("synsetid = %d, hypernyms: ", Integer.parseInt(fields[0]));
      for(int i = 1; i < fields.length; i++){
     //   StdOut.printf("%d ", Integer.parseInt(fields[i]));
        int v = id_2_synset_symtable.get(Integer.parseInt(fields[0])).id;
        int w = id_2_synset_symtable.get(Integer.parseInt(fields[i])).id;
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
    for(Synset s : synsetbag){
      for(String noun : s.nouns){
        stack.push(noun);
      }
    }
    return stack;
  }
  
  // is the word a WordNet noun?
  public boolean isNoun(String word){
    return (noun_2_tableid_symtable.get(word) != null);
  }
  
  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB){
   // assert(isNoun(nounA));
    //assert(isNoun(nounB));
    Bag<Integer> table_ida = noun_2_tableid_symtable.get(nounA);
    Bag<Integer> table_idb = noun_2_tableid_symtable.get(nounB);
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    return sap.length(table_ida, table_idb);
  }
  
  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB){
    Bag<Integer> table_ida = noun_2_tableid_symtable.get(nounA);
    Bag<Integer> table_idb = noun_2_tableid_symtable.get(nounB);
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    if (table_idb ==null) throw new IllegalArgumentException("not a wordnet noun");
    
    int ancestor = sap.ancestor(table_ida, table_idb);
    if (ancestor == -1) throw new IllegalArgumentException("no common ancestor found!");
    String result = "";
    for(String n : a[ancestor].nouns){
      if (result!=""){
       result += " " + n;
      }else{
       result = n;
      }
    }
    return result;
  }

  // do unit testing of this class
  public static void main(String[] args){
    WordNet wn = new WordNet(args[0], args[1]);
    SAP sap = new SAP(wn.digraph);
    assert(wn.isNoun("a"));
    while (!StdIn.isEmpty()) {
      String m = StdIn.readString();
      String n = StdIn.readString();
      int distance   = wn.distance(m, n);
      String ancestor_name = wn.sap(m, n);
      StdOut.printf("length = %d, ancestor = %s\n", distance, ancestor_name);
    }
  }
}
