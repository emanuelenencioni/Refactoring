package manager;

import java.util.*;
import SimplifyGraphFactory;

/**
 * 
 */
public class GraphManager {

    /**
     * Default constructor
     */
    public GraphManager() {

        graph = null;
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





    /**
     * @return
     */
    public boolean createGraph() {
        // TODO implement here
        if (matContainer == null)
            return false;
        
        //CREARE tutti i vertici scorrendo la matrice

        //MOLTIPLICARE ogni matrice per il suo peso
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
    public Graph simplifyGraph(void Graph g) {
        // TODO implement here

        if (graphStrategy == null)
            return null;
        

        //INVOCARE simplifyGraph usando la graphStrategy preimpostata


        return null; //RETURN il grafo restituito dalla funzione simplifyGraph
    }

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph findBestSolution(void Graph g, void LossFunctionStrategy lf) {
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
    public float lossFunction(void Graph g, void Graph sg) {
        // TODO implement here
        return 0.0f;
    }

    /**
     * @param SimplifyGraphStrategy sgs 
     * @return
     */
    public void setSimplifyGraphStrategy(void SimplifyGraphStrategy sgs) {
        // TODO implement here
        this.graphStrategy = sgs;

        return null;
    }

    /**
     * @param LossFunctionStrategy lfs 
     * @return
     */
    public void setLossFunctionStrategy(void LossFunctionStrategy lfs) {
        // TODO implement here
        this.lossStrategy = lfs;

        return null;
    }

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph myBestSolution(void Graph g, void LossFunctionStrategy lf) {
        // TODO0 implement here
        return null;
    }

    public void setApplicationAbstraction(ApplicationAbstraction mc){
        
        this.matContainer = mc;
        return null;

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