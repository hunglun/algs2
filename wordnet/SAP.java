import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
public class SAP {
    private boolean[] marked;
    private Digraph G;
    private int ancestor;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
      //TODO create a defensive copy of G
      this.G = G;
      marked = new boolean[G.V()];
      ancestor = -1;

      
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
      BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G,v);
      BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G,w);
      
      int result = Integer.MAX_VALUE;
      int temp;
      for (int i = 0; i < this.G.V(); i++) {
        if (vbfs.hasPathTo(i) && wbfs.hasPathTo(i)){
           temp = vbfs.distTo(i) + wbfs.distTo(i);
           if (temp < result) 
           {
             result = temp;           
           }
        }
      }
      
      if (result == Integer.MAX_VALUE) return -1;
      
      return result;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
      BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G,v);
      BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G,w);
      
      int result = Integer.MAX_VALUE;
      int ancestor = -1;
      int temp;
      
      for (int i = 0; i < this.G.V(); i++) {
        if (vbfs.hasPathTo(i) && wbfs.hasPathTo(i)){
          temp = vbfs.distTo(i) + wbfs.distTo(i);
          if (temp < result) 
          {
            result = temp;  
            ancestor = i;
          }
        }
      }
      
      return ancestor;
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
      BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G,v);
      BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G,w);
      
      int result = Integer.MAX_VALUE;
      int temp;
      for (int i = 0; i < this.G.V(); i++) {
        if (vbfs.hasPathTo(i) && wbfs.hasPathTo(i)){
          temp = vbfs.distTo(i) + wbfs.distTo(i);
          if (temp < result) 
          {
            result = temp;           
          }
        }
      }
      
      if (result == Integer.MAX_VALUE) return -1;
      
      return result;
      
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
      BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(G,v);
      BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(G,w);
      
      int result = Integer.MAX_VALUE;
      int ancestor = -1;
      int temp;
      
      for (int i = 0; i < this.G.V(); i++) {
        if (vbfs.hasPathTo(i) && wbfs.hasPathTo(i)){
          temp = vbfs.distTo(i) + wbfs.distTo(i);
          if (temp < result) 
          {
            result = temp;  
            ancestor = i;
          }
        }
      }
      
      return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
      In in = new In(args[0]);
      Digraph G = new Digraph(in);
      SAP sap = new SAP(G);
      while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
      }
    }
}
