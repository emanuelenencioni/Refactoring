package applicationModel;



/**
 * 
 */
public class Coupling {

    /**
     * @param e1 the first entity in the coupling (is directional)
     * @param e2 the second entity in the coupling
     * @param type param that show what type of Coupling is, selected from 4 different enum: CC,CQ,QC,QQ
     * @param coc the actual value of the coupling
     */
    public Coupling(Entity e1, Entity e2, Type type, float coc) {
        this.type = type;
        this.coOccurrence = coc;
        this.entity1 = e1;
        this.entity2 = e2;
    }

    /**
     * 
     * @return the type of the coupling
     */
    public Type getType(){
        return type;
    }

    /**
     * 
     * @return the value of the coupling
     */
    public float getCoOccurrence(){
        return coOccurrence;
    }

    /**
     * 
     * @return the coupling's source entity
     */
    public Entity getSrcEntity(){
        return entity1;
    }

    /**
     * 
     * @return the coupling's destination entity
     */
    public Entity getDestEntity(){
        return entity2;
    }

    /**
     * 
     * @param t new type of the coupling
     */
    public void setType(Type t){
        this.type = t;
    }
    /**
     * 
     * @param coc the new co-occurrence value to set at the coupling
     */
    public void setCoOccurrence(float coc){
        this.coOccurrence = coc;
    }
    /**
     * 
     * @param e the new entity for the coupling's source
     */
    public void setSrcEntity(Entity e){
        entity1 = e;
    }
    /**
     * 
     * @param e the new entity for the coupling's destination
     */
    public void setDestEntity(Entity e){
        entity2 = e;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(!(o instanceof Coupling)){
            return false;
        }
        Coupling c = (Coupling) o;
        
        return c.entity1.equals(this.entity1) 
                && c.entity2.equals(this.entity2) 
                && this.coOccurrence == c.coOccurrence;
    }

    /**
     * function that check if the given coupling and itself have same entities
     * @param e the coupling to check
     * @return true if the two coupling have the same entity, false otherwise
     */
    public boolean hasSameEntities(Coupling e){
        return e.entity1.equals(this.entity1) 
                && e.entity2.equals(this.entity2);
    }

    public boolean hasSameEntitiesAndType(Coupling c){
        return  this.hasSameEntities(c) && this.type == c.type;
    }

    /**
     * 
     */
    private Type type;

    /**
     * 
     */
    private float coOccurrence;
    private Entity entity1;
    private Entity entity2;
}