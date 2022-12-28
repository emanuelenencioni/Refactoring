package src.manager;
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
        float errorValue = 0;
        
        for(Edge e : g.getEdgeList())
            if(!sg.getEdgeList().contains(e))
                errorValue += Math.pow(e.getWeight()*100, 2);
    
        
        return  (float) (errorValue/g.getEdgeList().size());
    }

}
