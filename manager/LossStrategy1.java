package manager;

import java.util.*;
import weightedGraph.Graph;

/**
 * 
 */
public class LossStrategy1 implements LossFunctionStrategy {

    /**
     * Default constructor
     */
    public LossStrategy1() {
    }

    /**
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return numeric evaluation of the information lost between the graphs
     * higher value means more information was lost
     */
    public float lossFunction(Graph g, Graph sg) {
        // TODO implement here
        return 0.0f;
    }

}