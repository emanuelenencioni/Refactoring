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
    }

    @Override
    public Graph simplifyGraph(Graph g) {
        
        return null;
    }

    @Override
    public Graph myBestSolution(Graph g, LossFunctionStrategy lf) {
        
        return null;
    }
    
    public ArrayList<Edge> KruskalAlgorithm(ArrayList<Edge>edgeList, ArrayList<Vertex> vertexList) {
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
      

    private Set<Vertex> findSet(ArrayList<Set<Vertex>> vsl, Vertex v){
        for(Set<Vertex> s : vsl){
            if(s.contains(v))
                return s;
        }
        return null;
    }


    class MergeSort {
    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
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
    
        // Main function that sorts arr[l..r] using
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
}