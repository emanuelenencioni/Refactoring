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
     * The loss function is calculated as the sum of the squares of the weights of the edges in g * that are not present in sg, divided by the number of edges in g
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return Mean Square Error value
     */
    public float lossFunction(Graph g, Graph sg) {
        float errorValue = 0;
        
        for(Edge e : g.getEdgeList())
            if(!sg.getEdgeList().contains(e))
                errorValue += Math.pow(e.getWeight()*100, 2);
    
        
        return  (float) (errorValue/g.getEdgeList().size());
    }

}
