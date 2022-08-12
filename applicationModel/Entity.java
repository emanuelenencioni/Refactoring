package applicationModel;

import java.util.*;

/**
 * 
 */
public class Entity {

    /**
     * Default constructor
     */
    public Entity(String name) {
        this.name = name;
    }

    public String getEntity(){
        return name;
    }

    public void setEntity(String name){
        this.name = name;
    }
    /**
     * 
     */
    private String name;





}