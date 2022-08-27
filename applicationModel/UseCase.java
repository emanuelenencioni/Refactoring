package applicationModel;

import java.util.*;

/**
 * 
 */
public class UseCase extends ApplicationAbstraction {

    /**
     * 
     * @param id the id of the use case
     */
    public UseCase(String id){
        super(id, 1, null);
    }
    /**
     * 
     * @param id the id of the use case
     * @param f the frequency of the use case
     */
    public UseCase(String id, float f) {
        super(id, f, null);
    }
    /**
     * 
     * @param id the id of the use case
     * @param f the frequency of the use case
     * @param aa list of  End Points
     */
    public UseCase(String id, float f, ArrayList<EndPoint> epl){
        this(id,f);
        this.endPointList = new ArrayList<EndPoint>();
        for(int i = 0; i< epl.size(); i++)
            this.endPointList.add(epl.get(i));
    }

    /**
     * 
     * @param id the id of the use case
     * @param f the frequency of the use case
     * @param aa list of  End Points
     * @param strat the strategy to build the matrices
     */
    public UseCase(String id, float f,ArrayList<EndPoint> epl, BuildCoMatStrategy strat){
        this(id,f,epl);
        this.strategy = strat;
    }

    @Override
    public void buildMatrices() {
        ArrayList<ApplicationAbstraction> aa = new ArrayList<>();
        for (EndPoint ep : endPointList) {
            aa.add(ep);
        }
        ArrayList<CoOccurrenceMatrix>  list = buildCoMat(aa);
        for(CoOccurrenceMatrix coc : list)
            this.mapper.put(coc.getType(), coc);
    }

    /**
     * 
     */
   @Override
   protected ArrayList<CoOccurrenceMatrix> buildCoMat(ArrayList<ApplicationAbstraction> aa) {//TODO serve davvero?
       return strategy.buildCoMat(aa);
   }

   private ArrayList<EndPoint> endPointList;
}