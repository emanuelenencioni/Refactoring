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

    public float getFrequency(){
        return this.frequency;
    }
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

    public HashMap<Type, CoOccurrenceMatrix>getMapper(){
        return mapper;
    }
    /**
     * 
     */
    protected abstract void buildMatrices(ArrayList<CoOccurrenceMatrix> m);
        

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
    protected HashMap<Entity, Integer> getCoMapper(Type type) {
        return mapper.get(type).getMapper();
    }

    /**
     * mapper to map type with the cooccurrence matrix
     */
    protected HashMap<Type, CoOccurrenceMatrix> mapper;
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