
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