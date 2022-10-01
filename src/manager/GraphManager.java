package src.manager;

import java.util.*;

import applicationModel.ApplicationAbstraction;
import applicationModel.Entity;
import applicationModel.Type;
import src.weightedGraph.*;
import applicationModel.DomainModel;

/**
 * 
 */
public class GraphManager {

    /**
     * Default constructor
     */
    public GraphManager() {

        this.graph = new Graph();
        this.weightCC = 1;
        this.weightCQ = 1;
        this.weightQC = 1;
        this.weightQQ = 1;
        
        this.simplifyGraphFactory = new SimplifyGraphFactory();

        this.matContainer = null;
        this.graphStrategy = null;
        this.lossStrategy = null;
        this.domainModel = null;
        this.simplifiedGraph = null;

    }


    /**
     * Constructor that receives all attributes
     */
    public GraphManager(ApplicationAbstraction mc, float wCC, float wCQ, float wQC, float wQQ, SimplifyGraphStrategy sgs, LossFunctionStrategy lfs, DomainModel dm){

        this.domainModel = dm;
        this.matContainer = mc;
        this.weightCC = wCC;
        this.weightCQ = wCQ;
        this.weightQC = wQC;
        this.weightQQ = wQQ;
        this.graphStrategy = sgs;
        this.lossStrategy = lfs;

        if(!createGraph()){
            this.graph = null; // TODO : togliere boolean?
        }

        this.simplifyGraphFactory = new SimplifyGraphFactory();

        this.simplifiedGraph = null;

    }

    /**
     * Constructor that receives a graph
     */
    public GraphManager(Graph g){

        this.graph = g;

        this.matContainer = null;
        this.graphStrategy = null;
        this.lossStrategy = null;
        this.domainModel = null;
        this.simplifiedGraph = null;

        this.weightCC = 1;
        this.weightCQ = 1;
        this.weightQC = 1;
        this.weightQQ = 1;
        
        this.simplifyGraphFactory = new SimplifyGraphFactory();

    }


    /**
     * Graph resulting from associations between entities in CoOccurrence Matrices
     */
    private Graph graph;

    /**
     * Here is stored the result of the simplification function called (simplifyGraph/myBestSolution/findBestSolution)
     */
    private Graph simplifiedGraph;

    /**
     * Container of the CoOccurrence Matrices used to build the graph
     */
    private ApplicationAbstraction matContainer;

    /**
     * Weight of CoOccurrence Matrix CC
     */
    private float weightCC;

    /**
     * Weight of CoOccurrence Matrix CQ
     */
    private float weightCQ;

    /**
     * Weight of CoOccurrence Matrix QC
     */
    private float weightQC;

    /**
     * Weight of CoOccurrence Matrix QQ
     */
    private float weightQQ;

    /**
     * Factory that produces each SimplifyGraphStetegy
     */
    private SimplifyGraphFactory simplifyGraphFactory;

    /**
     * Strategy used to simplify the Graph
     */
    private SimplifyGraphStrategy graphStrategy;

    /**
     * Streategy used to calculate the Loss Function
     */
    private LossFunctionStrategy lossStrategy;

    /**
     * Domain model of the ApplicationModel
     */
    private DomainModel domainModel;



    /**
     * Create graph from the list of Co-Occurrence matrices given in ApplicationAbstraction matContainer
     * @return true if graph has been created, false if matContainer or domainModel is not set
     */
    private boolean createGraph() {
        
        if (this.matContainer == null)
            return false;

        if (this.domainModel == null)
            return false;
        
        // CREARE tutti i vertici scorrendo la lista delle entità
        ArrayList<Entity> entitiesList = this.domainModel.getEntityList();

        for (Entity x : entitiesList){

            Vertex v = new Vertex(x);
            this.graph.addVertex(v);

        }
        
        // CALCOLARE media ponderata dei valori nelle matrici di co-occorrenza per ogni coppia di entità

        int i = 0;
        int j = 0;
        float edgeWeight;
        for (Entity r : entitiesList){

            j = 0;

            for (Entity c : entitiesList){

                if(j > i){ // perché ad ogni iterazione si prende gli elementi a coppie riga-colonna, colonna-riga


                
                edgeWeight = //NUMERATORE
                                (this.weightCC*(this.matContainer.getCoValue(Type.CC, r, c) + this.matContainer.getCoValue(Type.CC, c, r)) + 
                                this.weightCQ*(this.matContainer.getCoValue(Type.CQ, r, c) + this.matContainer.getCoValue(Type.CQ, c, r)) + 
                                this.weightQC*(this.matContainer.getCoValue(Type.QC, r, c) + this.matContainer.getCoValue(Type.QC, c, r)) + 
                                this.weightQQ*(this.matContainer.getCoValue(Type.QQ, r, c) + this.matContainer.getCoValue(Type.QQ, c, r)))/(
                                //DENOMINATORE
                                2*this.weightCC + 2*this.weightCQ + 2*this.weightQC + 2*this.weightQQ
                                );

                Edge e = new Edge(edgeWeight, this.graph.getVertex(r), this.graph.getVertex(c));
                this.graph.addEdge(e);

                }
                
                j++;

            }

            i++;

        }

        return true;
    }

    /**
     * Find the best Graph cut that minimizes the loss value and save the best graph in simplifiedGraph attribute
     * @return return the loss value of the best graph cut
     */
    public Float findBestSolution() {

        if (this.graphStrategy == null)
            return null;

        if (this.graph == null)
            return null;
        
        //SETTARE il valore della lossFunction al massimo
        float bestLossValue = Float.MAX_VALUE;
        float currentLossValue;
        Graph bestGraph = null;
        Graph currentGraph = new Graph();
        //ITERARE per ogni SimplifyGraphType
        for (SimplifyGraphType t : SimplifyGraphType.values()){

            //GENERARE la SimplifyGraphStrategy con la SimplifyGraphFactory passando il SimplifyGraphType
            SimplifyGraphStrategy s = this.simplifyGraphFactory.createSimplifyGraphStrategy(t);

            //INVOCARE myBestSolution della ConcreteStrategy (che invocherà il suo simplifyGraph variando i suoi parametri)
            currentGraph = s.myBestSolution(this.graph, this.lossStrategy);

            //CALCOLARE il lossValue utilizzando la lossFunction
            currentLossValue = this.lossStrategy.lossFunction(this.graph, currentGraph);

            //CONFRONTARE il valore ottenuto con il lossValue attuale e sostituirlo in caso fosse migliore
            if (Float.compare(currentLossValue, bestLossValue) < 0){
                bestGraph = currentGraph;
                bestLossValue = currentLossValue;
            }

        }

        this.simplifiedGraph = bestGraph;

        return bestLossValue;
    }

    /**
     * Simplify the graph using the SimplifyGraphStrategy set and save the result in simplifiedGraph attribute
     * @return return the loss value of the simplification made
     */
    public Float simplifyAndComputeLoss() {

        if (this.graphStrategy == null)
            return null;

        if (this.graph == null)
            return null;
        
        //INVOCARE simplifyGraph usando la graphStrategy preimpostata
        this.simplifiedGraph = this.graphStrategy.simplifyGraph(this.graph); //RETURN il grafo restituito dalla funzione simplifyGraph

        // INVOCARE lossFunction della Strategia di Loss settata e ritornare il suo valore
        return this.lossStrategy.lossFunction(this.graph, this.simplifiedGraph);
    }

    /**
     * Set the Strategy to simplify the Graph
     * @param SimplifyGraphStrategy sgs 
     */
    public void setSimplifyGraphStrategy(SimplifyGraphStrategy sgs) {

        this.graphStrategy = sgs;

        return;
    }

    /**
     * Set the Strategy to compute the loss value
     * @param LossFunctionStrategy lfs 
     */
    public void setLossFunctionStrategy(LossFunctionStrategy lfs) {

        this.lossStrategy = lfs;

        return;
    }

    /**
     * Calculate the best graph cut for a specific SimplifyGraphStrategy set, varying the internal parameters of the Strategy 
     * and save the best graph in the attribute simplifiedGraph
     * @return the loss value for the best graph cut. Save the best graph in the attribute simplifiedGraph
     */
    public Float myBestSolution() {

        if (this.graphStrategy == null)
            return null;

        if (this.graph == null)
            return null;

        this.simplifiedGraph = this.graphStrategy.myBestSolution(this.graph, this.lossStrategy);
        return this.lossStrategy.lossFunction(this.graph, this.simplifiedGraph);
    }

    /**
     * Set the co-occurrence matrix container
     * @param ApplicationAbstraction mc
     */
    public void setApplicationAbstraction(ApplicationAbstraction mc){
        
        this.matContainer = mc;
        this.createGraph(); // TODO : decidere se mettere createGraph void o boolean
        return;

    }

    /**
     * Set the weight of co-occurrence matrix CC
     * @param float wCC
     */
    public void setWeightCC(float wCC){

        this.weightCC = wCC;

    }
    
    /**
     * Set the weight of co-occurrence matrix CQ
     * @param float wCQ
     */
    public void setWeightCQ(float wCQ){

        this.weightCQ = wCQ;

    }

    /**
     * Set the weight of co-occurrence matrix QC
     * @param float wQC
     */
    public void setWeightQC(float wQC){

        this.weightQC = wQC;

    }

    /**
     * Set the weight of co-occurrence matrix QQ
     * @param float wQQ
     */
    public void setWeightQQ(float wQQ){

        this.weightQQ = wQQ;

    }

    /**
     * Get the GraphManager graph
     * @return the GraphManager graph
     */
    public Graph getGraph(){

        return this.graph;

    }

    /**
     * Set the GraphManager graph
     * @param Graph g
     */
    public void setGraph(Graph g){

        this.graph = g;

    }

    /**
     * Set the DomainModel
     * @param DomainModel dm
     */
    public void setDomainModel(DomainModel dm){

        this.domainModel = dm;
        
    }

}