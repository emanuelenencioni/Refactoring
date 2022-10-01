package src.manager;


import src.weightedGraph.*;

/**
 * 
 */
public class CutSum implements LossFunctionStrategy {

    /**
     * Default constructor
     */
    public CutSum() {
    }

    /**
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return numeric evaluation of the information lost between the graphs
     * higher value means more information was lost
     */
    public float lossFunction(Graph g, Graph sg) {
        float g_sum = 0;
        float sg_sum = 0;
        for(Edge e : g.getEdgeList())
            g_sum += e.getWeight();
    
        for(Edge e : sg.getEdgeList())
            sg_sum += e.getWeight();
    
        return  (g_sum - sg_sum)/g.getEdgeList().size();
    }
}