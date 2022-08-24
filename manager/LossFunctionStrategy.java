package manager;

import java.util.*;
import weightedGraph.Graph;

/**
 * 
 */
public interface LossFunctionStrategy {


    /**
     * @param Graph g 
     * @param Graph sg 
     * @return
     */
    public float lossFunction(Graph g, Graph sg);

}