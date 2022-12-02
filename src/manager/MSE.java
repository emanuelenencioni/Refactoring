package src.manager;

import java.util.*;

import src.weightedGraph.Edge;
import src.weightedGraph.Graph;
/**
 * 
 */
public class MSE implements LossFunctionStrategy {

    /**
     * Default constructor
     */
    public MSE() {
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
            g_sum += e.getWeight()*100;
    
        for(Edge e : sg.getEdgeList())
            sg_sum += e.getWeight()*100;
        
        return  (float) (Math.pow((g_sum - sg_sum), 2)/g.getEdgeList().size());
    }

}