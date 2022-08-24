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
        entityList = new ArrayList<>();
        for(int i=0;i<es.length;i++){
            entityList.add(es[i]);
        }
    }
    /**
     * Copy constructor
     * @param es array list of entity
     */
    public DomainModel(ArrayList<Entity> es) {
        entityList = new ArrayList<>();
        for(int i=0;i<es.size();i++){
            entityList.add(es.get(i));
        }
    }
    /**
     * Default constructor
     */
    public DomainModel(){
        entityList = new ArrayList<Entity>();
    }

    public void addEntity(Entity e){
        entityList.add(e);
    }
    /**
     * 
     * @param idx the index of the entity in the ArrayList, 
     * @return the Entity or null if the idx is out the range the ArrayList's size
     */
    public Entity getEntity(int idx){
        if (idx < entityList.size() && idx >= 0){
            return entityList.get(idx);
        }
        return  null;
    }
    /**
     * delete the entity given the idx
     * @param idx the index of the entity in the ArrayList
     * @return the entity that has been deleted
     */
    public Entity deleteEntity(int idx){
        if (idx < entityList.size() && idx <= 0){
            return  entityList.remove(idx);
        }
        return null;
    }


    /**
     * get the list of all entities
     * @return the ArrayList of all entities
     */
    public ArrayList<Entity> getEntityList(){

        return entityList;
    }

    private ArrayList<Entity> entityList;

}