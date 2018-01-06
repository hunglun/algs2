import edu.princeton.cs.algs4.SuffixArray;
import edu.princeton.cs.algs4.StdOut;
public class CircularSuffixArray {
  
  private SuffixArray sa;
  public CircularSuffixArray(String s)    // circular suffix array of s
  {
    if(s == null) throw new IllegalArgumentException("error");
    sa = new SuffixArray(s);
  }
  public int length()                     // length of s
  {
    return sa.length();
  }
  public int index(int i)                 // returns index of ith sorted suffix
  {
    if(0>i || i>length()-1) throw new IllegalArgumentException("error");
    return sa.index(i);
  }
  public static void main(String[] args)  // unit testing (required)
  {
    String s = args[0];
    StdOut.println(s);
    CircularSuffixArray csa = new CircularSuffixArray(s);
    for(int i=0;i<csa.length();i++)
      StdOut.println(csa.index(i));
  }
}
