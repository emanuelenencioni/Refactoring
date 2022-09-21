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
        super(id, 1);
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
     * automatically build matrices
     */
    public BusinessLogic(String id, ArrayList<UseCase> ucl, BuildCoMatStrategy strat) {
        this(id, ucl);
        this.strategy = strat;
        buildMatrices();
    }


    @Override
    public void buildMatrices() {
        if(useCaseList.size() > 0 && strategy != null){
            ArrayList<ApplicationAbstraction> aa = new ArrayList<>();
            for (UseCase uc : useCaseList) {
                aa.add(uc);
            }
            for(CoOccurrenceMatrix coc : strategy.buildCoMat(aa))
                this.mapper.put(coc.getType(), coc);
        }
    }

    /**
     * function that set the new strategy to build the matrices
     * @param s the new strategy
     */
    public void setStrategy(BuildCoMatStrategy s){
        this.strategy = s;
    }

    private void checkUseCaseList(){
        for
    }

    ArrayList<UseCase> useCaseList;
    BuildCoMatStrategy strategy;
}