package applicationModel;

import java.util.*;

/**
 * 
 */
public class DomainModel {

    /**
     * @param es Array of entity
     */
    public DomainModel(Entity[] es) {
        entities = new ArrayList<>();
        for(int i=0;i<es.length;i++){
            entities.add(es[i]);
        }
    }
    /**
     * Copy constructor
     * @param es array list of entity
     */
    public DomainModel(ArrayList<Entity> es) {
        entities = new ArrayList<>();
        for(int i=0;i<es.size();i++){
            entities.add(es.get(i));
        }
    }
    /**
     * Default constructor
     */
    public DomainModel(){
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity e){
        entities.add(e);
    }
    /**
     * 
     * @param idx the index of the entity in the ArrayList, 
     * @return the Entity or null if the idx is out the range the ArrayList's size
     */
    public Entity getEntity(int idx){
        if (idx < entities.size() && idx >= 0){
            return entities.get(idx);
        }
        return  null;
    }
    /**
     * delete the entity given the idx
     * @param idx the index of the entity in the ArrayList
     * @return the entity that has been deleted
     */
    public Entity deleteEntity(int idx){
        if (idx < entities.size() && idx <= 0){
            return  entities.remove(idx);
        }
        return null;
    }

    private ArrayList<Entity> entities;

}