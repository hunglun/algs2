/**
 * Auto Generated Java Class.
 */
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
public class SP {
  private double distTo[][];
  private MyDirectedEdge edgeTo[][];
  private int w,h, width, height;
  private double m[][];
  
  public SP(int w, int h, double m[][]){
    h = h + 1;
   
    this.w = w ;
    this.h = h;
    this.width = w;
    this.height = h;
    this.m = new double[w][h];
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h-1; j++){
        this.m[i][j] = m[i][j];
      }
    }
    this.m[0][h-1] = 0;
    this.m[1][h-1] = 0;
    
    // SP algorithm initialisation
    // TODO compute it for vertical seam in a separate method.
    
    distTo = new double[w][h];
    edgeTo = new MyDirectedEdge[w][h];
    for(int i = 0; i < w; i++)
      for(int j = 0; j < h; j++)
      distTo[i][j] = Double.POSITIVE_INFINITY;
    
    // vertical seam only
    TopologicalOrder top = new TopologicalOrder(w,h,m,true);
    distTo[0][h-1] = 0;
    /*
     for(int i=0; i< w;i++){
     distTo[i][0] = m[i][0];
     edgeTo[i][0] = new MyDirectedEdge(new Pair(w,h), new Pair(i,0),0);
     }
     */
    //StdOut.printf("SP - height %d, width %d\n\n", h, w);
    for(Pair v : top.order()){
      //StdOut.println("top order:  "+ v);
      
      relax(v, true);
    }
    
    
    /*
    for(int j=0; j< h-1;j++){
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
    }*/
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
      nb.add(new MyDirectedEdge(v,new Pair(1,this.height - 1),0));
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
  
  
  
  
 
  
  public boolean hasPathTo(Pair v) {
    //validateVertex(v);
    return distTo[v.x()][v.y()] < Double.POSITIVE_INFINITY;
  }
  public double distTo(Pair v) {
//        validateVertex(v);
    return distTo[v.x()][v.y()];
  }
  
  public int[] verticalSeam(){
    return verticalPathTo(new Pair(1,h-1));
  }
  public int[] verticalPathTo(Pair v){
//     validateVertex(v);
    if (!hasPathTo(v)) return null;
    Stack<MyDirectedEdge> path = new Stack<MyDirectedEdge>();
    for (MyDirectedEdge e = edgeTo[v.x()][v.y()]; e != null; e = edgeTo[e.from().x()][e.from().y()]) {
     // StdOut.println("vertical seam: "+e);
      path.push(e);
    }
    int[] apath = new int[path.size()-1];
    int count=0;
    path.pop(); // skip the virtual start node.
    while(!path.isEmpty()){
      apath[count] = path.pop().from().x();
      count++;
    }
  
    return apath;
  }
  
  private void relax(Pair v, boolean isVertical){
    for(MyDirectedEdge e : adj(v, isVertical)){
      Pair w = e.to();
     // StdOut.println(e);
      if(distTo[w.x()][w.y()] > distTo[v.x()][v.y()] + m[w.x()][w.y()]){
      //  StdOut.printf("Successfully relaxed : %4.0f ", distTo[w.x()][w.y()]);
        distTo[w.x()][w.y()] = distTo[v.x()][v.y()] + m[w.x()][w.y()];        
       // StdOut.printf("--> %4.0f\n" , distTo[w.x()][w.y()]);
        edgeTo[w.x()][w.y()] = e;
      }
    }
  }
  
}
