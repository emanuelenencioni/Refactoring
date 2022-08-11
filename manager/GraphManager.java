package manager;

import java.util.*;

/**
 * 
 */
public class GraphManager {

    /**
     * Default constructor
     */
    public GraphManager() {
    }

    /**
     * 
     */
    private Graph graph;

    /**
     * 
     */
    private ApplicationAbstraction matContainer;

    /**
     * 
     */
    private float weightCC;

    /**
     * 
     */
    private float weightCQ;

    /**
     * 
     */
    private float weightQC;

    /**
     * 
     */
    private float weightQQ;






    /**
     * @return
     */
    public void createGraph() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    private Graph createDirectedGraph() {
        // TODO implement here
        return null;
    }

    /**
     * @param Graph g 
     * @return
     */
    private Graph createUndirectedGraph(void Graph g) {
        // TODO implement here
        return null;
    }

    /**
     * @param Graph g 
     * @return
     */
    public Graph simplifyGraph(void Graph g) {
        // TODO implement here
        return null;
    }

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph findBestSolution(void Graph g, void LossFunctionStrategy lf) {
        // TODO implement here
        return null;
    }

    /**
     * @param Graph g 
     * @param Graph sg 
     * @return
     */
    public float lossFunction(void Graph g, void Graph sg) {
        // TODO implement here
        return 0.0f;
    }

    /**
     * @param SimplifyGraphStrategy sgs 
     * @return
     */
    public void setSimplifyGraphStrategy(void SimplifyGraphStrategy sgs) {
        // TODO implement here
        return null;
    }

    /**
     * @param LossFunctionStrategy lfs 
     * @return
     */
    public void setLossFunctionStrategy(void LossFunctionStrategy lfs) {
        // TODO implement here
        return null;
    }

    /**
     * @param Graph g 
     * @param LossFunctionStrategy lf 
     * @return
     */
    public Graph myBestSolution(void Graph g, void LossFunctionStrategy lf) {
        // TODO implement here
        return null;
    }

}