package manager;

import java.util.*;
import weightedGraph.Graph;

/**
 * 
 */
public interface SimplifyGraphStrategy {


    /**
     * @param Graph g 
     * @return
     */
    public Graph simplifyGraph(Graph g);

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf);

}