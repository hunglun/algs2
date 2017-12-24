import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;
public class SeamCarver {
  private final int w;
  private final int h;
  private final Picture p;
  public SeamCarver(Picture picture)                {
    p = picture;
    w = p.width();
    h = p.height();
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
