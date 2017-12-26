/**
 * Auto Generated Java Class.
 */
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
public class SP {
  private double distTo[][];
  private MyDirectedEdge edgeTo[][];
  private int w,h;
  private double m[][];

  public SP(int w, int h, double m[][]){
    this.w = w;
    this.h = h;
    this.m = new double[w][h];
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h; j++){
        this.m[i][j] = m[i][j];
      }
    }
   // SP algorithm initialisation
    // TODO compute it for vertical seam in a separate method.

    distTo = new double[w][h];
    edgeTo = new MyDirectedEdge[w][h];
    for(int i = 0; i < w; i++)
      for(int j = 0; j < h; j++)
      distTo[i][j] = Double.POSITIVE_INFINITY;
    
    // vertical seam only
    TopologicalOrder top = new TopologicalOrder(w,h,m,true);
    for(int i=0; i< w;i++){
      distTo[i][0] = m[i][0];
      
    }
    for(Pair v : top.order()){
      //StdOut.println("top order:  "+ v);
      relax(v, true);
    }
    

  
    for(int j=0; j< h;j++){
      for(int i=0; i< w;i++){
        
        StdOut.printf("%4.0f ",m[i][j] );
      }
      StdOut.println();
    }
        StdOut.println();
    for(int j=0; j< h;j++){
      for(int i=0; i< w;i++){
        
        StdOut.printf("%4.0f ",distTo[i][j] );
      }
      StdOut.println();
    }
    
    for(int j=0; j< h;j++){
      for(int i=0; i< w;i++){
        
        StdOut.println(edgeTo[i][j] );
      }
      StdOut.println();
    }
  }
  
  private Iterable<MyDirectedEdge> adj_vertical(Pair v){
    Bag<MyDirectedEdge> nb = new Bag<MyDirectedEdge>();
    if (v.y == this.h - 1) return nb;
    nb.add(new MyDirectedEdge(v, new Pair(v.x,v.y+1),0));
    if (v.x != 0 ){
      nb.add(new MyDirectedEdge(v, new Pair(v.x-1,v.y+1),0));
    }
    if (v.x != this.w - 1 )
      nb.add(new MyDirectedEdge(v, new Pair(v.x+1,v.y+1),0));
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
  
  
  public int[] horizontalSeam(){
    return null;
  }
  
  public boolean hasPathTo(Pair v) {
    //validateVertex(v);
    return distTo[v.x][v.y] < Double.POSITIVE_INFINITY;
  }
   public double distTo(Pair v) {
//        validateVertex(v);
        return distTo[v.x][v.y];
    }

   public int[] verticalPathTo(Pair v){
//     validateVertex(v);
     if (!hasPathTo(v)) return null;
     Stack<MyDirectedEdge> path = new Stack<MyDirectedEdge>();
     for (MyDirectedEdge e = edgeTo[v.x][v.y]; e != null; e = edgeTo[e.from().x][e.from().y]) {
       path.push(e);
     }
     int[] apath = new int[path.size()];
     int count=0;
     for(MyDirectedEdge e : path){
       apath[count] = e.from().x;
       count++;
     }
     //apath[count]=v.x;
     return apath;
   }
  
  private void relax(Pair v, boolean isVertical){
    for(MyDirectedEdge e : adj(v, isVertical)){
      Pair w = e.to();
      if(distTo[w.x][w.y] > distTo[v.x][v.y] + m[w.x][w.y]){
        distTo[w.x][w.y] = distTo[v.x][v.y] + m[w.x][w.y];
        edgeTo[w.x][w.y] = e;
      }
    }
  }

}
