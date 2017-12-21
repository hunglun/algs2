/**
 * Auto Generated Java Class.
 */
import edu.princeton.cs.algs4.ST;
public class TestST {
  
  public static ST<Synset, Integer> st;
  private static class Synset implements Comparable<Synset>{
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
      
      return this.nouns[0].compareTo(that.nouns[0]);
    }

  }
  public static void main(String[] args) { 
    st = new ST<Synset, Integer>();
    String[] s1 = {"a"};
    String[] s2 = {"b"};
    String[] s3 = {"cfadsfds"};
    String[] s4 = {"hello"};
    st.put(new Synset(1, s1, "babkka",1),0);
    st.put(new Synset(1, s2, "babkka",1),1);
    st.put(new Synset(1, s3, "babkka",1),2);
    assert(st.get(new Synset(0, s1, "",0)) == 0);
    assert(st.get(new Synset(0, s2, "",0)) == 1);
    assert(st.get(new Synset(0, s3, "",0)) == 2);
    assert(null == st.get(new Synset(0, s4, "",0)));
  }
  
  /* ADD YOUR CODE HERE */
  
}
