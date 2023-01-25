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
     * @return numeric evaluation of the information lost
     */
    public float lossFunction(Graph g, Graph sg) {
        // float g_sum = 0;
        // float sg_sum = 0;
        float psi = 0.5f;
        float loss = 0;
        int numEntity = 0;
        float expected = 0.f;
        int actual = 0;

        HashMap<Vertex, Integer> hm = DFSCounter(sg);


        // x / log (base 2)(x)
        numEntity = sg.getVertexList().size();
        expected = numEntity / (float)(Math.log(numEntity) / Math.log(2));
        actual = hm.size();

        if(actual > expected){
            loss += Math.pow(Math.E, (2*(actual/expected)));
        }else{
            loss += Math.pow(Math.E, (2*(expected/actual)));
        }


        float avg = 0.f;
        for(Vertex v : hm.keySet()){

            avg = avgCCWeight(v);
            if(avg < psi)
                loss += (1-avg)*100;
            else
                loss += 1-avg;

        }
    
        return loss;
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

    /**
     * function that compute the average of the weight in a connected component
     * @param v the vertex of a connected component
     * @return the average weight
     */
    private float avgCCWeight(Vertex v){

        ArrayList<Edge> el = getEdgeListCC(v);
        if(el.size() == 0)
            return 0.0f;

        float sumWeight = 0;    

        for(Edge e : el)
            sumWeight += e.getWeight();

        return sumWeight/((float)el.size());

    }

    /**
     * function that compute the list of the edge of a connected component
     * @param v a vertex of a connected component
     * @return the list of all the edge of the connected component
     */
    private ArrayList<Edge> getEdgeListCC(Vertex v){
        ArrayList<Edge> el = new ArrayList<>();
        ArrayList<Vertex> vl = new ArrayList<>();
        ArrayList<Vertex> visited = new ArrayList<>();
    
        vl.add(v);
        Vertex vx = null;
        while(!vl.isEmpty()){
            vx = vl.remove(0);
            if(!vl.contains(vx))
                for(Edge e : vx.getNeighbour()){
                    if(!el.contains(e)){
                        el.add(e);
                        vl.add(e.getConnVertex(vx));
                    }
                    visited.add(vx);
                }
        }
        return el;
    }
}