import edu.princeton.cs.algs4.StdOut;
public class MoveToFront {
  private static final int R = 256;        // extended ASCII
  private static char A[];
  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode(){
    // initialise ascii array
    A = new char[R];
    for(char i=0; i<R; i++)
      A[i]=i;
    int out = 0;
    // read one 8-bit char at a time
    while (!BinaryStdIn.isEmpty()) {
      char in = BinaryStdIn.readChar();
      // locate character match
      for(int i=0;i<R;i++){
        if(A[i] == in){
          out = i;
          break;
        }
      }
     
      // output the match
      BinaryStdOut.write((char)out);
      // shift alphabet
      for(int i=out;i>0;i--)
        A[i]=A[i-1];
      
      A[0]=in;
//      StdOut.println("In:" + in + " Out:"+ out);
//      for(char c:A) StdOut.printf("%c ",c);
//      StdOut.println();
    }
    BinaryStdOut.flush();
    
  }
  
  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode(){
    A = new char[R];
    for(char i=0; i<R; i++)
      A[i]=i;
    char out = 0;
    // read one 8-bit char at a time
    while (!BinaryStdIn.isEmpty()) {
      char in = BinaryStdIn.readChar();
      out = A[in];
     
      // output the match
      BinaryStdOut.write((char)out);
      // shift alphabet
      for(int i=in;i>0;i--)
        A[i]=A[i-1];
      
      A[0]=out;

    }
    BinaryStdOut.flush();
  
  }
  
  // if args[0] is '-', apply move-to-front encoding
  // if args[0] is '+', apply move-to-front decoding
  public static void main(String[] args){
    if (args.length !=1) throw new IllegalArgumentException("Only accept - or +");
    if(args[0].equals("-")) encode();
    else {
      if(args[0].equals("+")) decode();
      else throw new IllegalArgumentException("Only accept - or +");
    }
  }
}