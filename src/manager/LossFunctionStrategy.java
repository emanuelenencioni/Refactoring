package src.manager;

import src.weightedGraph.Graph;

/**
 * 
 */
public interface LossFunctionStrategy {


    /**
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return numeric evaluation of the information lost between the graphs
     * higher value means more information was lost
     */
    public float lossFunction(Graph g, Graph sg);

}