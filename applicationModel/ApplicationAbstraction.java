package applicationModel;

import java.util.*;

/**
 * 
 */
public abstract class ApplicationAbstraction {

    /**
     * Default constructor
     */
    public ApplicationAbstraction(String id, float f,BuildCoMatStrategy strategy) {
        mapper = new HashMap<Type, CoOccurrenceMatrix>();
        this.ID = id;
        this.frequency = f;
        this.strategy = strategy;
    }

    /**
     * 
     */
    public abstract void buildMatrices();

    /**
     * 
     * @return the frequency
     */
    public float getFrequency(){
        return this.frequency;
    }

    /**
     * funciton that set the new frequency
     * @param f the new frequency, it should be betwee 0 and 1.
     */
    public void setFrequency(float f){
        this.frequency = f;
    }

    /**
     * 
     * @return the ID of the AppAbstraction
     */
    public String getID(){
        return this.ID;
    }

    /**
     * 
     * @return the hashmap of the matrices
     */
    public HashMap<Type, CoOccurrenceMatrix>getMapper(){
        return mapper;
    }

    /**
     * function that set the new strategy
     * @param strat the new strategy
     */
    public void setStrategy(BuildCoMatStrategy strat){
        this.strategy = strat;
    }

    /**
     * 
     * @return the strategy
     */
    public BuildCoMatStrategy getStrategy(){
        return strategy;
    }
        

    /**
     * 
     */
    protected abstract ArrayList<CoOccurrenceMatrix> buildCoMat(ArrayList<ApplicationAbstraction> aa);
    /**
     * add a coValue to the correct matrix
     * @param Coupling c 
     * 
     */
    protected void addCoValue(Coupling c) {
        mapper.get(c.getType()).addCoValue(c);
    }

    /**
     * @param Type type 
     * @param Entity e1 
     * @param Entity e2 
     * @return
     */
    protected float getCoValue(Type type, Entity e1, Entity e2) {
        return mapper.get(type).getValue(e1, e2);
    }

    /**
     * @param Type type the type of the co-occurrency matrix
     * @return the mapper of the matrix
     */
    public HashMap<Entity, Integer> getCoMapper(Type type) { //TODO : SERVE PUBLIC *******************************************************************
        return mapper.get(type).getMapper();
    }

    /**
     * mapper to map type with the cooccurrence matrix
     */
    protected HashMap<Type, CoOccurrenceMatrix> mapper; // TODO : PERCHE' PROTECTED? SERVE? O SI PUO' METTERE PRIVATE?
    /**
     * frequency of the ApplicationAbstraction
     */
    private float frequency;
    /**
     * 
     */
    private String ID;
    protected BuildCoMatStrategy strategy; //TODO è ok metterlo protected?
}