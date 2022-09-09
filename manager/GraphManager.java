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

        weightCC = 1;
        weightCQ = 1;
        weightQC = 1;
        weightQQ = 1;
        
        simplifyGraphFactory = new SimplifyGraphFactory();

        graphStrategy = null;
        lossStrategy = null;

    }

    // TODO : FARE COSTRUTTORE CON TUTTI GLI ATTRIBUTI IN INGRESSO

    // TODO : FARE UN COSTRUTTORE CHE PRENDE IN INGRESSO UN GRAFO


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
     * Create graph from the list of Co-Occurrence matrixes given in ApplicationAbstraction matContainer
     * @return true if graph has been created, false if matContainer is not set
     */
    private boolean createGraph() {
        // TODO implement here
        if (matContainer == null)
            return false;
        
        // CREARE tutti i vertici scorrendo la lista delle entità
        ArrayList<Entity> entitiesList = domainModel.getEntityList();

        for (Entity x : entitiesList){

            Vertex v = new Vertex(x);
            graph.addVertex(v);

        }
        
        // CALCOLARE media ponderata dei valori nelle matrici di co-occorrenza per ogni coppia di entità

        int i = 0;
        int j = 0;
        float edgeWeight;
        for (Entity r : entitiesList){

            j = 0;

            for (Entity c : entitiesList){

                if(j > i){ // perché la matrice dev'essere triangolare


                
                edgeWeight = //NUMERATORE
                                (weightCC*(matContainer.getCoValue(Type.CC, r, c) + matContainer.getCoValue(Type.CC, c, r)) + 
                                weightCQ*(matContainer.getCoValue(Type.CQ, r, c) + matContainer.getCoValue(Type.CQ, c, r)) + 
                                weightQC*(matContainer.getCoValue(Type.QC, r, c) + matContainer.getCoValue(Type.QC, c, r)) + 
                                weightQQ*(matContainer.getCoValue(Type.QQ, r, c) + matContainer.getCoValue(Type.QQ, c, r)))/(
                                //DENOMINATORE
                                2*weightCC + 2*weightCQ + 2*weightQC + 2*weightQQ
                                );

                Edge e = new Edge(edgeWeight, graph.getVertex(r), graph.getVertex(c));
                graph.addEdge(e);

                }
                
                j++;

            }

            i++;

        }

        return true;
    }

    /**
     * Find the best Graph cut that minimize the loss value
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return return the Graph that minimize the loss value
     */
    public Float findBestSolution() {
        // TODO implement here

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
            SimplifyGraphStrategy s = simplifyGraphFactory.createSimplifyGraphStrategy(t);

            //INVOCARE myBestSolution della ConcreteStrategy (che invocherà il suo simplifyGraph variando i suoi parametri)
            currentGraph = s.myBestSolution(this.graph, this.lossStrategy);

            //CALCOLARE il lossValue utilizzando la lossFunction
            currentLossValue = lossStrategy.lossFunction(this.graph, currentGraph);

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
     * Simplify the graph using the SimplifyGraphStrategy set and return the loss value of this simplification
     * @param Graph g 
     * @param Graph sg 
     * @return return the loss value between GraphManager graph g and an other graph sg
     */
    public Float simplifyAndComputeLoss() {
        // TODO implement here

        if (this.graphStrategy == null)
            return null;

        if (this.graph == null)
            return null;
        
        //INVOCARE simplifyGraph usando la graphStrategy preimpostata
        this.simplifiedGraph = graphStrategy.simplifyGraph(this.graph); //RETURN il grafo restituito dalla funzione simplifyGraph

        // INVOCARE lossFunction della Strategia di Loss settata e ritornare il suo valore
        return lossStrategy.lossFunction(this.graph, this.simplifiedGraph);
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
    public Float myBestSolution() {
        // TODO implement here

        if (this.graphStrategy == null)
            return null;

        if (this.graph == null)
            return null;

        this.simplifiedGraph = this.graphStrategy.myBestSolution(this.graph, this.lossStrategy);
        return this.lossStrategy.lossFunction(this.graph, this.simplifiedGraph);
    }

    public void setApplicationAbstraction(ApplicationAbstraction mc){
        
        this.matContainer = mc;
        this.createGraph();
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

    public Graph getGraph(){

        return this.graph;

    }

    public void setGraph(Graph g){

        this.graph = g;

    }

    public void setDomainModel(DomainModel domainModel){

        this.domainModel=domainModel;
        
    }

}