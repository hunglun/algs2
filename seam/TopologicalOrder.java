/******************************************************************************
  *  Compilation:  javac TopologicalOrder.java
  *  Execution:    java TopologicalOrder digraph.txt
  *  Dependencies: Digraph.java Queue.java Stack.java StdOut.java
  *                EdgeWeightedDigraph.java DirectedEdge.java
  *  Data files:   http://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
  *                http://algs4.cs.princeton.edu/42digraph/tinyDG.txt
  *
  *  Compute preorder and postorder for a digraph or edge-weighted digraph.
  *  Runs in O(E + V) time.
  *
  *  % java TopologicalOrder tinyDAG.txt
  *     v  pre post
  *  --------------
  *     0    0    8
  *     1    3    2
  *     2    9   10
  *     3   10    9
  *     4    2    0
  *     5    1    1
  *     6    4    7
  *     7   11   11
  *     8   12   12
  *     9    5    6
  *    10    8    5
  *    11    6    4
  *    12    7    3
  *  Preorder:  0 5 4 1 6 9 11 12 10 2 3 7 8 
  *  Postorder: 4 5 1 12 11 10 9 6 0 3 2 7 8 
  *  Reverse postorder: 8 7 2 3 0 6 9 10 11 12 1 5 4 
  *
  ******************************************************************************/


/**
 *  The {@code TopologicalOrder} class represents a data type for 
 *  determining depth-first search ordering of the vertices in a digraph
 *  or edge-weighted digraph, including preorder, postorder, and reverse postorder.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <em>preorder</em>, <em>postorder</em>, and <em>reverse postorder</em>
 *  operation takes take time proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
public class TopologicalOrder {
  private boolean[][] marked;          // marked[v] = has v been marked in dfs?
  private Queue<Pair> postorder;  // vertices in postorder
  
  /**
   * Determines a depth-first order for the edge-weighted digraph {@code G}.
   * @param G the edge-weighted digraph
   */
  private int height;
  private int width;
  public TopologicalOrder(int width, int height, float[][] m, boolean isVertical) {
       
    int w=width;
    int h=height;
    this.height=height;
    this.width = width;
    //StdOut.printf("Topological sort - height %d, width %d\n\n", h, w);
    postorder = new Queue<Pair>();
    marked    = new boolean[w][h];
    for (int i = 0; i < width; i++)
      for (int j = 0; j < height-1; j++)
      if (!marked[i][j]) dfs(new Pair(i,j),isVertical);
    
    if (!marked[0][h-1]) dfs(new Pair(0,h-1),isVertical);
    
  }
  
  // run DFS in edge-weighted digraph G from vertex v and compute preorder/postorder
  private void dfs(Pair v, boolean isVertical) {
    marked[v.x()][v.y()] = true;
    for (MyDirectedEdge e : adj(v,isVertical)) {
      Pair w = e.to();
      //StdOut.println(e);
      if (!marked[w.x()][w.y()]) {
        dfs(w, isVertical);
      }
    }
    postorder.enqueue(v);
  }

  



  private Iterable<MyDirectedEdge> adj_vertical(Pair v){
    Bag<MyDirectedEdge> nb = new Bag<MyDirectedEdge>();
    
    // handle virtual nodes;
    if (v.y() == this.height - 1) {
      if(v.x() == 1) // the virtual end node
        return nb;
      if(v.x() == 0){ // connect virtual start node to the first row
        for(int i=0; i<this.width; i++)
          nb.add(new MyDirectedEdge(v,new Pair(i,0),0));
        return nb;
      }
      return nb; // ignore all other virtual nodes.
    };
    
    // connect last row to the end virtual node.
    if (v.y() == this.height - 2) {
      //nb.add(new MyDirectedEdge(v,new Pair(1,this.height - 1),0));
      return nb;
    };
    
    nb.add(new MyDirectedEdge(v, new Pair(v.x(),v.y()+1),0));
    if (v.x() != 0 ){
      nb.add(new MyDirectedEdge(v, new Pair(v.x()-1,v.y()+1),0));
    }
    if (v.x() != this.width - 1 )
      nb.add(new MyDirectedEdge(v, new Pair(v.x()+1,v.y()+1),0));
    //for (  MyDirectedEdge e : nb) StdOut.println(e);
    return nb;
  }
  private Iterable<MyDirectedEdge> adj_horizontal(Pair v){
// TODO
    return null;
  }
  
  private Iterable<MyDirectedEdge> adj(Pair v, boolean isVertical){
    
    return isVertical? adj_vertical(v) : adj_horizontal(v);
  }
  
  
  /**
   * Returns the vertices in reverse postorder.
   * @return the vertices in reverse postorder, as an iterable of vertices
   */
  public Iterable<Pair> order() {
    Stack<Pair> reverse = new Stack<Pair>();
    for (Pair v : postorder)
      reverse.push(v);
    return reverse;
  }
  
  
  
  
  
  /**
   * Unit tests the {@code TopologicalOrder} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
//        In in = new In(args[0]);
//        Digraph G = new Digraph(in);
    float m[][] = {
      {1,2,3},
      {4,5,6},
      {7,8,9}      
    };
    TopologicalOrder dfs = new TopologicalOrder(3,3,m,true);

    for (Pair pair : dfs.order()){
      StdOut.println(pair);
    }
//
//        StdOut.print("Preorder:  ");
//        for (int v : dfs.pre()) {
//            StdOut.print(v + " ");
//        }
//        StdOut.println();
//
//        StdOut.print("Postorder: ");
//        for (int v : dfs.post()) {
//            StdOut.print(v + " ");
//        }
//        StdOut.println();
//
//        StdOut.print("Reverse postorder: ");
//        for (int v : dfs.reversePost()) {
//            StdOut.print(v + " ");
//        }
//        StdOut.println();
    
    
  }
  
}

/******************************************************************************
  *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
  *
  *  This file is part of algs4.jar, which accompanies the textbook
  *
  *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
  *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
  *      http://algs4.cs.princeton.edu
  *
  *
  *  algs4.jar is free software: you can redistribute it and/or modify
  *  it under the terms of the GNU General Public License as published by
  *  the Free Software Foundation, either version 3 of the License, or
  *  (at your option) any later version.
  *
  *  algs4.jar is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU General Public License for more details.
  *
  *  You should have received a copy of the GNU General Public License
  *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
  ******************************************************************************/
