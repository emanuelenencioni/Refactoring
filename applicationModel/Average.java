package applicationModel;

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
     */
    @Override
    public ArrayList<CoOccurrenceMatrix> buildCoMat(ArrayList<ApplicationAbstraction> aa) {
        ArrayList<CoOccurrenceMatrix> cOMatrixList = new ArrayList<>();
        
        Integer[] idx = new Integer[1];
        for (Type type : Type.values()) {
            ArrayList<Coupling> coupList = new ArrayList<Coupling>();
            for (ApplicationAbstraction app : aa) {
                HashMap<Entity, Integer> mapper = app.getCoMapper(type);
                Entity[] entities = (Entity[]) mapper.keySet().toArray();
                for(int i=0; i < entities.length; i++){
                    for(int j=0; j< entities.length; j++){
                        int idx1 = (i+j)%entities.length;
                        if(idx1!=i){
                            Coupling coupling = new Coupling(entities[i], entities[idx1], type, app.getCoValue(type, entities[i], entities[i+j])); //TODO verificare se bisogna moltiplicare per la frequenza solo quando si hanno più corrispondenze, o sempre.
                            Coupling coupling1 = new Coupling(entities[i+j], entities[i], type, app.getCoValue(type, entities[i+j], entities[i]));
                            if(checkCoupList(coupList, coupling, idx) ){
                                float value = coupling.getCoOccurrence() * app.getFrequency();
                                coupList.get(idx[0]).setCoOccurrence(coupList.get(idx[0]).getCoOccurrence() + value);
                            }
                            else
                                coupList.add(coupling);

                            if(checkCoupList(coupList, coupling1, idx) ){
                                float value = coupling1.getCoOccurrence() * app.getFrequency();
                                coupList.get(idx[0]).setCoOccurrence(coupList.get(idx[0]).getCoOccurrence() + value);
                            }
                            else
                                coupList.add(coupling1);
                        }
                    }
                }
            }
            cOMatrixList.add(new CoOccurrenceMatrix(type, coupList));
        }
        return cOMatrixList;
    }

    private boolean checkCoupList(ArrayList<Coupling> coupList, Coupling coupling, Integer[] idx){
        for(int i = 0; i < coupList.size(); i++)
            if(coupList.get(i).hasSameEntities(coupling)){
                idx[0] = i;
                return true;
            }
        
        return false;
    }
}