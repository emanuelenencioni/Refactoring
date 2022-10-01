package src.applicationModel;

import java.util.*;

/**
 * 
 */
public abstract class ApplicationAbstraction {

    /**
     * Default constructor
     */
    public ApplicationAbstraction(String id, float f) {
        mapper = new HashMap<Type, CoOccurrenceMatrix>();
        this.ID = id;
        this.frequency = f;
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
     * add a coValue to the correct matrix
     * @param Coupling c 
     * 
     */
    protected void addCoValue(Coupling c) {
        if(mapper.size() > 0)
            mapper.get(c.getType()).addCoValue(c);
    }

    /**
     * @param Type type 
     * @param Entity e1 
     * @param Entity e2 
     * @return
     */
    public Float getCoValue(Type type, Entity e1, Entity e2) {
        if(mapper.size() > 0)
            return mapper.get(type).getValue(e1, e2);
        else
            return null;
    }

    /**
     * @param Type type the type of the co-occurrency matrix
     * @return the mapper of the matrix
     */
    public HashMap<Entity, Integer> getCoMapper(Type type) {
        if(mapper.size() > 0)
            return mapper.get(type).getMapper();
        
        else
            return null;
        
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
}