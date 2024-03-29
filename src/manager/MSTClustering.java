package src.manager;

import java.util.*;

import src.weightedGraph.*;

/**
 * 
 */
public class MSTClustering implements SimplifyGraphStrategy {

    /**
     * Default constructor
     */
    public MSTClustering() {
        this(5,4);
    }
    /**
     * constructor fot test for deciding the params
     * @param n number of partition
     * @param s max entity per partition
     */
    public MSTClustering(int n, int s) {
        this.ms = new MergeSort();
        this.max_entity_per_service = s; //to define
        this.numb_partition = n;
        this.T0 = 1000;
        this.alpha = 10f;
    }

    @Override
    public Graph simplifyGraph(Graph g) {
        Graph sg = Kruskal(g);
        
        ms.sort(sg.getEdgeList(), 0, sg.getEdgeList().size()-1);
        
        int n = 1;
        while(n< numb_partition && !sg.getEdgeList().isEmpty()) {
            sg.removeEdge(sg.getEdgeList().get(0));
            n = depthFirstSearch(sg);
        }
        reAddEdge(g, reduceCluster(sg, this.max_entity_per_service));
        return sg;
    }

    @Override
    /**
     * Optimization algorithm inspired by simulated annealing
     */
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf) {
        float best = Integer.MAX_VALUE;
        Graph result = null;
        Graph best_result = null;
        float delta = 0;
        Double T = 0.;
        Random rand = new Random();
        int last_n = 0;
        int last_s = 0;
        int best_n = 0;
        int best_s = 0;
        int lastPath = 0;
        for(int i = 1; i< 1000; i++){
            T = this.T0/Math.log(i + alpha);
            last_n = numb_partition;
            last_s = max_entity_per_service;
            lastPath = choosePath(rand, g, lastPath);
            result = simplifyGraph(g);
            delta =  lf.lossFunction(g, result) - best;
            if(delta < 0){
                best = lf.lossFunction(g, result);
                best_result = result;
                best_s = max_entity_per_service;
                best_n = numb_partition;
            }
            else{
                if(rand.nextDouble() > Math.pow(Math.E, -(delta/T))){ //at start we accept solution with greater loss value 
                    numb_partition = last_n;
                    max_entity_per_service = last_s ;
                }else
                    lastPath = 0;
                }
        }
        numb_partition = best_n;
        max_entity_per_service = best_s;
        System.out.println("numb partition: "+numb_partition+"; max entity per microservice: "+max_entity_per_service);
        return best_result;
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
    /**
     * function that try to split the connected component with too much vertices inside it.
     * @param sg the simplified graph
     * @param s the number of max vertex per connected component
     * @return the new graph with new connected components
     */
    private Graph reduceCluster(Graph sg, int s){
            HashMap<Vertex, Integer> counter = DFSCounter(sg);
            int value;
            for(Vertex v : counter.keySet())
                if(counter.get(v) > s){
                    //here we have to add all the edge of the connected components
                    ArrayList<Edge> eList = getEdgeListCC(v);

                    while(counter.get(v) > s && eList.size()>0){
                        
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
                                if(e != null){
                                    sg.addEdge(e);
                                }
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
    private int choosePath(Random rand, Graph g, int lastPath){
        ArrayList<Integer> routes = new ArrayList<Integer>();
        for(int i = 0; i<=7;i++){
            routes.add(i);
        }
        
        if(numb_partition >= (g.getVertexList().size()-1)){
            for(int i = 0; i< routes.size();i++)
                if(routes.get(i) == 7 || routes.get(i) == 6 || routes.get(i) == 2) {
                    routes.remove(i);
                    i--;
                }
        }
        if(numb_partition <= 2){
            for(int i = 0; i< routes.size();i++)
                if(routes.get(i) == 0 || routes.get(i) == 3 || routes.get(i) == 5) {
                    routes.remove(i);
                    i--;
                }
        }

        if(max_entity_per_service >= (g.getVertexList().size()-1)){
            for(int i = 0; i< routes.size();i++)
                if(routes.get(i) == 1 || routes.get(i) == 5 || routes.get(i) == 1) {
                    routes.remove(i);
                    i--;
                }
        }

        if(max_entity_per_service <= 2){
            for(int i = 0; i< routes.size();i++)
                if(routes.get(i) == 0 || routes.get(i) == 3 || routes.get(i) == 6){
                    routes.remove(i);
                    i--;
                }
        }

        if(lastPath > 0 && routes.contains(lastPath)){
            return lastPath;
        }
        
        int route = routes.get(rand.nextInt(routes.size()));
        routeSelection(route);
        return route;
    }
    
    void routeSelection(int route){
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
