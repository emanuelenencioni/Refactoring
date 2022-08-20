package applicationModel;

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
     * @return
     */
    public abstract void buildMatrices();

    /**
     * 
     */
    public abstract void buildMatrices(ArrayList<CoOccurrenceMatrix> m);
        

    /**
     * 
     */
    public abstract void buildCoMat(ArrayList<ApplicationAbstraction> aa);
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
     * 
     */
    private HashMap<Type, CoOccurrenceMatrix> mapper;
    private float frequency;
    private String ID;

}