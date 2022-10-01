package src.manager;

import java.util.*;

import src.weightedGraph.Graph;
/**
 * 
 */
public class LossStrategy2 implements LossFunctionStrategy {

    /**
     * Default constructor
     */
    public LossStrategy2() {
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