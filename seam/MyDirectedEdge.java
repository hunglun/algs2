import edu.princeton.cs.algs4.StdOut;
public class MyDirectedEdge { 
    private final Pair v;
    private final Pair w;


    /**
     * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
     * the given {@code weight}.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public MyDirectedEdge(Pair v, Pair w, double weight) {
        if (v.x() < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (w.x() < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (v.y() < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (w.y() < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = new Pair(v.x(),v.y());
        this.w = new Pair(w.x(),w.y());
  
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public Pair from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public Pair to() {
        return w;
    }


    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w;
    }

    /**
     * Unit tests the {@code MyDirectedEdge} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        MyDirectedEdge e = new MyDirectedEdge(new Pair(12, 34), new Pair(0,90), 5.67);
        StdOut.println(e);
    }
}
