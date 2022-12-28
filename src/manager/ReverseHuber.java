package src.manager;


import java.util.ArrayList;

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
     * The loss function is calculated as the sum of the weight of the edge in g that are not present in sg if the value of the weight is lesser than a threshold, or as the sume of the squared weight otherwise
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return reverse Huber loss function
     */
    public float lossFunction(Graph g, Graph sg) {
        float errorValue = 0;
        ArrayList<Edge> errorList = new ArrayList<>();
        for(Edge e : g.getEdgeList()){
            if(!sg.getEdgeList().contains(e))
                errorList.add(e);
                
        }
        
        float th = 0.8f*(max(errorList))*100;
        float wt = 0;
        for(Edge e : errorList){
            wt = e.getWeight()*100;
            if(wt <= th){
                    errorValue += wt;
                } else {
                    errorValue += (wt*wt + th*th)/(2*th);
                }
                
        }
        return errorValue;
        //return  (g_sum - sg_sum)/g.getEdgeList().size();
    }

    private float max(ArrayList<Edge> el){
        if(el.size() == 0)
            return -1.0f;
        float max = 0;
        for(Edge e : el)
            if(e.getWeight()> max)
                max = e.getWeight();
        return max;
    }
}
