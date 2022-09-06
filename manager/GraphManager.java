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


    /**
     * Graph resulting from associations between entities in CoOccurrence Matrices
     */
    private Graph graph;

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
    public boolean createGraph() {
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
     * Call simplifyGraph of the Strategy set, passing the Graph as a parameter
     * @return return the simplified graph
     */
    public Graph simplifyGraph() {
        // TODO implement here

        if (graphStrategy == null)
            return null;
        
        //INVOCARE simplifyGraph usando la graphStrategy preimpostata
        return graphStrategy.simplifyGraph(this.graph); //RETURN il grafo restituito dalla funzione simplifyGraph
    }

    /**
     * Find the best Graph cut that minimize the loss value
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return return the Graph that minimize the loss value
     */
    public Graph findBestSolution(LossFunctionStrategy lf) {
        // TODO implement here

        //SETTARE il valore della lossFunction al massimo
        float bestLossValue = 1.0f; // = infinito? o forse uno
        float currentLossValue;
        Graph bestGraph = null;
        Graph currentGraph = new Graph();
        //ITERARE per ogni SimplifyGraphType
        for (SimplifyGraphType t : SimplifyGraphType.values()){

            //GENERARE la SimplifyGraphStrategy con la SimplifyGraphFactory passando il SimplifyGraphType
            SimplifyGraphStrategy s = simplifyGraphFactory.createSimplifyGraphStrategy(t);

            //INVOCARE myBestSolution della ConcreteStrategy (che invocherà il suo simplifyGraph variando i suoi parametri)
            currentGraph = s.myBestSolution(this.graph, lf);

            //CALCOLARE il lossValue utilizzando la lossFunction
            currentLossValue = lossStrategy.lossFunction(this.graph, currentGraph); // TODO : lossStrategy.lossFunction(..) oppure lossFunction(..)?

            //CONFRONTARE il valore ottenuto con il lossValue attuale e sostituirlo in caso fosse migliore
            if (currentLossValue < bestLossValue){ // TODO : eseguire confronto tra float con epsilon...
                bestGraph = currentGraph;
            }

        }

        return bestGraph;
    }

    /**
     * Call the lossFunction of the LossFunctionStrategy set
     * @param Graph g 
     * @param Graph sg 
     * @return return the loss value between GraphManager graph g and an other graph sg
     */
    public float lossFunction(Graph g, Graph sg) { //TODO : forse è meglio eliminare graph g e far fare il confronto sempre con this.graph?
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