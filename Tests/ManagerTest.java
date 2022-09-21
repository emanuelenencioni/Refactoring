package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import manager.*;
import weightedGraph.*;
import applicationModel.EndPoint;
import applicationModel.ApplicationAbstraction;
import applicationModel.Coupling;
import applicationModel.DomainModel;
import applicationModel.Entity;
import applicationModel.Type;

import java.util.*;

public class ManagerTest {
    
    @Test
    public void testGraphManager(){

        GraphManager manager = new GraphManager();

        ArrayList<Entity> enList = new ArrayList<>();
        for (int i=0; i<4; i++){
            enList.add(new Entity("en"+i));
        }
        
        DomainModel dm = new DomainModel(enList);
        //TODO rivedere questa istruzione quando ci sarà un costruttore di graphManager che setta i parametri
        manager.setDomainModel(dm);
        
        
        ArrayList<Coupling> cpList = new ArrayList<>();
        for (Entity r : enList){
            for (Entity c : enList){
                if (!r.equals(c)){

                    switch (r.getName()){
                        case "en0":
                            cpList.add(new Coupling(r, c, Type.CC, 1.f));
                            cpList.add(new Coupling(r, c, Type.CQ, 1.f));
                            cpList.add(new Coupling(r, c, Type.QC, 1.f));
                            cpList.add(new Coupling(r, c, Type.QQ, 1.f));
                            break;

                        case "en1":
                            cpList.add(new Coupling(r, c, Type.CC, 0.5f));
                            cpList.add(new Coupling(r, c, Type.CQ, 0.5f));
                            cpList.add(new Coupling(r, c, Type.QC, 0.5f));
                            cpList.add(new Coupling(r, c, Type.QQ, 0.5f));
                            break;

                        case "en2":
                            cpList.add(new Coupling(r, c, Type.CC, 0.25f));
                            cpList.add(new Coupling(r, c, Type.CQ, 0.25f));
                            cpList.add(new Coupling(r, c, Type.QC, 0.25f));
                            cpList.add(new Coupling(r, c, Type.QQ, 0.25f));
                            break;

                        default: break;                        
                    }

                } 

            } 

        }

        ApplicationAbstraction ep = new EndPoint(cpList, "ep", 1.f);
        ep.buildMatrices();

        manager.setApplicationAbstraction(ep);
        manager.setWeightCC(1.f);
        manager.setWeightCQ(0.75f);
        manager.setWeightQC(0.5f);
        manager.setWeightQQ(0.25f);

        // assertEquals(true, manager.getGraph()); // TODO : createGraph è privato

        Graph g = manager.getGraph();

        assertEquals(4, g.getVertexList().size());
        assertEquals(6, g.getEdgeList().size());

        // Vertex vx = new Vertex(new Entity("en50"));
        Edge e01 = g.getEdge(g.getVertexList().get(0), g.getVertexList().get(1));
        Edge e23 = g.getEdge(g.getVertexList().get(2), g.getVertexList().get(3));
        Edge e03 = g.getEdge(g.getVertexList().get(0), g.getVertexList().get(3));

        /**
        for (Edge e : g.getVertexList().get(0).getNeighbour()){

            if(e.getConnVertex(g.getVertexList().get(0)).equals(g.getVertexList().get(1)))
                e01 = e;

            if(e.getConnVertex(g.getVertexList().get(2)).equals(g.getVertexList().get(3)))
                e23 = e;

            if(e.getConnVertex(g.getVertexList().get(0)).equals(g.getVertexList().get(3)))
                e03 = e;
        }
        */
        
        assertEquals(0.75f, e01.getWeight(), 0.00005);
        assertEquals(0.125f, e23.getWeight(), 0.00005);
        assertEquals(0.5f, e03.getWeight(), 0.00005);
        
    }

    // TODO : Replicare test sopra usando gli altri due costruttori

    @Test
    public void testKruskal(){
        MSTClustering swk = new MSTClustering();
        ArrayList<Vertex> vl = new ArrayList<>();
        ArrayList<Edge> el = new ArrayList<>();
        for(int i = 0; i<5; i++){
            vl.add(new Vertex(new Entity("E"+i)));
        }
        Edge e1 =  new Edge(0.15f, vl.get(0), vl.get(1));
        Edge e2 =  new Edge(0.7f, vl.get(0), vl.get(2));
        Edge e3 = new Edge(0.3f, vl.get(2), vl.get(3));
        Edge e4 = new Edge(0.1f, vl.get(1), vl.get(3));
        Edge e5 = new Edge(0.5f, vl.get(1), vl.get(4));
        Edge e6 = new Edge(0.2f, vl.get(3), vl.get(4));
        el.add(0,e1);
        el.add(1,e2);
        el.add(2,e3);
        el.add(3,e4);
        el.add(4,e5);
        el.add(5,e6);
        Graph g = new Graph();
        for(Vertex v : vl)
            g.addVertex(v);
        for(Edge e : el)
            g.addEdge(e);   
        Graph sg = swk.Kruskal(g);
        
        assertEquals(false, sg.getEdgeList().contains(e1));
        assertEquals(true, sg.getEdgeList().contains(e2));
        assertEquals(true, sg.getEdgeList().contains(e3));
        assertEquals(false, sg.getEdgeList().contains(e4));
        assertEquals(true, sg.getEdgeList().contains(e5));
        assertEquals(true, sg.getEdgeList().contains(e6));
        
        assertEquals(e2, sg.getVertexList().get(0).getNeighbour().get(0));
        assertEquals(1,sg.getVertexList().get(0).getNeighbour().size());

        assertEquals(e2, sg.getVertexList().get(2).getNeighbour().get(0));
        assertEquals(2,sg.getVertexList().get(2).getNeighbour().size());

        assertEquals(e3, sg.getVertexList().get(2).getNeighbour().get(1));
        assertEquals(2,sg.getVertexList().get(2).getNeighbour().size());

        assertEquals(e3, sg.getVertexList().get(3).getNeighbour().get(0));
        assertEquals(2,sg.getVertexList().get(3).getNeighbour().size());

        assertEquals(e5, sg.getVertexList().get(1).getNeighbour().get(0));
        assertEquals(1,sg.getVertexList().get(1).getNeighbour().size());

        assertEquals(e5, sg.getVertexList().get(4).getNeighbour().get(0));
        assertEquals(2,sg.getVertexList().get(4).getNeighbour().size());

        assertEquals(e6, sg.getVertexList().get(3).getNeighbour().get(1));
        assertEquals(2,sg.getVertexList().get(3).getNeighbour().size());

        assertEquals(e6, sg.getVertexList().get(4).getNeighbour().get(1));
        assertEquals(2,sg.getVertexList().get(4).getNeighbour().size());

    }

    @Test
    public void simplifyWithKruskal(){
        MSTClustering swk = new MSTClustering(3, 3);
        ArrayList<Vertex> vl = new ArrayList<>();
        ArrayList<Edge> el = new ArrayList<>();
        for(int i = 0; i<10; i++){
            vl.add(new Vertex(new Entity("E"+i)));
        }
        
        el.add(new Edge(0.15f, vl.get(0), vl.get(1)));
        el.add(new Edge(0.5f, vl.get(0), vl.get(3)));
        el.add(new Edge(0.7f, vl.get(3), vl.get(1)));
        el.add(new Edge(0.3f, vl.get(1), vl.get(4)));
        el.add(new Edge(0.05f, vl.get(1), vl.get(2)));
        el.add(new Edge(0.8f, vl.get(2), vl.get(4)));
        el.add(new Edge(0.9f, vl.get(2), vl.get(5)));
        el.add(new Edge(0.1f, vl.get(3), vl.get(4)));
        el.add(new Edge(0.3f, vl.get(4), vl.get(5)));
        el.add(new Edge(0.01f, vl.get(4), vl.get(8)));
        el.add(new Edge(0.15f, vl.get(5), vl.get(8)));
        el.add(new Edge(0.1f, vl.get(5), vl.get(9)));
        el.add(new Edge(0.5f, vl.get(8), vl.get(9)));
        el.add(new Edge(0.3f, vl.get(7), vl.get(8)));
        el.add(new Edge(0.5f, vl.get(6), vl.get(7)));
        
        for(Vertex v : vl){
            for(Edge e : el){
                if(e.getVertex1().equals(v) || e.getVertex2().equals(v)){
                    v.addEdge(e);
                }
            }
        }

        Graph g = new Graph();
        for(Vertex v : vl){
            g.addVertex(v);
        }
        for(Edge e : el){
            g.addEdge(e);
        }
        Graph sg = swk.simplifyGraph(g);
    
        assertEquals(true, contains(sg, el.get(0)));
        assertEquals(true, contains(sg, el.get(1)));
        assertEquals(true, contains(sg, el.get(2)));
        assertEquals(false, contains(sg, el.get(3)));
        assertEquals(false, contains(sg, el.get(4)));
        assertEquals(true, contains(sg, el.get(5)));
        assertEquals(true, contains(sg, el.get(6)));
        assertEquals(false, contains(sg, el.get(7)));
        assertEquals(true, contains(sg, el.get(8)));
        assertEquals(false, contains(sg, el.get(9)));
        assertEquals(false, contains(sg, el.get(10)));
        assertEquals(false, contains(sg, el.get(11)));
        assertEquals(true, contains(sg, el.get(12)));
        assertEquals(false, contains(sg, el.get(13)));
        assertEquals(true, contains(sg, el.get(14)));
    }
    
    @Test
    public void testLossFunction(){
        MSTClustering swk = new MSTClustering(3, 3);
        ArrayList<Vertex> vl = new ArrayList<>();
        ArrayList<Edge> el = new ArrayList<>();
        for(int i = 0; i<10; i++){
            vl.add(new Vertex(new Entity("E"+i)));
        }
        
        el.add(new Edge(0.15f, vl.get(0), vl.get(1)));
        el.add(new Edge(0.5f, vl.get(0), vl.get(3)));
        el.add(new Edge(0.7f, vl.get(3), vl.get(1)));
        el.add(new Edge(0.3f, vl.get(1), vl.get(4)));
        el.add(new Edge(0.05f, vl.get(1), vl.get(2)));
        el.add(new Edge(0.8f, vl.get(2), vl.get(4)));
        el.add(new Edge(0.9f, vl.get(2), vl.get(5)));
        el.add(new Edge(0.1f, vl.get(3), vl.get(4)));
        el.add(new Edge(0.3f, vl.get(4), vl.get(5)));
        el.add(new Edge(0.01f, vl.get(4), vl.get(8)));
        el.add(new Edge(0.15f, vl.get(5), vl.get(8)));
        el.add(new Edge(0.1f, vl.get(5), vl.get(9)));
        el.add(new Edge(0.5f, vl.get(8), vl.get(9)));
        el.add(new Edge(0.3f, vl.get(7), vl.get(8)));
        el.add(new Edge(0.5f, vl.get(6), vl.get(7)));
        
        for(Vertex v : vl){
            for(Edge e : el){
                if(e.getVertex1().equals(v) || e.getVertex2().equals(v)){
                    v.addEdge(e);
                }
            }
        }

        Graph g = new Graph();
        for(Vertex v : vl){
            g.addVertex(v);
        }
        for(Edge e : el){
            g.addEdge(e);
        }
        Graph sg = swk.simplifyGraph(g);
        LossFunctionStrategy sfs = new LossStrategy1();
        assertEquals(0.067333, sfs.lossFunction(g, sg), 0.0005);
    }

    private boolean contains(Graph g, Edge e){
        for(Edge e1 : g.getEdgeList()){
            if(e1.equals(e)){
                return true;
            }
        }
        return false;
    }
}