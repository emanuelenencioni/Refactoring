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
     * @param e
     * @return
     * @override
     */
    public boolean equals(Object o){
        if (o == this)
            return true;
        
        if(!(o instanceof Entity))
            return false;
        
        Entity e = (Entity) o;
        return this.name.equals(e.name);
    }
    /**
     * 
     */
    private String name;
    




}