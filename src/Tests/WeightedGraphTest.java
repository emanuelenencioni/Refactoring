package src.Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import src.applicationModel.Entity;
import src.weightedGraph.*;

import java.util.*;

public class WeightedGraphTest{

    @Test
    public void testVertex(){
        Entity e1 = new Entity("e1");
        Entity e2 = new Entity("e2");

        Vertex v1 = new Vertex(e1);
        Vertex v2 = new Vertex(e2);

        assertEquals(false, v1.equals(v2));
        v1 = new Vertex(e2);
        assertEquals(true, v1.equals(v2));
    }
    

    @Test
    public void testEdge(){

        ArrayList<Entity> enList = new ArrayList<Entity>();
        ArrayList<Vertex> vxList = new ArrayList<Vertex>();
        ArrayList<Edge> edList = new ArrayList<Edge>();

        for(int i=0;i<10;i++){
            enList.add(new Entity("en"+i));
            vxList.add(new Vertex(enList.get(i)));
        }
        for (int i=0; i<5; i++)
            edList.add(new Edge(i+1, vxList.get(i), vxList.get(i+5)));
        
        assertEquals(true, edList.get(0).getConnVertex(vxList.get(0)).equals(vxList.get(5)));
        assertEquals(true, edList.get(0).getConnVertex(vxList.get(5)).equals(vxList.get(0)));
        assertNull(edList.get(0).getConnVertex(vxList.get(1)));

        assertEquals(false, edList.get(0).equals(edList.get(1)));
        Edge e = new Edge(1, vxList.get(5), vxList.get(0));
        assertEquals (true, edList.get(0).equals(e));
        Edge e1 = new Edge (2, vxList.get(5), vxList.get(0));
        assertEquals (false, e.equals(e1));  
    }

    @Test
    public void testGraph(){
    
    Graph g = new Graph();

    ArrayList<Vertex> vxList = new ArrayList<Vertex>();
    ArrayList<Edge> edList = new ArrayList<Edge>();
    
    for(int i=0;i<4;i++){
            vxList.add(new Vertex(new Entity("en"+i)));
            g.addVertex(vxList.get(i));
            
    }

    assertEquals(vxList.get(0), g.getVertex(new Entity ("en0")));
    
    for (int i=0; i<vxList.size(); i++){
        for(int j=0; j<vxList.size(); j++){
            if(i!=j)
                edList.add(new Edge(1, vxList.get(i), vxList.get(j)));
        }
    }

    for (Edge e : edList)
        g.addEdge(e);

    assertEquals(6, g.getEdgeList().size());
    assertEquals(4, g.getVertexList().size());
    

    int eSize = g.getEdgeList().size();
    int vSize = g.getVertexList().size();

    g.addVertex(new Vertex(new Entity("en0")));
    g.addEdge(new Edge(1, vxList.get(1), vxList.get(0)));
    g.addEdge(new Edge(1, vxList.get(0), vxList.get(0)));
    g.addEdge(new Edge(1, vxList.get(0), new Vertex(new Entity("entity"))));

    assertEquals(vSize, g.getVertexList().size());
    assertEquals(eSize, g.getEdgeList().size());

    g.removeEdge(edList.get(0));

    assertEquals(eSize-1, g.getEdgeList().size());
    g.removeVertex(vxList.get(0));

    assertFalse(g.getVertexList().contains(vxList.get(0)));
    assertEquals(eSize-3, g.getEdgeList().size());
      
    }

    



}