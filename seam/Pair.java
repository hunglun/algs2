public class Pair{
  private int x,y;
  public Pair(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public String toString() {
    return "[" + x + "," + y + "]" ;
  }
  public int x(){
    return x;
  }

  public int y(){
    return y;
  }
}
