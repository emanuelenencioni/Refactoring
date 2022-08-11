package applicationModel;

import java.util.*;

/**
 * 
 */
public abstract class ApplicationAbstraction {

    /**
     * Default constructor
     */
    public ApplicationAbstraction() {
    }

    /**
     * 
     */
    private HashMap<Type CoOccurrenceMatrix> mapper;

    /**
     * 
     */
    private float frequency;

    /**
     * 
     */
    private string ID;




    /**
     * 
     */
    public CoOccurrenceMatrix 4;


    /**
     * @param Coupling c 
     * @return
     */
    public void addCoValue(void Coupling c) {
        // TODO implement here
        return null;
    }

    /**
     * @param Type type 
     * @param Entity e1 
     * @param Entity e2 
     * @return
     */
    public float getCoValue(void Type type, void Entity e1, void Entity e2) {
        // TODO implement here
        return 0.0f;
    }

    /**
     * @param Type type 
     * @return
     */
    public HashMap getCoMapper(void Type type) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public void buildMatrices() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void buildMatrices(ArrayList<CoOccurrenceMatrix> m): void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void buildCoMat(ArrayList<ApplicationAbstraction> aa)() {
        // TODO implement here
    }

}