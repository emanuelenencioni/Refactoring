package manager;

import java.util.*;
import weightedGraph.*;
import applicationModel.ApplicationAbstraction;
import applicationModel.Entity;
import applicationModel.Type;
import applicationModel.DomainModel;

/**
 * 
 */
public class GraphManager {

    /**
     * Default constructor
     */
    public GraphManager() {

        graph = new Graph();
        matContainer = null;

        //TODO : Corretto impostare a 1?
        weightCC = 1;
        weightCQ = 1;
        weightQC = 1;
        weightQQ = 1;
        
        simplifyGraphFactory = new SimplifyGraphFactory();

        graphStrategy = null;
        lossStrategy = null;

    }

    /**
     * 
     */
    private Graph graph;

    /**
     * 
     */
    private ApplicationAbstraction matContainer;

    /**
     * 
     */
    private float weightCC;

    /**
     * 
     */
    private float weightCQ;

    /**
     * 
     */
    private float weightQC;

    /**
     * 
     */
    private float weightQQ;

    private SimplifyGraphFactory simplifyGraphFactory;

    private SimplifyGraphStrategy graphStrategy;

    private LossFunctionStrategy lossStrategy;

    private DomainModel domainModel;



    /**
     * create graph from the list of Co-Occurrence matrixes given in ApplicationAbstraction matContainer
     * @return true if graph has been created, false if matContainer is not set
     */
    public boolean createGraph() {
        // TODO implement here
        if (matContainer == null)
            return false;
        
        //CREARE tutti i vertici scorrendo la matrice (siccome le matrici hanno tutte le stessee righe e colonne, si prende una matrice qualsiasi, cioè CC)
        HashMap coMapper = matContainer.getCoMapper(Type.CC);
        
        Iterator<Entity> it = coMapper.keySet().iterator();
        while(it.hasNext()){
            Entity x = it.next();
            Vertex v = new Vertex(x);
            graph.addVertex(v);
        }
        //MOLTIPLICARE ogni matrice per il suo peso
        
        ArrayList<Entity> entitiesList = domainModel.getEntities();



        float[][] cOMatrix = new float[entitiesList.size()][entitiesList.size()];

        for (Entity r : entitiesList){

            for (Entity c : entitiesList){

                //matContainer.getCoValue(Type.CC, r, c) + matContainer.getCoValue(Type.CC, c, r) + resto


            }



        }



        //SOMMA CC+CQ+QC+QQ
        //SOMMA valori simmetrici (AB+BA)
        //DIVIDERE per 4 (così si ottiene la media)
        //CREARE gli archi da questi valori ottenuti


        return true;
    }

    /**
     * @param Graph g 
     * @return
     */
    public Graph simplifyGraph(Graph g) { // TODO : eviterei di passargli il grafo.. per il nostro scopo il GraphManager deve solo semplificafre il suo grafo
        // TODO implement here

        if (graphStrategy == null)
            return null;
        
        //INVOCARE simplifyGraph usando la graphStrategy preimpostata
        return graphStrategy.simplifyGraph(g); //RETURN il grafo restituito dalla funzione simplifyGraph
    }

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph findBestSolution(Graph g, LossFunctionStrategy lf) {
        // TODO implement here

        //SETTARE il valore della lossFunction al massimo
        int lossValue; // = infinito
        Graph bestGraph = null;
        //ITERARE per ogni SimplifyGraphType

        //GENERARE la SimplifyGraphStrategy con la SimplifyGraphFactory passando il SimplifyGraphType
        //INVOCARE myBestSolution della ConcreteStrategy (che invocherà il suo simplifyGraph variando i suoi parametri)
        //CALCOLARE il lossValue utilizzando la lossFunction
        //CONFRONTARE il valore ottenuto con il lossValue attuale e sostituirlo in caso fosse migliore
        //Altro?


        return bestGraph;
    }

    /**
     * @param Graph g 
     * @param Graph sg 
     * @return
     */
    public float lossFunction(Graph g, Graph sg) {
        // TODO implement here

        // INVOCARE lossFunction della Strategia di Loss settata e ritornare il suo valore
        return lossStrategy.lossFunction(g, sg);
    }

    /**
     * @param SimplifyGraphStrategy sgs 
     * @return
     */
    public void setSimplifyGraphStrategy(SimplifyGraphStrategy sgs) {
        // TODO implement here
        this.graphStrategy = sgs;

        return;
    }

    /**
     * @param LossFunctionStrategy lfs 
     * @return
     */
    public void setLossFunctionStrategy(LossFunctionStrategy lfs) {
        // TODO implement here
        this.lossStrategy = lfs;

        return;
    }

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf) {
        // TODO0 implement here
        return null;
    }

    public void setApplicationAbstraction(ApplicationAbstraction mc){
        
        this.matContainer = mc;
        return;

    }

    public void setWeightCC(float weightCC){

        this.weightCC = weightCC;

    }
    
    public void setWeightCQ(float weightCQ){

        this.weightCQ = weightCQ;

    }

    public void setWeightQC(float weightQC){

        this.weightQC = weightQC;

    }

    public void setWeightQQ(float weightQQ){

        this.weightQQ = weightQQ;

    }
}