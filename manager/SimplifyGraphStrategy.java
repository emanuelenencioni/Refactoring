package manager;

import java.util.*;

/**
 * 
 */
public interface SimplifyGraphStrategy {


    /**
     * @param Graph g 
     * @return
     */
    public Graph simplifyGraph(void Graph g);

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph myBestSolution(void Graph g, void LossFunctionStrategy lf);

}