import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;

public class BaseballElimination{
  private ST<String, Stat> scoreboard;
  public BaseballElimination(String filename){
    if (filename == null) throw new IllegalArgumentException("invalid filename"); 
    In in = new In(filename);
    if (in == null) throw new IllegalArgumentException("file can't be opened."); 
    int win, loss, remain;
    int[] game;
    String name;
    scoreboard = new ST<String, Stat>();
    
    // reading the input
    int num = in.readInt();
    game = new int[num];
    for(int i=0; i<num; i++){
      name = in.readString();
      win = in.readInt();
      loss = in.readInt();
      remain = in.readInt();
      for(int j=0; j<num; j++){
        game[j]=in.readInt();
      }
      scoreboard.put(name, new Stat(i, win,loss,remain,game));
    }
  }                    // create a baseball division from given filename in format specified below
  private class Stat{
    public int id, win, loss, remain;
    public int[] game;
    public Stat(int id, int win, int loss, int remain, int[] game){
      this.id = id;
      this.win = win;
      this.loss = loss;
      this.remain = remain;
      this.game = new int[game.length];
      for(int j=0; j< game.length; j++){
        this.game[j] = game[j];
      }
    }
  }
  public              int numberOfTeams(){
    return scoreboard.size();
  }                        // number of teams
  public Iterable<String> teams(){
    return scoreboard.keys();
  }                                // all teams
  public              int wins(String team){
    Stat stat = scoreboard.get(team);
    if (stat == null)  throw new IllegalArgumentException("error");
    return stat.win;
  }                      // number of wins for given team
  public              int losses(String team){
    Stat stat = scoreboard.get(team);
    if (stat == null)  throw new IllegalArgumentException("error");
    return stat.loss;
  }                    // number of losses for given team
  public              int remaining(String team){
    Stat stat = scoreboard.get(team);
    if (stat == null)  throw new IllegalArgumentException("error");
    return stat.remain;
  }                 // number of remaining games for given team
  public              int against(String team1, String team2){
    Stat stat1 = scoreboard.get(team1);
    if (stat1 == null)  throw new IllegalArgumentException("error");
    Stat stat2 = scoreboard.get(team2);
    if (stat2 == null)  throw new IllegalArgumentException("error");
    return stat1.game[stat2.id];
  }    // number of remaining games between team1 and team2
  public          boolean isEliminated(String team){
    // trivial case
    if (isTriviallyEliminated(team)) return true;
    
    // nontrivial case
    return true;
  }              // is given team eliminated?
  
  private boolean isTriviallyEliminated(String team){
    //w[x] + r[x] < w[i]
    int wx = wins(team);
    int rx = remaining(team);
    
    for(String other : teams()){
      if (wx + rx < wins(other)) return true;
    }
    return false;
  }
  private Iterable<String> certificateOfTrivialElimination(String team){
    
    Bag<String> teams = new Bag<String>();
    for(String t : teams()){
      if (t !=  team) teams.add(t);        
    }
    return teams;
  }
  public Iterable<String> certificateOfElimination(String team){
    if (isTriviallyEliminated(team)) return certificateOfTrivialElimination(team);
    return scoreboard.keys();
  }  // subset R of teams that eliminates given team; null if not eliminated
  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      }
      else {
        StdOut.println(team + " is not eliminated");
      }
    }
  }
}
