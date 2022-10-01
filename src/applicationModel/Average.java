 
package src.applicationModel;

import java.util.*;


/**
 * 
 */
public class Average implements BuildCoMatStrategy {

    /**
     * Default constructor
     */
    public Average() {
    }

    
    /**
     * 
     * function that do the average of the co occurrency matrix's values from a list of ApplicationAbstraction
     */
    @Override
     public ArrayList<CoOccurrenceMatrix> buildCoMat(ArrayList<ApplicationAbstraction> aa) {
        ArrayList<CoOccurrenceMatrix> cOMatrixList = new ArrayList<>();
        
        Integer idx = 0; // index used later when checking if coupling object is in the list
        for (Type type : Type.values()) { //iteraction on the type of matrix
            ArrayList<Coupling> coupList = new ArrayList<Coupling>();
            int counter = 0;
            for (ApplicationAbstraction app : aa) { // iteraction on list of application abstractions
                HashMap<Entity, Integer> mapper = app.getCoMapper(type);
                Entity[] entityList = mapper.keySet().toArray(new Entity[mapper.keySet().size()]);
                if(entityList.length != 0){
                    counter++; //used to do average of the count, if the actual applicationAbstraction has the matrix empty, it will be not considered to the final average.
                }
                for(int i=0; i < entityList.length; i++){ //iteraction of every entity in the actual matrix
                    for(int j=0; j< entityList.length; j++){
                        int idx1 = (i+j)%entityList.length;
                        if(idx1!=i && app.getCoValue(type, entityList[i], entityList[idx1]) != 0.f){
                            Coupling coupling = new Coupling(entityList[i], entityList[idx1], type, app.getCoValue(type, entityList[i], entityList[idx1]) * app.getFrequency());
                            
                            idx = checkCoupList(coupList, coupling); 
                            if(idx >= 0) //check if there is already a coupling in the list with the same entities, if true it sum the value to the previous one
                                coupList.get(idx).setCoOccurrence(coupList.get(idx).getCoOccurrence() + coupling.getCoOccurrence());
                            else
                                coupList.add(coupling);
                        }
                    }
                }
            }
            for(int i = 0; i < coupList.size(); i++)
                coupList.get(i).setCoOccurrence(coupList.get(i).getCoOccurrence()/counter); //actual average calculation
            cOMatrixList.add(new CoOccurrenceMatrix(type, coupList));
        }
        return cOMatrixList;
    }

    /**
     * private function to check if in a list of coupling there is already a coupling with the same entities (with the same order)
     * @param coupList the list 
     * @param coupling the coupling to seek in the list
     * 
     * @return the index of the coupling in the list, otherwise -1
     */
    private Integer checkCoupList(ArrayList<Coupling> coupList, Coupling coupling){
        for(int i = 0; i < coupList.size(); i++)
            if(coupList.get(i).hasSameEntities(coupling)){
                return i;
            }
        return -1;
    }
}