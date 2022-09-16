package manager;

import java.util.*;
import weightedGraph.*;

/**
 * 
 */
public class SimplifyWithKruskal implements SimplifyGraphStrategy {

    /**
     * Default constructor
     */
    public SimplifyWithKruskal() {
        this.ms = new MergeSort();
        this.max_entity_per_service = 5; 
        this.numb_partition = 4; //TODO da discutere, varia molto a seconda del progetto, magari mettere un valore che dipende da quante entità si hanno in gioco?
    }
    /**
     * constructor fot test for deciding the params
     * @param n number of partition
     * @param s max entity per partition
     */
    public SimplifyWithKruskal(int n, int s) {
        this.ms = new MergeSort();
        this.max_entity_per_service = s; //to define
        this.numb_partition = n;
    }

    @Override
    public Graph simplifyGraph(Graph g) {
        Graph sg = Kruskal(g);
        
        ms.sort(sg.getEdgeList(), 0, sg.getEdgeList().size()-1);
        
        int n = 1;
        while(n<= numb_partition) {
            sg.removeEdge(sg.getEdgeList().get(0));
            n = depthFirstSearch(sg);
        }
        reAddEdge(g, reduceCluster(sg, this.max_entity_per_service));
        return sg;
    }

    @Override
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf) {
        float best = 1000;
        Graph result = null;
        float delta = 0;
        int random = 0;
        Double T = 0.;
        Random rand = new Random();
        for(int i = 1; i< 1000; i++){
                result = simplifyGraph(g);
                random = rand.nextInt(8);
                T = this.schedule(i);
                choosePath(random);
                delta =  lf.lossFunction(g, result) - best;
                if(delta < 0)
                    best = lf.lossFunction(g, result);

                else if(rand.nextDouble() <= Math.pow(Math.E, delta/T)){
                    best = lf.lossFunction(g, result);
                }
                
        }
        return result;
    }
    
    public Graph Kruskal(Graph g) {
        // Sorting the edges
        MergeSort ms = new MergeSort();
        ArrayList<Edge> fel = new ArrayList<>();
        ms.sort(g.getEdgeList(), 0, g.getEdgeList().size()-1);
        reverse(g.getEdgeList());
        ArrayList<Set<Vertex>> vertexSetList = new ArrayList<>();
        for(Vertex v : g.getVertexList()){
            Set<Vertex> set = new HashSet<Vertex>();
            set.add(v);
            vertexSetList.add(set);
        }
        for (Edge e : g.getEdgeList()) {
           if( findSet(vertexSetList, e.getVertex1()) != findSet(vertexSetList, e.getVertex2()))
                fel.add(e);
                Set<Vertex> x = findSet(vertexSetList, e.getVertex1());
                Set<Vertex> y = findSet(vertexSetList, e.getVertex2());
                vertexSetList.remove(x);
                vertexSetList.remove(y);
                x.addAll(y);
                vertexSetList.add(x);
        }
        Graph sg = new Graph();
        for(Vertex v : g.getVertexList()){
            Vertex v1 = new Vertex(v.getEntity());
            sg.addVertex(v1);
        }
        Vertex v1 = null;
        Vertex v2 = null;
        for(int i = 0; i < fel.size(); i++){
            for(Vertex v : sg.getVertexList()){
                if(v.equals(fel.get(i).getVertex1()))
                    v1 = v;
                
                if(v.equals(fel.get(i).getVertex2()))
                    v2 = v;
            }
            if(v1 != null && v2 != null)
                sg.addEdge(new Edge(fel.get(i).getWeight(), v1, v2));
        }
        return sg;
      }

      private Graph reduceCluster(Graph sg, int s){
            HashMap<Vertex, Integer> counter = DFSCounter(sg);
            int value;
            for(Vertex v : counter.keySet())
                if(counter.get(v) > s){
                    ArrayList<Edge> eList = v.getNeighbour();
                    while(counter.get(v) > s){
                        
                        value = counter.get(v);
                        Edge e = eList.remove(min(eList));  //we delete the less significative edge
                        sg.removeEdge(e);
                        e.getVertex2().removeEdge(e);
                        counter = DFSCounter(sg);
                        if(counter.get(v) == value){
                            sg.addEdge(e);
                        }
                    }
                }
            return sg;
      }

      
      /**
       * look if an item is in a set, if true, return the set that contain the item
       * @param vsl the set of Vertices
       * @param v vertex
       * @return the set of vertices that contain the vertex
       */
    private Set<Vertex> findSet(ArrayList<Set<Vertex>> vsl, Vertex v){
        for(Set<Vertex> s : vsl){
            if(s.contains(v))
                return s;
        }
        return null;
    }

    /**
     * function that reverse items in an arrayList 
     * @param el the arraylist to reverse
     */
    private void reverse(ArrayList<Edge> el){
        ArrayList<Edge> x = new ArrayList<>();
        for(Edge e : el){
            x.add(e);
        }
        for(int i=0; i < el.size();i++){
            el.set(i,x.get(el.size()-i-1));
        }
    }

   
    // The function to do DFS traversal. It uses recursive DFSUtil()
    /**
     * function that compute the DFS traversal of the graph counting connected components
     * @param vl the vertics of the graph
     * @return the number of connected components
     */
    private int depthFirstSearch(Graph g)
    {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        int count = 0;

        for(Vertex v : g.getVertexList()){
            visited.put(v, false);
        }
        
        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        for (Vertex v : g.getVertexList())
            if ( !visited.get(v)){
                DFSUtil(v, visited);
                count ++;
            }
        return count;
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
     * function that re-add the deleted edge from kruskal, mantaining the connected components splitted
     * @param g
     * @param sg
     */
    private void reAddEdge(Graph g, Graph sg){
          // Mark all the vertices as not visited(set as
        // false by default in java)
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        HashMap<Vertex, Boolean> counted = new HashMap<>();

        for(Vertex v : sg.getVertexList()){
            visited.put(v, false);
            counted.put(v, false);
        }
        
        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        for (Vertex v : sg.getVertexList())
            if ( !visited.get(v)){
                DFSUtil(v, visited);
                for(Vertex v1 : visited.keySet())
                    if(visited.get(v1) && !counted.get(v1)){
                        counted.put(v1, true);
                        for(Vertex v2 : visited.keySet())
                            if(!v1.equals(v2) && !counted.get(v2) && visited.get(v2)){
                                Edge e = g.getEdge(v1, v2);
                                sg.addEdge(e);
                                counted.put(v2, true);
                            }
                    }
            }
    }


    private int min(ArrayList<Edge> el){
        float weight = 2;
        int index = -1;

        for(int i = 0; i< el.size(); i++)
            if(el.get(i).getWeight() < weight){
                index = i;
                weight = el.get(i).getWeight();
            }

        return index;
    }

    /**
     * function used to change the value of the hyperparams of the algorithm
     * @param route
     */
    private void choosePath(int route){
        if(route == 0){
            this.max_entity_per_service--;
            this.numb_partition--;
        }
        else if(route == 1){
            this.max_entity_per_service++;
            
        }
        else if(route == 2){
            this.numb_partition++;
        }
        else if(route == 3){
            this.numb_partition--;
        }
        else if(route == 4){
            this.max_entity_per_service--;
        }
        else if(route == 5){
            this.max_entity_per_service++;
            this.numb_partition--;
        }
        else if(route == 6){
            this.max_entity_per_service--;
            this.numb_partition++;
        }
        else if(route == 7){
            this.max_entity_per_service++;
            this.numb_partition++;
        }
    }

    private Double schedule(int t){
        return (this.T0/Math.log(t + alpha));
    }







    class MergeSort {
    // Merges two subarrays of el.
    // First subarray is el[l..m]
    // Second subarray is el[m+1..r]
        void merge(ArrayList<Edge> el, int l, int m, int r) {
            // Find sizes of two subarrays to be merged
            int n1 = m - l + 1;
            int n2 = r - m;
    
            /* Create temp arrays */
            ArrayList<Edge> L = new ArrayList<>();
            ArrayList<Edge> R = new ArrayList<>();
    
            /*Copy data to temp arrays*/
            for (int i=0; i<n1; ++i)
                L.add(el.get(l+i));
            for (int j=0; j<n2; ++j)
                R.add(el.get(m + 1+ j));
    
    
            /* Merge the temp arrays */
    
            // Initial indexes of first and second subarrays
            int i = 0, j = 0;
    
            // Initial index of merged subarray array
            int k = l;
            while (i < n1 && j < n2)
            {
                if (L.get(i).getWeight() <= R.get(j).getWeight()){
                    el.set(k,L.get(i));
                    i++;
                }
                else
                {
                    el.set(k,R.get(j));
                    j++;
                }
                k++;
            }
    
            /* Copy remaining elements of L[] if any */
            while (i < n1)
            {
                el.set(k,L.get(i));
                i++;
                k++;
            }
    
            /* Copy remaining elements of R[] if any */
            while (j < n2)
            {
                el.set(k,R.get(j));
                j++;
                k++;
            }
        }
    
        // Main function that sorts el[l..r] using
        // merge()
        void sort(ArrayList<Edge> el, int l, int r) {
            if (l < r)
            {
                // Find the middle point
                int m = (l+r)/2;
    
                // Sort first and second halves
                sort(el, l, m);
                sort(el , m+1, r);
    
                // Merge the sorted halves
                merge(el, l, m, r);
            }
        }
   
    }





    
    private MergeSort ms;
    private int numb_partition;
    private int max_entity_per_service;
    private int T0;
    private float alpha;
}