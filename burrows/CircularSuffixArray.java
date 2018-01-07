import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import java.util.Arrays;
public class CircularSuffixArray {
    private Suffix[] suffixes;

    /**
     * Initializes a suffix array for the given {@code text} string.
     * @param text the input string
     */
    public CircularSuffixArray(String text) {
	if(text==null) throw new IllegalArgumentException();
        int n = text.length();
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(text, i);
        Arrays.sort(suffixes);
    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }
        private int length() {
            return text.length();
        }
        private char charAt(int i) {
            return text.charAt((index + i) % length());
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;  // optimization
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }

        public String toString() {
            return text.substring(index) + text.substring(0,index);
        }
    }

    /**
     * Returns the length of the input string.
     * @return the length of the input string
     */
    public int length() {
        return suffixes.length;
    }


    /**
     * Returns the index into the original string of the <em>i</em>th smallest suffix.
     * That is, {@code text.substring(sa.index(i))} is the <em>i</em>th smallest suffix.
     * @param i an integer between 0 and <em>n</em>-1
     * @return the index into the original string of the <em>i</em>th smallest suffix
     * @throws java.lang.IllegalArgumentException unless {@code 0 <= i < n}
     */
    public int index(int i) {
        if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();
        return suffixes[i].index;
    }



    // longest common prefix of s and t
    private static int lcpSuffix(Suffix s, Suffix t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) return i;
        }
        return n;
    }


    // compare query string to suffix
    private static int compare(String query, Suffix suffix) {
        int n = Math.min(query.length(), suffix.length());
        for (int i = 0; i < n; i++) {
            if (query.charAt(i) < suffix.charAt(i)) return -1;
            if (query.charAt(i) > suffix.charAt(i)) return +1;
        }
        return query.length() - suffix.length();
    }
    public static void main(String[] args)  // unit testing (required)
    {
	String s = args[0];
	StdOut.println(s);
	CircularSuffixArray csa = new CircularSuffixArray(s);
	for(int i=0;i<csa.length();i++){
	    StdOut.printf("%2d ", csa.index(i));
	    for(int j=csa.index(i); j<csa.length();j++){
		StdOut.printf("%c",s.charAt(j));
	    }
	    for(int j=0; j<csa.index(i);j++){
		StdOut.printf("%c",s.charAt(j));
	    }
	    StdOut.println();
	}
	StdOut.println();

	for(int i=0;i<csa.length();i++){
	    StdOut.printf("%2d ", csa.index(i));
	    for(int j=csa.index(i); j<csa.length();j++){
		StdOut.printf("%c",s.charAt(j));
	    }
	    StdOut.println();
	}
	StdOut.println();

    }
}
