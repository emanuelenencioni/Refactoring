
import java.util.*;

import src.applicationModel.*;


/**
 * 
 */
public class mainclass {

    /**
     * Default constructor
     */
    public static void some(Integer a){
        a = a + 1;
    }
    public static void main(String Args[]) {

      InputManager im = new InputManager();

      ArrayList<Entity> result = im.getEntities();

      for (Entity e : result){
        System.out.println(e.getName());
      }

      ArrayList<Coupling> couplingsResult = im.getCouplings(result);

      if (couplingsResult == null){
        System.err.println("Error in coupling");
      }

      Entity x = new Entity("prova");
      DomainModel dm = new DomainModel();
      dm.addEntity(x);
      x = new Entity("bo");
      System.out.println(x.getName());
      Integer z = 10;
      some(z);
      System.out.println(z);

        
    }
    
}