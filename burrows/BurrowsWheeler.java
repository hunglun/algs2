import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

// build and run
// javac-algs4 BurrowsWheeler.java && java-algs4 BurrowsWheeler "-" < abra.txt | java-algs4 edu.princeton.cs.algs4.HexDump 16

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
  public static void transform(){
    String s = StdIn.readString();
    CircularSuffixArray sa = new CircularSuffixArray(s);
    for(int i=0;i<sa.length();i++){
      if(sa.index(i) == 0) {
        BinaryStdOut.write(i);
        BinaryStdOut.flush();
      }
    }
    int index;
    for(int i=0;i<sa.length();i++){
      index = sa.index(i);
      index = (index == 0) ? sa.length() - 1 : index - 1;
      StdOut.printf("%c", s.charAt(index));
    }

    
  }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
  public static void inverseTransform(){}

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
  public static void main(String[] args){
    if (args.length !=1) throw new IllegalArgumentException("Only accept - or +");
    
    if(args[0].equals("-")) transform();
    else {
      if(args[0].equals("+")) inverseTransform();
      else throw new IllegalArgumentException("Only accept - or +");
    }
    
  }
}