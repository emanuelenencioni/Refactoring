package applicationModel;

import java.util.*;

import org.w3c.dom.ranges.Range;

/**
 * 
 */
public class CoOccurrenceMatrix {

    /**
     * @param type the matrix's type of co-occurrence
     */
    public CoOccurrenceMatrix(Type type) {
        mapper = new HashMap<Entity, Integer>();
        this.type = type;
    }
    /**
     * constructor that take all the couping by list.
     * @param type
     * @param cl
     */
    public CoOccurrenceMatrix(Type type, ArrayList<Coupling> cl){
        this(type); 
        int map_index = 0;
        for(int i = 0; i < cl.size(); i++){
            if(cl.get(i).getType() == this.type){ //final check for same type of coupling
                if(!isInMatrix(cl.get(i).getSrcEntity())){
                    mapper.put(cl.get(i).getSrcEntity(), map_index);
                    map_index++;
                }
                if(!isInMatrix(cl.get(i).getDestEntity())){
                    mapper.put(cl.get(i).getDestEntity(), map_index);
                    map_index++;
                }

            }
        }
        this.cOMatrix = new float[mapper.size()][mapper.size()];
        int x,y;
        for(int i = 0; i < cl.size(); i++){
            if(cl.get(i).getType() == this.type){
                x = mapper.get(cl.get(i).getSrcEntity());
                y = mapper.get(cl.get(i).getDestEntity());
                cOMatrix[x][y] = cl.get(i).getCoOccurrence();
            }
        }
    }
    /**
     * @param Entity e1 
     * @param Entity e2 
     * @param value
     */
    public void addCoValue(void Entity e1, void Entity e2, void value) {
        
    }

    /**
     * @param Entity e1 
     * @param Entity e2 
     * @return
     */
    public float getValue(void Entity e1, void Entity e2) {
        // TODO implement here
        return 0.0f;
    }

    /**
     * @return
     */
    public HashMap getMapper() {
        // TODO implement here
        return null;
    }
    /**
     * function that check if an entity is already in the matrix, 
     * @param e entity to check
     * @return true if the entity is in the matrix, otherwise false
     */
    private Boolean isInMatrix(Entity e){
        Iterator<Entity> it = this.mapper.keySet().iterator();
        int c = 0;
        while(it.hasNext()){
            Entity x = it.next();
            if(e == x){
                c++;
            }
        }
        if(c == 0)
            return false;
        else 
            return true;
    }

    private HashMap<Entity, Integer> mapper;
    private float[][] cOMatrix;
    private Type type;
}