package src.manager;

import src.weightedGraph.*;
import java.util.*;


public class EdgeLoss implements LossFunctionStrategy {

    /**
     * Default constructor
     */
    public EdgeLoss() {
    }

    /**
     * @param Graph g starting graph
     * @param Graph sg simplified version of starting graph
     * @return numeric evaluation of the information lost //TODO
     */
    public float lossFunction(Graph g, Graph sg) {
        float g_sum = 0;
        float sg_sum = 0;
        float psi = 0.5f;
        float loss = 0;

        HashMap<Vertex, Integer> hm = DFSCounter(sg);

        // If the number of connected components is lower, loss value is higher 
        loss += (sg.getVertexList().size()/hm.size())*10; //TODO


        float avg = 0.f;
        for(Vertex v : hm.keySet()){

            avg = avgCCWeight(v);
            if(avg < psi)
                loss += avg*100;
            else
                loss += avg;

        }
        
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

    /**
     * using a depth first search to count the elements in all the connected components
     * @param vl the list of vertex
     * @return an hasmap where the key is a vertex of a connected component, the value is the number of element of that connected component.
     */
    private HashMap<Vertex, Integer> DFSCounter(Graph g)
    {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        HashMap<Vertex, Integer> counter = new HashMap<>();
        HashMap<Vertex, Boolean> counted = new HashMap<>();
        int count = 0;

        for(Vertex v : g.getVertexList()){
            visited.put(v, false);
            counted.put(v, false);
        }
        
        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        for (Vertex v : g.getVertexList())
            if ( !visited.get(v)){
                DFSUtil(v, visited);
                for(Vertex v1 : visited.keySet())
                    if(visited.get(v1) && !counted.get(v1)){
                        count++;
                        counted.put(v1, true);
                    }
                
                counter.put(v, count);
                count = 0;
            }
        return counter;
    }


    /**
     * The real DFS function that iterate on all vertices
     * @param v a vertex
     * @param visited an hashmap that check if that vertex has already been visited
     */
    private void DFSUtil(Vertex v, HashMap<Vertex, Boolean> visited) {
        // Mark the current node as visited and print it
        visited.put(v, true);

        // Recur for all the vertices adjacent to this vertex
        
        for(Edge e : v.getNeighbour())
        {
            if (!visited.get(e.getConnVertex(v)))
                DFSUtil(e.getConnVertex(v),visited);
        }
    }


    private float avgCCWeight(Vertex v){

        ArrayList<Vertex> toVisit = new ArrayList<>();
        ArrayList<Vertex> visited = new ArrayList<>();
        int numEdge = 0;
        float sumWeight = 0.f;

        toVisit.add(v);

        for (Vertex k : toVisit){

            visited.add(k);

            for (Edge e : v.getNeighbour()){

                if(!visited.contains(e.getConnVertex(k))){

                    sumWeight += e.getWeight();
                    numEdge++;
                    toVisit.add(e.getConnVertex(k));

                }

            }

        }

        return sumWeight/((float)numEdge);

    }
}