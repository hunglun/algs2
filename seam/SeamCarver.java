import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Bag;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.IndexMinPQ;
public class SeamCarver {
  private final int w;
  private final int h;
  private final Picture p;
  private final double[][] m;
  private double distTo[][];
  private Pair edgeTo[][];
  private IndexMinPQ<Double> pq;
  
  public SeamCarver(Picture picture)                {
    p = picture;
    w = p.width();
    h = p.height();
    pq = new IndexMinPQ<Double>(w*h+2);
    m = new double[w][h];
    distTo = new double[w][h+2];
    edgeTo = new Pair[w][h+2];
    for(int i = 0; i < w; i++){
      for(int j = 0; j < h; j++){
        m[i][j] = energy(i,j);
        distTo[i][j] = Double.POSITIVE_INFINITY;
//        edgeTo[i][j] = Double.POSITIVE_INFINITY;
      }
    }
    int start = w*h;
//    int end = w*h+1;
    distTo[w-1][h-1] = 0.0;
    pq.insert(w*h, 0.0);
    while(!pq.isEmpty())
      relax(0,0); // TODO
    
    
    StdOut.printf("widht %d, height %d\n", w, h);
    
  } // create a seam carver object based on the given picture
  public Picture picture()                          {
    return null;
  } // current picture
  public     int width()                            {  
    return w;
  } // width of current picture
  public     int height()                           {
    return h;
  } // height of current picture
  private int gx(int x, int y){
//    StdOut.printf("x : %d, y : %d\n", x, y);
    Color right = p.get(x+1,y);
    Color left  = p.get(x-1,y);
    
    // red
    int r = right.getRed() - left.getRed();
    int g = right.getGreen() - left.getGreen();
    int b = right.getBlue() - left.getBlue();
    
    return r*r + g*g + b*b;
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
    for(int i = x-1; i <= x+1; i++){
      for(int j = y-1; j <= y+1; j++){
        if (i < 0 || j < 0 || j > this.h - 1 || i > this.w - 1) continue;
        nb.add(new Pair(i,j));
      }
    }
    return nb;
  }
  
  private void relax(int x, int y){
    for(Pair nb : adj(x,y)){
      if(distTo[nb.x][nb.y] > distTo[x][y] + m[x][y]){
        distTo[nb.x][nb.y] = distTo[x][y] + m[x][y];
        edgeTo[nb.x][nb.y] = nb;
        int key = nb.x * this.w + nb.y;
        if (pq.contains(key)) pq.changeKey(key,distTo[nb.x][nb.y]);
        else pq.insert(key,distTo[nb.x][nb.y]);
      }
    }
  
  }
  private int gy(int x, int y){
    
 //  StdOut.printf("x : %d, y : %d\n", x, y);
    Color right = p.get(x,y+1);
    Color left  = p.get(x,y-1);
    
    // red
    int r = right.getRed() - left.getRed();
    int g = right.getGreen() - left.getGreen();
    int b = right.getBlue() - left.getBlue();
    
    return r*r + g*g + b*b;
  }
  public  double energy(int x, int y)               {
    if (x == 0 || y == 0 || y == this.h - 1 || x == this.w -1) return 1000;
    return Math.sqrt(gx(x,y) + gy(x,y));
  } // energy of pixel at column x and row y
  public   int[] findHorizontalSeam()               {
    return null;
  } // sequence of indices for horizontal seam
  public   int[] findVerticalSeam()                 {
    return null;
  } // sequence of indices for vertical seam
  public    void removeHorizontalSeam(int[] seam)   {
  } // remove horizontal seam from current picture
  public    void removeVerticalSeam(int[] seam)     {
  } // remove vertical seam from current picture
}
