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
  private ST<String, Integer> scoreboard;
  private int[] w,l,r;
  private int[][] g;
  private String[] names;
  private int numberOfTeams;
  private Bag<String> inCut;
  public BaseballElimination(String filename){
    if (filename == null) throw new IllegalArgumentException("invalid filename"); 
    In in = new In(filename);
    if (in == null) throw new IllegalArgumentException("file can't be opened."); 
    
    String name;
    scoreboard = new ST<String, Integer>();
    
    // reading the input
    numberOfTeams = in.readInt();
    names = new String[numberOfTeams];
    w = new int[numberOfTeams];
    l = new int[numberOfTeams];
    r = new int[numberOfTeams];
    g = new int[numberOfTeams][numberOfTeams];
    for(int i=0; i<numberOfTeams; i++){
      name = in.readString();
      w[i] = in.readInt();
      l[i] = in.readInt();
      r[i] = in.readInt();
      names[i]=name;
      for(int j=0; j<numberOfTeams; j++){
        g[i][j]=in.readInt();
      }
      scoreboard.put(name, i);
    }
  }                    // create a baseball division from given filename in format specified below

  public              int numberOfTeams(){
    return numberOfTeams;
  }                        // number of teams
  public Iterable<String> teams(){
    return scoreboard.keys();
  }                                // all teams
  public              int wins(String team){
    Integer stat = scoreboard.get(team);
    if (stat == null)  throw new IllegalArgumentException("error");
    return w[stat];
  }                      // number of wins for given team
  public              int losses(String team){
    Integer stat = scoreboard.get(team);
    if (stat == null)  throw new IllegalArgumentException("error");
    return l[stat];
  }                    // number of losses for given team
  public              int remaining(String team){
    Integer stat = scoreboard.get(team);
    if (stat == null)  throw new IllegalArgumentException("error");
    return r[stat];
  }                 // number of remaining games for given team
  public              int against(String team1, String team2){
    Integer stat1 = scoreboard.get(team1);
    if (stat1 == null)  throw new IllegalArgumentException("error");
    Integer stat2 = scoreboard.get(team2);
    if (stat2 == null)  throw new IllegalArgumentException("error");
    return g[stat1][stat2];
  }    // number of remaining games between team1 and team2
  
  public          boolean isEliminated(String team){
    // trivial case
    if (trivialCase(team).size() > 0 ) return true;
    
    // nontriviarl case
    // use arithmetic sum to compute the total number of binary combination of remaing teams.
    int games = ((numberOfTeams - 1) * (numberOfTeams - 2)) / 2;
    int v = 2 + games + (numberOfTeams - 1);
    FlowNetwork fn = new FlowNetwork(v);
    
// add flow edges
    
    
    Queue<Integer> otherTeams = new Queue<Integer>();
    for(String key : scoreboard){
      if(key == team) continue;
      otherTeams.enqueue(scoreboard.get(key));
    }
    
    // link game vertices and team vertices
    int gameid = numberOfTeams ;
    int home;
    
    // link start with game vertices
    int startid = scoreboard.get(team);
    int total_capacity = 0;
    while(!otherTeams.isEmpty()){
      home = otherTeams.dequeue();
      for (int guest : otherTeams){        
        fn.addEdge(new FlowEdge(gameid, guest,Double.POSITIVE_INFINITY));
        fn.addEdge(new FlowEdge(gameid, home,Double.POSITIVE_INFINITY));
        
        fn.addEdge(new FlowEdge(startid, gameid,g[home][guest]));
        assert(g[home][guest] == g[guest][home]);
        total_capacity += g[home][guest];
        gameid++;
      }
    }
    
    // link start with game vertices
/*    int startid = scoreboard.get(team);
    for(int i=numberOfTeams; i<gameid; i++){
      //TODO update capacity
      fn.addEdge(new FlowEdge(startid, i,Double.POSITIVE_INFINITY));
    }*/
    // link sink with team vertices
    int endid = v - 1;
    for(int i=0; i<numberOfTeams; i++){
      if(startid == i) continue;
      
      fn.addEdge(new FlowEdge(i, endid ,w[startid] + r[startid] - w[i]));
    }
   //StdOut.println("Flow network:" + fn + " End"); 
    
    FordFulkerson ff = new FordFulkerson(fn,startid, endid);
/*    StdOut.println("Total capacity: " + total_capacity);
    StdOut.println("Try to eliminate team " + team + " : " + startid);
    StdOut.println("Max flow value:" + ff.value()); 
  */  
    inCut = new Bag<String>();
    for(int i=0; i< numberOfTeams;i++){
      if (ff.inCut(i) && i!= startid)
        inCut.add(names[i]);
    }
    return total_capacity != ff.value();
  }              // is given team eliminated?
  

  public Iterable<String> certificateOfElimination(String team){
      Bag<String> result;
      result = trivialCase(team);
      if (result.size() > 0) return result;
      
      // non trivial case
      if (isEliminated(team)) return inCut;
      return null;
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
