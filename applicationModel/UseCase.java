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

    /**
     * function that add an endpoint object to the list of endpoint
     * @param ep
     */
    public void addEndPoint(EndPoint ep){
        this.endPointList.add(ep);
    }

    /**
     * 
     * @param idx the index of an endpoint
     * @return an endpoint object
     */
    public EndPoint getEndPoint(int idx){
        if(idx > 0 && idx<endPointList.size())
            return this.endPointList.get(idx);
        
        return null;
    }

    /**
     * function that remove an endpoint object from the list
     * @param idx the index of the endpoint object
     * @return the endpount object removed
     */
    public EndPoint removeEndPoint(int idx){
        if(idx > 0 && idx<endPointList.size())
            return this.endPointList.remove(idx);
        
        return null;
    }

    /**
     * 
     * @return the list of endpoint objects
     */
    public ArrayList<EndPoint> getEndpointList(){
        return this.endPointList;
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