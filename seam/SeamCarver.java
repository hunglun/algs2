import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Bag;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
  private int w;
  private int h;
  private double[][]m;

  private MyColor[][] p_color;

  private class MyColor {
    public int red,green,blue;
    public MyColor(int red, int green, int blue){
      this.red = red;
      this.green = green;
      this.blue = blue;
    }
  }  
  public SeamCarver(Picture picture)                {
    // compute energy matrix
    if (picture == null)  throw new IllegalArgumentException("error");
    
    w = picture.width();
    h = picture.height();
    m = new double[w][h];
    p_color = new MyColor[w][h];
    
    Color color;
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h; j++){
        color = picture.get(i,j);
        p_color[i][j] = new MyColor(color.getRed(),color.getGreen(),color.getBlue());
      }
    }
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h; j++){
        m[i][j] = energy(i,j);
       
      }
    }    
    //sp = new SP(w,h,m);
    
    
    
    //StdOut.printf("width %d, height %d\n", w, h);
    
  } // create a seam carver object based on the given picture
  

  
  public Picture picture()                          {
    Picture pic = new Picture(w, h);
    MyColor c;
    for(int i = 0; i < w ; i++){
      for(int j = 0; j < h; j++){
        c = p_color[i][j];
        pic.set(i,j,new Color (c.red,c.green,c.blue));
      }
    }
    return pic;
  } // current picture
  public     int width()                            {  
    return w;
  } // width of current picture
  public     int height()                           {
    return h;
  } // height of current picture
  private int gx(int x, int y){
//    StdOut.printf("x : %d, y : %d\n", x, y);
    MyColor right = p_color[x+1][y];
    MyColor left  = p_color[x-1][y];
    
    // red
    int r = right.red - left.red;
    int g = right.green - left.green;
    int b = right.blue - left.blue;
    
    return r*r + g*g + b*b;
  }
 
 
  private int gy(int x, int y){
    
 //  StdOut.printf("x : %d, y : %d\n", x, y);
    MyColor right = p_color[x][y+1];
    MyColor left  = p_color[x][y-1];
    
    // red
    int r = right.red - left.red;
    int g = right.green - left.green;
    int b = right.blue - left.blue;
    
    
    return r*r + g*g + b*b;
  }
  public  double energy(int x, int y)       
 {
    if (x>w-1 || y >h-1 || x < 0 || y <0)
      throw new IllegalArgumentException("error");
    if (x == 0 || y == 0 || y == this.h - 1 || x == this.w -1) return 1000;
    return Math.sqrt(gx(x,y) + gy(x,y));
  } // energy of pixel at column x and row y
  public   int[] findHorizontalSeam()    
  {
    SP sp_t;
    double [][]m_t = new double[h][w];
    for(int i = 0; i < h ; i++){
      for(int j = 0; j < w; j++){
        m_t[i][j] = m[j][i];
      }
    }    
    sp_t = new SP(h,w,m_t);
    return sp_t.verticalSeam();   
  } // sequence of indices for horizontal seam
  
  public   int[] findVerticalSeam()                 {
    SP sp = new SP(w,h,m);
    return sp.verticalSeam();
  } // sequence of indices for vertical seam
  
  public    void removeHorizontalSeam(int[] seam)   {
    if (seam == null) throw new IllegalArgumentException("error");
    if (seam.length != w) throw new IllegalArgumentException("error");
    int prev = seam[0];
    for(int i : seam){
      if (Math.abs(i - prev) > 1) throw new IllegalArgumentException("error");
      if(i < 0 ||  i > h -1)  throw new IllegalArgumentException("error");
      prev = i;
    }
    
    h = h - 1;
    for(int i = 0; i < w ; i++){
     for(int j = 0; j < h; j++){
       
       if (j >= seam[i]){
         p_color[i][j] = p_color[i][j+1];
       }
     }
   }  
   
    
   for(int j = 0; j < h; j++){
     for(int i = 0; i < w ; i++){
       m[i][j] = energy(i,j);
  //     StdOut.printf("%10.2f",m[i][j]);
     }
   //  StdOut.println();
   }   
   
   
  } // remove horizontal seam from current picture
  public    void removeVerticalSeam(int[] seam)     {
    if (seam == null) throw new IllegalArgumentException("error");
    if (seam.length != h) throw new IllegalArgumentException("error");
    int prev = seam[0];
    
    for(int i : seam){
      if (Math.abs(i - prev) > 1) throw new IllegalArgumentException("error");
      if(i < 0 ||  i > w -1)  throw new IllegalArgumentException("error");
      prev = i;
    }
    
    w = w - 1;
    for(int j = 0; j < h; j++){
      for(int i = 0; i < w ; i++){
        
        if (i >= seam[j]){
          p_color[i][j] = p_color[i+1][j];
          m[i][j] = m[i+1][j];
        }
      }
    }  
    
    for(int j = 0; j < h; j++){
      for(int i = 0; i < w ; i++){
        
        if (Math.abs(i - seam[j]) < 2){
          m[i][j] = energy(i,j);
        }
        //StdOut.printf("%10.2f",m[i][j]);
      }
     // StdOut.println();
    }   
    
  } // remove vertical seam from current picture
}
