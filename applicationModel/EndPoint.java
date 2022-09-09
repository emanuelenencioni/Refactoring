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
     * automatically creates the matrices
     */
    public EndPoint(ArrayList<Coupling> cl, String ID, float freq){
        super(ID, freq);
        this.coupList = new ArrayList<Coupling>();
        for(int i = 0; i< cl.size(); i++)
            this.coupList.add(cl.get(i));
        buildMatrices();
    }

    /**
     * Function that build the co occurrence matrices from the coupling list
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
     * function that append a coupling object to the end of the list
     * This method is only usable if user know the type, it is not in the parent class interface
     * @param c the coupling to append
     */
    public void addCoupling(Coupling c){
        coupList.add(c);
    }

    /**
     * function that return a coupling object from the list
     * This method is only usable if user know the type, it is not in the parent class interface
     * @param idx the index of the coupling object in the list
     * @return a coupling object
     */
    public Coupling getCoupling(int idx){
        return coupList.get(idx);
    }

    /**
     * function that delete a coupling from the list giving the idx
     * This method is only usable if user know the type, it is not in the parent class interface
     * @param idx the index of the coupling object to remove in the list
     * @return the coupling object removed
     */
    public Coupling removeCoupling(int idx){
        return coupList.remove(idx);
    }

    /**
     * coupling list 
     */
    private ArrayList<Coupling> coupList; 

}