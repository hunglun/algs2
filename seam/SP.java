/**
 * Auto Generated Java Class.
 */
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.IndexMinPQ;
public class SP {
  private double distTo[];
  private Pair edgeTo[];
  private IndexMinPQ<Double> pq; 
  private int w,h;
  private int m[][];
  /* ADD YOUR CODE HERE */
  public SP(int w, int h, int m[][]){
    this.w = w;
    this.h = h;
    
    for(int i = 0; i < w*h ; i++){
      for(int j = 0; j < h; j++){
        this.m[i][j] = m[i][j];
      }
    }
   // SP algorithm initialisation
    pq = new IndexMinPQ<Double>(w*h+2);
    distTo = new double[w*h+2];
    edgeTo = new Pair[w*h+2];
    for(int i = 0; i < w*h; i++)
      distTo[i] = Double.POSITIVE_INFINITY;

    int start = w*h;
    distTo[start] = 0.0;
    pq.insert(start, 0.0);
    while(!pq.isEmpty()){
      relax(pq.delMin()); // TODO
    }
  
  }
  
   private class Pair{
    public int x,y;
    public Pair(int x, int y){
      this.x = x;
      this.y = y;
    }
  }
  private Iterable<Pair> adj(int x, int y){
    Bag<Pair> nb = new Bag<Pair>();
    
    // special case 1
    if(x == 0 && y == h + 1){
      for(int i = 0; i < this.w; i++){
        nb.add(new Pair(0,i));
      }
      return nb;
    }
    // special case 2
    if(x == w && y == h){
      for(int i = 0; i < this.w; i++){
        nb.add(new Pair(0,i));
      }
      return nb;
    }
    for(int i = x-1; i <= x+1; i++){
      for(int j = y-1; j <= y+1; j++){
        if (i < 0 || j < 0 || j > this.h - 1 || i > this.w - 1) continue;
        nb.add(new Pair(i,j));
      }
    }
    return nb;
  }
  
  private void relax(int i){
    int y = i / w;
    int x = i % w;
      
    for(Pair nb : adj(x,y)){
      if(distTo[nb.x + w * nb.y] > distTo[nb.x + w * nb.y] + m[x][y]){
        distTo[nb.x + w * nb.y] = distTo[nb.x + w * nb.y] + m[x][y];
        edgeTo[nb.x + w* nb.y] = nb;
        int key = nb.x  + this.w * nb.y;
        if (pq.contains(key)) pq.changeKey(key,distTo[nb.x + w * nb.y]);
        else pq.insert(key,distTo[nb.x + w * nb.y]);
      }
    }
  }
  
}
