package applicationModel;



/**
 * 
 */
public class Entity {

    /**
     * @param the name of the entity
     */
    public Entity(String name) {
        this.name = name;
    }
    /**
     * Default constructor
     */
    public Entity(){
        this.name = "";
    }
    /**
     * copy constructor
     * @param e entity to copy
     */
    public Entity(Entity e){
        this.name = e.name;
    }
    /**
     * 
     * @return the name of the entity
     */
    public String getName(){
        return name;
    }
    
    /**
     * 
     */
    private String name;





}