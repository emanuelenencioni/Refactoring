package applicationModel;

import java.util.*;

/**
 * 
 */
public class BusinessLogic extends ApplicationAbstraction {

    /**
     * 
     * @param id the id of the Business Logic
     */
    public BusinessLogic(String id) {
        super(id, 1, null);
    }

    /**
     * 
     * @param id the id of the Business Logic
     * @param ucl the array
     */
    public BusinessLogic(String id, ArrayList<UseCase> ucl) {
        this(id);
        for(int i = 0; i< ucl.size(); i++)
            this.useCaseList.add(ucl.get(i));
    }

    /**
     * 
     * @param id
     * @param ucl
     * @param strat
     */
    public BusinessLogic(String id, ArrayList<UseCase> ucl, BuildCoMatStrategy strat) {
        this(id, ucl);
        this.strategy = strat;
    }


    @Override
    public void buildMatrices() {
        ArrayList<ApplicationAbstraction> aa = new ArrayList<>();
        for (UseCase uc : useCaseList) {
            aa.add(uc);
        }
        ArrayList<CoOccurrenceMatrix>  list = buildCoMat(aa);
        for(CoOccurrenceMatrix coc : list)
            this.mapper.put(coc.getType(), coc);
    }
   
    @Override
    protected ArrayList<CoOccurrenceMatrix> buildCoMat(ArrayList<ApplicationAbstraction> aa){ //TODO serve davvero?
            return strategy.buildCoMat(aa);
    }

    ArrayList<UseCase> useCaseList;
}