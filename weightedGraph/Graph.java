package weightedGraph;

import java.util.*;

/**
 * 
 */
public class Graph {

    
    public Graph() {
        vertex = new ArrayList<Vertex>();
        edge = new ArrayList<Edge>();
    }

    /**
     * 
     */
    private ArrayList<Vertex> vertex;

    /**
     * 
     */
    private ArrayList<Edge> edge;




    /**
     * @return
     */
    public void addVertex(Vertex v) {
        vertex.add(v);
    }

    /**
     * @return
     */
    public void addEdge(Edge e) {
        edge.add(e);
    }

    /**
     * @param Vertex v 
     * @return
     */
    public void removeVertex(Vertex v) {
        vertex.remove(v);
    }

    /**
     * @param Edge e 
     * @return
     */
    public void removeEdge(Edge e) {
        edge.remove(e);
    }

}