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
    public void testSimplifyWithKruskal(){
        SimplifyWithKruskal swk = new SimplifyWithKruskal();
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

        ArrayList<Edge> el2 = swk.KruskalAlgorithm(el, vl);
        
        assertEquals(true, el2.contains(e1));
        assertEquals(false, el2.contains(e2));
        assertEquals(true, el2.contains(e3));
        assertEquals(true, el2.contains(e4));
        assertEquals(false, el2.contains(e5));
        assertEquals(true, el2.contains(e6));
    }
    
}