package manager;

import java.util.*;
import weightedGraph.Graph;

/**
 * 
 */
public interface SimplifyGraphStrategy {


    /**
     * @param Graph g starting graph to be simplified
     * @return simplified version of starting graph
     */
    public Graph simplifyGraph(Graph g);

    /**
     * @param Graph g starting graph to be simplified
     * @param LossFunctionStrategy lf loss function to be used to find the best solution
     * @return simplified version of starting graph
     */
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf);

}