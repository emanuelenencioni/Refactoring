package manager;

import java.util.*;

/**
 * 
 */
public interface LossFunctionStrategy {


    /**
     * @param Graph g 
     * @param Graph sg 
     * @return
     */
    public float lossFunction(void Graph g, void Graph sg);

}