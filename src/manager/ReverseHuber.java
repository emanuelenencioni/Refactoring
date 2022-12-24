package src.manager;


import src.weightedGraph.*;

/**
 * 
 */
public class ReverseHuber implements LossFunctionStrategy {

    /**
     * Default constructor
     */
    public ReverseHuber() {
    }

    /**
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return numeric evaluation of the information lost between the graphs
     * higher value means more information was lost //TODO
     */
    public float lossFunction(Graph g, Graph sg) {
        float g_sum = 0;
        float sg_sum = 0;
        
        for(Edge e : g.getEdgeList())
            g_sum += e.getWeight()*100;
    
        for(Edge e : sg.getEdgeList())
            sg_sum += e.getWeight()*100;
        double th = 0.2*(g_sum - sg_sum);
        float res = Math.abs(g_sum - sg_sum);
        if(res <= th){
            return res;
        }
        else 
            return (float) ((Math.pow(res,2) + Math.pow(th, 2))/(2*th));
        //return  (g_sum - sg_sum)/g.getEdgeList().size();
    }
}