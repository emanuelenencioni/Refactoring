package weightedGraph;

import java.util.*;
import applicationModel.Entity;

/**
 * 
 */
public class Vertex {

    
    public Vertex(Entity e) {
        entity = e;
        neighbour = new ArrayList<Edge>();
    }

    public Vertex(Vertex v){
        this.entity = v.entity;
        this.neighbour = new ArrayList<Edge>();   
        for(Edge e : v.neighbour)

            this.neighbour.add(new Edge(e));
        this.entity = new Entity(v.entity);
    }

    /**
     * @param Edge e 
     * @return
     */
    public void addEdge(Edge e) {
        if (!neighbour.contains(e))
        neighbour.add(e);
    }

    /**
     * @param Edge e 
     * @return
     */
    public void removeEdge(Edge e) {
        neighbour.remove(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex) {
            Vertex v = (Vertex) obj;
            return v.entity.equals(this.entity);
        }
        return false;
    }

    public ArrayList<Edge> getNeighbour(){
        return neighbour;
    }

    public Entity getEntity(){
        return entity;
    }


    private Entity entity;
    private ArrayList<Edge> neighbour;

}