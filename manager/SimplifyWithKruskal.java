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
        this.max_entity_per_service = 3;
        this.numb_partition = 5;
    }

    @Override
    public Graph simplifyGraph(Graph g) {
        ArrayList<Edge> edgesMST = Kruskal(g.getEdgeList(), g.getVertexList());
        ms.sort(edgesMST, 0, edgesMST.size()-1);
        reverse(edgesMST);
        int n = 1;
        while(n<= numb_partition) {
            Edge e = edgesMST.remove(0);
            e.getVertex1().removeEdge(e);
            e.getVertex2().removeEdge(e);
            n = depthFirstSearch(g.getVertexList());
        }
        return reduceCluster(edgesMST, this.max_entity_per_service);
        
    }

    @Override
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf) {
        
        return null;
    }
    
    public ArrayList<Edge> Kruskal(ArrayList<Edge>edgeList, ArrayList<Vertex> vertexList) {
        // Sorting the edges
        MergeSort ms = new MergeSort();
        ArrayList<Edge> fel = new ArrayList<>();
        ms.sort(edgeList, 0, edgeList.size()-1);
        
        ArrayList<Set<Vertex>> vertexSetList = new ArrayList<>();
        for(Vertex v : vertexList){
            Set<Vertex> set = new HashSet<Vertex>();
            set.add(v);
            vertexSetList.add(set);
        }
        for (Edge e : edgeList) {
           if( findSet(vertexSetList, e.getVertex1()) != findSet(vertexSetList, e.getVertex2()))
                fel.add(e);
                Set<Vertex> x = findSet(vertexSetList, e.getVertex1());
                Set<Vertex> y = findSet(vertexSetList, e.getVertex2());
                vertexSetList.remove(x);
                vertexSetList.remove(y);
                x.addAll(y);
                vertexSetList.add(x);
        }
        
        return fel;
      }

      public ArrayList<Edge> reduceCluster(ArrayList<Edge> el, int s){
            return null;
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
        ArrayList<Edge> x = el;
        for(int i=0; i < el.size();i++){
            el.set(0,x.get(el.size()-i-1));
        }
    }

   
    // The function to do DFS traversal. It uses recursive DFSUtil()
    /**
     * function that compute the DFS traversal of the graph counting connected components
     * @param vl the vertics of the graph
     * @return the number of connected components
     */
    private int depthFirstSearch(ArrayList<Vertex> vl)
    {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        int count = 0;

        for(Vertex v : vl){
            visited.put(v, false);
        }
        
        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        for (Vertex v : vl)
            if ( !visited.get(v))
                DFSUtil(v, visited);
                count +=1;
        return count;
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
}