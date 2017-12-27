import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Bag;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
public class SeamCarver {
  private int w;
  private int h;
  private final Picture p;
  private double[][]m, m_t;
  private SP sp, sp_t;
  
  public SeamCarver(Picture picture)                {
    // compute energy matrix
    p = picture;
    w = p.width();
    h = p.height();
    m = new double[w][h];
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h; j++){
        m[i][j] = energy(i,j);
      }
    }    
    sp = new SP(w,h,m);
    
    m_t = new double[h][w];
    for(int i = 0; i < h ; i++){
      for(int j = 0; j < w; j++){
        m_t[i][j] = energy(j,i);
      }
    }    
    sp_t = new SP(h,w,m_t);
    
    StdOut.printf("width %d, height %d\n", w, h);
    
  } // create a seam carver object based on the given picture
  

  
  public Picture picture()                          {
    return p;
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
    
    
    return sp_t.verticalSeam();
    
  } // sequence of indices for horizontal seam
  public   int[] findVerticalSeam()                 {
   
    return sp.verticalSeam();

  } // sequence of indices for vertical seam
  public    void removeHorizontalSeam(int[] seam)   {
    
    double[][] new_m = new double[w][h-1]; 
    Queue<Integer> q = new Queue<Integer>();
    for(int i: findHorizontalSeam())
      q.enqueue(i);
    
    int count = 0;
    int hseami = q.dequeue();
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h; j++){
        if (hseami == j) {
          hseami = q.dequeue();
        }else{
          new_m[count%w][count/w] = m[i][j];
          count++;
        }
      }
    }
    
    m = new_m;
    h--;
    sp = new SP(w,h,m);
  } // remove horizontal seam from current picture
  public    void removeVerticalSeam(int[] seam)     {
    
    double[][] new_m = new double[w-1][h]; 
    Queue<Integer> q = new Queue<Integer>();
    for(int i: findVerticalSeam())
      q.enqueue(i);
    
    int count = 0;
    int vseami = q.dequeue();
    for(int i = 0; i < h ; i++){
      for(int j = 0; j < w; j++){
        if (vseami == j) {
          vseami = q.dequeue();
        }else{
          new_m[count%w][count/w] = m[i][j];
          count++;
        }
      }
    }
    
    m = new_m;
    w--;
    
    sp = new SP(w,h,m);
  } // remove vertical seam from current picture
}
