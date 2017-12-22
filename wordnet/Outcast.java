import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
  private WordNet wn;  
  public Outcast(WordNet wordnet){
    wn = wordnet;    
  }         // constructor takes a WordNet object
  public String outcast(String[] nouns){
    int d, max;
    String outcast = "";
    max = 0;    
    for(String n1 : nouns){
      d = 0;
      for(String n2 : nouns){
        d += wn.distance(n1,n2);
      }
      if (d > max) {
          max = d;
          outcast = n1;
      }
    }
    return outcast;
  }   // given an array of WordNet nouns, return an outcast
  public static void main(String[] args)  // see test client below
  {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
