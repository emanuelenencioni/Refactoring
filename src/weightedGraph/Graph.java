package src.weightedGraph;

import java.util.*;
import org.graphstream.graph.implementations.SingleGraph;

import src.applicationModel.Entity;

/**
 * 
 */
public class Graph {

    
    public Graph() {
        vertexList = new ArrayList<Vertex>();
        edgeList = new ArrayList<Edge>();
    }

    public Graph(Graph g){
        this.vertexList = new ArrayList<Vertex>();
        this.edgeList = new ArrayList<Edge>();
        for(int i = 0; i < g.getVertexList().size(); i++){
            addVertex(new Vertex(g.getVertexList().get(i).getEntity()));

        }
        Vertex  v2 = null;
        Vertex v1 = null;
        for(int i = 0; i < g.getEdgeList().size(); i++){
            for(Vertex v : vertexList){
                if(v.equals(g.getEdgeList().get(i).getVertex1()))
                    v1 = v;
                
                if(v.equals(g.getEdgeList().get(i).getVertex2()))
                    v2 = v;
            }
            if(v1 != null && v2 != null)
                addEdge(new Edge(g.getEdgeList().get(i).getWeight(), v1, v2));
        }
    }


    /**
     * @return
     * if the graph already contains v, it is unchanged
     */
    public void addVertex(Vertex v) {
        if (!vertexList.contains(v))
            vertexList.add(v);
    }

    /**
     * @return
     * if the graph already contains e,
     * or doesn't contain one or both of the vertices connected by e, or both the vertices are the same
     * it is unchanged
     */
    public void addEdge(Edge e) {
        if(!edgeList.contains(e)
           && !e.getVertex1().equals(e.getVertex2())
           && vertexList.contains(e.getVertex1())
           && vertexList.contains(e.getVertex2()))
            edgeList.add(e);
            e.getVertex1().addEdge(e);
            e.getVertex2().addEdge(e);
        }
    
    

    /**
     * @param Vertex v 
     * @return
     * if v was connected to any edges, they are removed
     */
    public void removeVertex(Vertex v) {
        if(vertexList.contains(v)){
            ArrayList<Edge> n = new ArrayList<>(v.getNeighbour());
            for (Edge e : n)
                removeEdge(e);
            vertexList.remove(v);
        }
    }

    /**
     * @param Edge e 
     * @return
     */
    public void removeEdge(Edge e) {
        edgeList.remove(e);
        e.getVertex1().removeEdge(e);
        e.getVertex2().removeEdge(e);
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public ArrayList<Vertex> getVertexList(){
        return vertexList;
    }

    /**
     * @param Entity e
     * @return vertex associated with e (that contains e)
     * returns null if no vertex contains e
     */

    public Vertex getVertex(Entity e){
        
        for (Vertex v : vertexList){
            if (e.equals(v.getEntity()))
                return v;
        }
        return null;
    }

    /**
     * @param Vertex v
     * @param Vertex w, order of the parameters does not matter
     * @return edge that connects vertices v and w
     * returns null if v and w are not connected
     */  
    public Edge getEdge(Vertex v, Vertex w){

        for (Edge e : edgeList)
            if (v.equals(e.getVertex1())||v.equals(e.getVertex2()))
                if (e.getConnVertex(v).equals(w))
                return e;
            
        
        return null;

    }

    public void visualizeGraph(){
        org.graphstream.graph.Graph graph = new SingleGraph("");
        
        for(int i = 0; i<vertexList.size();i++){
            graph.addNode(vertexList.get(i).getEntity().getName());
            graph.getNode(i).setAttribute("ui.label", graph.getNode(i).getId());
        }
        for(int i = 0;i<edgeList.size();i++){
            graph.addEdge(edgeList.get(i).getVertex1().getEntity().getName()+edgeList.get(i).getVertex2().getEntity().getName(), edgeList.get(i).getVertex1().getEntity().getName(), edgeList.get(i).getVertex2().getEntity().getName());
            graph.getEdge(i).setAttribute("ui.label", edgeList.get(i).getWeight());
        }
        graph.setAttribute("ui.stylesheet", "node { text-background-mode: rounded-box;  fill-color: black; size: 30px, 35px; text-background-color: black; text-color: white;} edge{text-mode: normal;text-background-mode: rounded-box; text-background-color: yellow;}");
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
    }


    private ArrayList<Vertex> vertexList;

    private ArrayList<Edge> edgeList;

}