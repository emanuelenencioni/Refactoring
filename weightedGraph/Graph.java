package weightedGraph;

import java.util.*;

import applicationModel.Entity;

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
     * if the graph already contains v, it is unchanged
     */
    public void addVertex(Vertex v) {
        if (!vertex.contains(v))
        vertex.add(v);
    }

    /**
     * @return
     * if the graph already contains e,
     * or doesn't contain one or both of the vertices connected by e, or both the vertices are the same
     * it is unchanged
     */
    public void addEdge(Edge e) {
        if(!edge.contains(e)
           && !e.getVertex1().equals(e.getVertex2())
           && vertex.contains(e.getVertex1())
           && vertex.contains(e.getVertex2()))
        edge.add(e);
        e.getVertex1().addEdge(e);
        e.getVertex2().addEdge(e);
        }
    
    

    /**
     * @param Vertex v 
     * @return
     * if v was connected to any edges, they are removed
     */
    public void removeVertex(Vertex v) {
        if(vertex.contains(v)){
            ArrayList<Edge> n = new ArrayList<>(v.getNeighbour());
            for (Edge e : n)
                removeEdge(e);
            vertex.remove(v);
        }
    }

    /**
     * @param Edge e 
     * @return
     */
    public void removeEdge(Edge e) {
        edge.remove(e);
        e.getVertex1().removeEdge(e);
        e.getVertex2().removeEdge(e);
    }

    public ArrayList<Edge> getEdge() {
        return edge;
    }

    public ArrayList<Vertex> getVertex(){
        return vertex;
    }

    /**
     * @param Entity e
     * @return vertex associated with e (that contains e)
     * returns null if no vertex contains e
     */

    public Vertex getVertex(Entity e){
        
        for (Vertex v : vertex){
            if (e.equals(v.getEntity()))
                return v;
        }
        return null;
    }

}