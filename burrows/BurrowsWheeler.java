import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinarySearch;
import java.util.Arrays;
// build and run
// javac-algs4 BurrowsWheeler.java && java-algs4 BurrowsWheeler "-" < abra.txt | java-algs4 edu.princeton.cs.algs4.HexDump 16

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
  public static void transform(){

      
    String s = BinaryStdIn.readString();
    while(!BinaryStdIn.isEmpty()){
	s += BinaryStdIn.readString();
    }
//    StdOut.println(s);
    CircularSuffixArray sa = new CircularSuffixArray(s);
    for(int i=0;i<sa.length();i++){
      if(sa.index(i) == 0) {
        BinaryStdOut.write(i);
        
        break;
      }
    }
    int index;
    for(int i=0;i<sa.length();i++){
      index = sa.index(i);
      index = (index == 0) ? sa.length() - 1 : index - 1;
      BinaryStdOut.write(s.charAt(index));
//      StdOut.printf("%c", s.charAt(index));
    }
    BinaryStdOut.flush();
    
  }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
  public static void inverseTransform(){
    int first = BinaryStdIn.readInt();
    char t[] = BinaryStdIn.readString().toCharArray();
    int st[] = new int[t.length];

    int count[] = new int[256];
    for(int i=0; i<count.length; i++){
	count[i]=0;
    }
    for(int i=0; i<t.length; i++){
      st[i]=t[i];
    }
    
    Arrays.sort(st);
//    StdOut.println(first);
//    for(char c : st) StdOut.printf("%c",c);
//    StdOut.println(first);
//    for(char c : t) StdOut.printf("%c",c);
//    StdOut.println();
    
    boolean marked[] = new boolean[t.length];
    int next[] = new int[t.length];

    char c;
    int indexST;
    //    StdOut.println();
    for(int i=0; i<t.length; i++){
	c = t[i];
	indexST = BinarySearch.indexOf(st,c);

	// move index up
	while(indexST!=0 && st[indexST-1] == c) indexST--;
	
	indexST += count[c];
	count[c]++;
	next[indexST] = i;
	//	StdOut.printf("%d ",indexST);
    }
    
    //     for(int ch : next) StdOut.printf("%d ",ch);
    //    StdOut.println();
    
    int index = first;
    int _count = 0;
    while (_count < t.length ){
	BinaryStdOut.write((char)st[index]);
      index = next[index];
      _count++;
    }
    BinaryStdOut.flush();
//    StdOut.println();
  }

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
