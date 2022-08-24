package applicationModel;

import java.util.*;


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
                if(!isInMatrix(cl.get(i).getSrcEntity()))
                    mapper.put(cl.get(i).getSrcEntity(), map_index++);
                
                if(!isInMatrix(cl.get(i).getDestEntity()))
                    mapper.put(cl.get(i).getDestEntity(), map_index++);
                

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
     * add a coupling to the co-occurrence matrix
     * @param Entity source the source of the coupling, row
     * @param Entity dest  destination of the coupling, column
     * @param value value of the coupling
     */
    public void addCoValue(Entity source,Entity dest, float value) { //TODO non c'è il controllo del tipo, capire se è ok o no
        int size = cOMatrix.length;
        if(!isInMatrix(source))
            mapper.put(source, size++);
        
        if(!isInMatrix(dest))
            mapper.put(dest, size++);
        if(size > cOMatrix.length){
            float[][] x = cOMatrix.clone();
            
        
            cOMatrix = new float[size][size];
            for(int i = 0; i < x.length; i++){
                for(int j = 0; j < x.length; j++){
                    cOMatrix[i][j] = x[i][j];
                }
            }
        
        }
        cOMatrix[mapper.get(source)][mapper.get(dest)] = value;
    }
    /**
     * add a coupling to the co-occurrence matrix only if they are the same type
     * @param c
     */
    public void addCoValue(Coupling c){
        if(c.getType() == this.type){
            addCoValue(c.getSrcEntity(), c.getDestEntity(), c.getCoOccurrence());
        }
    }

    /**
     * 
     * @param Entity source the source of the coupling, row
     * @param Entity dest destination of the coupling, column
     * @return the co-occurrence value, or null if one of the entity isn't in the co-occurrence matrix
     */
    public Float getValue(Entity source, Entity dest) {
        Integer x = mapper.get(source);
        Integer y = mapper.get(dest);
        if(x == null || y == null)
            return null;
        else
            return cOMatrix[x][y];
    }

    /**
     * @return the mapper of the matrix
     */
    public HashMap<Entity, Integer> getMapper() {
        return mapper;
    }
    /**
     * 
     * @return the type of the matrix
     */
    public Type getType(){
        return type;
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
            if(e.equals(x)){
                c++; //TODO : PERCHE c++? non si potrebbe tronare subito true? *********************************************************************
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