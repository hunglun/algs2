import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination{
  private ST<String, Stat> scoreboard;
  private int numberOfTeams;
  public BaseballElimination(String filename){
    if (filename == null) throw new IllegalArgumentException("invalid filename"); 
    In in = new In(filename);
    if (in == null) throw new IllegalArgumentException("file can't be opened."); 
    int win, loss, remain;
    int[] game;
    String name;
    scoreboard = new ST<String, Stat>();
    
    // reading the input
    numberOfTeams = in.readInt();
    game = new int[numberOfTeams];
    for(int i=0; i<numberOfTeams; i++){
      name = in.readString();
      win = in.readInt();
      loss = in.readInt();
      remain = in.readInt();
      for(int j=0; j<numberOfTeams; j++){
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
    if (trivialCase(team).size() > 0 ) return true;
    
    // nontriviarl case
    // use arithmetic sum to compute the total number of binary combination of remaing teams.
    int games = ((numberOfTeams - 1) * numberOfTeams) / 2;
   
    FlowNetwork fn = new FlowNetwork(2 + games + (numberOfTeams - 1));
    
// add flow edges
    
    
    Queue<Stat> otherTeams = new Queue<Stat>();
    for(String key : scoreboard){
      if(key == team) continue;
      otherTeams.enqueue(scoreboard.get(key));
    }
    
    // link game vertices and team vertices
    int gameid = numberOfTeams;
    Stat home;
    while(!otherTeams.isEmpty()){
      home = otherTeams.dequeue();
      for (Stat guest : otherTeams){        
        fn.addEdge(new FlowEdge(gameid, guest.id,Double.POSITIVE_INFINITY));
        fn.addEdge(new FlowEdge(gameid, home.id,Double.POSITIVE_INFINITY));
        gameid++;
      }
    }
    
    // link start with game vertices
    /*int startid = gameid;
    for(int i=numberOfTeams; i<startid; i++){
      fn.addEdge(new FlowEdge(startid, i,Double.POSITIVE_INFINITY));
    */
    // link sink with team vertices
    StdOut.println("Flow network:" + fn + " End"); 
    return true;
  }              // is given team eliminated?
  

  public Iterable<String> certificateOfElimination(String team){
      Bag<String> result;
      result = trivialCase(team);
      if (result.size() > 0) return result;
      
      result = new Bag<String>();
      
      // non trivial case
      return result;
  }  // subset R of teams that eliminates given team; null if not eliminated
  
  // Private methods //////////////////////////////////////////////////////////
  private Bag<String> trivialCase(String team){
    //w[x] + r[x] < w[i]
    Bag<String> teams = new Bag<String>();
    int wx = wins(team);
    int rx = remaining(team);
    
    for(String other : teams()){
      if (wx + rx < wins(other)) teams.add(other);
    }
    return teams;
  }
    
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
