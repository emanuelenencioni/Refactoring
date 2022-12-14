package src.applicationModel;

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
        super(id, 1);
    }
    /**
     * 
     * @param id the id of the use case
     * @param f the frequency of the use case
     */
    public UseCase(String id, float f) {
        super(id, f);
    }
    /**
     * 
     * @param id the id of the use case
     * @param f the frequency of the use case
     * @param aa list of  End Points
     * 
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
     * automatically creates the matrices
     */
    public UseCase(String id, float f, ArrayList<EndPoint> epl, BuildCoMatStrategy strat){
        this(id,f,epl);
        this.strategy = strat;
        buildMatrices();
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
     * function that set the new strategy to build the matrices
     * @param s the new strategy
     */
    public void setStrategy(BuildCoMatStrategy s){
        this.strategy = s;
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
        checkEndPointList();
        if(endPointList.size() > 0 && strategy != null){
            ArrayList<ApplicationAbstraction> aa = new ArrayList<>();
            for (EndPoint ep : endPointList) {
                aa.add(ep);
            }
            for(CoOccurrenceMatrix coc : strategy.buildCoMat(aa))
                this.mapper.put(coc.getType(), coc);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if(!(obj instanceof UseCase)){
            return false;
        }
        UseCase c = (UseCase) obj;
    
        return this.getCoMapper(Type.CC).equals(c.getCoMapper(Type.CC)) && 
            this.getCoMapper(Type.CQ).equals(c.getCoMapper(Type.CQ)) &&  
            this.getCoMapper(Type.QC).equals(c.getCoMapper(Type.QC)) &&
            this.getCoMapper(Type.QQ).equals(c.getCoMapper(Type.QQ)) &&
            this.getID().equals(c.getID()) && this.getFrequency() == c.getFrequency();
    }

    private void checkEndPointList(){
        for(int i = 0; i < endPointList.size(); i++){
            for(int j = 0; j < endPointList.size(); j++){
                if(j != i && endPointList.get(i).equals(endPointList.get(j))){
                    endPointList.remove(j);
                    j--;
                }
            }
        }
    }

   private ArrayList<EndPoint> endPointList; 
   private BuildCoMatStrategy strategy;
}