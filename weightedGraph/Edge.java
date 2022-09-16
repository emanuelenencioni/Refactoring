package weightedGraph;

/**
 * 
 */
public class Edge {

    
    public Edge(float weight, Vertex v1, Vertex v2) {
        this.weight = weight;
        this.vertex1 = v1;
        this.vertex2 = v2;
    }

    /**
     * @param Vertex v one of the vertices connected by the edge
     * @return the vertex connected to v by the edge
     */
    public Vertex getConnVertex(Vertex v) {
        if (v.equals(vertex1))
            return vertex2;
        if (v.equals(vertex2))
            return vertex1;
        return null;
    }

    public Vertex getVertex1(){
        return vertex1;
    }

    public Vertex getVertex2(){
        return vertex2;
    }


    /**
     * @return
     */
    public float getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge e = (Edge) obj;
            return e.weight == this.weight
                && ((e.vertex1.equals(this.vertex1) && e.vertex2.equals(this.vertex2)) || (e.vertex1.equals(this.vertex2) && e.vertex2.equals(this.vertex1)));
        }
        return false;
    }
    
    private float weight;
    private Vertex vertex1;
    private Vertex vertex2;


}