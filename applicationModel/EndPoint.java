package applicationModel;

import java.util.*;

/**
 * 
 */
public class EndPoint extends ApplicationAbstraction {

    /**
     * 
     * @param cl the arrayList of Couping
     * @param ID
     * @param freq
     */
    public EndPoint(ArrayList<Coupling> cl, String ID, float freq){
        super(ID, freq, null);
        this.coupList = new ArrayList<Coupling>();
        for(int i = 0; i< cl.size(); i++)
            this.coupList.add(cl.get(i));
        
    }

    /**
     * Function that build the matrices from the coupling list
     */
    @Override
    public void buildMatrices() {
        for (Type type : Type.values()) {
            ArrayList<Coupling> cl = new ArrayList<Coupling>();
            for(int i = 0; i < coupList.size(); i++){
                if(coupList.get(i).getType() == type){
                    cl.add(coupList.get(i));
                }
                mapper.put(type, new CoOccurrenceMatrix(type, cl));
            }
        }
    }

    /**
     * function that add a Couping object to the list of the endpoint
     * @param Coupling , the object to add
     */
    public void addCoupling(Coupling c) {
        coupList.add(c);
    }
    /**
     * 
     * @param idx index of the coupling to get
     * @return the Coupling object at the index requested
     */
    public Coupling getCoupling(int idx){
        return coupList.get(idx);
    }
    @Override
    protected void buildMatrices(ArrayList<CoOccurrenceMatrix> m) {
        //TODO a che serve questo metodo?
    }

    @Override
    protected void buildCoMat(ArrayList<ApplicationAbstraction> aa) {}
    /**
     * coupling list 
     */
    private ArrayList<Coupling> coupList; 

}